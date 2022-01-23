package sampleTest;

public class ResponseBodyDeserialization {
	
	private String name;
	private int postId;
	private int id;
	private String body;
	private String email;
	
	public ResponseBodyDeserialization() {
		
	}

	public ResponseBodyDeserialization(String name, int postId, int id, String body, String email) {
		super();
		this.name = name;
		this.postId = postId;
		this.id = id;
		this.body = body;
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "ResponseBodyDeserialization [name=" + name + ", postId=" + postId + ", id=" + id + ", body=" + body
				+ ", email=" + email + "]";
	}
	
	
	
	

}
