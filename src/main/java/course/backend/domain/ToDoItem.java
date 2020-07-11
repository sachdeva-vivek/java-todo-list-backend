package course.backend.domain;

import java.sql.Timestamp;
import java.util.Objects;

public class ToDoItem {
	
	private String id;
	private String name;
	private Status status;
	
	private Timestamp createdAt;
	private Timestamp updatedAt;
	
	public ToDoItem() {}
	
	public ToDoItem(String name, Status status) {
		this.name = name;
		this.status = status;
	}
	
	public ToDoItem(String id, String name, Status status) {
		this(name, status);
		this.id = id;
		
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	@Override
	public String toString() {
		return "ToDoItem [id=" + id + ", name=" + name + ", status=" + status + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(createdAt, id, name, status, updatedAt);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ToDoItem other = (ToDoItem) obj;
		return Objects.equals(createdAt, other.createdAt) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name) && status == other.status
				&& Objects.equals(updatedAt, other.updatedAt);
	}
	
}
