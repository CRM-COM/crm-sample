package crm.converter;

import crm.entity.OfferGlobalisation;
import crm.model.OfferGlobalisationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OfferGlobalizationConverter {

    List<OfferGlobalisationDto> toDtos(Iterable<? extends OfferGlobalisation> offerGlobalisations);

    OfferGlobalisationDto toDto(OfferGlobalisation entity);

    @Mappings({
            @Mapping(target="id", ignore = true),
            @Mapping(target="offer", ignore = true)
    })
    OfferGlobalisation toEntity(OfferGlobalisationDto offerGlobalisationDto);
}
