package Chopsticks.HairHaeJoBackend.service;

import Chopsticks.HairHaeJoBackend.dto.article.ArticleIdDto;
import Chopsticks.HairHaeJoBackend.dto.article.MakeArticleDto;
import Chopsticks.HairHaeJoBackend.dto.holiday.HolidayDto;
import Chopsticks.HairHaeJoBackend.dto.reservation.ImPossibleTimeResponse;
import Chopsticks.HairHaeJoBackend.entity.article.Article;
import Chopsticks.HairHaeJoBackend.entity.article.Articlestate;
import Chopsticks.HairHaeJoBackend.entity.holiday.DesignerHoliday;
import Chopsticks.HairHaeJoBackend.entity.holiday.DesignerHolidayRepository;
import Chopsticks.HairHaeJoBackend.entity.user.Role;
import Chopsticks.HairHaeJoBackend.entity.user.User;
import Chopsticks.HairHaeJoBackend.entity.user.UserRepository;
import Chopsticks.HairHaeJoBackend.jwt.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DesignerHolidayService {

    private final DesignerHolidayRepository designerHolidayRepository;
    private final UserRepository userRepository;
    public void post(HolidayDto holidayDto) throws IOException {


        long currentId= SecurityUtil.getCurrentMemberId();
        User user=userRepository.findById(currentId)
                .orElseThrow(() -> new RuntimeException("로그인 상태가 아닙니다"));

        if(user.getRole() != Role.ROLE_DESIGNER) throw new RuntimeException("헤어디자이너만 접근 가능합니다");
        try {
            designerHolidayRepository.save(holidayDto.toholiday(currentId));
        }
        catch(Exception e) {
            throw new RuntimeException("휴일 작성을 실패했습니다");
        }

    }

    public void change(HolidayDto holidayDto) throws IOException {


        long currentId= SecurityUtil.getCurrentMemberId();
        User user=userRepository.findById(currentId)
                .orElseThrow(() -> new RuntimeException("로그인 상태가 아닙니다"));

        if(user.getRole() != Role.ROLE_DESIGNER) throw new RuntimeException("헤어디자이너만 접근 가능합니다");
        try {

            DesignerHoliday holiday =designerHolidayRepository.findBydesignerId(currentId);
            if(holiday==null) throw new RuntimeException();
            else {
                holiday.setDesignerHoliday(holidayDto.getHoliday());
                designerHolidayRepository.save(holiday);
            }

        }
        catch(Exception e) {
            throw new RuntimeException("휴일 작성을 실패했습니다");
        }

    }

    public  ArrayList<HolidayDto>  view(long holidayDto) throws IOException {


        long currentId= SecurityUtil.getCurrentMemberId();
        User user=userRepository.findById(currentId)
                .orElseThrow(() -> new RuntimeException("로그인 상태가 아닙니다"));

        if(user.getRole() != Role.ROLE_DESIGNER) throw new RuntimeException("헤어디자이너만 접근 가능합니다");

        DesignerHoliday holiday=null;
        try {
            holiday =designerHolidayRepository.findBydesignerId(currentId);
            if(holiday==null) throw new RuntimeException();



        }
        catch(Exception e) {
            throw new RuntimeException("휴일 확인을 실패했습니다");
        }
        ArrayList<HolidayDto> data=getHoliday(holiday.getDesignerHoliday().split(","));
        return data;


    }
    private ArrayList<HolidayDto> getHoliday(String[] holiday) {
        ArrayList<HolidayDto> ReturnData=new ArrayList<>();
        for (String s : holiday) {
            int holidayInt = Integer.parseInt(s);
            if (holidayInt > 100) {
                ReturnData.add(new HolidayDto("매월 " + holidayInt % 100 + " 휴일입니다"));

            } else {
                ReturnData.add(new HolidayDto("매월 " + (int) holidayInt / 10 + "째주 " + holidayInt % 10 + "째날 휴일입니다"));
            }
        }
        return ReturnData;

    }
}
