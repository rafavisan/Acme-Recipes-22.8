package acme.features.epicure.epicureDashboard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.artifact.ArtifactType;
import acme.entities.fineDish.StatusType;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface EpicureEpicureDashBoardRepository extends AbstractRepository {
	
	@Query("select count(f) from FineDish f where f.status =: status")
	Integer countFineDishByStatus(StatusType status);
	
	@Query("select avg(f.budget.amount) from FineDish f where f.status = :status")
	Double calcAverageFineDishRetailPriceByStatus(StatusType status);
	
	@Query("select stddev(f.budget.amount) from FineDish f where f.status = :status")
	Double calcDeviationFineDishRetailPriceByStatus(StatusType status);
	
	@Query("select max(f.budget.amount) from FineDish f where f.status = :status")
	Double calcMaximumFineDishRetailPriceByStatus(StatusType status);
	
	@Query("select min(a.retailPrice.amount) from Artifact a where a.type == UTENSIL")
	Double calcMinimumUtensilRetailPrice();

}
