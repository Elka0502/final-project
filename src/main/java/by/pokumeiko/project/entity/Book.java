package by.pokumeiko.project.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Entity
public class Book {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@NotEmpty
	private String name;
	@Column(name = "id_language")
	private Long idlanguage;
	@Column(name = "id_kind_of_book")
	private Long idkindOfBook;
	@Column(name = "id_publishing")
	private Long idpublishing;
	@Column(name = "id_subject")
	private Long idsubject;
	private Integer count;
	@Min(1900) 
    @Max(2100)
	private Integer year;
	@Column(insertable = false, updatable = false)
	private Integer available;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_language", insertable = false, updatable = false)
    private Language language;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_publishing", insertable = false, updatable = false)
    private Publishing publishing;   
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_kind_of_book", insertable = false, updatable = false)
    private KindOfBook kind_of_book;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_subject", insertable = false, updatable = false)
    private Subject subject;

	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "books_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors = new HashSet<>();

	public Set<Author> getAuthors() {
	
		List<Author> list = new ArrayList<Author>(authors) ; 
		Collections.sort(list, (o1, o2) -> o1.getFname().compareTo(o2.getFname()));
		
		authors = new LinkedHashSet<>(list); 
		
		return authors;
	}

	public void setAuthors(Set<Author> authors) {
		this.authors = authors;
	}

	public Integer getAvailable() {
		return available;
	}

	public String getKindOfBookName() {
		return kind_of_book.getName();
	}

	public String getSubjectName() {
		return subject.getName();
	}
	
	public String getLanguageName() {
		return language.getName();
	}

	public Publishing getPublishing() {
		return publishing;
	}

	public void setPublishing(Publishing publishing) {
		this.publishing = publishing;
	}

	public String getPublishingName() {
		return publishing.getName();
	}
	
	public Book() {
    }
 
    public Book(String name, Long idlanguage, 
    		Long idkindOfBook, Long idpublishing, Long idsubject, Integer count, Integer year) {
		this.name = name;
		
		if (idlanguage == null) {
			this.idlanguage = 1L;
		} else this.idlanguage = idlanguage;
		if (idkindOfBook == null) {
			this.idkindOfBook = 1L;
		} else this.idkindOfBook = idkindOfBook;
		if (idpublishing == null) {
			this.idpublishing = 1L;
		} else this.idpublishing = idpublishing;
		if (idsubject == null) {
			this.idsubject =  1L;
		} else this.idsubject = idsubject;
		if (count == null) {
			this.count = 0;
		} else this.count = count;
		this.year  = year;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", name=" + name + ", id_language=" + idlanguage + ",  id_kind_of_book=" + idkindOfBook
				+ ", id_publishing=" + idpublishing + ", id_subject="
				+ idsubject + ", count=" + count + ", year=" + year + "]";
	}
	
	 public Integer getYear() {
			return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}
		
	public Long getIdlanguage() {
		return idlanguage;
	}

	public void setIdlanguage(Long idlanguage) {
		this.idlanguage = idlanguage;
	}
	
	public Long getIdkindOfBook() {
		return idkindOfBook;
	}

	public void setIdkindOfBook(Long idkindOfBook) {
		this.idkindOfBook = idkindOfBook;
	}

	public Long getIdpublishing() {
		return idpublishing;
	}

	public void setIdpublishing(Long idpublishing) {
		this.idpublishing = idpublishing;
	}

	public Long getIdsubject() {
		return idsubject;
	}

	public void setIdsubject(Long idsubject) {
		this.idsubject = idsubject;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
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
	
	public void addAuthor(Author author){
		authors.add(author);
    }

	public void clearAuthor(){
		authors.clear();
    }
	
    public void addAuthor(String fname, String iname, String oname){
    	Author author = new Author();
    	author.setFname(fname);
    	author.setIname(iname);
    	author.setOname(oname);
       	authors.add(author);
    }
    
    public String getStringBook(){
        String strBook ="";
        String authorFIO = "";
        
        for (Author author: getAuthors()) {
        	strBook = strBook + (strBook.equals("") ? author.getFio() : ", " + author.getFio());
        	
        	authorFIO = (authorFIO.equals("") ? authorFIO : authorFIO + ", ") +
	        			(author.getOname().equals("") ?
	        			author.getIname().substring(0, 1) + ". " + author.getFname()+ "."  :
	        			author.getIname().substring(0, 1) + "." + author.getOname().substring(0, 1) + ". " + author.getFname() + ".");
           }
        
        strBook = (strBook.equals("") ? "" : strBook + " ") + name + (authorFIO.equals("") ? "" : "/ " + authorFIO);
        
        if (!getPublishingName().equals(" ")) {
        	strBook = strBook + " - " + getPublishingName() + ", ";
        } else strBook = strBook + " - ";
        
        strBook = strBook + year + ".";
        return strBook;
    } 
}