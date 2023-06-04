package Chopsticks.HairHaeJoBackend.controller;

import Chopsticks.HairHaeJoBackend.dto.APIMessages;
import Chopsticks.HairHaeJoBackend.dto.Inventory.MakeInventoryDto;
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


}
