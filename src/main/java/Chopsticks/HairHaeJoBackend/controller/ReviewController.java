package Chopsticks.HairHaeJoBackend.controller;

import Chopsticks.HairHaeJoBackend.dto.APIMessages;
import Chopsticks.HairHaeJoBackend.dto.review.DesignerReviewRequestDto;
import Chopsticks.HairHaeJoBackend.dto.review.ReviewRequestDto;
import Chopsticks.HairHaeJoBackend.repository.ReviewRepository;
import Chopsticks.HairHaeJoBackend.service.ReviewService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

	private final ReviewService reviewService;
	private final ReviewRepository reviewRepository;


	//후기 등록
	@PostMapping("/{reservationId}")
	public ResponseEntity<APIMessages> addreview(
		@RequestPart(value = "image") MultipartFile image,
		@RequestParam("jsonList") String jsonList,
		@PathVariable int reservationId) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		ReviewRequestDto requestDto = objectMapper.readValue(jsonList, new TypeReference<>() {
		});
		APIMessages messages = APIMessages.builder()
			.success(true)
			.message("후기 생성완료")
			.data(reviewService.saveReview(image,requestDto,reservationId))
			.build();
		return ResponseEntity.ok(messages);
	}

	//디자이너 후기 답변
	@PutMapping("/response/{reviewId}")
	public ResponseEntity<APIMessages> replyreview(@RequestBody DesignerReviewRequestDto requestDto,
		@PathVariable Integer reviewId)
	throws IOException {
		reviewService.designerReview(requestDto, reviewId);
		APIMessages messages = APIMessages.builder()
			.success(true)
			.message("후기에 답변 성공")
			.build();
		return ResponseEntity.ok(messages);
	}

	//디자이너의 리뷰리스트 조회
	@GetMapping("/list/{designerId}")
	public ResponseEntity<APIMessages> searchreviewlist(@PathVariable Long designerId)
	{
		APIMessages messages = APIMessages.builder()
			.success(true)
			.message("조회 성공")
			.data(reviewService.DesignerReviewSearch(designerId))
			.build();

		return ResponseEntity.ok(messages);
	}

	//리뷰 상세 조회

	@GetMapping("/{reviewId}")
	public ResponseEntity<APIMessages> searchreviewdetail(@PathVariable int reviewId)
	{
		APIMessages messages = APIMessages.builder()
			.success(true)
			.message("조회 성공")
			.data(reviewService.DetailReviewSearch(reviewId))
			.build();

		return ResponseEntity.ok(messages);

	}


}
