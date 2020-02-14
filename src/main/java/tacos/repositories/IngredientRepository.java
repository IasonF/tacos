package tacos.repositories;

import org.springframework.data.repository.CrudRepository;
import tacos.entities.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
