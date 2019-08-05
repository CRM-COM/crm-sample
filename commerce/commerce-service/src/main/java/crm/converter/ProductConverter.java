package crm.converter;

import crm.entity.Product;
import crm.model.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = {OfferConverter.class})
public interface ProductConverter {
    List<ProductDto> toDtos(Iterable<? extends Product> products);

    @Mappings({
            @Mapping(target = "productSku", ignore = true)
    })
    ProductDto toDto(Product entity);
}
