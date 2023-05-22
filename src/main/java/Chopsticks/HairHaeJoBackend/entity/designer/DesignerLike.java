package Chopsticks.HairHaeJoBackend.entity.designer;

import Chopsticks.HairHaeJoBackend.entity.user.User;
import java.time.LocalDateTime;
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
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Designer_like")
public class DesignerLike {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "like_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "client_id")
	private User clientId;

	@ManyToOne
	@JoinColumn(name = "designer_id")
	private User designerId;

	@CreationTimestamp
	@Column(name = "created_at")
	private LocalDateTime createdAt = LocalDateTime.now();
}
