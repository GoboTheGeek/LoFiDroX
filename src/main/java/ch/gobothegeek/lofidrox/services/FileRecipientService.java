package ch.gobothegeek.lofidrox.services;

import ch.gobothegeek.lofidrox.model.entities.FileRecipient;
import ch.gobothegeek.lofidrox.repositories.FileDescriptorRepository;
import ch.gobothegeek.lofidrox.repositories.FileRecipientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class FileRecipientService {
	private static final Logger logger = LoggerFactory.getLogger(FileRecipientService.class);

	@Inject private FileDescriptorRepository fileDescriptorRepository;
	@Inject private FileRecipientRepository fileRecipientRepository;

	// add recipients for a file descriptor
	@Transactional(Transactional.TxType.REQUIRED)
	public List<FileRecipient> addRecipients(Integer fileId, String[] users) {
		List<FileRecipient> recipients;
		FileRecipient rec;

		if ((null != fileId) && (null != users) && (0 < users.length)) {
			recipients = new ArrayList<>();
			for (String userTo : users) {
				rec = this.fileRecipientRepository.createLink(fileId, userTo, false);
				recipients.add(rec);
			}
			return recipients;
		}
		logger.error("Missing parameters to add recipients [fileId=" + (null != fileId) + "], [users=" + ((null != users) && (0 < users.length)) + "]");
		return null;
	}

	// return list of files for a specific user
	@Transactional(Transactional.TxType.REQUIRED)
	public List<FileRecipient> listFilesForUser(String owner) {
		return this.fileRecipientRepository.listFilesForUser(owner);
	}

	@Transactional(Transactional.TxType.REQUIRED)
	public FileRecipient findFileByIdAndOwner(Integer id, String owner) {
		Optional<FileRecipient> file;

		file = this.fileRecipientRepository.findFileByIdAndUserTo(id, owner);
		return (file.orElse(null));
	}

	@Transactional(Transactional.TxType.REQUIRED)
	public FileRecipient markRead(FileRecipient recipient) {
		recipient = this.fileRecipientRepository.markRead(recipient);
		return recipient;
	}
}
