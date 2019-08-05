package crm.converter;

import crm.entity.OfferPrice;
import crm.model.OfferPriceDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = TechProviderConverter.class)
public interface OfferPriceConverter {

    List<OfferPriceDto> toDtos(Iterable<? extends OfferPrice> offerPrices);

    @Mappings({
            @Mapping(target = "currencySymbol", ignore = true)
    })
    OfferPriceDto toDto(OfferPrice entity);
}
