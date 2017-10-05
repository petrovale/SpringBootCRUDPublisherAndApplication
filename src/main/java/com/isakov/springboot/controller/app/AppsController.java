package com.isakov.springboot.controller.app;

import com.isakov.springboot.model.App;
import com.isakov.springboot.service.AppService;
import com.isakov.springboot.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class AppsController {

    @Autowired
    private AppService appService;

    @Autowired
    private PublisherService publisherService;

    @RequestMapping("/apps")
    public String index(Authentication auth, Model model) {
        User user = (User) auth.getPrincipal();
        Long publisherId = publisherService.findByName(user.getUsername()).getId();
        List<App> apps = (List<App>) appService.findAllApps(publisherId);
        model.addAttribute("apps", apps);
        return "apps";
    }

    @RequestMapping(value = "/apps/add", method = RequestMethod.GET)
    public String addStudent(Model model){
        model.addAttribute("app", new App());
        return "addApp";
    }

    @RequestMapping(value = "/apps/save", method = RequestMethod.POST)
    public String save(Authentication auth, App app){
        User user = (User) auth.getPrincipal();
        app.setPublisher(publisherService.findByName(user.getUsername()));
        appService.saveApp(app);
        return "redirect:/apps";
    }


}
