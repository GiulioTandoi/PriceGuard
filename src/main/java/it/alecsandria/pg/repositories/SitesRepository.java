package it.alecsandria.pg.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.alecsandria.pg.entities.Site;

@Repository(value = "it.recruiting.sviluppo.repos.HomeRepository")
public interface SitesRepository extends CrudRepository<Site, Long>{

		public Site getById(Long id);

}
