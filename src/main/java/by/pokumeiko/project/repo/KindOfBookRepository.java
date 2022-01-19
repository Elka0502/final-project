package by.pokumeiko.project.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import by.pokumeiko.project.entity.KindOfBook;

public interface KindOfBookRepository extends JpaRepository<KindOfBook, Long> {
	
    public Page<KindOfBook> findByIdNotOrderByName (Long id, Pageable pageable);	

}
