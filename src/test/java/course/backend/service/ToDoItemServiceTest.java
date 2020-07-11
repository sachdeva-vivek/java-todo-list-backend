package course.backend.service;

import java.util.Arrays;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.Mockito;

import course.backend.dao.ToDoItemDao;
import course.backend.domain.Status;
import course.backend.domain.ToDoItem;
import course.backend.dto.Response;

public class ToDoItemServiceTest {
	
	@Test
	public void testInsertSuccess() {
		// given
		ToDoItemServiceImpl toDoItemService = new ToDoItemServiceImpl();
		
		ToDoItemDao toDoItemDao = Mockito.mock(ToDoItemDao.class);
		
		toDoItemService.setToDoItemDao(toDoItemDao);
		
		ToDoItem item = new ToDoItem("test", Status.DONE);
		
		Mockito.when(toDoItemDao.insertItem(item)).thenReturn("abc-def");
		
		// when
		Response response = toDoItemService.insertItem(item);
		
		// then
		Mockito.verify(toDoItemDao).insertItem(item);
		MatcherAssert.assertThat(response.id, Matchers.equalTo("abc-def"));
		MatcherAssert.assertThat(response.errors, Matchers.equalTo(Arrays.asList()));
		
	}

}
