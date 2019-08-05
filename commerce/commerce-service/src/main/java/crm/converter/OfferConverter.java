package crm.converter;

import crm.entity.Offer;
import crm.model.OfferDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = {OfferPriceConverter.class})
public interface OfferConverter {

    List<OfferDto> toDtos(Iterable<? extends Offer> productOffers);

    @Mappings({
            @Mapping(target = "externalReference", ignore = true)
    })
    OfferDto toDto(Offer entity);
}
