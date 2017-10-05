package com.isakov.springboot.service;

import com.isakov.springboot.model.Publisher;
import com.isakov.springboot.repositories.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

	public void savePublisher(Publisher publisher) {
		publisherRepository.save(publisher);
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
	public UserDetails loadUserByUsername(String publisherName) throws UsernameNotFoundException {
		Publisher p = publisherRepository.findByName(publisherName);

		UserDetails user = new org.springframework.security.core.userdetails.User(publisherName, p.getPasswordHash(), true,
				true, true, true, AuthorityUtils.createAuthorityList(p.getRole()));

		System.out.println("ROLE: " + p.getRole());
		return user;
	}
}
