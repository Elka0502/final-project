package by.pokumeiko.project.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import by.pokumeiko.project.entity.Subject;

public interface SubjectRepository extends  JpaRepository<Subject, Long>  {
	
	public Page<Subject> findByIdNotOrderByName (Long id, Pageable pageable);
}
