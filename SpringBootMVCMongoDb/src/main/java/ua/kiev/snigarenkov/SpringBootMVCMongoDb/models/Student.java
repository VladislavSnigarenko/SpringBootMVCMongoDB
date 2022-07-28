package ua.kiev.snigarenkov.SpringBootMVCMongoDb.models;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "students")
@Data
@ToString
public class Student {

	@Id // tip: в mongo primaryKey (_id в mongo), будет именно id даже есть не будет аннотации @Id. @Id обязателен есть id колекции называется по другому (не id)
	private String id;
	private String firstName;
	private String lastName;
	private String eMail;

	public Student() {
	}
	
	public Student(String firstName, String lastName, String eMail) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.eMail = eMail;
	}
	
}
