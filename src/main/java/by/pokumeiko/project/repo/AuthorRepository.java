package by.pokumeiko.project.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import by.pokumeiko.project.entity.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

	public Page<Author> findByIdNotOrderByFnameAscInameAscOnameAsc (Long id, Pageable pageable);
	
	public List<Author> findByIdNotOrderByFnameAscInameAscOnameAsc (Long id);

	public Author findByIdIs (Long id);
}
