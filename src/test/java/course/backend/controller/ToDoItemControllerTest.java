package course.backend.controller;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import course.backend.ToDoApplication;
import course.backend.domain.Status;
import course.backend.domain.ToDoItem;
import course.backend.dto.Response;
import course.backend.service.ToDoItemService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ToDoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ToDoItemControllerTest {
	
	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();
	ObjectMapper mapper = new ObjectMapper();
	
	@MockBean
    private ToDoItemService toDoItemService;

	@Test
	public void getAllItems() throws Exception {
		
		// given
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		List<ToDoItem> items = Arrays.asList(new ToDoItem("123", "test", Status.PENDING));
		String expected = mapper.writeValueAsString(items);
		
        Mockito.when(toDoItemService.getAllItems()).thenReturn(items);
		
        // when
		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/items"),
				HttpMethod.GET, entity, String.class);

		// then
		JSONAssert.assertEquals(expected, response.getBody(), false);
		MatcherAssert.assertThat(response.getStatusCode(), Matchers.equalTo(HttpStatus.OK));
	}
	
	@Test
	public void createItemSuccessfully() throws Exception {
		// given
		headers.add("Content-Type", "application/json");
		ToDoItem item = new ToDoItem("test", Status.PENDING);
		
		String body = mapper.writeValueAsString(item);
		
        HttpEntity<String> entity = new HttpEntity<String>(body, headers);
        
        Response responseExpected = new Response();
        responseExpected.id = "abc-def";
        Mockito.when(toDoItemService.insertItem(item)).thenReturn(responseExpected);
		
        // when
		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/items"),
				HttpMethod.POST, entity, String.class);

		// then
		
		String outputBody = response.getBody();
		Response responseObject  = mapper.readValue(outputBody, Response.class);
		
		Mockito.verify(toDoItemService).insertItem(item);
		MatcherAssert.assertThat(response.getStatusCode(), Matchers.equalTo(HttpStatus.CREATED));
		MatcherAssert.assertThat(responseObject.id, Matchers.equalTo(responseExpected.id));
		MatcherAssert.assertThat(responseObject.errors, Matchers.equalTo(Arrays.asList()));
	}
	
	@Test
	public void createItemReturnsErrors() throws Exception {
		// given
		headers.add("Content-Type", "application/json");
		ToDoItem item = new ToDoItem(null, Status.PENDING);
		
		String body = mapper.writeValueAsString(item);
		
        HttpEntity<String> entity = new HttpEntity<String>(body, headers);
        
        List<String> errors = Arrays.asList("name cannot be blank.");
        Response responseExpected = new Response();
        responseExpected.errors = errors;
        Mockito.when(toDoItemService.insertItem(item)).thenReturn(responseExpected);
		
        // when
		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/items"),
				HttpMethod.POST, entity, String.class);

		// then
		String outputBody = response.getBody();
		Response responseObject  = mapper.readValue(outputBody, Response.class);
		
		Mockito.verify(toDoItemService).insertItem(item);
		MatcherAssert.assertThat(response.getStatusCode(), Matchers.equalTo(HttpStatus.BAD_REQUEST));
		MatcherAssert.assertThat(responseObject.id, Matchers.nullValue());
		MatcherAssert.assertThat(responseObject.errors, Matchers.equalTo(errors));
	}

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

}
