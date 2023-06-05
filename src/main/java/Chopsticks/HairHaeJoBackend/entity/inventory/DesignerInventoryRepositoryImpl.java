package Chopsticks.HairHaeJoBackend.entity.inventory;

import Chopsticks.HairHaeJoBackend.dto.article.ArticlelistResponseDto;
import Chopsticks.HairHaeJoBackend.entity.article.QArticle;
import Chopsticks.HairHaeJoBackend.entity.user.QUser;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DesignerInventoryRepositoryImpl implements DesignerInventoryRepositoryCustom {
    @Autowired
    EntityManager em;


    public Collection<Item> listfilter(String category,String name,boolean orderBystock,boolean orderByprice,boolean isWarning) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QItem Item= QItem.item;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QDesignerInventory DesignerInventory= QDesignerInventory.designerInventory;
        if(!category.equals("all")) booleanBuilder.and(Item.itemCategory.contains(category));
        if(!category.equals("all")) booleanBuilder.and(Item.itemName.contains(name));
        if(!isWarning) booleanBuilder.and(Item.stock.lt(Item.warningStock.add(1)));
        if(orderBystock) return
        if(orderByprice) return
        return queryFactory.select(Projections.fields()).from(Article).innerJoin(DesignerInventory.item,Item).where(booleanBuilder).fetch();
    }

    private OrderSpecifier[] createOrderSpecifier(OrderCondition orderCondition) {

        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();

        if(Objects.isNull(orderCondition)){
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, person.name));
        }else if(orderCondition.equals(OrderCondition.AGE)){
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, person.age));
        }else{
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, person.region));
        }
        return orderSpecifiers.toArray(new OrderSpecifier[orderSpecifiers.size()]);
    }
}
