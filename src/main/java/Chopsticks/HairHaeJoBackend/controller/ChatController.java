package Chopsticks.HairHaeJoBackend.controller;

import Chopsticks.HairHaeJoBackend.dto.APIMessages;
import Chopsticks.HairHaeJoBackend.dto.chat.ChatMessageRequestDto;
import Chopsticks.HairHaeJoBackend.dto.report.ReportRequestDto;
import Chopsticks.HairHaeJoBackend.service.ChatService;
import Chopsticks.HairHaeJoBackend.service.S3UploadService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate template;
    private final ChatService chatService;
    private final S3UploadService s3UploadService;

    //채팅방 조회 or 생성
    @PostMapping("/chat")
    public ResponseEntity<APIMessages> getChatRoom(@RequestParam Long userId) {
        APIMessages messages = APIMessages.builder()
            .success(true)
            .message("채팅방 조회 완료")
            .data(chatService.getChatRoom(userId))
            .build();
        return ResponseEntity.ok(messages);
    }

    //채팅방 리스트 조회
    @GetMapping("/chat")
    public ResponseEntity<APIMessages> getChatRoomList() {
        APIMessages messages = APIMessages.builder()
            .success(true)
            .message("채팅방 리스트 조회 완료")
            .data(chatService.getChatRoomList())
            .build();
        return ResponseEntity.ok(messages);
    }

    //채팅 내역 조회
    @GetMapping("/chat/history")
    public ResponseEntity<APIMessages> getChatHistory(@RequestParam Long roomId) {
        APIMessages messages = APIMessages.builder()
            .success(true)
            .message("메시지 내역 조회 완료")
            .data(chatService.getMessageList(roomId))
            .build();
        return ResponseEntity.ok(messages);
    }

    //채팅 이미지 업로드
    @PostMapping("/chat/image")
    public ResponseEntity<APIMessages> uploadChatImage(
        @RequestPart(value = "chatImage") MultipartFile image)
        throws IOException {
        APIMessages messages = APIMessages.builder()
            .success(true)
            .message("이미지 채팅 저장 완료")
            .data(s3UploadService.upload(image))
            .build();
        return ResponseEntity.ok(messages);
    }

    //메시지 전송
    @MessageMapping("/chat/message")
    public void message(ChatMessageRequestDto message) {
        template.convertAndSend("/sub/chat/" + message.getRoomId(),
            chatService.saveMessage(message));
    }

    //채팅방 나가기
    @DeleteMapping("/chat/leave")
    public ResponseEntity<APIMessages> leaveChatRoom(@RequestParam Long roomId) {
        chatService.leaveChatRoom(roomId);
        APIMessages messages = APIMessages.builder()
            .success(true)
            .message("채팅방 나가기 완료")
            .build();
        return ResponseEntity.ok(messages);
    }
}
