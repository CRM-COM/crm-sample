package crm.entity;

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

    private String externalId;

    private String name;

    private String email;

    private String password;

    public Member(String externalId, String name, String email, String password) {
        this.externalId = externalId;
        this.name = name;
        this.email = email;
        this.password = password;
    }

}
