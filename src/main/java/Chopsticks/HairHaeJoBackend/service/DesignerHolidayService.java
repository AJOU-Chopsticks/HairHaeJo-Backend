package Chopsticks.HairHaeJoBackend.service;


import Chopsticks.HairHaeJoBackend.dto.holiday.HolidayDto;

import Chopsticks.HairHaeJoBackend.entity.holiday.DesignerHoliday;
import Chopsticks.HairHaeJoBackend.entity.holiday.DesignerHolidayRepository;
import Chopsticks.HairHaeJoBackend.entity.user.Role;
import Chopsticks.HairHaeJoBackend.entity.user.User;
import Chopsticks.HairHaeJoBackend.entity.user.UserRepository;
import Chopsticks.HairHaeJoBackend.jwt.SecurityUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

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
        DesignerHoliday holiday =designerHolidayRepository.findBydesignerId(currentId);
        if(holiday!=null) addeach(holidayDto);
        else {
            try {
                designerHolidayRepository.save(holidayDto.toholiday(currentId));
            } catch (Exception e) {
                throw new RuntimeException("휴일 작성을 실패했습니다");
            }
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
    public void delete() throws IOException {


        long currentId= SecurityUtil.getCurrentMemberId();
        User user=userRepository.findById(currentId)
                .orElseThrow(() -> new RuntimeException("로그인 상태가 아닙니다"));

        if(user.getRole() != Role.ROLE_DESIGNER) throw new RuntimeException("헤어디자이너만 접근 가능합니다");
        try {

            DesignerHoliday holiday =designerHolidayRepository.findBydesignerId(currentId);
            if(holiday==null) throw new RuntimeException();
            else {

                designerHolidayRepository.delete(holiday);
            }

        }
        catch(Exception e) {
            throw new RuntimeException("휴일 삭제를 실패했습니다");
        }

    }
    public void deleteeach(HolidayDto holidayDto) throws IOException {


        long currentId= SecurityUtil.getCurrentMemberId();
        User user=userRepository.findById(currentId)
                .orElseThrow(() -> new RuntimeException("로그인 상태가 아닙니다"));
        DesignerHoliday holiday;
        if(user.getRole() != Role.ROLE_DESIGNER) throw new RuntimeException("헤어디자이너만 접근 가능합니다");
        try {

            holiday =designerHolidayRepository.findBydesignerId(currentId);
            if(holiday==null) throw new RuntimeException();
            String nowHoliday=DeleteHoliday(Arrays.asList(holidayDto.getHoliday().split(",")),Arrays.asList(holiday.getDesignerHoliday().split(",")));
            holiday.setDesignerHoliday(nowHoliday);
            designerHolidayRepository.save(holiday);
        }
        catch(Exception e) {
            throw new RuntimeException("휴일 삭제를 실패했습니다");
        }






    }
    public void addeach(HolidayDto holidayDto) throws IOException {


        long currentId= SecurityUtil.getCurrentMemberId();
        User user=userRepository.findById(currentId)
                .orElseThrow(() -> new RuntimeException("로그인 상태가 아닙니다"));
        DesignerHoliday holiday;

        if(user.getRole() != Role.ROLE_DESIGNER) throw new RuntimeException("헤어디자이너만 접근 가능합니다");
        try {

            holiday =designerHolidayRepository.findBydesignerId(currentId);
            if(holiday==null) throw new RuntimeException();
            String nowHoliday=AddHoliday(Arrays.asList(holidayDto.getHoliday().split(",")),Arrays.asList(holiday.getDesignerHoliday().split(",")));
            holiday.setDesignerHoliday(nowHoliday);
            System.out.println(nowHoliday);
            designerHolidayRepository.save(holiday);

        }
        catch(Exception e) {
            throw new RuntimeException("휴일 추가를 실패했습니다");
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
            ReturnData.add(new HolidayDto(s));
        }
        return ReturnData;

    }

    private String DeleteHoliday(List<String> deleteholiday,List<String> holiday) {


        Iterator<String> it = holiday.iterator();
        List<String> newHoliday= new ArrayList<>();
        while (it.hasNext()) {
            String item = it.next();
            boolean checkdelete=false;
            for(String Delete:deleteholiday) {
                if(item.equals(Delete))  {
                    checkdelete=true;
                }
            }
            if(!checkdelete)  newHoliday.add(item);


        }


        String[] returnlist= newHoliday.toArray(new String[0]);
        return String.join(",", returnlist);

    }

    private String AddHoliday(List<String> Addholiday,List<String> holiday) {



        List<String> newHoliday= new ArrayList<>();

        newHoliday.addAll(holiday);
        for(String add: Addholiday) {
                boolean redundancy=false;
                for(String Holiday:holiday) {
                    if(Holiday.equals(add)) redundancy=true;
                }
                if(!redundancy) newHoliday.add(add);
        }





        String[] returnlist= newHoliday.toArray(new String[0]);
        return String.join(",", returnlist);

    }
}
