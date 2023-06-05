package Chopsticks.HairHaeJoBackend.controller;

import Chopsticks.HairHaeJoBackend.dto.APIMessages;
import Chopsticks.HairHaeJoBackend.dto.Inventory.ChangeInventoryDto;
import Chopsticks.HairHaeJoBackend.dto.Inventory.MakeInventoryDto;
import Chopsticks.HairHaeJoBackend.dto.Inventory.UseInventoryDto;
import Chopsticks.HairHaeJoBackend.dto.article.MakeArticleDto;
import Chopsticks.HairHaeJoBackend.dto.holiday.HolidayDto;
import Chopsticks.HairHaeJoBackend.service.InventoryService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/crm/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    @PostMapping("")
    public ResponseEntity<APIMessages> PostInventory(@RequestPart(value = "itemImage",required = false) MultipartFile itemImage,@RequestParam("jsonList") String jsonList) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new SimpleModule());
        MakeInventoryDto InventoryDto = objectMapper.readValue(jsonList, new TypeReference<>() {});


        inventoryService.postInventory(InventoryDto,itemImage);
        APIMessages apiMessages=APIMessages.builder().success(true)
                .message("인벤토리 작성 성공")
                .build();
        return ResponseEntity.ok(apiMessages);

    }

    @PutMapping("/use")
    public ResponseEntity<APIMessages> useStock(@RequestParam("jsonList") String jsonList) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new SimpleModule());
        UseInventoryDto InventoryDto = objectMapper.readValue(jsonList, new TypeReference<>() {});
        APIMessages apiMessages=APIMessages.builder().success(true)
                    .message("인벤토리 변경 성공")
                    .data(inventoryService.usestock(InventoryDto))
                    .build();


        return ResponseEntity.ok(apiMessages);

    }

    @PutMapping("")
    public ResponseEntity<APIMessages> ChangeInventory(@RequestPart(value = "itemImage",required = false) MultipartFile itemImage,@RequestParam("jsonList") String jsonList) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new SimpleModule());
            ChangeInventoryDto InventoryDto = objectMapper.readValue(jsonList, new TypeReference<>() {});
        APIMessages apiMessages=APIMessages.builder().success(true)
                .message("인벤토리 변경 성공")
                .data(inventoryService.Change(itemImage,InventoryDto))
                .build();
        return ResponseEntity.ok(apiMessages);

    }
    @DeleteMapping("")
    public ResponseEntity<APIMessages> ChangeInventory(@RequestParam(value = "itemId") int itemId) throws IOException {
        inventoryService.delete(itemId);
        APIMessages apiMessages=APIMessages.builder().success(true)
                .message("인벤토리 삭제 성공")
                .build();
        return ResponseEntity.ok(apiMessages);

    }

    @GetMapping("")
    public ResponseEntity<APIMessages> ViewInventory(@RequestParam String category, String name, boolean orderBystock, boolean orderByprice, boolean isWarning) throws IOException {

        APIMessages apiMessages=APIMessages.builder().success(true)
                .message("인벤토리 조회 성공")
                .data(inventoryService.View(category, name, orderBystock, orderByprice, isWarning))
                .build();
        return ResponseEntity.ok(apiMessages);

    }


}
