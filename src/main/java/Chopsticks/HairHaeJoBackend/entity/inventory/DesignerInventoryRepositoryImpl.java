package Chopsticks.HairHaeJoBackend.entity.inventory;

import Chopsticks.HairHaeJoBackend.dto.Inventory.ChangeInventoryDto;
import Chopsticks.HairHaeJoBackend.dto.Inventory.itemViewDto;
import Chopsticks.HairHaeJoBackend.dto.article.ArticlelistResponseDto;
import Chopsticks.HairHaeJoBackend.entity.article.QArticle;
import Chopsticks.HairHaeJoBackend.entity.user.QUser;
import Chopsticks.HairHaeJoBackend.jwt.SecurityUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class DesignerInventoryRepositoryImpl implements DesignerInventoryRepositoryCustom {
    @Autowired
    EntityManager em;


    public Collection<itemViewDto> listfilter(String category, String name, boolean orderBystock, boolean orderByprice, boolean isWarning,long userid) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QItem Item= QItem.item;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QDesignerInventory DesignerInventory= QDesignerInventory.designerInventory;
        if(!category.equals("all")) booleanBuilder.and(Item.itemCategory.contains(category));

        if(!name.equals("all")) booleanBuilder.and(Item.itemName.contains(name));

        if(isWarning) booleanBuilder.and(Item.stock.lt(Item.warningStock.add(1)));
        OrderSpecifier[] orderSpecifiers=createOrderSpecifier(orderBystock,orderByprice);

        return queryFactory.select(Projections.fields(itemViewDto.class,Item.itemId,Item.itemName,Item.itemCategory,Item.itemPhoto,Item.stock,Item.warningStock,Item.itemPrice)).from(DesignerInventory).innerJoin(DesignerInventory.item,Item).where(booleanBuilder.and(DesignerInventory.userId.eq(userid))).orderBy(orderSpecifiers).fetch();


    }

    private OrderSpecifier[] createOrderSpecifier(boolean orderBystock, boolean orderByprice) {

        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();
        QItem Item= QItem.item;
        if(orderBystock){
            orderSpecifiers.add(new OrderSpecifier(Order.DESC,Item.stock));
        }
        if(orderByprice) {
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, Item.itemPrice));
        }
        orderSpecifiers.add(new OrderSpecifier(Order.ASC,Item.itemName));
        return orderSpecifiers.toArray(new OrderSpecifier[orderSpecifiers.size()]);
    }
}
