package crm.controller;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserOrganisationController {

//  private final UserAccessor userAccessor;
//  private final UserOrganisationService service;
//
//  @PostMapping("/userOrganisation")
//  @ApiImplicitParam(name = "X-auth-token", value = "Authorization token", paramType = "header")
//  public void addUserOrganisation(@Valid UserOrganisationDto userOrganisationDto) {
//    service
//        .addUserOrganisation(userAccessor.getCurrentUser().getExternalId(), userOrganisationDto);
//  }
//
//  @GetMapping("/availableOrganisations")
//  @ApiImplicitParam(name = "X-auth-token", value = "Authorization token", paramType = "header")
//  public List<OrganisationDto> getAvailableOrganisations() {
//    return service
//        .getAvailableOrganisations(userAccessor.getCurrentUser().getExternalId());
//  }
}
