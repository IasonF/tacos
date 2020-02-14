package tacos.repositories;

import org.springframework.data.repository.CrudRepository;
import tacos.entities.User;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);

}
