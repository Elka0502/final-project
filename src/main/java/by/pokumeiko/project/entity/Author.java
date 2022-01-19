package by.pokumeiko.project.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;


@Entity
public class Author {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@NotEmpty 
	private String iname;
	@NotEmpty 
	private String fname;
	private String oname;
	
	@Column(insertable = false, updatable = false)
	private String fio;
	 
	public Author() {
    }
 
 	public Author(String iname, String fname, String oname) {
		this.iname = iname;
		this.fname = fname;
		this.oname = oname;
	}

	@Override
	public String toString() {
		return "Author [id=" + id + ", iname=" + iname + ", fname=" + fname + ", oname=" + oname + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIname() {
		return iname;
	}

	public void setIname(String iname) {
		this.iname = iname;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getOname() {
		return oname;
	}

	public void setOname(String oname) {
		this.oname = oname;
	}

	public String getFio() {
		return fio;
	}

	
}