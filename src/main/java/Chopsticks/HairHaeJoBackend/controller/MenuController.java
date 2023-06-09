package Chopsticks.HairHaeJoBackend.controller;

import Chopsticks.HairHaeJoBackend.dto.APIMessages;
import Chopsticks.HairHaeJoBackend.dto.article.MakeArticleDto;
import Chopsticks.HairHaeJoBackend.dto.menu.ChangeMenuDto;
import Chopsticks.HairHaeJoBackend.dto.menu.MakeMenuDto;
import Chopsticks.HairHaeJoBackend.dto.menu.MenuIdDto;
import Chopsticks.HairHaeJoBackend.service.MenuService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/menu")
public class MenuController {
    private final MenuService menuService;

    @GetMapping("")
    public ResponseEntity<APIMessages> viewMenu(@RequestParam("menuId") int menuId) throws IOException

    {


        APIMessages apiMessages=APIMessages.builder().success(true)
                .message("디자이너 메뉴 조회 성공")
                .data(menuService.viewMenu(menuId))
                .build();
        return ResponseEntity.ok(apiMessages);
    }
    @GetMapping("/list")
    public ResponseEntity<APIMessages> viewMenuList(@RequestParam(value = "designerId") String designerId) throws IOException

    {


        APIMessages apiMessages=APIMessages.builder().success(true)
                .message("디자이너 메뉴 리스트 조회 성공")
                .data(menuService.viewMenuList(Long.parseLong(designerId)))
                .build();
        return ResponseEntity.ok(apiMessages);
    }

    @PostMapping(value="/list")
    public ResponseEntity<APIMessages> posting(@RequestParam("jsonList") String jsonList) throws IOException

    {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new SimpleModule());
        MakeMenuDto menuDto = objectMapper.readValue(jsonList, new TypeReference<>() {});
        menuService.setMenu(menuDto);
        APIMessages apiMessages=APIMessages.builder().success(true)
                .message("메뉴 추가 성공")
                .build();


        return ResponseEntity.ok(apiMessages);
    }

    @PutMapping(value="/list")
    public ResponseEntity<APIMessages> retouch(@RequestParam("jsonList") String jsonList) throws IOException

    {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new SimpleModule());
        ChangeMenuDto menuDto = objectMapper.readValue(jsonList, new TypeReference<>() {});
        menuService.retouchMenu(menuDto);
        APIMessages apiMessages=APIMessages.builder().success(true)
                .message("메뉴 수정 성공")
                .build();


        return ResponseEntity.ok(apiMessages);
    }

    @DeleteMapping(value="/list")
    public ResponseEntity<APIMessages> delete(@RequestParam("jsonList") String jsonList) throws IOException

    {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new SimpleModule());
        MenuIdDto menuDto = objectMapper.readValue(jsonList, new TypeReference<>() {});
        menuService.delete(menuDto.getMenuId());
        APIMessages apiMessages=APIMessages.builder().success(true)
                .message("메뉴 삭제 성공")
                .build();


        return ResponseEntity.ok(apiMessages);
    }
}
