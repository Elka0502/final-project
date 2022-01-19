package by.pokumeiko.project.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import by.pokumeiko.project.entity.Place;
import by.pokumeiko.project.repo.PlaceRepository;

@Service
@Transactional
public class PlaceService {
 
    @Autowired
    private PlaceRepository placeRepo;
    
    /**Displaying a list of places where books are issued*/
    public List<Place> listAll() {
      	return placeRepo.findAll();
    }
}
