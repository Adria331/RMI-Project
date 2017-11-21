
import java.io.Serializable;


public class Content implements Serializable{

	private String filepath;
	private String title;
	private String description;
	private String filename;
	private String ownerName;
	private String key;

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

	public String getContentKey(){
		return this.key;
	}

	public String getDescription(){
		return this.description;
	}

	public String getOwnerName(){
		return this.ownerName;
	}

	public String getFilename(){
		return this.filename;
	}

	public String getfilepath(){
		return this.filepath;
	}

	public void setContentKey(String key){
		this.key = key;
	}

	public void setFilepath(String filepath){
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

	public void setFilename(String filename){
		this.filename = filename;
	}
}