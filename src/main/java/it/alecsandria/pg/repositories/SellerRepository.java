package it.alecsandria.pg.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.alecsandria.pg.entities.Seller;

@Repository(value = "it.alecsandria.pg.repositories.SellerRepository")
public interface SellerRepository extends CrudRepository<Seller, Integer> {

	@Query(value = "select s.id, s.name, s.searchLabel from seller s", nativeQuery = true)
	public List<Seller> findAll();
	
}
