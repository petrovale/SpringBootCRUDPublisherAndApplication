package com.isakov.springboot.controller.version;

import com.isakov.springboot.model.AppVersion;
import com.isakov.springboot.service.AppService;
import com.isakov.springboot.service.AppVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class VersionController {
    @Autowired
    private AppService appService;

    @Autowired
    private AppVersionService appVersionService;

    @RequestMapping("/apps/{id}/versions/")
    public String index(@PathVariable("id") Long appId, Model model) {
        List<AppVersion> versions = appVersionService.findAllAppVersions(appId);
        model.addAttribute("versions", versions);
        model.addAttribute("nameapp", appService.findById(appId).getName());
        return "versions";
    }


}
