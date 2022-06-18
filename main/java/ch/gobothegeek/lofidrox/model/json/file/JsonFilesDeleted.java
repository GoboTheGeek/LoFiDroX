package ch.gobothegeek.lofidrox.model.json.file;


// a json wrapper used to return deleted files count
public class JsonFilesDeleted {
	private int deletedCount = -1;

	public JsonFilesDeleted() { }

	public JsonFilesDeleted(int deletedCount) {
		this.deletedCount = deletedCount;
	}

	public int getDeletedCount() { return deletedCount; }
	public void setDeletedCount(int deletedCount) { this.deletedCount = deletedCount; }
}
