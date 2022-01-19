package by.pokumeiko.project.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Person {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@NotEmpty
	private String fname;
	@NotEmpty
	private String iname;
	private String oname;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthday;
	private String email;
	@Column(name = "passportnumber")
	private String passportNumber;
	@Column(name = "passportwho")
	private String passportWho;
	private String adress;
	private String phone;
	private Integer id_gender;
	
	public Person() {
    }
 
    public Person(Long id, String fname, String iname, String oname, Date birthday, 
    		String email, String passportNumber, String passportWho,
    		String adress, String phone, Integer id_gender) {
    	this.id = id;
		this.fname = fname;
		this.iname = iname;
		this.birthday = birthday;
		this.email = email;
		this.passportNumber = passportNumber;
		this.passportWho = passportWho;
		this.adress = adress;
		this.phone = phone;
		this.id_gender = id_gender;
	}

	@Override
	public String toString() {
		return "PersonData [id_gender=" + id_gender + ",id=" + id + ", fname=" + fname + ", iname=" + iname + ", oname=" + oname + ", birthday="
				+ birthday + ", email=" + email + ", passportNumber=" + passportNumber + ", passportWho=" + passportWho
				+ ", adress=" + adress + ", phone=" + phone + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getIname() {
		return iname;
	}

	public void setIname(String iname) {
		this.iname = iname;
	}

	public String getOname() {
		return oname;
	}

	public void setOname(String oname) {
		this.oname = oname;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public String getPassportWho() {
		return passportWho;
	}

	public void setPassportWho(String passportWho) {
		this.passportWho = passportWho;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getId_gender() {
		return id_gender;
	}

	public void setId_gender(Integer id_gender) {
		this.id_gender = id_gender;
	}
	
	public String getFio() {
		String personFio = "";
		if (!oname.equals("")) {
			personFio = fname + " " + iname + " " + oname;
		} else {
			personFio = fname + " " + iname;
		}
		return personFio;
	}
}
