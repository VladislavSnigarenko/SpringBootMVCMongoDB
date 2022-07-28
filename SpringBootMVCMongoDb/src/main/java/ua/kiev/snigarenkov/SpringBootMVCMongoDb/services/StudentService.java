package ua.kiev.snigarenkov.SpringBootMVCMongoDb.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.springframework.web.bind.annotation.PostMapping;
import ua.kiev.snigarenkov.SpringBootMVCMongoDb.models.Student;
import ua.kiev.snigarenkov.SpringBootMVCMongoDb.repository.StudentRepository;

import javax.annotation.PostConstruct;

@Service
public class StudentService {

    static final int ROWS_MAX = 100;

	@Value("${config.rowsMax:100}") // такие вещи как ROWS_MAX лучще уместить в конфигурацию application.properties + default value если нет такого значения
    private long rowsMax;

	@Autowired
	StudentRepository studentRepository;

	public void init() {
		// intValue() возвращает примитив int a не обьект Integer
		int cnt = count().intValue();
		if(cnt < ROWS_MAX) { // в общем смысла в этом if нету, если cnt > ROWS_MAX то цикл for 0 раз проитерирует
			for (int i = (cnt + 1); i < ROWS_MAX; i++) {
				Student student = new Student("firstName_" + i, "lastName_" + i, "e" + i + "@.mail");

				// лучше использовать .insert(), в случае .save()
				// spring может не понять это новый обьект или нет
				// и сделать дополнительный find перед .save,
				// в случае .insert мы точно даем понять что это новый уникальный обьект и гарантированно будет только один запрос.
				// Однако тут вообще можно использовать InsertAll. Если у нас ROWS_MAX == 100 то будет 100 insert, a есть 100k то это вообще будет очень долго.
				studentRepository.save(student);
			}
		}
	}

	@PostConstruct
	// что бы не делать
	//  @Bean
	//	CommandLineRunner init(StudentService studentService){
	//		return args -> {
	//			LOG.info("EXECUTING : studentService init");
	//			studentService.init();
	//		};
	//	}
	public void init_my() {
		List<Student> studentList = getStudentToInsert(count(), rowsMax);

		studentRepository.insert(studentList); // Только один запрос при любом случае rowsMax
	}

	// внимание на разделение методов так что бы они были легко тестируемы,
	// напрмет тут нет контекста сколько вернет count(), какая конфигурация rowsMax. Метод как только для бизнес логике
	static List<Student> getStudentToInsert(long studentInDB, long maxStudents) {
		return LongStream.range(studentInDB, maxStudents+1) // maxStudents+1 потому что в range второй аргумент не включается
				.mapToObj(StudentService::createStudent)
				.collect(Collectors.toList()); // в java > 8 есть метод .toList() который работает быстрее
	}

	static Student createStudent(long i) {
		return new Student("firstName_" + i, "lastName_" + i, "e" + i + "@.mail");
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
		// тут может быть большая ошибка, если не будте в базе такого id то будет NoSuchElementException
		return studentRepository.findById(id).get(); // в общем использвание в 95% .get() это потенциальная ошибка в коде или в дизайне и можно написать лучше
	}


	// лучше использовать findЧтоТоТам и возвращать Optional<ЧтоТоТам> или getЧтоТоТам и возвращать ЧтоТоТам или null
	public Optional<Student> findById_my(String id){
		return studentRepository.findById(id);
	}

	public Student getById_my(String id){
		return studentRepository.findById(id).orElse(null);
	}
	
	public List<Student> findAll(){
		return studentRepository.findAll();
	}

	public List<Student> findAll(Pageable pageable){
		return studentRepository.findAll(pageable).getContent();
	}

	// в контроллере есть использование функции findAll(page) для того что бы взять пользователей с базы и позже count() что бы узнать сколько всего пользоватей
	// если бы findAll возвращал Page<Student> то у page Page есть метод getTotalElements() и count() былби не нужен. К тому бы count() это доболнительная лишняя нагрузна на базу
	public Page<Student> findAll_my(Pageable pageable){
		return studentRepository.findAll(pageable);
	}

	public List<Student> findByKeyword(String keyword){
		return studentRepository.findByKeyword(keyword);
	}
	
	public Long count(){
		return studentRepository.count(); // если функция возвращает примитив то лучше использовать примитив long а не обьект Long
	}
	
}
