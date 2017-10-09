package com.isakov.springboot.web.publisher;

import com.isakov.springboot.model.Publisher;
import com.isakov.springboot.repository.PublisherRepository;
import com.isakov.springboot.to.SignupForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class PublisherController {
	@Autowired
    private PublisherRepository publisherRepository;

	@RequestMapping("/login")
	public String login() {
		return "login";
	}

    @RequestMapping(value = "signup")
    public String addStudent(Model model){
    	model.addAttribute("signupform", new SignupForm());
        return "signup";
    }

    @RequestMapping(value = "saveuser", method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute("signupform") SignupForm signupForm, BindingResult bindingResult) {
    	System.out.println(bindingResult.toString());
    	if (!bindingResult.hasErrors()) { // validation errors
    		if (signupForm.getPassword().equals(signupForm.getPasswordCheck())) { // check password match		
	    		String pwd = signupForm.getPassword();
		    	BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
		    	String hashPwd = bc.encode(pwd);
	
		    	Publisher newPublisher = new Publisher();
		    	newPublisher.setPasswordHash(hashPwd);
		    	newPublisher.setName(signupForm.getUsername());
		    	newPublisher.setRole("USER");
		    	if (publisherRepository.findByName(signupForm.getUsername()) == null) {
		    		publisherRepository.save(newPublisher);
		    	}
		    	else {
	    			bindingResult.rejectValue("username", "error.userexists", "Username already exists");    	
	    			return "signup";		    		
		    	}
    		}
    		else {
    			bindingResult.rejectValue("passwordCheck", "error.pwdmatch", "Passwords does not match");    	
    			return "signup";
    		}
    	}
    	else {
    		return "signup";
    	}
    	return "redirect:/login";    	
    }
}
