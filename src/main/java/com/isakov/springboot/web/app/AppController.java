package com.isakov.springboot.web.app;

import com.isakov.springboot.AuthorizedPublisher;
import com.isakov.springboot.model.App;
import com.isakov.springboot.service.AppService;
import com.isakov.springboot.service.GenreServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AppController {

    @Autowired
    private AppService appService;

    @Autowired
    private GenreServiceImpl genreService;

    @RequestMapping("/")
    public String home(Model model) {
        return index(model);
    }

    @RequestMapping("/apps")
    public String index(Model model) {
        int publisherId = AuthorizedPublisher.id();
        List<App> apps = appService.getAllWithGenre(publisherId);
        model.addAttribute("apps", apps);
        return "apps";
    }

    @RequestMapping(value = "/apps/add", method = RequestMethod.GET)
    public String addApp(Model model){
        model.addAttribute("genres", genreService.getAll());
        model.addAttribute("app", new App());
        return "addApp";
    }

    @RequestMapping(value = "/apps/save", method = RequestMethod.POST)
    public String save(App app, @RequestParam int genreId){
        int publisherId = AuthorizedPublisher.id();
        appService.saveApp(app, publisherId, genreId);
        return "redirect:/apps";
    }

    @RequestMapping(value = "/apps/delete/{id}", method = RequestMethod.GET)
    public String deleteApp(@PathVariable("id") int appId) {
        int publisherId = AuthorizedPublisher.id();
        appService.deleteAppById(appId, publisherId);
        return "redirect:/apps";
    }
}
