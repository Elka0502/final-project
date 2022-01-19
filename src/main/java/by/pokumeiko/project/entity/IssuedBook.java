package by.pokumeiko.project.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class IssuedBook {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@Column(name = "date_order")
	@NotEmpty
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date dateOrd;
	@Column(name = "id_user")
	@NotEmpty
	private Long idUser;
	@Column(name = "id_book")
	@NotEmpty
	private Long idBook;
	@Column(name = "countbook")
	@NotEmpty
	private Integer countBook;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_book", insertable = false, updatable = false)
    private Book book;

	public IssuedBook() {
    }

	@Override
	public String toString() {
		return "orderBook [id=" + id + ", dateOrder=" + dateOrd + ", idUser=" + idUser + ", idBook=" + idBook
				+ ", countBook=" + countBook + "]";
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDateOrd() {
		return dateOrd;
	}

	public void setDateOrd(Date dateOrd) {
		this.dateOrd = dateOrd;
	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public Long getIdBook() {
		return idBook;
	}

	public void setIdBook(Long idBook) {
		this.idBook = idBook;
	}

	public Integer getCountBook() {
		return countBook;
	}

	public void setCountBook(Integer countBook) {
		this.countBook = countBook;
	}
    
	public String getStringBook(){
        String strBook ="";
        String authorFIO = "";
        for (Author author: book.getAuthors()) {
        	strBook = strBook + author.getFio() + " ";
        	if (!author.getOname().equals("")) {
        		authorFIO = "/ " + author.getIname().substring(0, 1) + "." + author.getOname().substring(0, 1) + ". " + author.getFname() + ". ";
        	} else {
        		authorFIO = "/ " + author.getIname().substring(0, 1) + ". " + author.getFname()+ ". ";
        	}
        }
        
        strBook = strBook + book.getName() + authorFIO;
        
        if (!book.getPublishingName().equals(" ")) {
        	strBook = strBook + " - " + book.getPublishingName() + ", ";
        } else strBook = strBook + " - ";
        
        strBook = strBook + book.getYear() + ".";
        return strBook;
    } 
	
}
