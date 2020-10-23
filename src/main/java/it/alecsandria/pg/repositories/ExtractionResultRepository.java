package it.alecsandria.pg.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.alecsandria.pg.entities.ExtractionResult;

@Repository(value = "it.alecsandria.pg.repositories.ExtractionResultRepository")
public interface ExtractionResultRepository extends CrudRepository<ExtractionResult, Integer> {
	
}
