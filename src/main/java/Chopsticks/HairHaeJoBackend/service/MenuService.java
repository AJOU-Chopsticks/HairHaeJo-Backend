package Chopsticks.HairHaeJoBackend.service;

import Chopsticks.HairHaeJoBackend.dto.menu.ChangeMenuDto;
import Chopsticks.HairHaeJoBackend.dto.menu.MakeMenuDto;
import Chopsticks.HairHaeJoBackend.entity.menu.DesignerMenu;
import Chopsticks.HairHaeJoBackend.entity.menu.DesignerMenuRepository;
import Chopsticks.HairHaeJoBackend.entity.menu.MenuList;
import Chopsticks.HairHaeJoBackend.jwt.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class MenuService {

    private final DesignerMenuRepository designerMenuRepository;
    public void setMenu(MakeMenuDto menuDto)  {

        designerMenuRepository.save(menuDto.toDesignerMenu(SecurityUtil.getCurrentMemberId()));

    }
    public Collection<MenuList> viewMenuList(long designerId)  throws IOException  {
        Collection<MenuList> designerMenu=designerMenuRepository.findBydesignerId(designerId);
        return designerMenu;
    }

    public DesignerMenu viewMenu(int currentMenuId) throws IOException  {
        DesignerMenu designerMenu=designerMenuRepository.findById(currentMenuId).get();
        return designerMenu;
    }


    public void retouchMenu(ChangeMenuDto menuDto) throws IOException  {
        long currentId= SecurityUtil.getCurrentMemberId();
        DesignerMenu designerMenu=designerMenuRepository.findById(menuDto.getMenuId()).get();
        designerMenu.setMenuContent(menuDto.getMenuContent());
        designerMenu.setMenuPrice(menuDto.getMenuPrice());
        designerMenu.setMenuName(menuDto.getMenuName());
        designerMenuRepository.save(designerMenu);


    }

    public void delete(int currentMenuId) throws IOException   {
        try {
            designerMenuRepository.deleteById(currentMenuId);
        }
        catch(Exception e) {
            throw new RuntimeException("메뉴를 삭제할 수 없습니다");

        }
    }
}
