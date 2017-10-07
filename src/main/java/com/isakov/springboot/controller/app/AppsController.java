package com.isakov.springboot.controller.app;

import com.isakov.springboot.model.App;
import com.isakov.springboot.model.AppVersion;
import com.isakov.springboot.service.AppService;
import com.isakov.springboot.service.AppVersionService;
import com.isakov.springboot.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class AppsController {

    @Autowired
    private AppService appService;

    @Autowired
    private AppVersionService appVersionService;

    @Autowired
    private PublisherService publisherService;

    @RequestMapping("/apps")
    public String index(Authentication auth, Model model) {
        User user = (User) auth.getPrincipal();
        Long publisherId = publisherService.findByName(user.getUsername()).getId();
        List<App> apps = appService.findAllApps(publisherId);
        model.addAttribute("apps", apps);
        return "apps";
    }

    @RequestMapping(value = "/apps/add", method = RequestMethod.GET)
    public String addApp(Model model){
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

    @RequestMapping(value = "/apps/addAppVersion/{id}", method = RequestMethod.GET)
    public String addAppVersion(@PathVariable("id") Long appId, Model model){
        //model.addAttribute("versions", appVersionService.findAllAppVersions());
        model.addAttribute("app", appService.findById(appId));
        model.addAttribute("version", new AppVersion());
        return "addAppVersion";
    }

    @RequestMapping(value="/apps/{path}/versions", method=RequestMethod.POST)
    public String appsAddVersion(@PathVariable Long path, AppVersion version) {
        AppVersion appVersion = appVersionService.findByName(version.getName());
        App app = appService.findById(path);

        if (app != null) {
            if (!app.hasVersion(appVersion)) {
                version.setApp(app);
                appVersionService.saveAppVersion(version);
            }
            return "redirect:/apps";
        }

        return "redirect:/apps";
    }

    @RequestMapping(value="/apps/edit/{id}", method=RequestMethod.GET)
    public String editApp(@PathVariable("id") Long appId, Model model){
        model.addAttribute("app", appService.findById(appId));
        return "editApp";
    }

    @RequestMapping(value = "/apps/delete/{id}", method = RequestMethod.GET)
    public String deleteApp(@PathVariable("id") Long appId, Model model) {
        appService.deleteAppById(appId);
        return "redirect:/apps";
    }
}
