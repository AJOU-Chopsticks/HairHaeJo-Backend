package Chopsticks.HairHaeJoBackend.service;

import Chopsticks.HairHaeJoBackend.dto.chat.ChatMessageRequestDto;
import Chopsticks.HairHaeJoBackend.dto.chat.ChatMessageResponseDto;
import Chopsticks.HairHaeJoBackend.dto.chat.ChatRoomResponseDto;
import Chopsticks.HairHaeJoBackend.dto.report.ReportRequestDto;
import Chopsticks.HairHaeJoBackend.entity.chat.ChatMessage;
import Chopsticks.HairHaeJoBackend.entity.chat.ChatMessage.Type;
import Chopsticks.HairHaeJoBackend.entity.chat.ChatMessageRepository;
import Chopsticks.HairHaeJoBackend.entity.chat.ChatRoom;
import Chopsticks.HairHaeJoBackend.entity.chat.ChatRoomRepository;
import Chopsticks.HairHaeJoBackend.entity.report.Report;
import Chopsticks.HairHaeJoBackend.entity.report.Report.reportType;
import Chopsticks.HairHaeJoBackend.entity.report.ReportRepository;
import Chopsticks.HairHaeJoBackend.entity.user.Role;
import Chopsticks.HairHaeJoBackend.entity.user.User;
import Chopsticks.HairHaeJoBackend.entity.user.UserRepository;
import Chopsticks.HairHaeJoBackend.jwt.SecurityUtil;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final ReportRepository reportRepository;

    //채팅방 생성 or 조회
    public ChatRoomResponseDto getChatRoom(Long otherId) {
        User user = getCurrentUser();
        User other = userRepository.findById(otherId)
            .orElseThrow(() -> new RuntimeException("상대방 정보가 없습니다."));

        if (user.getRole() == Role.ROLE_USER) {
            ChatRoom chatRoom = chatRoomRepository.findByClientIdAndDesignerId(user, other);
            if (chatRoom == null) {
                ChatRoomResponseDto responseDto = toChatRoomResponseDto(
                    createChatRoom(user, other));
                return responseDto;
            }
            chatRoom.setClientStatus(true);
            chatRoom.setDesignerStatus(true);
            ChatRoomResponseDto responseDto = toChatRoomResponseDto(chatRoom);
            return responseDto;
        } else {
            ChatRoom chatRoom = chatRoomRepository.findByClientIdAndDesignerId(other, user);
            if (chatRoom == null) {
                ChatRoomResponseDto responseDto = toChatRoomResponseDto(
                    createChatRoom(other, user));
                return responseDto;
            }
            chatRoom.setClientStatus(true);
            chatRoom.setDesignerStatus(true);
            ChatRoomResponseDto responseDto = toChatRoomResponseDto(chatRoom);
            return responseDto;
        }
    }

    //채팅방 리스트 조회
    public List<ChatRoomResponseDto> getChatRoomList() {
        User user = getCurrentUser();
        List<ChatRoom> chatRooms;
        if (user.getRole() == Role.ROLE_USER) {
            chatRooms = chatRoomRepository.findByClientIdAndClientStatus(user, true);
        } else {
            chatRooms = chatRoomRepository.findByDesignerIdAndDesignerStatus(user, true);
        }

        List<ChatRoomResponseDto> chatRoomResponseDtoList = new ArrayList<>();
        for (ChatRoom room : chatRooms) {
            chatRoomResponseDtoList.add(toChatRoomResponseDto(room));
        }
        return chatRoomResponseDtoList;
    }

    //채팅내역 조회
    public List<ChatMessageResponseDto> getMessageList(Long roomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
            .orElseThrow(() -> new RuntimeException("채팅방 정보가 없습니다."));
        List<ChatMessage> chatMessages = chatMessageRepository.findAllByChatRoomId(chatRoom);
        List<ChatMessageResponseDto> chatMessageResponseDtoList = new ArrayList<>();
        for (ChatMessage message : chatMessages) {
            chatMessageResponseDtoList.add(toChatMessageResponseDto(message));
        }
        return chatMessageResponseDtoList;
    }

    //채팅내역 저장
    public ChatMessageResponseDto saveMessage(ChatMessageRequestDto messageDto) {
        User user = userRepository.findById(messageDto.getWriterId())
            .orElseThrow(() -> new RuntimeException("유저 정보가 없습니다."));
        ChatRoom chatRoom = chatRoomRepository.findById(messageDto.getRoomId())
            .orElseThrow(() -> new RuntimeException("채팅방 정보가 없습니다."));
        ChatMessage chatMessage = ChatMessage.builder()
            .type(messageDto.getType())
            .writerId(user)
            .chatRoomId(chatRoom)
            .imageMessage(messageDto.getImage())
            .textMessage(messageDto.getText())
            .build();
        chatRoomRepository.save(chatRoom.updateTimeStamp());
        return toChatMessageResponseDto(chatMessageRepository.save(chatMessage));
    }

    //채팅방 나가기
    public void leaveChatRoom(Long roomId) {
        User user = getCurrentUser();
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
            .orElseThrow(() -> new RuntimeException("채팅방 정보가 없습니다."));
        chatMessageRepository.save(ChatMessage.builder()
            .type(Type.TYPE_INFO)
            .writerId(user)
            .chatRoomId(chatRoom)
            .textMessage(user.getName() + "님이 상담을 종료했습니다.")
            .build());
        chatRoomRepository.save(chatRoom.updateTimeStamp());
        if (user.getRole() == Role.ROLE_USER) {
            chatRoom.setClientStatus(false);
        } else {
            chatRoom.setDesignerStatus(false);
        }
        chatRoomRepository.save(chatRoom);
        if(!chatRoom.getClientStatus() && !chatRoom.getDesignerStatus()){
            chatRoomRepository.delete(chatRoom);
        }
    }

    //채팅방 생성
    private ChatRoom createChatRoom(User client, User designer) {
        User user = getCurrentUser();
        ChatRoom chatRoom = chatRoomRepository.save(ChatRoom.builder()
            .clientId(client)
            .designerId(designer)
            .build());
        chatMessageRepository.save(ChatMessage.builder()
            .type(Type.TYPE_INFO)
            .writerId(user)
            .chatRoomId(chatRoom)
            .textMessage(user.getName() + "님이 상담을 시작했습니다.")
            .build());
        return chatRoom;
    }

    //최근 채팅 내용 조회
    private String getLastMessage(ChatRoom chatRoom) {
        ChatMessage message = chatMessageRepository.findFirstByChatRoomIdOrderByCreatedAtDesc(
            chatRoom);
        if (message == null) {
            return null;
        } else if (message.getType() == Type.TYPE_IMAGE) {
            return "사진";
        } else {
            return message.getTextMessage();
        }
    }

    private User getCurrentUser() {
        User user = userRepository.findById(SecurityUtil.getCurrentMemberId())
            .orElseThrow(() -> new RuntimeException("로그인 정보가 없습니다."));
        return user;
    }

    private ChatRoomResponseDto toChatRoomResponseDto(ChatRoom chatRoom) {
        User client = chatRoom.getClientId();
        User designer = chatRoom.getDesignerId();
        ChatRoomResponseDto responseDto = ChatRoomResponseDto.builder()
            .chatRoomId(chatRoom.getId())
            .clientId(client.getId())
            .designerId(designer.getId())
            .clientName(client.getName())
            .designerName(designer.getName())
            .clientImage(client.getProfileImage())
            .designerImage(designer.getProfileImage())
            .updatedAt(chatRoom.getUpdatedAt())
            .lastMessage(getLastMessage(chatRoom))
            .build();
        return responseDto;
    }

    private ChatMessageResponseDto toChatMessageResponseDto(ChatMessage message) {
        ChatMessageResponseDto responseDto = ChatMessageResponseDto.builder()
            .type(message.getType())
            .writerName(message.getWriterId().getName())
            .text(message.getTextMessage())
            .image(message.getImageMessage())
            .createdAt(message.getCreatedAt())
            .build();
        return responseDto;
    }
}
