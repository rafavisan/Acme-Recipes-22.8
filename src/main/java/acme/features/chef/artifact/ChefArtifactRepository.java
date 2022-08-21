package acme.features.chef.artifact;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.artifact.Artifact;
import acme.entities.artifact.ArtifactType;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface ChefArtifactRepository extends AbstractRepository {

	@Query("select a from Artifact a where a.type = :artifactType and a.chef.id = :chefId")
	Collection<Artifact> findManyByArtifactTypeAndChef(ArtifactType artifactType, Integer chefId);
}
