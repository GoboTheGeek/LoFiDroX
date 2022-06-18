package ch.gobothegeek.lofidrox.model.json.file;

import java.util.ArrayList;
import java.util.List;

// a json wrapper used to get an upload file
public class JsonFilesList {
	private List<JsonFileInfo> files = null;

	public JsonFilesList() {
		this.files = new ArrayList<>();
	}

	public JsonFilesList(List<JsonFileInfo> files) {
		this.files = files;
	}

	public List<JsonFileInfo> getFiles() { return files; }
	public void setFiles(List<JsonFileInfo> files) { this.files = files; }
}
