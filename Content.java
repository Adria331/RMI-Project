
import java.io.Serializable;


public class Content implements Serializable{

	private String filepath;
	private String title;
	private String description;
	private String filename;

	private String ownerName;
	private byte[] file;
	
	public Content(){
		
	}

	public Content(String title, String description, String filename, String filepath) {
		this.title = title;
		this.description = description;
		this.filename = filename;
		this.filepath = filepath;
	}

	public String getTitle(){
		return this.title;
	}

	public String getDescription(){
		return this.description;
	}

	public String getOwnerName(){
		return this.ownerName;
	}

	public byte[] getFile(){
		return this.file;
	}

	public String getFilename(){
		return this.filename;
	}

	public String getfilepath(){
		return this.filepath;
	}

	public void setfilepath(String filepath){
		this.filepath = filepath;
	}

	public void setTitle(String newTitle){
		this.title = newTitle;
	}

	public void setDescription(String newDescription){
		this.description = newDescription;
	}

	public void setOwnerName(String name){
		this.ownerName = name;
	}

	public void setFile(byte[] file){
		this.file = file;
	}

	public void setFilename(String filename){
		this.filename = filename;
	}
}