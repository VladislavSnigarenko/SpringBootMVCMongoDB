package ua.kiev.snigarenkov.SpringBootMVCMongoDb.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import ua.kiev.snigarenkov.SpringBootMVCMongoDb.models.Student;

@Configuration
public class StudentQuery {

	@Autowired
	private final MongoTemplate mongoTemplate;

	public StudentQuery(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	} 
	
	public List<Student> findByKeyword(String keyword){
		Query query = Query.query(Criteria.where("firstName").regex(keyword)
							.orOperator(Criteria.where("lastName").regex(keyword)));
		return mongoTemplate.find(query, Student.class);
	}
	
	

}
