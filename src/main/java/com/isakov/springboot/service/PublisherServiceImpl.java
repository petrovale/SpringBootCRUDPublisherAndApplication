package com.isakov.springboot.service;

import com.isakov.springboot.AuthorizedPublisher;
import com.isakov.springboot.model.Publisher;
import com.isakov.springboot.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.isakov.springboot.util.PublisherUtil.prepareToSave;

@Service("publisherService")
@Transactional
public class PublisherServiceImpl implements PublisherService, UserDetailsService{

	@Autowired
	private PublisherRepository publisherRepository;

	public Publisher findById(Long id) {
		return publisherRepository.findOne(id);
	}

	public Publisher findByName(String name) {
		return publisherRepository.findByName(name);
	}

	public Publisher savePublisher(Publisher publisher) {
		return publisherRepository.save(prepareToSave(publisher));
	}

	public void updatePublisher(Publisher publisher){
		savePublisher(publisher);
	}

	public void deletePublisherById(Long id){
		publisherRepository.delete(id);
	}

	public void deleteAllPublishers(){
		publisherRepository.deleteAll();
	}

	public List<Publisher> findAllPublishers(){
		return publisherRepository.findAll();
	}

	public boolean isPublisherExist(Publisher publisher) {
		return findByName(publisher.getName()) != null;
	}

	@Override
	public AuthorizedPublisher loadUserByUsername(String publisherName) throws UsernameNotFoundException {
		Publisher p = publisherRepository.findByName(publisherName);
		if (p == null) {
			throw new UsernameNotFoundException("Publisher " + publisherName + " is not found");
		}
		return new AuthorizedPublisher(p);
	}
}
