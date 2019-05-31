package crm.service;

import crm.config.MemberStream;
import crm.config.OrganisationStream;
import crm.entity.Member;
import crm.entity.MemberIdentity;
import crm.entity.MemberOrganisation;
import crm.event.MemberCreateEvent;
import crm.event.MemberOrganisationCreateEvent;
import crm.exception.MicroserviceException;
import crm.model.IdentityProvider;
import crm.repository.MemberIdentityRepository;
import crm.repository.MemberOrgansationRepository;
import crm.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberHandlerService {

  private final MemberRepository memberRepository;

  private final MemberIdentityRepository memberIdentityRepository;

  private final MemberOrgansationRepository organsationRepository;

  private final BCryptPasswordEncoder passwordEncoder;

  @StreamListener(MemberStream.INPUT)
  public void handleMember(@Payload MemberCreateEvent memberEvent) {
    log.info("Received member create event for id: {}", memberEvent.getExternalId());
    Member member = memberRepository.save(new Member(memberEvent.getExternalId(), memberEvent.getTitle(),
            memberEvent.getForename(), memberEvent.getSurname(), memberEvent.getNickname(),
            memberEvent.getAvatarExternalId(), false));
    saveIdentities(memberEvent, member);
  }

  private void saveIdentities(@Payload MemberCreateEvent memberEvent, Member member) {
    savePasswordIdentity(memberEvent, member);
    saveCreditCardIdentity(memberEvent, member);
    savePhoneIdentity(memberEvent, member);
  }

  private void savePasswordIdentity(MemberCreateEvent memberEvent, Member member) {
    memberIdentityRepository.save(MemberIdentity.builder()
        .identityProvider(IdentityProvider.PASSWORD)
        .identChallenge(memberEvent.getEmail())
        .identValue(passwordEncoder.encode(memberEvent.getPassword()))
        .member(member)
        .build());
  }

  private void saveCreditCardIdentity(MemberCreateEvent memberEvent, Member member) {
    memberIdentityRepository.save(MemberIdentity.builder()
        .identityProvider(IdentityProvider.CREDIT_CARD)
        .identChallenge(memberEvent.getEmail())
        .identValue(passwordEncoder.encode(memberEvent.getCardNumber()))
        .member(member)
        .build());
  }

  private void savePhoneIdentity(MemberCreateEvent memberEvent, Member member) {
    memberIdentityRepository.save(MemberIdentity.builder()
        .identityProvider(IdentityProvider.PHONE)
        .identChallenge(memberEvent.getEmail())
        .identValue(memberEvent.getPhoneNumber())
        .member(member)
        .build());
  }

  @StreamListener(OrganisationStream.INPUT)
  public void handleOrganisation(@Payload MemberOrganisationCreateEvent organisationCreateEvent) {
    log.info("Received member organisation create event for member id: {}",
        organisationCreateEvent.getMemberExternalId());

    organsationRepository.save(MemberOrganisation.builder()
        .externalId(organisationCreateEvent.getOrganisationExternalId())
        .member(getMemberOrThrow(organisationCreateEvent))
        .name(organisationCreateEvent.getOrganisationName())
        .build());

  }

  private Member getMemberOrThrow(MemberOrganisationCreateEvent organisationCreateEvent) {
    String memberId = organisationCreateEvent.getMemberExternalId();
    return memberRepository.findByExternalId(memberId)
        .orElseThrow(() -> new MicroserviceException(HttpStatus.NOT_FOUND,
            "Cannot find member with id " + memberId));
  }
}
