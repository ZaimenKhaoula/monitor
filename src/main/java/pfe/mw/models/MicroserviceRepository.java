package pfe.mw.models;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MicroserviceRepository  extends MongoRepository<Microservice, String>		{
	public Microservice findMicroserviceByidMicroservice(String idMicroservice) ;
}