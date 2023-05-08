package Chopsticks.HairHaeJoBackend.service;

import Chopsticks.HairHaeJoBackend.dto.menu.ChangeMenuDto;
import Chopsticks.HairHaeJoBackend.dto.menu.MakeMenuDto;
import Chopsticks.HairHaeJoBackend.entity.menu.DesignerMenu;
import Chopsticks.HairHaeJoBackend.entity.menu.DesignerMenuRepository;
import Chopsticks.HairHaeJoBackend.entity.menu.MenuList;
import Chopsticks.HairHaeJoBackend.jwt.SecurityUtil;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class MenuService {

    private DesignerMenuRepository designerMenuRepository;
    public Collection<MenuList> viewMenuList(long designerId) {
        Collection<MenuList> designerMenu=designerMenuRepository.findBydesignerId(designerId);
        return designerMenu;
    }

    public DesignerMenu viewMenu(int currentMenuId) {
        DesignerMenu designerMenu=designerMenuRepository.findById(currentMenuId).get();
        return designerMenu;
    }

    public void setMenu(MakeMenuDto menuDto) {
        long currentId= SecurityUtil.getCurrentMemberId();
        designerMenuRepository.save(menuDto.toDesignerMenu(currentId));
        return;

    }
    public void retouchMenu(ChangeMenuDto menuDto) {
        long currentId= SecurityUtil.getCurrentMemberId();
        DesignerMenu designerMenu=designerMenuRepository.findById(menuDto.getMenuId()).get();
        designerMenu.setMenuContent(menuDto.getMenuContent());
        designerMenu.setMenuPrice(menuDto.getMenuPrice());
        designerMenu.setMenuName(menuDto.getMenuName());
        designerMenuRepository.save(designerMenu);
        return;

    }

    public void delete(int currentMenuId)  {
        try {
            designerMenuRepository.deleteById(currentMenuId);
        }
        catch(Exception e) {
            throw new RuntimeException("메뉴를 삭제할 수 없습니다");

        }
    }
}
