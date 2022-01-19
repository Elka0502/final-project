package by.pokumeiko.project.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import by.pokumeiko.project.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

	public Page<Book> findAllByOrderByIdDesc(Pageable pageable);
	
	public List<Book> findByIdlanguage(Long idlanguage);
	
	public List<Book> findByIdpublishing(Long idpublishing);
	
	public List<Book> findByIdsubject(Long idsubject);
	
	public List<Book> findByIdkindOfBook(Long idkindOfBook);
	
	public Integer countAllById(@Param("author_id") Long id);
	
	@Query(value = "SELECT * FROM catalog_search(:author_name, :book_name, cast (:id_publishing as integer), cast(:id_kind_of_book as integer), cast (:id_subject as integer), cast (:id_language as integer), :year)", nativeQuery = true)
	public List<Long> search(String author_name, String book_name, Long id_publishing, Long id_kind_of_book, Long id_subject, Long id_language, Integer year);
	
	@Query(value = "SELECT * FROM find_book(:numBook, :author_name, :book_name)", nativeQuery = true)
	public List<Long> find(String numBook, String author_name, String book_name);
}
