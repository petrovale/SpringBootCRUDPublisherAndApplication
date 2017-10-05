package com.isakov.springboot.controller.app;

import com.isakov.springboot.model.App;
import com.isakov.springboot.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class AppsController {

    @Autowired
    private AppService appService;

    @RequestMapping("/apps")
    public String index(Model model) {
        List<App> apps = (List<App>) appService.findAllApps();
        model.addAttribute("apps", apps);
        return "apps";
    }
}
