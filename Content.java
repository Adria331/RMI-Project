
public class Content{

	private String filepath;
	private String title;
	private String description;
	
	public Content(String title, String description, String filepath){
		this.title = title;
		this.description = description;
	}

	public String getTitle(){
		return this.title;
	}

	public String getDescription(){
		return this.description;
	}

	public String getId(){
		return this.id;
	}

	public void setTitle(String newTitle){
		this.title = newTitleM;
	}

	public void setDescription(String newDescription){
		this.description = newDescription;
	}
}