package course.backend.dao;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import course.backend.domain.Status;
import course.backend.domain.ToDoItem;

@Configuration
public class ToDoItemDaoImpl implements ToDoItemDao {
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public ToDoItem get(String id) {
		String sql = "select * from item where id = :id";
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		
		return namedParameterJdbcTemplate.query(sql, paramMap, (rs) -> {
			 if (rs.next()) {
		    	ToDoItem item = new ToDoItem(rs.getString("id"), rs.getString("name"), Status.valueOf(rs.getString("status")));
				item.setCreatedAt(rs.getTimestamp("date_created"));
				item.setUpdatedAt(rs.getTimestamp("date_modified"));
				return item;
			 }
			 return null;
		});
		
	}
	
	@Override
	public List<ToDoItem> getAllItems() {
		
		String sql = "select * from item";
		
		List<ToDoItem> items = namedParameterJdbcTemplate.query(sql, (rs, rowNum) -> {
			ToDoItem item = new ToDoItem(rs.getString("id"), rs.getString("name"), Status.valueOf(rs.getString("status")));
			item.setCreatedAt(rs.getTimestamp("date_created"));
			item.setUpdatedAt(rs.getTimestamp("date_modified"));
			return item;
		});
		
		
		return items;
		
	}
	
	@Override
	public String insertItem(ToDoItem item) {
		
		String sql = "insert into item (id, name, status) values (:id, :name, :status)";
		
		String id = UUID.randomUUID().toString();
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		paramMap.put("name", item.getName());
		paramMap.put("status", item.getStatus().name());
		
		namedParameterJdbcTemplate.update(sql, paramMap);
		
		return id;
	}
	
	@Override
	public void updateItem(ToDoItem item, String id) {
		
		String sql = "update item set name = :name, status = :status, date_modified = :dateModified where id = :id";
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("name", item.getName());
		paramMap.put("status", item.getStatus().name());
		paramMap.put("id", id);
		paramMap.put("dateModified", new Timestamp(System.currentTimeMillis()));
		
		namedParameterJdbcTemplate.update(sql, paramMap);
	}
	
	@Override
	public void deleteItem(String id) {
		String sql = "delete from item where id = :id";
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		
		namedParameterJdbcTemplate.update(sql, paramMap);
	}
	

}
