package acme.features.epicure.epicureDashboard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.fineDish.StatusType;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface EpicureEpicureDashBoardRepository extends AbstractRepository {
	
	@Query("select count(f) from FineDish f where f.status = :status")
	Integer countFineDishByStatus(StatusType status);
	
	@Query("select avg(f.budget.amount) from FineDish f where f.status = :status")
	Double calcAverageFineDishBudgetByStatus(StatusType status);
	
	@Query("select stddev(f.budget.amount) from FineDish f where f.status = :status")
	Double calcDeviationFineDishBudgetByStatus(StatusType status);
	
	@Query("select max(f.budget.amount) from FineDish f where f.status = :status")
	Double calcMaximumFineDishBudgetByStatus(StatusType status);
	
	@Query("select min(f.budget.amount) from FineDish f where f.status = :status")
	Double calcMinimumFineDishBudgetByStatus(StatusType status);
	
	
	
	

}
