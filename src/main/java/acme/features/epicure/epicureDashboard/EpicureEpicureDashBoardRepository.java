package acme.features.epicure.epicureDashboard;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.epicureDashboard.EpicureDashboard;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Epicure;

@Repository
public interface EpicureEpicureDashBoardRepository extends AbstractRepository {
	
	@Query("SELECT ed FROM EpicureDashBoard ed WHERE ed.userAccount =:epicure.userAccount")
	Collection<EpicureDashboard> findByEpicure(Epicure epicure);

}
