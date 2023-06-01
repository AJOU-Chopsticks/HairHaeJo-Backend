package Chopsticks.HairHaeJoBackend.service;


import Chopsticks.HairHaeJoBackend.dto.review.DesignerReviewRequestDto;
import Chopsticks.HairHaeJoBackend.dto.review.ReviewIdRequestDto;
import Chopsticks.HairHaeJoBackend.dto.review.ReviewRequestDto;

import Chopsticks.HairHaeJoBackend.dto.review.ReviewResponseDto;
import Chopsticks.HairHaeJoBackend.dto.review.ReviewSearchResponseDto;
import Chopsticks.HairHaeJoBackend.entity.reservation.Reservation;
import Chopsticks.HairHaeJoBackend.entity.reservation.ReservationRepository;
import Chopsticks.HairHaeJoBackend.entity.review.Review;
import Chopsticks.HairHaeJoBackend.entity.user.User;
import Chopsticks.HairHaeJoBackend.entity.user.UserRepository;
import Chopsticks.HairHaeJoBackend.jwt.SecurityUtil;
import Chopsticks.HairHaeJoBackend.entity.review.ReviewRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

	private final UserRepository userRepository;
	private final S3UploadService s3UploadService;
	private final ReviewRepository reviewRepository;
	private final ReservationRepository reservationRepository;

	//고객입장: 후기 등록
	public ReviewIdRequestDto saveReview(MultipartFile reviewImage, ReviewRequestDto requestDto, int reservationId)
		throws IOException{
		Reservation reservation = reservationRepository.findById(reservationId)
			.orElseThrow(() -> new RuntimeException("없는 예약 아이디입니다."));
		Review review;

		if(reservation.getClientId() == getCurrentUser().getId()) {
			if (reviewRepository.existsByReservationId(reservation)){
				throw new RuntimeException("이미 리뷰가 존재합니다.");
			}
			if (reviewImage.isEmpty()) {
				review = reviewRepository.save(
					requestDto.toReview(getCurrentUser(),reservation.getDesignerId(),reservation ,null));

			} else {
				review = reviewRepository.save(
					requestDto.toReview(getCurrentUser(), reservation.getDesignerId(),reservation,
						s3UploadService.upload(reviewImage)));
			}
			return new ReviewIdRequestDto(review.getReviewId());
		}
		else {throw new RuntimeException("본인이 작성한 후기가 아닙니다.");}
	}

	//디자이너입장: 후기 답변 등록
	public void designerReview (DesignerReviewRequestDto requestDto,Integer reviewId){
		Review review = reviewRepository.findById(reviewId)
			.orElseThrow(() -> new RuntimeException("없는 리뷰 아이디입니다."));

		if(review.getDesigner()==getCurrentUser()) {
			review.setReply(requestDto.getReply());
			review.setUpdatedAt(LocalDateTime.now());
			reviewRepository.save(review);
		} else{
			throw new RuntimeException("답변의 주인이 아닙니다.");
		}
	}

	//고객, 디자이너 모두: 후기리스트 조회

	public List <ReviewResponseDto> DesignerReviewSearch(Long designerId) {

		List<Review> reviews = reviewRepository.findByDesigner_Id(designerId);
		List<ReviewResponseDto> response = new ArrayList<>();
		for(Review review: reviews){
			response.add(toReviewResponseDto(review));
		}
		return response;

	}

	//후기 상세 조회
	public ReviewSearchResponseDto DetailReviewSearch(int ReviewId) {

		Review detailreview = reviewRepository.findById(ReviewId)
			.orElseThrow(() -> new RuntimeException("없는 리뷰아이디입니다."));

		return ReviewSearchResponseDto.of(detailreview);

	}



	private ReviewResponseDto toReviewResponseDto(Review review){

		ReviewResponseDto responseDto = ReviewResponseDto.builder()
			.reviewId(review.getReviewId())
			.clientId(review.getClient().getId())
			.reviewBody(review.getReviewBody())
			.reply(review.getReply())
			.rating(review.getRating())
			.reviewImage(review.getReviewImage())
			.build();
		return responseDto;
	}

	public Review toReview2(User client, User designer, String reply) {
		return Review.builder()
			.client(client)
			.designer(designer)
			.createdAt(LocalDateTime.now())
			.reply(reply)
			.build();
	}

	private User getCurrentUser() {
		User user = userRepository.findById(SecurityUtil.getCurrentMemberId())
			.orElseThrow(() -> new RuntimeException("로그인 정보가 없습니다."));
		return user;
	}





}
