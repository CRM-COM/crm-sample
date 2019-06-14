package crm.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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

    private String title;

    private String forename;

    private String surname;

    private String nickname;

    private String avatarExternalId;

    private boolean isDeleted = false;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<MemberIdentity> memberIdentities = new HashSet<>();

    public Member(String externalId, String title, String forename, String surname, String nickname, String avatarExternalId, boolean isDeleted) {
        this.externalId = externalId;
        this.title = title;
        this.forename = forename;
        this.surname = surname;
        this.nickname = nickname;
        this.avatarExternalId = avatarExternalId;
        this.isDeleted = isDeleted;
    }
}
