package crm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Member {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, unique = true, length = 36)
    private String externalId;

    private String name;

    @Column(nullable = false, unique = true, length = 60)
    private String email;

    public Member(String externalId, String name, String email) {
        this.externalId = externalId;
        this.name = name;
        this.email = email;
    }

}
