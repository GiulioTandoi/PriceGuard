package it.alecsandria.pg.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.alecsandria.pg.entities.Dealer;

@Repository(value = "it.alecsandria.pg.repositories.DealerRepository")
public interface DealerRepository extends CrudRepository<Dealer, Integer> {

}
