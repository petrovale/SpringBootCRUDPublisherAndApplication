package com.isakov.springboot.service;

import com.isakov.springboot.model.App;
import com.isakov.springboot.repository.AppRepository;
import com.isakov.springboot.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AppServiceImpl implements AppService {
    @Autowired
    private AppRepository appRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    public App findById(Long id) {
        return appRepository.findOne(id);
    }

    public App findByName(String name) {
        return appRepository.findByName(name);
    }

    @Transactional
    @Override
    public App saveApp(App app, long publisherId) {
        if (!app.isNew() && get(app.getId(), publisherId) == null) {
            return null;
        }
        app.setPublisher(publisherRepository.getOne(publisherId));
        return appRepository.save(app);
    }

    @Override
    public App get(long id, long publisherId) {
        App app = appRepository.findOne(id);
        return app != null && app.getPublisher().getId() == publisherId ? app : null;
    }

    public void updateApp(App app, long publisherId){
        saveApp(app, publisherId);
    }

    public void deleteAppById(Long id){
        appRepository.delete(id);
    }

    public void deleteAllApps(){
        appRepository.deleteAll();
    }

    public List<App> getAll(Long publisherId){
        return appRepository.getAll(publisherId);
    }

    public boolean isAppExist(App app) {
        return findByName(app.getName()) != null;
    }
}
