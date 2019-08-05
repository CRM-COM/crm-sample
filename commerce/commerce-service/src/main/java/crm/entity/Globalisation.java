package crm.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@EqualsAndHashCode
@MappedSuperclass
public class Globalisation {

    @GeneratedValue
    @Id
    private Long id;

    /**
     * iso6391LanguageCode
     */
    private String language;

    private String name;

    private String description;
}
