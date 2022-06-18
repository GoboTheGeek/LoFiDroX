package ch.gobothegeek.lofidrox.model.json.file;

import java.util.List;

// a json wrapper used to get file ids to delete
public class JsonFilesDelete {
	private List<Integer> files = null;

	public JsonFilesDelete() { }

	public JsonFilesDelete(List<Integer> files) {
		this.files = files;
	}

	public List<Integer> getFiles() { return files; }
	public void setFiles(List<Integer> files) { this.files = files; }
}
