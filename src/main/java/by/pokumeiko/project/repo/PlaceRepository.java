package by.pokumeiko.project.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import by.pokumeiko.project.entity.Place;
	 
public interface PlaceRepository extends JpaRepository<Place, Long> {
	
	
}
