package acme.features.chef.pimpam;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.artifact.Artifact;
import acme.entities.pimpam.Pimpam;
import acme.entities.systemSetting.SystemSettings;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Chef;

@Repository
public interface ChefPimpamRepository extends AbstractRepository{
	
	@Query("select p from Pimpam p where p.id = :id")
	Pimpam findOnePimpamById(int id);
	
	@Query("select p from Pimpam p")
	Collection<Pimpam> findAllPimpams();
	
	@Query("select a from Artifact a where a.id = :id")
	Artifact findArtifactById(int id);
	
	
	@Query("select a from Artifact a")
	List<Artifact> findAllArtifacts();
	
	@Query("select c from Chef c where c.id = :id")
	Chef findChefById(int id);
	
	@Query("select s from SystemSettings s")
	SystemSettings findConfiguration();
	

}
