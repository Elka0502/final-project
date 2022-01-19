package by.pokumeiko.project.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Entity
public class Language{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@NotEmpty
	private String name;
 
	public Language() {
    }
 
    public Language(String name) {
    	this.name = name;
    }
    
    public Long getId() {
        return id;
    }

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isSelected(Long languageId){
        if (languageId != null) {
            return languageId.equals(id);
        }
        return false;
    } 
}