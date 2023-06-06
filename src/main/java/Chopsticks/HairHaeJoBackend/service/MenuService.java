package Chopsticks.HairHaeJoBackend.service;

import Chopsticks.HairHaeJoBackend.dto.article.ArticleViewDto;
import Chopsticks.HairHaeJoBackend.dto.menu.ChangeMenuDto;
import Chopsticks.HairHaeJoBackend.dto.menu.MakeMenuDto;
import Chopsticks.HairHaeJoBackend.entity.menu.DesignerMenu;
import Chopsticks.HairHaeJoBackend.entity.menu.DesignerMenuRepository;
import Chopsticks.HairHaeJoBackend.entity.menu.MenuList;
import Chopsticks.HairHaeJoBackend.entity.user.Role;
import Chopsticks.HairHaeJoBackend.entity.user.User;
import Chopsticks.HairHaeJoBackend.entity.user.UserRepository;
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
@Transactional
public class MenuService {

    private final DesignerMenuRepository designerMenuRepository;
    private final UserRepository userRepository;
    public void setMenu(MakeMenuDto menuDto)  {
        try {

            designerMenuRepository.save(menuDto.toDesignerMenu(SecurityUtil.getCurrentMemberId()));
        }
        catch(Exception e) {
            throw new RuntimeException("메뉴글 저장을 실패했습니다");
        }
    }
    public Collection<MenuList> viewMenuList(long designerId)  throws IOException  {
        Collection<MenuList> designerMenu;
        try {
            designerMenu = designerMenuRepository.findBydesignerId(designerId);
        }
        catch(Exception e) {
            throw new RuntimeException("메뉴 리스트 조회를 실패했습니다");
            }

        return designerMenu;
    }

    public DesignerMenu viewMenu(int currentMenuId) throws IOException  {
        DesignerMenu designerMenu=designerMenuRepository.findById(currentMenuId)
                .orElseThrow(()->new RuntimeException("해당 메뉴가 존재하지 않습니다"));
        return designerMenu;
    }


    public void retouchMenu(ChangeMenuDto menuDto) throws IOException  {
        long currentId= SecurityUtil.getCurrentMemberId();
        DesignerMenu designerMenu=designerMenuRepository.findById(menuDto.getMenuId())
                .orElseThrow(() -> new RuntimeException("해당 메뉴가 존재하지 않습니다"));
        ismine(designerMenu);
        try {
            designerMenu.setMenuContent(menuDto.getMenuContent());
            designerMenu.setMenuPrice(menuDto.getMenuPrice());
            designerMenu.setMenuName(menuDto.getMenuName());
        }
        catch( Exception e){
            throw new RuntimeException("메뉴 수정을 실패했습니다");
        }

        designerMenuRepository.save(designerMenu);


    }

    public void delete(int currentMenuId) throws IOException   {
        DesignerMenu designerMenu=designerMenuRepository.findById(currentMenuId)
                .orElseThrow(() -> new RuntimeException("해당 메뉴가 존재하지 않습니다"));
        ismine(designerMenu);
        try {
            designerMenuRepository.deleteById(currentMenuId);
        }
        catch(Exception e) {
            throw new RuntimeException("메뉴를 삭제할 수 없습니다");

        }
    }

    private void ismine(DesignerMenu designerMenu) {
        long currentId= SecurityUtil.getCurrentMemberId();
        User user=userRepository.findById(currentId)
                .orElseThrow(() -> new RuntimeException("로그인 상태가 아닙니다"));

        if(user.getRole() != Role.ROLE_DESIGNER||designerMenu.getDesignerId()!=currentId) throw new RuntimeException("본인만 접근 가능합니다");

    }
}
