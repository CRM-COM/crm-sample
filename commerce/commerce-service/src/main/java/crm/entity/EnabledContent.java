package crm.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
public class EnabledContent {
	@GeneratedValue 
	@Id
    private Long id;
   
	@CreatedDate
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    
	@Column(length=36, nullable=false)
	private String contentExternalId;
	
	@ManyToOne(optional = false)
	private ProductEnablement productEnablement;

}
