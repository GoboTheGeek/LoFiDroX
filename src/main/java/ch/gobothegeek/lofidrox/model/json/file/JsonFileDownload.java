package ch.gobothegeek.lofidrox.model.json.file;

// a json wrapper used to get an download file
public class JsonFileDownload {
	private String filename;
	private String type;
	private String data; // BASE64 with data type (ex: data:image/gif;base64,...)

	public JsonFileDownload() { }

	public JsonFileDownload(String filename, String type, String data) {
		this.filename = filename;
		this.type = type;
		this.data = data;
	}

	public String getFilename() { return filename; }
	public void setFilename(String filename) { this.filename = filename; }

	public String getType() { return type; }
	public void setType(String type) { this.type = type; }

	public String getData() { return data; }
	public void setData(String data) { this.data = data; }
}
