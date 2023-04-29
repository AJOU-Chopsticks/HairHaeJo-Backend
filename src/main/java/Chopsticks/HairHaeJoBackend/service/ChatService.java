package Chopsticks.HairHaeJoBackend.service;

import Chopsticks.HairHaeJoBackend.dto.chat.ChatMessageRequestDto;
import Chopsticks.HairHaeJoBackend.dto.chat.ChatMessageResponseDto;
import Chopsticks.HairHaeJoBackend.dto.chat.ChatRoomResponseDto;
import Chopsticks.HairHaeJoBackend.entity.chat.ChatMessage;
import Chopsticks.HairHaeJoBackend.entity.chat.ChatMessageRepository;
import Chopsticks.HairHaeJoBackend.entity.chat.ChatRoom;
import Chopsticks.HairHaeJoBackend.entity.chat.ChatRoomRepository;
import Chopsticks.HairHaeJoBackend.entity.user.Role;
import Chopsticks.HairHaeJoBackend.entity.user.User;
import Chopsticks.HairHaeJoBackend.entity.user.UserRepository;
import Chopsticks.HairHaeJoBackend.jwt.SecurityUtil;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

	private final ChatMessageRepository chatMessageRepository;
	private final ChatRoomRepository chatRoomRepository;
	private final UserRepository userRepository;

	public ChatRoomResponseDto getChatRoom(Long otherId) {
		User user = userRepository.findById(SecurityUtil.getCurrentMemberId())
			.orElseThrow(() -> new RuntimeException("로그인 정보가 없습니다."));
		User other = userRepository.findById(otherId)
			.orElseThrow(() -> new RuntimeException("로그인 정보가 없습니다."));

		if (user.getRole() == Role.ROLE_USER) {
			if (chatRoomRepository.existsByClientIdAndDesignerId(user, other)) {
				ChatRoomResponseDto responseDto = toChatRoomResponseDto(
					chatRoomRepository.findByClientIdAndDesignerId(user, other));
				return responseDto;
			} else {
				ChatRoomResponseDto responseDto = toChatRoomResponseDto(
					createChatRoom(user, other));
				return responseDto;
			}
		} else {
			if (chatRoomRepository.existsByClientIdAndDesignerId(other, user)) {
				ChatRoomResponseDto responseDto = toChatRoomResponseDto(
					chatRoomRepository.findByClientIdAndDesignerId(other, user));
				return responseDto;
			} else {
				ChatRoomResponseDto responseDto = toChatRoomResponseDto(
					createChatRoom(other, user));
				return responseDto;
			}
		}
	}

	public List<ChatRoomResponseDto> getChatRoomList() {
		User user = userRepository.findById(SecurityUtil.getCurrentMemberId())
			.orElseThrow(() -> new RuntimeException("로그인 정보가 없습니다."));
		List<ChatRoom> chatRooms = chatRoomRepository.findByClientIdOrDesignerId(user, user);

		List<ChatRoomResponseDto> chatRoomResponseDtoList = new ArrayList<>();
		for (ChatRoom room : chatRooms) {
			chatRoomResponseDtoList.add(toChatRoomResponseDto(room));
		}

		return chatRoomResponseDtoList;
	}

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

	public ChatMessageResponseDto saveMessage(ChatMessageRequestDto messageDto) {
		User user = userRepository.findById(messageDto.getWriterId())
			.orElseThrow(() -> new RuntimeException("유저 정보가 없습니다."));
		ChatRoom chatRoom = chatRoomRepository.findById(messageDto.getRoomId())
			.orElseThrow(() -> new RuntimeException("채팅방 정보가 없습니다."));
		ChatMessage chatMessage = ChatMessage.builder()
			.writerId(user)
			.chatRoomId(chatRoom)
			.imageMessage(messageDto.getImage())
			.textMessage(messageDto.getImage())
			.build();
		return toChatMessageResponseDto(chatMessageRepository.save(chatMessage));
	}

	public ChatRoom createChatRoom(User client, User designer) {
		ChatRoom chatRoom = ChatRoom.builder()
			.clientId(client)
			.designerId(designer)
			.build();
		return chatRoomRepository.save(chatRoom);
	}

	public ChatRoomResponseDto toChatRoomResponseDto(ChatRoom chatRoom) {
		ChatRoomResponseDto responseDto = ChatRoomResponseDto.builder()
			.chatRoomId(chatRoom.getId())
			.clientId(chatRoom.getClientId().getId())
			.designerId(chatRoom.getDesignerId().getId())
			.clientName(chatRoom.getClientId().getName())
			.designerName(chatRoom.getDesignerId().getName())
			.createdAt(chatRoom.getCreatedAt())
			.build();
		return responseDto;
	}

	public ChatMessageResponseDto toChatMessageResponseDto(ChatMessage message) {
		ChatMessageResponseDto responseDto = ChatMessageResponseDto.builder()
			.writerName(message.getWriterId().getName())
			.text(message.getTextMessage())
			.image(message.getImageMessage())
			.createdAt(message.getCreatedAt())
			.build();
		return responseDto;
	}
}
