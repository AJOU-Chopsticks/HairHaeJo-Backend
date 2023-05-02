package Chopsticks.HairHaeJoBackend.entity.chat;

import Chopsticks.HairHaeJoBackend.entity.user.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    List<ChatRoom> findByClientIdAndClientStatus(User userId, boolean status);

    List<ChatRoom> findByDesignerIdAndDesignerStatus(User userId, boolean status);

    ChatRoom findByClientIdAndDesignerId(User clientId, User designerId);

    Boolean existsByClientIdAndDesignerId(User clientId, User designerId);

}
