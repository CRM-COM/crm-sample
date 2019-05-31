package crm.entity;

import javax.persistence.*;

import crm.model.IdentityProvider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberIdentity {

  @Id
  @GeneratedValue
  private long id;

  @Column(nullable = false, unique = true, length = 36)
  private String externalId;

  @Enumerated(EnumType.STRING)
  private IdentityProvider identProvider;

  private String identValue;

  private String identChallenge;

  private String identAlgorithm;

  private boolean isVerified;

  @ManyToOne
  @JoinColumn
  private Member member;
}
