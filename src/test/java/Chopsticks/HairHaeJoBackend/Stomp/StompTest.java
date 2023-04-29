package Chopsticks.HairHaeJoBackend.Stomp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class StompTest {
	@Autowired
	private WebSocketService webSocketService;

	@LocalServerPort
	private int port;

	private WebSocketStompClient webSocketStompClient;
	private StompSession stompSession;

	@BeforeEach
	public void setup() throws InterruptedException, ExecutionException, TimeoutException {
		webSocketStompClient = new WebSocketStompClient(new SockJsClient(
			List.of(new WebSocketTransport(new StandardWebSocketClient()))));
		webSocketStompClient.setMessageConverter(new MappingJackson2MessageConverter());
		stompSession = webSocketStompClient.connect(
			String.format("ws://localhost:%d/ws", port),
			new StompSessionHandlerAdapter() {}).get(1, TimeUnit.SECONDS);
	}

	@Test
	public void testPublishAndSubscribe() throws Exception {
		String topic = "/topic/test";
		String payload = "test message";

		TestSubscriber testSubscriber = new TestSubscriber();
		stompSession.subscribe(topic, testSubscriber);

		webSocketService.send(topic, payload);

		testSubscriber.await();

		assertEquals(payload, testSubscriber.getLastPayload());
	}

	private static class TestSubscriber implements StompFrameHandler {
		private final CountDownLatch latch = new CountDownLatch(1);
		private String lastPayload;

		public void handleFrame(StompHeaders headers, Object payload) {
			lastPayload = payload.toString();
			latch.countDown();
		}

		public void await() throws InterruptedException {
			latch.await();
		}

		public String getLastPayload() {
			return lastPayload;
		}
	}
}
