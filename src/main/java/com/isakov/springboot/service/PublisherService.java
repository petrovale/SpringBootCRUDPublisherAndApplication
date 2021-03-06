package com.isakov.springboot.service;

import com.isakov.springboot.model.Publisher;

import java.util.List;

public interface PublisherService {

	Publisher findById(int id);

	Publisher findByName(String name);

	Publisher savePublisher(Publisher publisher);

	void updatePublisher(Publisher publisher);

	int deletePublisherById(int id);

	void deleteAllPublishers();

	List<Publisher> findAllPublishers();

	boolean isPublisherExist(Publisher user);
}