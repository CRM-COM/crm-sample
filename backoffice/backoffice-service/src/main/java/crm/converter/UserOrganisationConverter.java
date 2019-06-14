package crm.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import crm.entity.Organisation;
import crm.entity.Role;
import crm.entity.User;
import crm.entity.UserOrganisation;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface UserOrganisationConverter {

  @Mapping(target = "id", ignore = true)
  UserOrganisation toEntity(User user, Organisation organisation, Role role);
}
