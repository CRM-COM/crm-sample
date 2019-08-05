package crm.converter;

import crm.entity.OfferEntitlement;
import crm.model.OfferEntitlementDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OfferEntitlementConverter {

    List<OfferEntitlementDto> toDtos(Iterable<? extends OfferEntitlement> offerEntitlements);

    OfferEntitlementDto toDto(OfferEntitlement entity);

    @Mappings({
            @Mapping(target = "availableOn", ignore = true),
            @Mapping(target = "expiresOn", ignore = true),
            @Mapping(target = "maximumInstances", ignore = true),
            @Mapping(target = "maxmumAccessLimit", ignore = true),
            @Mapping(target = "maxDevices", ignore = true),
            @Mapping(target = "rentalPeriod", ignore = true),
            @Mapping(target = "rentalPeriodType", ignore = true),
            @Mapping(target = "rentalGrace", ignore = true),
            @Mapping(target = "rentalGracePeriodType", ignore = true),
            @Mapping(target = "ownershipExpirationType", ignore = true),
            @Mapping(target = "allowedDeviceType", ignore = true),
            @Mapping(target = "ownershipExpiration", ignore = true)
    })
    OfferEntitlementDto reduceDto(OfferEntitlementDto original);

}