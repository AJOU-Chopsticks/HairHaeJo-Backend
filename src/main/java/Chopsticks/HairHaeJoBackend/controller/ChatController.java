package Chopsticks.HairHaeJoBackend.controller;

import Chopsticks.HairHaeJoBackend.dto.APIMessages;
import Chopsticks.HairHaeJoBackend.dto.chat.ChatMessageRequestDto;
import Chopsticks.HairHaeJoBackend.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate template;
    private final ChatService chatService;

    @PostMapping ("/chat")
    public ResponseEntity<APIMessages> getChatRoom(@RequestParam Long userId){
        APIMessages messages = APIMessages.builder()
            .success(true)
            .message("채팅방 조회 완료")
            .data(chatService.getChatRoom(userId))
            .build();
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/chat")
    public ResponseEntity<APIMessages> getChatRoomList(){
        APIMessages messages = APIMessages.builder()
            .success(true)
            .message("채팅방 리스트 조회 완료")
            .data(chatService.getChatRoomList())
            .build();
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/chat/history")
    public ResponseEntity<APIMessages> getMessageHistory(@RequestParam Long roomId){
        APIMessages messages = APIMessages.builder()
            .success(true)
            .message("메시지 내역 조회 완료")
            .data(chatService.getMessageList(roomId))
            .build();
        return ResponseEntity.ok(messages);
    }

    @MessageMapping("/chat/message")
    public void message(ChatMessageRequestDto message){
        template.convertAndSend("/sub/chat/" + message.getRoomId(), chatService.saveMessage(message));
    }
}
