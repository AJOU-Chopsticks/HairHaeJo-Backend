package Chopsticks.HairHaeJoBackend.entity.order;

import Chopsticks.HairHaeJoBackend.entity.article.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository  extends JpaRepository<Order, Integer> {
    public Order findBytid(String tid);
}
