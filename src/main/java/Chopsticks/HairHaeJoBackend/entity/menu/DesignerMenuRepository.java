package Chopsticks.HairHaeJoBackend.entity.menu;


import Chopsticks.HairHaeJoBackend.dto.menu.MenuListDto;
import Chopsticks.HairHaeJoBackend.entity.article.Article;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Collection;

public interface DesignerMenuRepository extends JpaRepository<DesignerMenu, Integer>{
    Collection<MenuList> findBydesignerId(long designerId);

}
