package ua.kiev.snigarenkov.SpringBootMVCMongoDb.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ua.kiev.snigarenkov.SpringBootMVCMongoDb.models.Student;
import ua.kiev.snigarenkov.SpringBootMVCMongoDb.repository.StudentQuery;
import ua.kiev.snigarenkov.SpringBootMVCMongoDb.repository.StudentRepository;

@Service
public class StudentService {

	@Value("${ITEMS_PER_PAGE}")
	private int itemsPerPage;

	@Value("${ROWS_MAX}")
	private long rowsMax;

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	StudentQuery studentQuery;

	@PostConstruct
	public void init() {
		List<Student> studentList = getStudentToInsert(count(), rowsMax);
		studentRepository.insert(studentList);
	}
	
	static  List<Student> getStudentToInsert(long studentInDB, long maxStudents){
		return LongStream.range(studentInDB, maxStudents + 1).
				mapToObj(StudentService::createStudent).
				collect(Collectors.toList());
	}
	
	static Student createStudent(long i) {
		return new Student("firstName" + i, "lastName_" + i, "e" + i + "@mail");
	}
	
	public Student save(Student student) {
		return studentRepository.save(student);
	}
	
	public boolean existsById(String id){
		return studentRepository.existsById(id);
	}
	
	public void deleteById(String id){
		studentRepository.deleteById(id);
	}
	
	public Optional<Student> findById(String id){
		return studentRepository.findById(id);
	}
	
	public List<Student> findAll(){
		return studentRepository.findAll();
	}

	public List<Student> findAll(Pageable pageable){
		return studentRepository.findAll(pageable).getContent();
	}

	public List<Student> findByKeyword(String keyword){
		return studentQuery.findByKeyword(keyword);
		}
	
	public Long count(){
		return studentRepository.count();
	}

	public int getItemsPerPage() {
		return itemsPerPage;
	}
	
}
