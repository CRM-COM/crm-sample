package crm.converter;

import crm.entity.ProductGlobalisation;
import crm.model.ProductGlobalisationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductGlobalisationConverter {

    List<ProductGlobalisationDto> toDtos(Iterable<? extends ProductGlobalisation> productGlobalisations);

    ProductGlobalisationDto toDto(ProductGlobalisation entity);

    @Mappings({
            @Mapping(target="id", ignore = true),
            @Mapping(target="product", ignore = true)
    })
    ProductGlobalisation toEntity(ProductGlobalisationDto entity);
}
