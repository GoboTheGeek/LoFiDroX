package ch.gobothegeek.lofidrox.model.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

// Described a link between a file and a receiver
@Entity
@Table(name = "PUBLIC.PUBLIC.FILERECIPIENT")
@IdClass(FileRecipientPK.class)
public class FileRecipient implements Serializable {
	@Id @Column(name = "ID") @GeneratedValue(strategy = GenerationType.IDENTITY) private Integer id; // unique recipient number
	@Column(name = "FILEID") private Integer fileId; // id of file linked
	@Column(name = "USERTO") private String userTo; // username who can access this file
	@Column(name = "DOWNLOADED") private Boolean downloaded = Boolean.FALSE; // true if user has downloaded file

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FILEID", referencedColumnName = "ID")
	private FileDescriptor file; // file linked

	public FileRecipient() { }

	public FileRecipient(Integer id, Integer fileId, String userTo, Boolean downloaded) {
		this.id = id;
		this.fileId = fileId;
		this.userTo = userTo;
		this.downloaded = downloaded;
	}

	public Integer getId() { return id; }
	public void setId(Integer id) { this.id = id; }

	public Integer getFileId() { return fileId; }
	public void setFileId(Integer fileId) { this.fileId = fileId; }

	public String getUserTo() { return userTo; }
	public void setUserTo(String userTo) { this.userTo = userTo; }

	public Boolean getDownloaded() { return downloaded; }
	public void setDownloaded(Boolean downloaded) { this.downloaded = downloaded; }

	public FileDescriptor getFile() { return file; }
	public void setFile(FileDescriptor file) { this.file = file; }

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		FileRecipient fileRecipient = (FileRecipient) o;
		return Objects.equals(getFileId(), fileRecipient.getFileId()) && Objects.equals(getUserTo(), fileRecipient.getUserTo());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getFileId(), getUserTo());
	}

	@Override
	public String toString() {
		return "FileRecipient{" + "id=" + id + ", fileId=" + fileId + ", userTo='" + userTo + '\'' + ", downloaded=" + downloaded + '}';
	}
}
