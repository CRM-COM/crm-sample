package crm.converter;

import crm.entity.TechProvider;
import crm.model.TechProviderDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TechProviderConverter {

    List<TechProviderDto> toDtos(Iterable<? extends TechProvider> techProviders);

    TechProviderDto toDto(TechProvider entity);
}
