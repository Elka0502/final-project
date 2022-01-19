package by.pokumeiko.project.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import by.pokumeiko.project.entity.Language;
	 
public interface LanguageRepository extends JpaRepository<Language, Long> {
	
	public Page<Language> findByIdNotOrderByName (Long id, Pageable pageable);	
}
