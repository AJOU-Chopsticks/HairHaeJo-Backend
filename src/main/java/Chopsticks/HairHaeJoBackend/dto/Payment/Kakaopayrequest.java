package Chopsticks.HairHaeJoBackend.dto.Payment;

import Chopsticks.HairHaeJoBackend.entity.article.Article;
import Chopsticks.HairHaeJoBackend.entity.article.Articlestate;
import Chopsticks.HairHaeJoBackend.entity.order.Order;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Kakaopayrequest {
    private int menu_id;

    public Order toOrder(long user,String tid,int amount,int taxfree) {
        return Order.builder()
                .menuId(menu_id)
                .totalAmount(amount)
                .tid(tid)
                .taxFree(taxfree)
                .userId(user)
                .build();
    }

}
