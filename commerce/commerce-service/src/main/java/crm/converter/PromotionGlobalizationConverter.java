package crm.converter;

import crm.entity.PromotionGlobalisation;
import crm.model.PromotionGlobalisationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface PromotionGlobalizationConverter {
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "promotion", ignore = true)
    })
    PromotionGlobalisation toEntity(PromotionGlobalisationDto promotionGlobalisationDto);
}
