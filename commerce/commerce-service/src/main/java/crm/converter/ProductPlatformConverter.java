package crm.converter;

import crm.entity.ProductPlatform;
import crm.model.ProductPlatformDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ProductPlatformConverter {
    ProductPlatformDto toDto(ProductPlatform entity);
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "product", ignore = true),
            @Mapping(target = "sequence", ignore = true)
    })
    ProductPlatform fromDto(ProductPlatformDto productPlatformDto);
}

