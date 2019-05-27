package crm.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import crm.model.IdentityProvider;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Builder
public class MemberIdentity {

  @Id
  @GeneratedValue
  private long id;

  @Enumerated(EnumType.STRING)
  private IdentityProvider provider;

  private String cardNumber;

  private String identValue;

  @ManyToOne
  @JoinColumn
  private Member member;
}
