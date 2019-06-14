package crm.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import crm.converter.OrganisationConverter;
import crm.converter.UserOrganisationConverter;
import crm.entity.Organisation;
import crm.entity.Role;
import crm.entity.User;
import crm.entity.UserOrganisation;
import crm.exception.MicroserviceException;
import crm.model.OrganisationDto;
import crm.model.UserOrganisationDto;
import crm.repository.OrganisationRepository;
import crm.repository.RoleRepository;
import crm.repository.UserOrganisationRepository;
import crm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserOrganisationService {


  private final OrganisationRepository organisationRepository;
  private final UserRepository userRepository;
  private final UserOrganisationRepository userOrganisationRepository;
  private final RoleRepository roleRepository;
  private final OrganisationConverter organisationConverter;
  private final UserOrganisationConverter userOrganisationConverter;

  @Transactional
  public void addUserOrganisation(String externalId, UserOrganisationDto userOrganisationDto) {
    UserOrganisation userOrganisation = userOrganisationConverter
        .toEntity(getUser(externalId), getOrganisation(userOrganisationDto), getRole(userOrganisationDto));
    log.info("Save userOrganisation: {}", userOrganisation);
    userOrganisationRepository.save(userOrganisation);
  }

  public List<OrganisationDto> getAvailableOrganisations(String userExternalId) {
    return userOrganisationRepository.findAllOrganisationsByUserExternalId(userExternalId).stream()
        .map(this::convertToDto)
        .collect(
            Collectors.toList());
  }

  private User getUser(String externalId) {
    return userRepository.findByExternalId(externalId);
  }

  private Organisation getOrganisation(UserOrganisationDto userOrganisationDto) {
    return organisationRepository.findByExternalId(userOrganisationDto.getOrganisationId())
        .orElseThrow(() -> new MicroserviceException(
            "Organisation with externalId: " + userOrganisationDto.getOrganisationId() + " was not found"));
  }

  private Role getRole(UserOrganisationDto userOrganisationDto) {
    return roleRepository.findByExternalId(userOrganisationDto.getRoleId())
        .orElseThrow(() -> new MicroserviceException(
            "Role with externalId: " + userOrganisationDto.getRoleId() + " was not found"));
  }

  private OrganisationDto convertToDto(Organisation organisation) {
    return organisationConverter.toDto(organisation);
  }

}
