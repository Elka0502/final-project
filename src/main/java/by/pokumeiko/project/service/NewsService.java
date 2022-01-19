package by.pokumeiko.project.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.pokumeiko.project.Application;
import by.pokumeiko.project.entity.News;
import by.pokumeiko.project.repo.NewsRepository;

@Service
@Transactional
public class NewsService {
 
	private static final Logger LOGGER = LoggerFactory.getLogger(NewsService.class);
	
    @Autowired
    private NewsRepository newsRepo;
    
    /**Output a list of news  order by date*/
    public Page<News> listAll(Pageable pageable) {
      	return newsRepo.findAllByOrderByStatusDescDateNewsDesc(pageable);
    }
    
    /**Output a list of news where status = show order by date*/
    public Page<News> listNews(Pageable pageable) {
      	return newsRepo.findByStatusOrderByDateNewsDesc(1L, pageable);
    }
  
    public News save(News news) {
    	
    	News news_new = newsRepo.save(news);
    	
    	LOGGER.info(String.format("Пользователь idUser= %d сохранил новость id= %d в базе", Application.id_user, news.getId()));
    	
    	return news_new;
    }
    
    public News get(Long id) {
        return newsRepo.findById(id).get();
    }
     
    /**Deleting news from the database*/
    public void delete(Long id) {
    		
		newsRepo.deleteById(id);
		
		LOGGER.info(String.format("Пользователь idUser= %d удалил новость id= %d из базы", Application.id_user, id));
     }
}
