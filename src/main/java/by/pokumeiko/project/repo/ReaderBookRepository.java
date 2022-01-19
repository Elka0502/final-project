package by.pokumeiko.project.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import by.pokumeiko.project.entity.ReaderBook;

public interface ReaderBookRepository extends JpaRepository<ReaderBook, Long> {

	public Page<ReaderBook> findAllByIdUserAndIdStatusInOrderByIdStatusAscDateBeginAscDateEndAsc (Long id, Set<Long> idStatus, Pageable pageable);	
	
	public List<ReaderBook> findAllByIdUserAndIdStatusInOrderByIdStatusAscDateBeginAscDateEndAsc (Long id, Set<Long> idStatus);	
	
	public Page<ReaderBook> findReaderBookByOrders_Id(Long idOrder, Pageable pageable);	
	
	public List<ReaderBook> findByIdUser(Long id);	
	
	public List<ReaderBook> findByIdBook(Long id);
}
