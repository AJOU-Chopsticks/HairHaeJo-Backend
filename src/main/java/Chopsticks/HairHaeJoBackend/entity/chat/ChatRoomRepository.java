package Chopsticks.HairHaeJoBackend.entity.chat;

import Chopsticks.HairHaeJoBackend.entity.user.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    List<ChatRoom> findByClientIdOrDesignerId(User clientId, User designerId);

    ChatRoom findByClientIdAndDesignerId(User clientId, User designerId);

    Boolean existsByClientIdAndDesignerId(User clientId, User designerId);

}
