package ch.gobothegeek.lofidrox.model.entities;

import java.io.Serializable;
import java.util.Objects;

public class FileDescriptorPK implements Serializable {
	private Integer id;

	public FileDescriptorPK() { }

	public FileDescriptorPK(Integer id) {
		this.id = id;
	}

	public Integer getId() { return id; }
	public void setId(Integer id) { this.id = id; }

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		FileDescriptorPK filePK = (FileDescriptorPK) o;
		return Objects.equals(id, filePK.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return "FileDbPK{" + "id=" + id + '}';
	}
}
