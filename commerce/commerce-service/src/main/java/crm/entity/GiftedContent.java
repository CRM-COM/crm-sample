package crm.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Entity 
@Getter
@Setter
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
public class GiftedContent {
	@GeneratedValue 
	@Id
    private Long id;
   
	@CreatedDate
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

	@Column(length=36, nullable=false)
	private String memberExternalId;

	private String recipientEmail;

	@Column(length=36, nullable=false)
	private String recipientExternalId;

	@Column(length=36, nullable=false)
	private String contentExternalId;

	public GiftedContent(Date createdOn, String memberExternalId, String contentExternalId, String recipientEmail,
                         String recipientExternalId) {
		this.createdOn = createdOn;
		this.memberExternalId = memberExternalId;
		this.contentExternalId = contentExternalId;
		this.recipientEmail = recipientEmail;
		this.recipientExternalId = recipientExternalId;
	}
}

