package course.backend.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import course.backend.dao.ToDoItemDao;
import course.backend.domain.ToDoItem;
import course.backend.dto.Response;

@Configuration
public class ToDoItemServiceImpl implements ToDoItemService {
	
	@Autowired
	private ToDoItemDao toDoItemDao;
	
	@Override
	public ToDoItem get(String id) {
		return toDoItemDao.get(id);
	}
	
	@Override
	public List<ToDoItem> getAllItems() {
		return toDoItemDao.getAllItems();
	}
	
	@Override
	public Response insertItem(ToDoItem item) {
		Response response = new Response();
		List<String> errors = validateItem(item);
		
		if (errors.size() > 0) {
			response.errors = errors;
			return response;
		}
		
		String id = toDoItemDao.insertItem(item);
		response.id = id;
		return response;
	}
	
	@Override
	public List<String> updateItem(ToDoItem item, String id) {
		
		List<String> errors = validateItem(item);
		
		if (errors.size() > 0) {
			return errors;
		}
		
		toDoItemDao.updateItem(item, id);
		return new ArrayList<String>();
	}
	
	@Override
	public void deleteItem(String id) {
		toDoItemDao.deleteItem(id);
	}
	
	private List<String> validateItem(ToDoItem item) {
		
		List<String> errors = new ArrayList<String>();
		
		if (StringUtils.isBlank(item.getName())) {
			errors.add("item name cannot be blank");
		}
		
		if (item.getStatus() == null) {
			errors.add("item status cannot be null");
		}
		
		return errors;
		
	}

	void setToDoItemDao(ToDoItemDao toDoItemDao) {
		this.toDoItemDao = toDoItemDao;
	}
	
}
