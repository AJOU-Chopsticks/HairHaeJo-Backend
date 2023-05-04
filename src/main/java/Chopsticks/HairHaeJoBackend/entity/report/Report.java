package Chopsticks.HairHaeJoBackend.entity.report;

import Chopsticks.HairHaeJoBackend.entity.user.User;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="Report")
public class Report {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "report_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "reporter_id")
	private User reporterId;

	@ManyToOne
	@JoinColumn(name = "report_target_id")
	private User targetId;

	@Enumerated(EnumType.STRING)
	@Column(name = "report_type")
	private reportType reportType;

	@Column(name = "report_reason")
	private String reportReason;

	@CreationTimestamp
	@Column(name = "created_at")
	private LocalDateTime createdAt = LocalDateTime.now();
	public enum reportType{
		ARTICLE, CHAT, REVIEW
	}
}
