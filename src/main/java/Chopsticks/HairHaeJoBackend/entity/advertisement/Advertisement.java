package Chopsticks.HairHaeJoBackend.entity.advertisement;

import Chopsticks.HairHaeJoBackend.entity.user.User;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Advertisement")
public class Advertisement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ad_id")
	private Long advertiseId;

	@ManyToOne
	@JoinColumn(name = "advertiser_id")
	User advertiserId;

	@Column(name="tid")
	private String tid;

	@Column(name = "ad_price")
	private int adPrice;

	@Column(name = "location")
	private String location;

	@Column(name = "image")
	private String image;

	@Column(name = "body")
	private String body;

	@Column(name = "text")
	private String text;

	@Column(name = "start_date")
	private LocalDate startDate;

	@Column(name = "end_date")
	private LocalDate endDate;

	@Column(name = "state")
	private int state;
}
