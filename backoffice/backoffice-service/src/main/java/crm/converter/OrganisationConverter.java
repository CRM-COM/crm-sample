package crm.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import crm.entity.LifeCycleState;
import crm.entity.Organisation;
import crm.model.OrganisationDto;
import crm.model.OrganisationRequest;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface OrganisationConverter {
  @Mapping(target = "fullAddress", source = "organisation.location.fullAddress")
  OrganisationDto toDto(Organisation organisation);

  @Mappings({
      @Mapping(target = "location.fullAddress", source = "organisationRequest.fullAddress"),
      @Mapping(target = "name", source = "organisationRequest.organisationName"),
      @Mapping(target = "description", source = "organisationRequest.organisationDescription"),
      @Mapping(target = "location.latitude", source = "organisationRequest.latitude"),
      @Mapping(target = "location.longitude", source = "organisationRequest.longitude"),
      @Mapping(target = "location.areaCode", source = "organisationRequest.areaCode"),
      @Mapping(target = "location.zipPostcode", source = "organisationRequest.zipPostcode"),
      @Mapping(target = "location.countryCode", source = "organisationRequest.countryCode")
  })
  Organisation toEntity(OrganisationRequest organisationRequest, LifeCycleState state);
}
