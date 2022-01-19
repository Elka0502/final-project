package by.pokumeiko.project.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import by.pokumeiko.project.entity.StatusNews;
import by.pokumeiko.project.repo.StatusNewsRepository;

@Service
@Transactional
public class StatusNewsService {
 
    @Autowired
    private StatusNewsRepository statusNewsRepo;
    
    /**Displaying a list of new's status */
    public List<StatusNews> listAll() {
      	return statusNewsRepo.findAll();
    }
}
