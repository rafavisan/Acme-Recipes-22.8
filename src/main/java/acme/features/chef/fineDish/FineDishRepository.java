package acme.features.chef.fineDish;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.fineDish.FineDish;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface FineDishRepository extends AbstractRepository{
	
	@Query("select a from FineDish a where a.id = :id")
	FineDish findOneFineDishById(int id);

	@Query("select a from FineDish a")
	Collection<FineDish> findManyFineDish();
	
	@Query("select a from FineDish a where a.chef.id = :i")
	Collection<FineDish> findManyFineDishByChef(Integer i);

}
