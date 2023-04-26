package Chopsticks.HairHaeJoBackend.service;

import Chopsticks.HairHaeJoBackend.entity.chat.ChatMessage;
import Chopsticks.HairHaeJoBackend.entity.chat.ChatMessageRepository;
import Chopsticks.HairHaeJoBackend.entity.chat.ChatRoom;
import Chopsticks.HairHaeJoBackend.entity.chat.ChatRoomRepository;
import Chopsticks.HairHaeJoBackend.entity.user.Role;
import Chopsticks.HairHaeJoBackend.entity.user.User;
import Chopsticks.HairHaeJoBackend.entity.user.UserRepository;
import Chopsticks.HairHaeJoBackend.jwt.SecurityUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    public ChatRoom getChatRoom(Long otherId){
        User user = userRepository.findById(SecurityUtil.getCurrentMemberId())
            .orElseThrow(() -> new RuntimeException("로그인 정보가 없습니다."));
        if(user.getRole() == Role.ROLE_USER ){
            if(chatRoomRepository.existsByClientIdAndDesignerId(user.getId(), otherId)){
                return chatRoomRepository.findByClientIdAndDesignerId(user.getId(), otherId);
            }
            else{
                return createChatRoom(user.getId(), otherId);
            }
        }
        else{
            if(chatRoomRepository.existsByClientIdAndDesignerId(otherId, user.getId())){
                return chatRoomRepository.findByClientIdAndDesignerId(otherId, user.getId());
            }
            else{
                return createChatRoom(otherId, user.getId());
            }
        }
    }

    public List<ChatRoom> getChatRoomList(){
        Long userId = SecurityUtil.getCurrentMemberId();
        return chatRoomRepository.findByClientIdOrDesignerId(userId, userId);
    }

    public List<ChatMessage> getMessageList(Long roomId){
        return chatMessageRepository.findAllByChatRoomId(roomId);
    }

    public ChatRoom createChatRoom(Long clientId, Long designerId){
        User client = userRepository.findById(clientId)
            .orElseThrow(() -> new RuntimeException("유저 정보가 없습니다."));
        User designer = userRepository.findById(designerId)
            .orElseThrow(() -> new RuntimeException("유저 정보가 없습니다."));
        ChatRoom chatRoom = ChatRoom.builder()
            .clientId(client)
            .designerId(designer)
            .build();
        return chatRoomRepository.save(chatRoom);
    }
}
