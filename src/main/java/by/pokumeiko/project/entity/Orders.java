package by.pokumeiko.project.entity;

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
public class Orders {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@Column(name = "date_order")
	@NotNull
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date dateOrd;
	@Column(name = "id_user")
	@NotNull
	private Long idUser;
	@Column(name = "id_status")
	@NotNull
	private Long idStatus;
	@Column(name = "num_order")
	private String numOrder;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_status", insertable = false, updatable = false)
    private StatusOrder statusOrder;

	
	@OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "order_book",
            joinColumns = @JoinColumn(name = "id_order"),
            inverseJoinColumns = @JoinColumn(name = "id_reader_book")
    )
    private Set<ReaderBook> readerBooks = new HashSet<>();
	
	
	public Orders() {
    }
	
	@Override
	public String toString() {
		return "Orders [id=" + id + ", dateOrd=" + dateOrd + ", idUser=" + idUser + ", idStatus=" + idStatus
				+ ", numOrder=" + numOrder + ", statusOrder=" + statusOrder + ", readerBooks=" + readerBooks + "]";
	}

	public StatusOrder getStatusOrder() {
		return statusOrder;
	}

	public void setStatusOrder(StatusOrder statusOrder) {
		this.statusOrder = statusOrder;
	}

	public Set<ReaderBook> getReaderBooks() {
		return readerBooks;
	}

	public void setReaderBooks(Set<ReaderBook> readerBooks) {
		this.readerBooks = readerBooks;
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

	public Long getIdStatus() {
		return idStatus;
	}

	public void setIdStatus(Long idStatus) {
		this.idStatus = idStatus;
	}

	public String getNumOrder() {
		return numOrder;
	}

	public void setNumOrder(String numOrder) {
		this.numOrder = numOrder;
	}
	
	public String getStatusName() {
		return statusOrder.getName();
	}
}
