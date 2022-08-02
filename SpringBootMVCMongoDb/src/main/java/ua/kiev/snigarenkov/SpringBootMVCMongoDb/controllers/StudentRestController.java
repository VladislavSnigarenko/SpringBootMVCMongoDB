package ua.kiev.snigarenkov.SpringBootMVCMongoDb.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ua.kiev.snigarenkov.SpringBootMVCMongoDb.exception.StudentNotFoundException;
import ua.kiev.snigarenkov.SpringBootMVCMongoDb.models.Student;
import ua.kiev.snigarenkov.SpringBootMVCMongoDb.repository.StudentQuery;
import ua.kiev.snigarenkov.SpringBootMVCMongoDb.services.StudentService;

@RestController
public class StudentRestController {

	private final StudentService studentService; 
	private final StudentQuery studentQuery; 
	
	StudentRestController(StudentService studentService, StudentQuery studentQuery){
		this.studentService = studentService;
		this.studentQuery = studentQuery;
	}
	
	@GetMapping(value = "/students")
	public List<Student> getStudents(@RequestParam(required = false) String keyword){
		List<Student> students;
		if (keyword != null) {
			students = studentQuery.findByKeyword(keyword);  
		} else {
			students = studentService.findAll();  
		}
		return students;  
	}  
	
	@GetMapping(value = "/students/{id}")
	public Student getOneStudent(@PathVariable String id){
		return studentService.findById(id).
				orElseThrow(() -> new StudentNotFoundException(id));
	}
	
	@PostMapping(value = "/students")
	public void createStudent(@RequestBody Student student)  
	{  
		studentService.save(student);  
	}
	
	@PutMapping(value = "/students/{id}")
	public Student updateStudent(@RequestBody Student newStudent, @PathVariable String id){  
		 return studentService.findById(id)
			      .map(student -> {
			    	  student.setFirstName(newStudent.getFirstName());
			    	  student.setLastName(newStudent.getLastName());
			    	  student.seteMail(newStudent.geteMail());
			    	  return studentService.save(student);
			      })
			      .orElseGet(() -> {
			    	  newStudent.setId(id);
			    	  return studentService.save(newStudent);
			      });		
	}
	
	@DeleteMapping(value = "/students/{id}")
	public void deleteStudent(@PathVariable String id){  
		studentService.deleteById(id);
	}
	
	
}
