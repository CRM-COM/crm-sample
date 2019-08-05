package crm.converter;

import crm.entity.OfferFeatures;
import crm.model.OfferFeaturesDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OfferFeaturesConverter {

    List<OfferFeaturesDto> toDtos(Iterable<? extends OfferFeatures> offerFeatures);

    OfferFeaturesDto toDto(OfferFeatures entity);
}
