package crm.entity;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.data.annotation.Immutable;

import lombok.Getter;
import lombok.Setter;

@Immutable
@Getter
@Setter
@Entity
public class Role {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true, length = 36)
    private String externalId;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(length = 1024)
    private String description;

    @Column(nullable = false, length=8192)
    private String permissions="";

    public Collection<String> getPermissions() {
        return Arrays.asList(permissions.split(","));
    }
    public void setPermissions(Collection<String> pm) {
        permissions = String.join(",",pm);
    }
}
