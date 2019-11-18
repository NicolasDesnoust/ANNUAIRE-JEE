package dev.web;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.business.IDirectoryManager;
import dev.model.Person;

@Controller()
@RequestMapping("/user")
public class UserController {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    IDirectoryManager manager;
    
    @Autowired()
    User user;

    @ModelAttribute("user")
    public User newUser() {
        return user;
    }

    @RequestMapping(value = "/login")
    public String login() {	 
        logger.info("going to login page");
        
        return "login";
    }
    
    @RequestMapping(value = "/home")
    public String home() {

        logger.info("welcome page");
        
        return "home";
    }
    
    @RequestMapping(value = "/connection")
    public String connection(
    		@RequestParam(value = "personid", required = true) String personId,
    		@RequestParam(value = "password", required = true) String password,
    		final RedirectAttributes redirAtt) {
        
        if (manager.login(user, personId, password)) {
        	logger.info("login user " + user);
        	return "home";
        }
        
        redirAtt.addFlashAttribute("errorLogin", true);
        	 
        logger.info("login failed");
        
        return "redirect:login";
    }

    @RequestMapping(value = "/logout")
    public String logout() {
        logger.info("logout user " + user);
        
        manager.logout(user);
        
        return "home";
    }
}