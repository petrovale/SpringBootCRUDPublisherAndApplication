package com.isakov.springboot.web.app;

import com.isakov.springboot.AuthorizedPublisher;
import com.isakov.springboot.model.App;
import com.isakov.springboot.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class AppController {

    @Autowired
    private AppService appService;

    @RequestMapping("/")
    public String home(Model model) {
        return index(model);
    }

    @RequestMapping("/apps")
    public String index(Model model) {
        long publisherId = AuthorizedPublisher.id();
        List<App> apps = appService.getAll(publisherId);
        model.addAttribute("apps", apps);
        return "apps";
    }

    @RequestMapping(value = "/apps/add", method = RequestMethod.GET)
    public String addApp(Model model){
        model.addAttribute("app", new App());
        return "addApp";
    }

    @RequestMapping(value = "/apps/save", method = RequestMethod.POST)
    public String save(App app){
        long publisherId = AuthorizedPublisher.id();
        appService.saveApp(app, publisherId);
        return "redirect:/apps";
    }

    @RequestMapping(value="/apps/edit/{id}", method=RequestMethod.GET)
    public String editApp(@PathVariable("id") Long appId, Model model){
        model.addAttribute("app", appService.findById(appId));
        return "editApp";
    }

    @RequestMapping(value = "/apps/delete/{id}", method = RequestMethod.GET)
    public String deleteApp(@PathVariable("id") Long appId) {
        appService.deleteAppById(appId);
        return "redirect:/apps";
    }
}
