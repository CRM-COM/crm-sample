package crm.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class OfferEntitlementDto {

    private Date availableOn;

    private Date expiresOn;

    private Integer maximumInstances;

    private Integer maxmumAccessLimit;

    private Integer maxDevices;

    private Integer rentalPeriod;

    private PeriodType rentalPeriodType;

    private Integer rentalGrace;

    private PeriodType rentalGracePeriodType;

    private Integer freePeriod;

    private PeriodType freePeriodType;

    private Integer validityPeriod;

    private PeriodType validityPeriodType;

    private ExpirationType ownershipExpirationType;

    private Date ownershipExpiration;

    private Set<DeviceType> allowedDeviceType = new HashSet<>();

}
