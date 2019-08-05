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
@EntityListeners(AuditingEntityListener.class)
@Builder
public class ProductEnablement {
	@GeneratedValue 
	@Id
    private Long id;

	@Column(unique = true, length=36, nullable=false)
	private String externalId;
	@Column(length=36, nullable=false)
	private String productExternalId;
	@Column(length=36, nullable=false)
	private String subscriberExternalId;
	
    @CreatedDate
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date validFrom;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date validTo;

    @Temporal(TemporalType.TIMESTAMP)
    private Date freeTrialEnd;

    private int maximumInstances;
    private int instanceCount;

    @Builder.Default
    private boolean onlineAccessAllowed = true;
    private boolean offlineAccessAllowed;

    @Builder.Default
    private boolean enabled = true;
    private boolean pendingCancel;

    private String offerExternalId;
    private String offerExternalReference;

    private String techProvider;

}