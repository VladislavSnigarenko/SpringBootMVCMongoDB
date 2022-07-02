package ua.kiev.snigarenkov.SpringBootMVCMongoDb.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import ua.kiev.snigarenkov.SpringBootMVCMongoDb.models.Student;

public interface StudentRepository extends MongoRepository<Student, String>{
	
	 //Custom query
	 @Query("{$or :[{ 'firstName' : { $regex: ?0, $options:'i' } },{ 'lastName' : { $regex: ?0, $options:'i' } }]}")        
	 List<Student> findByKeyword(String keyword);
	 
}
