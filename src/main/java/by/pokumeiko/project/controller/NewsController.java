package by.pokumeiko.project.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import by.pokumeiko.project.Application;
import by.pokumeiko.project.entity.News;
import by.pokumeiko.project.entity.Pager;
import by.pokumeiko.project.entity.StatusNews;
import by.pokumeiko.project.service.NewsService;
import by.pokumeiko.project.service.StatusNewsService;

@Controller
@RequestMapping("/news")
public class NewsController {

	@Autowired
    private NewsService newsService;
	
	@Autowired
    private StatusNewsService statusNewsService;
	
	@Value("${file.path}")
    private String pathImage;
	
	@GetMapping(value = {"/", ""})
	public ModelAndView news(@RequestParam("pageSize") Optional<Integer> pageSize,
			 				 @RequestParam("page") Optional<Integer> page, Model model) {
	
		return pagination(pageSize, page);
	}
	
	@GetMapping(value = {"/show"})
	public ModelAndView newsShow(@RequestParam("pageSize") Optional<Integer> pageSize,
			 				 	 @RequestParam("page") Optional<Integer> page, Model model) {
		
		return paginationShow(pageSize, page);
	}
	
	/**Adding news*/  
	@PostMapping("/addNews")
	public String addNews(News news, @RequestParam(required = false, name = "file") MultipartFile file) {
		try {
			if (file!= null) {
				String filePath =  pathImage + file.getOriginalFilename();
				Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
				
				news.setImageName(file.getOriginalFilename());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		newsService.save(news);
		return "redirect:/news";
	}
	
		
	/**Edit news*/
	@PostMapping("/editNews")
	public String editNews (News news, @RequestParam(required = false, name = "file") MultipartFile file) {
		try {
			if ((file!= null) && (!file.getOriginalFilename().equals(""))) {
				
				String filePath = pathImage + file.getOriginalFilename();
				Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
				
				news.setImageName(file.getOriginalFilename());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		newsService.save(news);
	    return "redirect:/news";
	}
	
	/**Deleting news*/
	@GetMapping("delete/{id}")
	public String deleteNews(@PathVariable("id") Long id) {
		
		newsService.delete(id);
		
		return "redirect:/news";   
	}
	
	private ModelAndView pagination(Optional<Integer> pageSize, Optional<Integer> page) {
    	
		ModelAndView modelAndView = new ModelAndView("news");
        
        int evalPageSize = pageSize.orElse(Application.INITIAL_PAGE_SIZE);
        int evalPage = (page.orElse(0) < 1) ? Application.INITIAL_PAGE : page.get() - 1;
       
        Page<News> listNews = newsService.listAll(PageRequest.of(evalPage, evalPageSize));
       
      	Pager pager = new Pager(listNews.getTotalPages(), listNews.getNumber(), Application.BUTTONS_TO_SHOW);
   
      	List<StatusNews> listStatus = statusNewsService.listAll();
      	
      	modelAndView.addObject("listNews", listNews);
      	modelAndView.addObject("listStatus", listStatus);
      	modelAndView.addObject("pageSizes", Application.PAGE_SIZES);
      	modelAndView.addObject("selectedPageSize", evalPageSize);
      	modelAndView.addObject("pager", pager);
      	modelAndView.addObject("news", new News());
   
      	return modelAndView;
    }
	
	private ModelAndView paginationShow(Optional<Integer> pageSize, Optional<Integer> page) {
    	
		ModelAndView modelAndView = new ModelAndView("newsShow");
        
        int evalPageSize = pageSize.orElse(Application.INITIAL_PAGE_SIZE);
        int evalPage = (page.orElse(0) < 1) ? Application.INITIAL_PAGE : page.get() - 1;
       
        Page<News> listNews = newsService.listNews(PageRequest.of(evalPage, evalPageSize));
       
      	Pager pager = new Pager(listNews.getTotalPages(), listNews.getNumber(), Application.BUTTONS_TO_SHOW);
   
      	modelAndView.addObject("listNews", listNews);
       	modelAndView.addObject("pageSizes", Application.PAGE_SIZES);
      	modelAndView.addObject("selectedPageSize", evalPageSize);
      	modelAndView.addObject("pager", pager);
         
        return modelAndView;
    }
}