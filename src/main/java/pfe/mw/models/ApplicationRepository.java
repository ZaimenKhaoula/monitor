package pfe.mw.models;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends MongoRepository<Application, String>{
	public Application findApplicationByName(String nameApplication) ;
	
}
