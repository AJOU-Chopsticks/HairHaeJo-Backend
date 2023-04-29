package Chopsticks.HairHaeJoBackend.entity.chat;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findAllByChatRoomId(ChatRoom roomId);

    ChatMessage findFirstByChatRoomIdOrderByCreatedAtDesc(ChatRoom chatRoom);
}
