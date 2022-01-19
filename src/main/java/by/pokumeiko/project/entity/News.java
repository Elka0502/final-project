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
import javax.validation.constraints.NotNull;
import org.springframework.context.annotation.PropertySource;
import org.springframework.format.annotation.DateTimeFormat;

@PropertySource("application.properties")
@Entity
public class News {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@NotNull
	@Column(name = "datenews")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateNews;
	@Column(name = "text1")
	private String text1;
	@Column(name = "text2")
	private String text2;
	@Column(name = "text3")
	private String text3;
	@NotNull
	@Column(name = "status")
	private Long status;
	@Column(name = "imagename")
	private String imageName;
		
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status", insertable = false, updatable = false)
    private StatusNews statusNews;
	
	public News() {
		
	}

	public News(Date dateNews, String text1, String text2, String text3, Long status, String imageName) {
		
		if (dateNews == null) {
			this.dateNews = new Date();
		} else {
			this.dateNews = dateNews;
		}
		this.text3 = text3;
		this.text1 = text1;
		this.text2 = text2;
		if (status == null) {
			this.status = 0L;
		} else {
			this.status = status;
		}
		this.imageName = imageName;
	}

	@Override
	public String toString() {
		return "News [id=" + id + ", dateNews=" + dateNews + ", text3=" + text3 + ", text1=" + text1
				+ ", text2=" + text2 + ", status=" + status + ", imageName=" + imageName + "]";
	}

	public StatusNews getStatusNews() {
		return statusNews;
	}

	public void setStatusNews(StatusNews statusNews) {
		this.statusNews = statusNews;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDateNews() {
		return dateNews;
	}

	public void setDateNews(Date dateNews) {
		this.dateNews = dateNews;
	}

	public String getText3() {
		return text3;
	}

	public void setText3(String text3) {
		this.text3 = text3;
	}

	public String getText1() {
		return text1;
	}

	public void setText1(String text1) {
		this.text1 = text1;
	}

	public String getText2() {
		return text2;
	}

	public void setText2(String text2) {
		this.text2 = text2;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}
	
	public String  getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	
	public String getStatusNewsName() {
		return statusNews.getName();
	}
}
