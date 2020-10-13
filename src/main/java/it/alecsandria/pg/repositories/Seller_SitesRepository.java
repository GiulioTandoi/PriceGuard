package it.alecsandria.pg.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.alecsandria.pg.entities.Seller_Sites;

@Repository(value = "it.alecsandria.pg.repositories.Seller_SitesRepository")
@Transactional
public interface Seller_SitesRepository extends CrudRepository<Seller_Sites, Integer> {

	@Query(value = "SELECT DISTINCT sites.id, sites.name, sites.urlRobotService FROM seller, seller_sites, sites WHERE seller_sites.idSeller = :idSeller and seller_sites.idSite = sites.id" , nativeQuery = true)
	public List<Object []> findAllByIdSeller(@Param(value= "idSeller") int idSeller);
}
