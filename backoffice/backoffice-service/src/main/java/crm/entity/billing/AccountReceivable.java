package crm.entity.billing;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AccountReceivable {
  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false)
  private String externalLinkId;

  @Enumerated(EnumType.STRING)
  private AccountReceivableType type;
}
