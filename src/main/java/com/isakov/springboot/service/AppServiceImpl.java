package com.isakov.springboot.service;

import com.isakov.springboot.model.App;
import com.isakov.springboot.repositories.AppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppServiceImpl implements AppService {
    @Autowired
    private AppRepository appRepository;

    public App findById(Long id) {
        return appRepository.findOne(id);
    }

    public App findByName(String name) {
        return appRepository.findByName(name);
    }

    public void saveApp(App app) {
        appRepository.save(app);
    }

    public void updateApp(App app){
        saveApp(app);
    }

    public void deleteAppById(Long id){
        appRepository.delete(id);
    }

    public void deleteAllApps(){
        appRepository.deleteAll();
    }

    public List<App> findAllApps(Long publisherId){
        return appRepository.findByPublisher_id(publisherId);
    }

    public boolean isAppExist(App app) {
        return findByName(app.getName()) != null;
    }
}
