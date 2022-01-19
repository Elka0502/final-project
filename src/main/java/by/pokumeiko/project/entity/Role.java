package by.pokumeiko.project.entity;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
     
    private String name;
    
    @Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + "]";
	}
    
    public Role(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Role() {
		
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