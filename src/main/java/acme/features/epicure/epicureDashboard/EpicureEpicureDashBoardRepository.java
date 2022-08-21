package acme.features.epicure.epicureDashboard;

import org.springframework.stereotype.Repository;

import acme.framework.repositories.AbstractRepository;

@Repository
public interface EpicureEpicureDashBoardRepository extends AbstractRepository {
	
//	@Query("SELECT ed FROM EpicureDashBoard ed WHERE ed.userAccount =:epicure.userAccount")
//	Collection<EpicureDashboard> findByEpicure(Epicure epicure);

}
