
public class Content{

	private String title;
	private String description;
	private String id; // clau per guardarlo

	public Content(String title, String description){
		this.title = title;
		this.description = description;
		this.id = "";
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
		this.title = newTitleM
	}

	public void setDescription(String newDescription){
		this.description = newDescription;
	}
}