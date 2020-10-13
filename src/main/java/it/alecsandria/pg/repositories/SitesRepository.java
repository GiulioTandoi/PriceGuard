package it.alecsandria.pg.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.alecsandria.pg.entities.Site;

@Repository(value = "it.alecsandria.pg.repositories.SitesRepository")
@Transactional
public interface SitesRepository extends CrudRepository<Site, Integer>{

	@Query(value = "select s.id, s.name, s.urlrobotService from sites s where s.id = :id", nativeQuery = true)
		public Site getById(@Param(value= "id") int id);

}
