package ch.gobothegeek.lofidrox.services;

import ch.gobothegeek.lofidrox.exceptions.LfdException;
import ch.gobothegeek.lofidrox.model.entities.FileDescriptor;
import ch.gobothegeek.lofidrox.model.entities.FileRecipient;
import ch.gobothegeek.lofidrox.model.json.file.JsonFileDownload;
import ch.gobothegeek.lofidrox.repositories.FileDescriptorRepository;
import ch.gobothegeek.lofidrox.repositories.FileRecipientRepository;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.deltaspike.core.api.config.ConfigResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
@Transactional
public class FileDescriptorService {
	private static final Logger logger = LoggerFactory.getLogger(FileDescriptorService.class);
	private static final String BASE64_DATA = "data:";
	private static final String BASE64_TAG = ";base64,";

	@Inject private FileDescriptorRepository fileDescriptorRepository;
	@Inject private FileRecipientService fileRecipientService;
	@Inject private FileRecipientRepository fileRecipientRepository;

	@Transactional(Transactional.TxType.REQUIRED)
	public FileDescriptor addFile(String filename, String userSrc) {

		if ((null != filename) && (null != userSrc)) {
			return this.fileDescriptorRepository.createFile(filename, null, userSrc, null);
		}
		return null;
	}

	// upload a file to multiples receivers
	@Transactional(Transactional.TxType.REQUIRED)
	public boolean uploadFileToUsers(String[] users, String filename, String base64Data, String userPost) {
		FileDescriptor file;
		String folder;
		File fileOut;
		String base64Head;
		byte[] content;

		if ((null != users) && (0 < users.length) && (null != filename) && (null != base64Data)) {
			// prepare upload: add file name to database
			file = this.addFile(filename, userPost);
			try {
				// compute destination folder name
				folder = ConfigResolver.getPropertyValue("application.storage.base");
				folder = folder.replace("{source}", userPost);
				// compute filename
				fileOut = new File(folder, UUID.randomUUID().toString());
				// ensure this path exists
				FileUtils.forceMkdir(new File(folder));
				// extract base64 header before decoding
				base64Head = base64Data.substring(BASE64_DATA.length(), base64Data.indexOf(BASE64_TAG));
				// get Base64 content
				content = Base64.decodeBase64(base64Data.substring(base64Data.indexOf(BASE64_TAG) + BASE64_TAG.length()));
				FileUtils.writeByteArrayToFile(fileOut, content);
				// update file in DB
				if (!this.updateFilePathAndType(file.getId(), fileOut.getAbsolutePath(), base64Head)) {
					logger.error("Unable to update file [" + file.getId() + "]");
					throw new LfdException("Unable to update file [" + file.getId() + "]");
				}
				return (null != this.fileRecipientService.addRecipients(file.getId(), users));
			} catch (Exception e) {
				System.out.println("Unable to write file [" + filename + "]");
				e.printStackTrace();
				// delete file from DB
				this.deleteFile(file);
			}
		}
		logger.error("Missing parameters to upload file [users: " + ((null != users) && (0 < users.length)) + "], [filename=" + (null != filename) + "], " +
				"[data=" + (null != base64Data) + "]");
		return false;
	}

	@Transactional(Transactional.TxType.REQUIRED)
	public Boolean updateFilePathAndType(Integer fileId, String path, String type) {
		FileDescriptor file;

		if ((null != fileId) && (null != path)) {
			file = this.fileDescriptorRepository.updateFilePathAndType(fileId, path, type);
			return (null != file);
		}
		return Boolean.FALSE;
	}

	@Transactional(Transactional.TxType.REQUIRED)
	public void deleteFile(FileDescriptor file) {
		if (null != file) {
			FileUtils.deleteQuietly(new File(file.getPath()));
			this.fileDescriptorRepository.deleteFile(file.getId());
		}
	}

	@Transactional(Transactional.TxType.REQUIRED)
	public JsonFileDownload downloadFile(Integer fileId, String user) {
		FileRecipient rec;
		JsonFileDownload jsonDL;
		StringBuilder content;

		jsonDL = new JsonFileDownload();
		// start by finding file in database
		rec = this.fileRecipientService.findFileByIdAndOwner(fileId, user);
		if ((null != rec) && null != rec.getFile()) {
			// mark file as read
			this.fileRecipientService.markRead(rec);
			// add filename (without path)
			jsonDL.setFilename(rec.getFile().getName());
			try {
				content = new StringBuilder();
				// add Base64 header
				content.append(BASE64_DATA);
				content.append(rec.getFile().getDataType());
				content.append(BASE64_TAG);
				// add Base64 file content
				content.append(Base64.encodeBase64String(FileUtils.readFileToByteArray(new File(rec.getFile().getPath()))));
				jsonDL.setType(rec.getFile().getDataType());
				jsonDL.setData(content.toString());
				return jsonDL;
			} catch (IOException e) {
				logger.error("Unable to read file [" + rec.getFile().getPath() + "]", e);
				e.printStackTrace();
			}
		} else {
			logger.error("Unable to fetch file with id [" + fileId + "] for user [" + user + "]");
		}
		return null;
	}

	@Transactional(Transactional.TxType.REQUIRED)
	public int deleteFiles(List<Integer> files, String user) {
		List<FileDescriptor> filesDesc;
		List< FileRecipient> recips;

		if ((null != files) && (0 < files.size()) && (null != user)) {
			// find files by id
			filesDesc = this.fileDescriptorRepository.findByIdIn(files);
			if (filesDesc.size() == files.size()) { // we got all required files
				for (FileDescriptor fileDesc : filesDesc) {
					// break link between recipient and file
					this.fileRecipientRepository.deleteLink(user, fileDesc.getId());
					// check if a user hold this file
					recips = this.fileRecipientRepository.findAnyByFileId(fileDesc.getId());
					if ((null == recips) || (0 == recips.size())) {
						// no one hold the file, remove it
						this.deleteFile(fileDesc);
					}
				}
				return filesDesc.size();
			}
		}
		return -1;
	}
}
