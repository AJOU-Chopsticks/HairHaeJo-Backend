package Chopsticks.HairHaeJoBackend.entity.portfolio;

import Chopsticks.HairHaeJoBackend.entity.user.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    List<Portfolio> findByDesignerId(User userId);

    List<Portfolio> findByCategoryAndTagAndGender(String category, String tag, int gender);

    default List<Portfolio> findByStyle(String category, String tag, int gender) {
        if ("all".equals(category) && "all".equals(tag) && "all".equals(gender)) {
            return findAll();
        } else if ("all".equals(category) && "all".equals(tag)) {
            return findByGender(gender);
        } else if ("all".equals(category) && gender == 2) {
            return findByTag(tag);
        } else if ("all".equals(tag) && gender == 2) {
            return findByCategory(category);
        } else if ("all".equals(category)) {
            return findByTagAndGender(tag, gender);
        } else if ("all".equals(tag)) {
            return findByCategoryAndGender(category, gender);
        } else if (gender == 2) {
            return findByCategoryAndTag(category, tag);
        } else {
            return findByCategoryAndTagAndGender(category, tag, gender);
        }
    }

    List<Portfolio> findByCategory(String category);

    List<Portfolio> findByTag(String tag);

    List<Portfolio> findByGender(int gender);

    List<Portfolio> findByCategoryAndTag(String category, String tag);

    List<Portfolio> findByCategoryAndGender(String category, int gender);

    List<Portfolio> findByTagAndGender(String tag, int gender);

}
