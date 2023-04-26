package Chopsticks.HairHaeJoBackend.entity.chat;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    List<ChatRoom> findByClientIdOrDesignerId(Long clientId, Long designerId);

    ChatRoom findByClientIdAndDesignerId(Long clientId, Long designerId);

    Boolean existsByClientIdAndDesignerId(Long clientId, Long designerId);

}
