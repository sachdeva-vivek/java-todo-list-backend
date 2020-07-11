package course.backend.dao;

import java.util.List;

import course.backend.domain.ToDoItem;

public interface ToDoItemDao {
	
	ToDoItem get(String id);
	
	List<ToDoItem> getAllItems();
	
	String insertItem(ToDoItem item);

	void updateItem(ToDoItem item, String id);
	
	void deleteItem(String id);

}
