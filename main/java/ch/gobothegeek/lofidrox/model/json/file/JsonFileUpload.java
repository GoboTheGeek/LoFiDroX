package ch.gobothegeek.lofidrox.model.json.file;

// a json wrapper used to get an upload file
public class JsonFileUpload {
	private String filename;
	private String[] users;
	private String data;

	public JsonFileUpload() { }

	public JsonFileUpload(String filename, String[] users, String data) {
		this.filename = filename;
		this.users = users;
		this.data = data;
	}

	public String getFilename() { return filename; }
	public void setFilename(String filename) { this.filename = filename; }

	public String[] getUsers() { return users; }
	public void setUsers(String[] users) { this.users = users; }

	public String getData() { return data; }
	public void setData(String data) { this.data = data; }
}
