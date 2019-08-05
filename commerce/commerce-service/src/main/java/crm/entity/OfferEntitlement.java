package crm.entity;

import crm.model.DeviceType;
import crm.model.ExpirationType;
import crm.model.PeriodType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
public class OfferEntitlement {

	@Id
	private Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
    private Date availableOn;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expiresOn;
    
    private Integer maximumInstances;

    private Integer maxmumAccessLimit;
    
    private Boolean offlineAccess;

    private Integer maxDevices;
    
    private Integer rentalPeriod;

    @Enumerated(EnumType.STRING)
    private PeriodType rentalPeriodType;
    
    private Integer rentalGrace;

    @Enumerated(EnumType.STRING)
    private PeriodType rentalGracePeriodType;

    private Integer freePeriod;

    @Enumerated(EnumType.STRING)
    private PeriodType freePeriodType;
    
    private Integer validityPeriod;

    @Enumerated(EnumType.STRING)
    private PeriodType validityPeriodType;
    
    @Enumerated(EnumType.STRING)
    private ExpirationType ownershipExpirationType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date ownershipExpiration;
    
	@ElementCollection
	@Enumerated(EnumType.STRING)
	private Set<DeviceType> allowedDeviceType;

	@MapsId
	@OneToOne(optional=false)
	private Offer offer;

}
