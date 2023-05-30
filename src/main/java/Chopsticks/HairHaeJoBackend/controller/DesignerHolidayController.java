package Chopsticks.HairHaeJoBackend.controller;

import Chopsticks.HairHaeJoBackend.dto.APIMessages;
import Chopsticks.HairHaeJoBackend.dto.holiday.HolidayDto;
import Chopsticks.HairHaeJoBackend.service.DesignerHolidayService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/crm/holiday")
public class DesignerHolidayController {

    private final DesignerHolidayService designerHolidayService;

    @PostMapping("")
    public ResponseEntity<APIMessages> PostHoliday(@RequestParam("jsonList") String jsonList) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new SimpleModule());
        HolidayDto holidayDto = objectMapper.readValue(jsonList, new TypeReference<>() {});

        designerHolidayService.post(holidayDto);
        APIMessages apiMessages=APIMessages.builder().success(true)
                .message("휴일 작성 성공")
                .build();
        return ResponseEntity.ok(apiMessages);

    }



    @DeleteMapping("")
    public ResponseEntity<APIMessages> DeleteHolidayeach(@RequestParam("jsonList") String jsonList) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new SimpleModule());
        HolidayDto holidayDto = objectMapper.readValue(jsonList, new TypeReference<>() {});

        designerHolidayService.deleteeach(holidayDto);
        APIMessages apiMessages=APIMessages.builder().success(true)
                .message("휴일 삭제 성공")
                .build();
        return ResponseEntity.ok(apiMessages);

    }


    @GetMapping("")
    public ResponseEntity<APIMessages> viewHoliday(@RequestParam("DesignerId") long DesignerId) throws IOException {

        APIMessages apiMessages=APIMessages.builder().success(true)
                .message("휴일 조회 성공")
                .data( designerHolidayService.view(DesignerId))
                .build();
        return ResponseEntity.ok(apiMessages);

    }

}
