package crm.entity;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

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

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<MemberIdentity> memberIdentities = new HashSet<>();

    public Member(String externalId, String name, String email) {
        this.externalId = externalId;
        this.name = name;
        this.email = email;
    }

}
