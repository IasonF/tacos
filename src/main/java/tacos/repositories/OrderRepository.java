package tacos.repositories;

import org.springframework.data.repository.CrudRepository;
import tacos.entities.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
