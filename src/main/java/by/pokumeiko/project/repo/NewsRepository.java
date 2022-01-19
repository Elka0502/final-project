package by.pokumeiko.project.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import by.pokumeiko.project.entity.News;
	 
public interface NewsRepository extends JpaRepository<News, Long> {
	
	public Page<News> findAllByOrderByStatusDescDateNewsDesc (Pageable pageable);	
	
	public Page<News> findByStatusOrderByDateNewsDesc (Long id, Pageable pageable);
}
