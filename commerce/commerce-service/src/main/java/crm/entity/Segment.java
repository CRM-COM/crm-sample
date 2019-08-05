package crm.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Segment {
	@GeneratedValue
	@Id
    private int id;
 
	@Column(length=512,nullable=false)
	private String name;

}
