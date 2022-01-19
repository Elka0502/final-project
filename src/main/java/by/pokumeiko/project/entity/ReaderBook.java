package by.pokumeiko.project.entity;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class ReaderBook {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@Column(name = "date_begin")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date dateBegin;
	@Column(name = "date_end")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date dateEnd;
	
	@Column(name = "id_user")
	@NotNull
	private Long idUser;
	@Column(name = "id_book")
	@NotNull
	private Long idBook;
	@Column(name = "id_status")
	@NotNull
	private Long idStatus;
	@Column(name = "id_place")
	@NotNull
	private Long idPlace;
		
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_status", insertable = false, updatable = false)
    private StatusReaderBook statusReaderBook;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_place", insertable = false, updatable = false)
    private Place place;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_book", insertable = false, updatable = false)
    private Book book;
	
	
	@OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "order_book",
            joinColumns = @JoinColumn(name = "id_reader_book"),
            inverseJoinColumns = @JoinColumn(name = "id_order")
    )
    private Set<Orders> orders = new HashSet<>();
	
	public ReaderBook() {
    }
	
	@Override
	public String toString() {
		return "ReaderBook [id=" + id + ", dateBegin=" + dateBegin + ", dateEnd=" + dateEnd + ", idUser=" + idUser
				+ ", idBook=" + idBook + ", idStatus=" + idStatus + ", idPlace=" + idPlace + ", statusReaderBook="
				+ statusReaderBook + ", book=" + book + "]";
	}
	
	public Set<Orders> getOrders() {
		return orders;
	}

	public void setOrders(Set<Orders> orders) {
		this.orders = orders;
	}

	public Place getPlace() {
		return place;
	}

	public void setPlace(Place place) {
		this.place = place;
	}

	public String getNamePlace() {
		return place.getName();
	}

	public StatusReaderBook getStatusReaderBook() {
		return statusReaderBook;
	}

	public void setStatusReaderBook(StatusReaderBook statusReaderBook) {
		this.statusReaderBook = statusReaderBook;
	}

	
	public Date getDateBegin() {
		return dateBegin;
	}

	public void setDateBegin(Date dateBegin) {
		this.dateBegin = dateBegin;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public String getDateEndString() {
		if (dateEnd == null) {
			return "";
		} else {
			Format formatter = new SimpleDateFormat("dd-MM-yyyy");
			return formatter.format(dateEnd);
		}
	}
	
	public String getDateBeginString() {
		if (dateBegin == null) {
			return "";
		} else {
			Format formatter = new SimpleDateFormat("dd-MM-yyyy");
			return formatter.format(dateBegin);
		}
	}
	
	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public Long getIdBook() {
		return idBook;
	}

	public void setIdBook(Long idBook) {
		this.idBook = idBook;
	}

	public Long getIdPlace() {
		return idPlace;
	}

	public void setIdPlace(Long idPlace) {
		this.idPlace = idPlace;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}
	
	public String getNameBook() {
		return book.getStringBook();
	}
	
	public String getNumBook() {
		return Long.toString( book.getId());
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public Long getIdStatus() {
		return idStatus;
	}

	public void setIdStatus(Long idStatus) {
		this.idStatus = idStatus;
	}

	public String getStatusName() {
	  return statusReaderBook.getName();
	}
}
