package ch.gobothegeek.lofidrox.repositories;

import ch.gobothegeek.lofidrox.model.entities.FileRecipient;
import ch.gobothegeek.lofidrox.model.entities.FileRecipientPK;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryParam;
import org.apache.deltaspike.data.api.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

// repository used to access files recipients table
@ApplicationScoped
@Repository(forEntity = FileRecipient.class)
public abstract class FileRecipientRepository extends LfdRepository<FileRecipient, FileRecipientPK> {
	private final Logger logger = LoggerFactory.getLogger(FileRecipientRepository.class);

	// return recipient by its id
	@Transactional(Transactional.TxType.REQUIRED)
	public abstract Optional<FileRecipient> findById(Integer id);

	// return recipients for a file
	@Transactional(Transactional.TxType.REQUIRED)
	public abstract List<FileRecipient> findAnyByFileId(Integer id);

	// return recipient for a file and a user
	@Transactional(Transactional.TxType.REQUIRED)
	public abstract Optional<FileRecipient> findAnyByUserToAndFileId(String userTo, Integer id);

	// create the required file
	@Transactional(Transactional.TxType.REQUIRED)
	public FileRecipient createLink(Integer fileId, String userTo, Boolean downloaded) {
		FileRecipient file;

		file = new FileRecipient(null, fileId, userTo, downloaded);
		file = this.save(file);
		return file;
	}

	// return list of files for a specific user
	@Transactional(Transactional.TxType.REQUIRED)
	@Query(value = "SELECT rec FROM FileRecipient rec LEFT JOIN rec.file f WHERE rec.userTo = :uto ORDER BY f.name")
	public abstract List<FileRecipient> listFilesForUser(@QueryParam("uto") String userTo);

	// delete specified file
	@Transactional(Transactional.TxType.REQUIRED)
	public void deleteLink(String user, Integer id) {
		Optional<FileRecipient> recipient;

		recipient = this.findAnyByUserToAndFileId(user, id);
		recipient.ifPresent(this::remove);
	}

	// return file for a specific id and user
	@Transactional(Transactional.TxType.REQUIRED)
	@Query(value = "SELECT rec FROM FileRecipient rec LEFT JOIN rec.file file WHERE rec.userTo = :uto AND file.id = :fid")
	public abstract Optional<FileRecipient> findFileByIdAndUserTo(@QueryParam("fid") Integer id, @QueryParam("uto") String userTo);

	// mark file read
	@Transactional(Transactional.TxType.REQUIRED)
	public FileRecipient markRead(FileRecipient recipient) {
		if (null != recipient) {
			recipient.setDownloaded(true);
			recipient = this.save(recipient);
		}
		return recipient;
	}
}
