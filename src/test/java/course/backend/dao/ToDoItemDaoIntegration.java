package course.backend.dao;


import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import course.backend.domain.Status;
import course.backend.domain.ToDoItem;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ToDoItemDaoIntegration {
	
	@Autowired
	private ToDoItemDao toDoItemDao;
	
	@Test
	public void insertRetrieveItems() {
		
		// given
		String randomItemName = RandomStringUtils.random(50, true, true);
		
		String id = UUID.randomUUID().toString();
		
		ToDoItem item = new ToDoItem(id, randomItemName, Status.DONE);
		
		toDoItemDao.insertItem(item);
		
		// when
		List<ToDoItem> items = toDoItemDao.getAllItems();
		
		// then
		boolean foundItem = items.stream().anyMatch(toDoItem -> toDoItem.getName().equals(randomItemName) && toDoItem.getStatus()== Status.DONE);
		
		MatcherAssert.assertThat(foundItem, Matchers.is(true));
		
	}
	
	@Test
	public void updateItem() {
		
		// given
		String randomItemName = RandomStringUtils.random(50, true, true);
		
		ToDoItem item = new ToDoItem(randomItemName, Status.PENDING);
		
		String id = toDoItemDao.insertItem(item);
		
		String newRandomItemName = RandomStringUtils.random(50, true, true);
		item = new ToDoItem(newRandomItemName, Status.DONE);
		
		// when
		toDoItemDao.updateItem(item, id);
		
		// then
		ToDoItem updatedItem = toDoItemDao.get(id);
		
		MatcherAssert.assertThat(updatedItem.getName(), Matchers.equalTo(newRandomItemName));
		MatcherAssert.assertThat(updatedItem.getStatus(), Matchers.equalTo(Status.DONE));
		MatcherAssert.assertThat(updatedItem.getUpdatedAt(), Matchers.notNullValue());
		
	}
	
	@Test
	public void deleteItem() {
		// given
		String randomItemName = RandomStringUtils.random(50, true, true);
		
		String id = UUID.randomUUID().toString();
		
		ToDoItem item = new ToDoItem(id, randomItemName, Status.PENDING);
		
		toDoItemDao.insertItem(item);
		
		// when
		toDoItemDao.deleteItem(id);
		
		// then
		ToDoItem shouldBeNull = toDoItemDao.get(id);
		
		MatcherAssert.assertThat(shouldBeNull, Matchers.nullValue());
	}

}
