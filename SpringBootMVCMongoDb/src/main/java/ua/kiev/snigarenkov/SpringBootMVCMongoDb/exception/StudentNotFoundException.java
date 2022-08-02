package ua.kiev.snigarenkov.SpringBootMVCMongoDb.exception;

public class StudentNotFoundException extends RuntimeException{

	public StudentNotFoundException(String id) {
	    super("Could not find student " + id);
	  }
	
}
