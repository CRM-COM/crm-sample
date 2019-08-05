package crm.converter;

import crm.entity.OfferPromotion;
import crm.entity.Promotion;
import crm.model.PromotionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = {PromotionGlobalizationConverter.class})
public interface PromotionConverter {

    List<PromotionDto> toDtos(Iterable<? extends Promotion> offerPromotions);

    @Mappings({
            @Mapping(target = "externalId", source = "externalId"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "description", source = "description"),
            @Mapping(target = "percentage", source = "percentage"),
            @Mapping(target = "fixedPrice", source = "fixedPrice"),
            @Mapping(target = "startDate", source = "startDate"),
            @Mapping(target = "endDate", source = "endDate"),
            @Mapping(target = "extraInstances", source = "extraInstances"),
            @Mapping(target = "coupon", source = "coupon"),
            @Mapping(target = "iso4127CurrencyCode", source = "iso4127CurrencyCode"),
    })
    PromotionDto toDto(Promotion entity);

    default PromotionDto toDto(OfferPromotion offerPromotion) {
        return toDto(offerPromotion.getPromotion());
    }
}
