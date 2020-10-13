package it.alecsandria.pg.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.alecsandria.pg.entities.Product;

@Repository(value = "it.alecsandria.pg.repositories.ProductRepository")
@Transactional
public interface ProductRepository extends CrudRepository<Product, Integer> {

	public List<Product> findAllByidSeller(int idSeller);
	
}
