package com.isakov.springboot.service;

import com.isakov.springboot.model.App;
import com.isakov.springboot.model.Version;
import com.isakov.springboot.repository.AppRepository;
import com.isakov.springboot.repository.GenreRepository;
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

    @Autowired
    private GenreRepository genreRepository;

    @Transactional
    @Override
    public App saveApp(App app, int publisherId, int genreId) {
        if (!app.isNew() && get(app.getId(), publisherId) == null) {
            return null;
        }
        app.getGenres().add(genreRepository.getOne(genreId));
        app.setPublisher(publisherRepository.getOne(publisherId));
        return appRepository.save(app);
    }

    @Override
    public App get(int id, int publisherId) {
        App app = appRepository.findOne(id);
        return app != null && app.getPublisher().getId() == publisherId ? app : null;
    }

    @Override
    public int deleteAppById(int id, int publisherId){
        return appRepository.delete(id, publisherId);
    }

    @Override
    public List<App> getAll(int publisherId){
        return appRepository.getAll(publisherId);
    }

    @Override
    public List<App> getAllWithGenre(int publisherId) {
        return appRepository.getAllWithGenre(publisherId);
    }
}
