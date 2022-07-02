package ua.kiev.snigarenkov.SpringBootMVCMongoDb.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ua.kiev.snigarenkov.SpringBootMVCMongoDb.models.Student;
import ua.kiev.snigarenkov.SpringBootMVCMongoDb.repository.StudentRepository;

@Service
public class StudentService {

    static final int ROWS_MAX = 100;

	@Autowired
	StudentRepository studentRepository;

	public void init() {
		Integer cnt = count().intValue(); 
		if(cnt < ROWS_MAX) {
			for (int i = (cnt + 1); i < ROWS_MAX; i++) {
				Student student = new Student("firstName_" + i, "lastName_" + i, "e" + i + "@.mail");
				studentRepository.save(student);
			}	
		}
	}
	
	public void save(Student student) {
		studentRepository.save(student);
	}
	
	public boolean existsById(String id){
		return studentRepository.existsById(id);
	}
	
	public void deleteById(String id){
		studentRepository.deleteById(id);
	}
	
	public Student findById(String id){
		return studentRepository.findById(id).get();
	}
	
	public List<Student> findAll(){
		return studentRepository.findAll();
	}

	public List<Student> findAll(Pageable pageable){
		return studentRepository.findAll(pageable).getContent();
	}

	public List<Student> findByKeyword(String keyword){
		return studentRepository.findByKeyword(keyword);
	}
	
	public Long count(){
		return studentRepository.count();
	}
	
}
