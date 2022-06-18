package ch.gobothegeek.lofidrox.model.json.file;

// a json wrapper used to indicate that file has been uploaded to server
public class JsonFileUploaded {
	private String filename;
	private Boolean written;

	public JsonFileUploaded() {
		this.written = false;
	}

	public JsonFileUploaded(String filename) {
		this.filename = filename;
		this.written = false;
	}

	public String getFilename() { return filename; }
	public void setFilename(String filename) { this.filename = filename; }

	public Boolean getWritten() { return written; }
	public void setWritten(Boolean written) { this.written = written; }
}
