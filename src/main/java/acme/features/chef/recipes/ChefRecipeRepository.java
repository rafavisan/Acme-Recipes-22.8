package acme.features.chef.recipes;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.recipe.Recipe;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface ChefRecipeRepository extends AbstractRepository {

	@Query("select r from Recipe r where r.chef.id=:id")
	Collection<Recipe> findManyByPublised(int id);
}
