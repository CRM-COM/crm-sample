package crm.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import crm.model.OrganisationDto;
import crm.model.OrganisationRequest;
import crm.service.OrganisationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/backoffice")
public class OrganisationController {

  private final OrganisationService service;

  @PostMapping("/organisation/register")
  public OrganisationDto addOrganisation(@Valid OrganisationRequest organisationRequest) {
    return service.addOrganisation(organisationRequest);
  }
}
