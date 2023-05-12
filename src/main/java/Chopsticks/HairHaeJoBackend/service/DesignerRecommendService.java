package Chopsticks.HairHaeJoBackend.service;

import Chopsticks.HairHaeJoBackend.dto.designer.RecommendResponseDto;
import Chopsticks.HairHaeJoBackend.entity.user.User;
import Chopsticks.HairHaeJoBackend.entity.user.UserRepository;
import Chopsticks.HairHaeJoBackend.jwt.SecurityUtil;
import Chopsticks.HairHaeJoBackend.entity.designer.DesignerRecommendRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DesignerRecommendService {

    private final DesignerRecommendRepository designerRecommendRepository;
    private final UserRepository userRepository;

    public List<RecommendResponseDto> getRecommendedDesigners(String region) {
        User user = getCurrentUser();
        List<Long> recommendDesignerIdList = getRecommendedDesignerIdList(user.getGender(),
            user.getAge(), region);
        List<RecommendResponseDto> list = new ArrayList<>();

        for (Long id : recommendDesignerIdList) {
            User designer = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("디자이너 정보가 없습니다." ));
            RecommendResponseDto responseDto = RecommendResponseDto.builder()
                .designerId(designer.getId())
                .Name(designer.getName())
                .ProfileImage(designer.getProfileImage()).build();
            list.add(responseDto);
        }

        return list;
    }

    private List<Long> getRecommendedDesignerIdList(int gender, int age, String region) {
        HashMap<Long, Long> count = new HashMap<>();

        List<Long> designers = designerRecommendRepository.getDesignersByRegion(region);
        for (Long id : designers) {
            count.put(id, 0L);
        }

        List<Object[]> reservation = designerRecommendRepository.countReservation(gender, age,
            region);
        for (Object[] obj : reservation) {
            Long id = Long.parseLong(String.valueOf(obj[0]));
            Long cnt = Long.parseLong(String.valueOf(obj[1]));
            count.put(id, count.get(id) + cnt * 3);
        }

        List<Object[]> like = designerRecommendRepository.countLike(gender, age, region);
        for (Object[] obj : like) {
            Long id = Long.parseLong(String.valueOf(obj[0]));
            Long cnt = Long.parseLong(String.valueOf(obj[1]));
            count.put(id, count.get(id) + cnt);
        }

        List<Object[]> review = designerRecommendRepository.countReview(gender, age, region);
        for (Object[] obj : review) {
            Long id = Long.parseLong(String.valueOf(obj[0]));
            Long cnt = Long.parseLong(String.valueOf(obj[1]));
            count.put(id, count.get(id) + cnt * 5);
        }

        List<Long> designerList = new ArrayList<>(count.keySet());
        Collections.sort(designerList, (o1, o2) -> (int) (count.get(o2) - count.get(o1)));

        return getTop10(designerList);
    }

    private List<Long> getTop10(List<Long> list){
        if (list.size() > 10) {
            list.subList(10, list.size()).clear();
        }
        return list;
    }

    private User getCurrentUser() {
        User user = userRepository.findById(SecurityUtil.getCurrentMemberId())
            .orElseThrow(() -> new RuntimeException("로그인 정보가 없습니다." ));
        return user;
    }
}
