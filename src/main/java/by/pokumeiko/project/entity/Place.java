package by.pokumeiko.project.entity;

import javax.persistence.*;

@Entity
@Table(name = "place")
public class Place {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
     
    private String name;
    
    @Override
	public String toString() {
		return "Place [id=" + id + ", name=" + name + "]";
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