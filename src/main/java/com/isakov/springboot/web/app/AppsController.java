package com.isakov.springboot.web.app;

import com.isakov.springboot.AuthorizedPublisher;
import com.isakov.springboot.model.App;
import com.isakov.springboot.model.Version;
import com.isakov.springboot.service.AppService;
import com.isakov.springboot.service.VersionService;
import com.isakov.springboot.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private VersionService versionService;

    @RequestMapping("/apps")
    public String index(Model model) {
        long publisherId = AuthorizedPublisher.id();
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
    public String save(App app){
        long publisherId = AuthorizedPublisher.id();
        appService.saveApp(app, publisherId);
        return "redirect:/apps";
    }

    @RequestMapping(value = "/apps/addVersion/{id}", method = RequestMethod.GET)
    public String addAppVersion(@PathVariable("id") Long appId, Model model){
        long publisherId = AuthorizedPublisher.id();
        model.addAttribute("versions", versionService.findAllAppVersions(appId, publisherId));
        model.addAttribute("app", appService.findById(appId));
        model.addAttribute("version", new Version());
        return "addVersion";
    }

    @RequestMapping(value="/apps/{path}/versions", method=RequestMethod.POST)
    public String appsAddVersion(@PathVariable("path") Long appId, Version version) {
        long publisherId = AuthorizedPublisher.id();
        versionService.saveVersion(version, appId, publisherId);
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
