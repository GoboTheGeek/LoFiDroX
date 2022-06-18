package ch.gobothegeek.lofidrox.model.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

// Described a user in this application
@Entity
@Table(name = "PUBLIC.PUBLIC.FILEDESCRIPTOR")
@IdClass(FileDescriptorPK.class)
public class FileDescriptor implements Serializable {
	@Id @Column(name = "ID") @GeneratedValue(strategy = GenerationType.IDENTITY) private Integer id; // unique file number
	@Column(name = "NAME") private String name; // filename to show
	@Column(name = "FILEPATH") private String path; // path to access file on disk
	@Column(name = "USERFROM") private String source; // user who sent this file
	@Column(name = "SENTON") @Temporal(TemporalType.TIMESTAMP) private Date sendOn; // timestamp when file was received on server
	@Column(name = "DATATYPE") private String dataType; // type of base64 data

	@OneToMany(fetch = FetchType.EAGER, targetEntity = FileRecipient.class, mappedBy = "file")
	private List<FileRecipient> recipients;

	public FileDescriptor() { }

	public FileDescriptor(Integer id, String name, String path, String source, Date sendOn, String dataType) {
		this.id = id;
		this.name = name;
		this.path = path;
		this.source = source;
		this.sendOn = sendOn;
		this.dataType = dataType;
	}

	public Integer getId() { return id; }
	public void setId(Integer id) { this.id = id; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public String getPath() { return path; }
	public void setPath(String path) { this.path = path; }

	public String getSource() { return source; }
	public void setSource(String source) { this.source = source; }
	public Date getSendOn() { return sendOn; }
	public void setSendOn(Date sendOn) { this.sendOn = sendOn; }

	public String getDataType() { return dataType; }
	public void setDataType(String dataType) { this.dataType = dataType; }

	public List<FileRecipient> getRecipients() { return recipients; }
	public void setRecipients(List<FileRecipient> recipients) { this.recipients = recipients; }

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		FileDescriptor file = (FileDescriptor) o;
		return Objects.equals(path, file.path);
	}

	@Override
	public int hashCode() {
		return Objects.hash(path);
	}

	@Override
	public String toString() {
		return "FileDb{" + "id=" + id + ", name='" + name + '\'' + ", path='" + path + '\'' + ", source='" + source + '\'' + '}';
	}
}
