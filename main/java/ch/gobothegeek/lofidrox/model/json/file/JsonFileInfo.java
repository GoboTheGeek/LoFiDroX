package ch.gobothegeek.lofidrox.model.json.file;

// a json wrapper used to get an upload file
public class JsonFileInfo {
	private Integer id;
	private String filename;
	private Boolean isNew;
	private String sentBy;
	private String sentOn;

	public JsonFileInfo() { }

	public JsonFileInfo(Integer id, String filename, Boolean isNew, String sentBy, String sentOn) {
		this.id = id;
		this.filename = filename;
		this.isNew = isNew;
		this.sentBy = sentBy;
		this.sentOn = sentOn;
	}

	public Integer getId() { return id; }
	public void setId(Integer id) { this.id = id; }

	public String getFilename() { return filename; }
	public void setFilename(String filename) { this.filename = filename; }

	public Boolean getIsNew() { return isNew; }
	public void setIsNew(Boolean isNew) { isNew = isNew; }

	public String getSentBy() { return sentBy; }
	public void setSentBy(String sentBy) { this.sentBy = sentBy; }

	public String getSentOn() { return sentOn; }
	public void setSentOn(String
			                      sentOn) { this.sentOn = sentOn; }
}
