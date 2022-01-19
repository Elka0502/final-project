package by.pokumeiko.project.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "status_order")
public class StatusOrder {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotEmpty
    private String name;
    
    @Override
	public String toString() {
		return "StatusOrder [id=" + id + ", name=" + name + "]";
	}
    
	public Long getId() {
        return id;
    }
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setId(Long id) {
		this.id = id;
	}
     
   
}