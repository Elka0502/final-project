package by.pokumeiko.project.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import by.pokumeiko.project.entity.StatusNews;
	 
public interface StatusNewsRepository extends JpaRepository<StatusNews, Long> {
	
	
}
