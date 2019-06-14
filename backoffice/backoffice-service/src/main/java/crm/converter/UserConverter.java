package crm.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import crm.entity.LifeCycleState;
import crm.entity.User;
import crm.model.OrganisationRequest;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface UserConverter {

  @Mapping(target = "password", source = "password")
  User toEntity(OrganisationRequest s,
      String password, LifeCycleState state);
}
