package course.backend.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import course.backend.domain.ToDoItem;
import course.backend.dto.Response;
import course.backend.service.ToDoItemService;

@RestController
public class ToDoItemController {
	
	 @Resource
	 private ToDoItemService toDoItemService;
	
	 @GetMapping(path = "/items")
	 public ResponseEntity<List<ToDoItem>> items() {
	   return new ResponseEntity<List<ToDoItem>>(toDoItemService.getAllItems(), HttpStatus.OK);
	 }
	
	 @GetMapping(path="/items/{id}")
	 public ResponseEntity<ToDoItem> item(@PathVariable("id") String id) {
		 
		 ToDoItem item = toDoItemService.get(id);
		 if (item != null) {
			 return new ResponseEntity<ToDoItem>(item, HttpStatus.OK);
		 }
		 
		 return new ResponseEntity<ToDoItem>(item, HttpStatus.NOT_FOUND);
	 }
	 
	 @PostMapping(path="/items")
	 public ResponseEntity<Response> createItem( @RequestBody ToDoItem item, UriComponentsBuilder uriComponentsBuilder) {

		 Response response = toDoItemService.insertItem(item);
		 
		 if (response.errors.size() > 0) {
			 return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
		 }
		 
		 UriComponents uriComponents = 
				 uriComponentsBuilder.path("/items/{id}").buildAndExpand(response.id);
		 
		 HttpHeaders headers = new HttpHeaders();
		 headers.setLocation(uriComponents.toUri());
		 
		 return new ResponseEntity<Response>(response, headers, HttpStatus.CREATED);
		 
	 }
	 
	 @PutMapping(path="/items/{id}")
	 public ResponseEntity<List<String>> updateItem(@RequestBody ToDoItem item, @PathVariable("id") String id) {
		 
		 if (toDoItemService.get(id) == null) {
			 return new ResponseEntity<List<String>>(HttpStatus.NOT_FOUND);
		 }
		 
		 List<String> errors = toDoItemService.updateItem(item, id);
		 
		 if (errors.size() > 0) {
			 return new ResponseEntity<List<String>>(errors, HttpStatus.BAD_REQUEST);
		 }
			
		 return new ResponseEntity<List<String>>(HttpStatus.OK);
	 }
	 
	 @DeleteMapping(path="/items/{id}")
	 public ResponseEntity<Void> deleteItem(@PathVariable("id") String id) {

		 toDoItemService.deleteItem(id);
		 
		 return new ResponseEntity<Void>(HttpStatus.OK);
	 }
	 
	 

}
