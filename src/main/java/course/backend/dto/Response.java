package course.backend.dto;

import java.util.ArrayList;
import java.util.List;

public class Response {
	
	public List<String> errors = new ArrayList<String>();
	public String id;
	
	public List<String> getErrors() {
		return errors;
	}
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	

}
