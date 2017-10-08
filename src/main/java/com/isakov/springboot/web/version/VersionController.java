package com.isakov.springboot.web.version;

import com.isakov.springboot.AuthorizedPublisher;
import com.isakov.springboot.model.Version;
import com.isakov.springboot.service.AppService;
import com.isakov.springboot.service.VersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class VersionController {
    @Autowired
    private AppService appService;

    @Autowired
    private VersionService versionService;

    @RequestMapping("/apps/{id}/versions/")
    public String index(@PathVariable("id") Long appId, Model model) {
        long publisherId = AuthorizedPublisher.id();
        model.addAttribute("versions", versionService.findAllAppVersions(appId, publisherId));
        model.addAttribute("app", appService.get(appId, publisherId));
        return "versions";
    }

    @RequestMapping(value = "/apps/{id}/versions/addVersion/", method = RequestMethod.GET)
    public String addAppVersion(@PathVariable("id") Long appId, Model model){
        long publisherId = AuthorizedPublisher.id();
        model.addAttribute("versions", versionService.findAllAppVersions(appId, publisherId));
        model.addAttribute("app", appService.findById(appId));
        model.addAttribute("version", new Version());
        return "addVersion";
    }

    @RequestMapping(value = "/apps/{appid}/versions/delete/{versionid}", method = RequestMethod.GET)
    public String deleteApp(@PathVariable("appid") Long appId,@PathVariable("versionid") Long versionId) {
        long publisherId = AuthorizedPublisher.id();
        versionService.deleteById(versionId, appId, publisherId);
        return "redirect:/apps";
    }

    @RequestMapping(value="/apps/{appid}/versions/edit/{versionid}", method=RequestMethod.GET)
    public String editVersion(@PathVariable("appid") Long appId, @PathVariable("versionid") Long versionId, Model model){
        long publisherId = AuthorizedPublisher.id();
        model.addAttribute("appid", appId);
        model.addAttribute("version", versionService.get(versionId, appId, publisherId));
        return "editVersion";
    }

    @RequestMapping(value = "/apps/{appid}/versions/save", method = RequestMethod.POST)
    public String save(@PathVariable("appid") Long appId, Version version){
        long publisherId = AuthorizedPublisher.id();
        versionService.saveVersion(version, appId, publisherId);
        return "redirect:/apps";
    }
}
