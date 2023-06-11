package Chopsticks.HairHaeJoBackend.entity.portfolio;

import Chopsticks.HairHaeJoBackend.entity.user.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    List<Portfolio> findByDesignerIdOrderByUpdatedAtDesc(User userId);

    List<Portfolio> findByCategoryAndTagAndGenderOrderByUpdatedAtDesc(String category, String tag, int gender);

    default List<Portfolio> findByStyle(String category, String tag, int gender) {
        if ("all".equals(category) && "all".equals(tag) && gender == 2) {
            return findAll();
        } else if ("all".equals(category) && "all".equals(tag)) {
            return findByGenderOrderByUpdatedAtDesc(gender);
        } else if ("all".equals(category) && gender == 2) {
            return findByTagOrderByUpdatedAtDesc(tag);
        } else if ("all".equals(tag) && gender == 2) {
            return findByCategoryOrderByUpdatedAtDesc(category);
        } else if ("all".equals(category)) {
            return findByTagAndGenderOrderByUpdatedAtDesc(tag, gender);
        } else if ("all".equals(tag)) {
            return findByCategoryAndGenderOrderByUpdatedAtDesc(category, gender);
        } else if (gender == 2) {
            return findByCategoryAndTagOrderByUpdatedAtDesc(category, tag);
        } else {
            return findByCategoryAndTagAndGenderOrderByUpdatedAtDesc(category, tag, gender);
        }
    }

    List<Portfolio> findByCategoryOrderByUpdatedAtDesc(String category);

    List<Portfolio> findByTagOrderByUpdatedAtDesc(String tag);

    List<Portfolio> findByGenderOrderByUpdatedAtDesc(int gender);

    List<Portfolio> findByCategoryAndTagOrderByUpdatedAtDesc(String category, String tag);

    List<Portfolio> findByCategoryAndGenderOrderByUpdatedAtDesc(String category, int gender);

    List<Portfolio> findByTagAndGenderOrderByUpdatedAtDesc(String tag, int gender);

}
