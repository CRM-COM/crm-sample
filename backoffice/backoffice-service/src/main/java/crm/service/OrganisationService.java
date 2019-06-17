package crm.service;

import static org.springframework.util.StringUtils.isEmpty;

import javax.transaction.Transactional;

import crm.entity.billing.AccountReceivable;
import crm.entity.billing.AccountReceivableType;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import crm.converter.OrganisationConverter;
import crm.converter.UserConverter;
import crm.converter.UserOrganisationConverter;
import crm.entity.LifeCycleState;
import crm.entity.Organisation;
import crm.entity.User;
import crm.exception.MicroserviceException;
import crm.model.OrganisationDto;
import crm.model.OrganisationRequest;
import crm.repository.OrganisationRepository;
import crm.repository.RoleRepository;
import crm.repository.UserOrganisationRepository;
import crm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class OrganisationService {

  private final OrganisationRepository organisationRepository;
  private final OrganisationConverter organisationConverter;
  private final UserRepository userRepository;
  private final UserConverter userConverter;
  private final RoleRepository roleRepository;
  private final UserOrganisationRepository userOrganisationRepository;
  private final UserOrganisationConverter userOrganisationConverter;

  public OrganisationDto addOrganisation(OrganisationRequest organisationRequest) {
    log.info("Trying to register organisation: {}", organisationRequest);
    User entity = userRepository.findByEmail(organisationRequest.getEmail()).orElse(null);
    if (entity != null) {
      log.info("User already exists in the system");
      throw new MicroserviceException(HttpStatus.BAD_REQUEST, "The user with email: " + organisationRequest.getEmail() + " already exists");
    } else {
      entity = createUser(organisationRequest);
    }
    var organisation = createOrganisation(organisationRequest);
    var role = roleRepository.findByName("admin");
    userOrganisationRepository.save(userOrganisationConverter.toEntity(entity, organisation, role));

    return organisationConverter.toDto(organisation);
  }

  private Organisation createOrganisation(OrganisationRequest organisationRequest) {
    var vatId = organisationRequest.getVatId();
    if(!isEmpty(vatId) && organisationRepository.existsOrganisationByVatId(vatId)){
      throw new MicroserviceException(HttpStatus.BAD_REQUEST, "The vatId: " + vatId + " already exists");
    }
    var organisation = organisationConverter.toEntity(organisationRequest, LifeCycleState.ACTIVE);
    final var accountReceivable = new AccountReceivable();
    accountReceivable.setExternalLinkId("dummy-external-link");
    accountReceivable.setType(AccountReceivableType.CRM);
    organisation.setAccountReceivable(accountReceivable);
    return organisationRepository.save(organisation);
  }

  private User createUser(OrganisationRequest organisationRequest) {
    var password = new BCryptPasswordEncoder().encode(organisationRequest.getPassword());
    var user = userConverter.toEntity(organisationRequest, password, LifeCycleState.ACTIVE);
    return userRepository.save(user);
  }
}