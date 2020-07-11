package course.backend.service;

import java.util.List;

import course.backend.domain.ToDoItem;
import course.backend.dto.Response;

public interface ToDoItemService {
	
	ToDoItem get(String id);
	
	List<ToDoItem> getAllItems();
	
	Response insertItem(ToDoItem item);
	
	List<String> updateItem(ToDoItem item, String id);
	
	void deleteItem(String id);

}
