package com.isakov.springboot.service;

import com.isakov.springboot.model.Publisher;

import java.util.List;

public interface PublisherService {

	Publisher findById(Long id);

	Publisher findByName(String name);

	void savePublisher(Publisher user);

	void updatePublisher(Publisher user);

	void deletePublisherById(Long id);

	void deleteAllPublishers();

	List<Publisher> findAllPublishers();

	boolean isPublisherExist(Publisher user);
}