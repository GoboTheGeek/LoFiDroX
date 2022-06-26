package ch.gobothegeek.lofidrox.repositories;

import ch.gobothegeek.lofidrox.model.entities.FileDescriptor;
import ch.gobothegeek.lofidrox.model.entities.FileDescriptorPK;
import ch.gobothegeek.lofidrox.services.FileDescriptorService;
import org.apache.deltaspike.data.api.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

// repository used to access files table
@ApplicationScoped
@Repository(forEntity = FileDescriptor.class)
public abstract class FileDescriptorRepository extends LfdRepository<FileDescriptor, FileDescriptorPK> {
	private final Logger logger = LoggerFactory.getLogger(FileDescriptorRepository.class);

	@Inject private FileRecipientRepository fileRecipientRepository;
	@Inject private FileDescriptorService fileDescriptorService;

	// return file by its name
	@Transactional(Transactional.TxType.REQUIRED)
	public abstract Optional<FileDescriptor> findById(Integer id);

	@Transactional(Transactional.TxType.REQUIRED)
	public abstract List<FileDescriptor> findByIdIn(List<Integer> ids);

	// create the required file
	@Transactional(Transactional.TxType.REQUIRED)
	public FileDescriptor createFile(String filename, String path, String source, String type) {
		FileDescriptor file;

		file = new FileDescriptor(null, filename, path, source, new Date(), type);
		file = this.save(file);
		return file;
	}

	// update file path
	@Transactional(Transactional.TxType.REQUIRED)
	public FileDescriptor updateFilePathAndType(Integer id, String path, String type) {
		Optional<FileDescriptor> file;

		file = this.findById(id);
		if (file.isPresent()) {
			file.get().setPath(path);
			file.get().setDataType(type);
			this.save(file.get());
			return file.get();
		}
		return null;
	}

	// delete specified file
	@Transactional(Transactional.TxType.REQUIRED)
	public void deleteFile(Integer id) {
		Optional<FileDescriptor> file;

		file = this.findById(id);
		file.ifPresent(this::remove);
	}
}
