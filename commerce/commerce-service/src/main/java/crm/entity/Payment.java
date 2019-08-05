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
public class Payment {
	@GeneratedValue 
	@Id
    private Long id;

    @Column(unique = true, length=36, nullable=false)
    private String externalId;
	
    @CreatedDate
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @Column(nullable = false)
    private String paymentReference;
    
    @Column(nullable=false, length=3)
    private String currency;
    
    private double netPricePaid;
    private double grossPricePaid;
    
    @Column(length=32)
    private String coupon;

    @Column(nullable = false)
    private String member;
    
    private String product;
    
    private String type;
    
    private boolean refunded;

    private String techProvider;
}
