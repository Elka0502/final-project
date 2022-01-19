package by.pokumeiko.project.entity;

import java.util.*;

import javax.persistence.*;
 
@Entity
@Table(name = "users")
public class User {
 
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    private String username;
    private String user_password;
    private Integer enabled = 1;
     
   

	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
    
	@Override
	public String toString() {
			return "User [id=" + id + ", username=" + username + ", user_password=" + user_password + ", enabled=" + enabled
					+ ", roles=" + roles + "]";
	}
	 
    public User() {
		
	}

	public User(String username, String user_password, Integer enabled, Set<Role> roles) {
		this.username = username;
		this.user_password = user_password;
		this.enabled = enabled;
		this.roles = roles;
	}

	public Long getId() {
        return id;
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUser_password() {
		return user_password;
	}

	public void setUser_password(String user_password) {
	  	this.user_password = user_password;
	}

	public boolean isEnabled() {
		return (enabled==1?true:false);
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void addRole(Role role){
        roles.add(role);
    }

    public void addRole(String name){
        Role role = new Role();
        role.setName(name);
        roles.add(role);
    }
}