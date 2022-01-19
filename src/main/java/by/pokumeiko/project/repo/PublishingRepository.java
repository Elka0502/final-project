package by.pokumeiko.project.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import by.pokumeiko.project.entity.Publishing;

public interface PublishingRepository extends JpaRepository<Publishing, Long> {
	
	  public Page<Publishing> findByIdNotOrderByName (Long id, Pageable pageable);
}
