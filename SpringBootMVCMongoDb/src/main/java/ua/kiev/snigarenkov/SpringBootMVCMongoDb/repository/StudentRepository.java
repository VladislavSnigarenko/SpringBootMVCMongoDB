package ua.kiev.snigarenkov.SpringBootMVCMongoDb.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import ua.kiev.snigarenkov.SpringBootMVCMongoDb.models.Student;

public interface StudentRepository extends MongoRepository<Student, String>{
	
	 //Custom query
	 // В общем норм, но как-то сложно и трудно читаемо
	 @Query("{$or :[{ 'firstName' : { $regex: ?0, $options:'i' } },{ 'lastName' : { $regex: ?0, $options:'i' } }]}")        
	 List<Student> findByKeyword(String keyword);

	 // если используем JPA то именно JPA может сделать нам сделать нужный запрос
	 List<Student> findByFirstNameMatchesRegexOrLastNameMatchesRegex(String regex);

	 // Но в общем конкретно в монго jpa редко используется
	 // Почему? Основной смысл mongo в использование нативных операторов, например findAndModify() и другие. Это можно реализовать с помощью MongoTemplate
	 // Именно это дает возможность использовать такой плюс монго как скорость работы. (Для back программиста часто возникает задача какую базу выбрать)
	 // Можно бы было написать этот Repository так, в конкретно в случает этого приложение MongoRepository хорошо заходит

	 class StudentRepository {

		 private final MongoTemplate mongoTemplate;

		 public StudentRepository(@Autowired MongoTemplate mongoTemplate) {
			 this.mongoTemplate = mongoTemplate;
		 }

		 // тот же самый find только без странного синтаксиса ...{ $regex: ?0, $options:'i' } }]}
		 public List<Student> findByKeyword(String keyword) {
			 Query query = Query.query(
					 Criteria.where("firstName").regex(keyword)
					 		.orOperator(Criteria.where("firstName").regex(keyword)));

			 return mongoTemplate.find(query, Student.class);
		 }

		 // Так же можно есть есть задача например подбить у пользователя лайк на один то можно сделать это так.
		 // Легко и супер быстро, в sql так бы было куда сложнее.
		 // И нельзя в Jpa тоже не получится так сделать
		 public Student userInteraction() {
			 Query query = new Query();
			 Update update = new Update();
			 update.inc("interactionCount");
			 return mongoTemplate.findAndModify(query, FindAndModifyOptions.options().returnNew(true), update, Student.class )ж
		 }

	 }

}
