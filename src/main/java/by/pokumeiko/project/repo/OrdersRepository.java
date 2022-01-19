package by.pokumeiko.project.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import by.pokumeiko.project.entity.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Long> {

	public Page<Orders> findByIdUserOrderByIdStatusAscDateOrdDescNumOrderDesc (Long id, Pageable pageable);
	
	public List<Orders> findByIdUser (Long id);
	
	public Orders findByReaderBooks_Id (Long id);
	
	public Orders findByNumOrder(String str);
	
	@Modifying
	@Query(value = "INSERT INTO order_book(id_order, id_reader_book) VALUES (:id_order, :id_reader_book)", nativeQuery = true)
	public void saveNewOrder(@Param("id_order") Long id_order, @Param("id_reader_book") Long id_reader_book);
}
