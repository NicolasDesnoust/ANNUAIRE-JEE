package dev.web;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.business.IDirectoryManager;
import dev.model.Group;
import dev.model.Person;

@Controller()
@RequestMapping("/person")
public class PersonController {

    @Autowired
    IDirectoryManager manager;
    
    @Autowired
    User user;
    
    @ModelAttribute("user")
    public User getUser() {
        return user;
    }

    protected final Log logger = LogFactory.getLog(getClass());

    @ModelAttribute("persons")
    Collection<Person> persons(@RequestParam(value = "groupId", required = false) Integer groupId,
    							@RequestParam(value = "personName", required = false) String input) {
        if (groupId != null) {
        	Group group = manager.findGroup(groupId);
            
            if (group != null) {
            	Collection<Person> persons = manager.findAllPersons(user, groupId);
            	
            	if (!persons.isEmpty()) {
            		logger.info("Persons from group " + groupId + " found");
            	}
            	else {
            		logger.info("Group " + groupId + " is empty");
            	}
            	return persons;
            }
        }
        else if (input != null) {
        	Collection<Person> persons = manager.findAllPersons(user, input);
        	
        	if (!persons.isEmpty()) {
        		logger.info("Persons like " + input + " found");
        	}
        	else {
        		logger.info("No persons found");
        	}
        	return persons;
        }

        return null;
    }
    
    @ModelAttribute("group")
    Group group(@RequestParam(value = "groupId", required = false) Integer groupId) {
        if (groupId != null) {
        	Group group = manager.findGroup(groupId);
            
            if (group != null) {
            	logger.info("Group " + groupId + " found");
            	return group;
            }
        }
        logger.info("Group " + groupId + "not found");

        return null;
    }
    
 // controleur d'envoi des personnes a la vue
    @RequestMapping(value = "/searchPersons", method = RequestMethod.GET)
    public String listPersons(@ModelAttribute("persons") Collection<Person> persons, final RedirectAttributes redirAtt) {
    	if (persons == null) {
    		redirAtt.addFlashAttribute("errorPersonName", true);
    		return "redirect:/actions/user/home";
    	}
    	
        return "personsSearch";
    }
    
    // controleur d'envoi des personnes a la vue
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listPersonsFromGroup(@ModelAttribute("persons") Collection<Person> persons, final RedirectAttributes redirAtt) {
    	
        return "personsList";
    }
    
    @ModelAttribute("person")
    Person person(@RequestParam(value = "personId", required = false) String personId) {
        if (personId != null) {
            logger.info("Editing person " + personId);
            return manager.findPerson(user, personId);
        }

        return null;
    }
    
    // controleur d'edition d'une personne
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editPerson(@ModelAttribute Person p) {
    	if(manager.isConnectedAs(user, p))
    		return "personForm";
    	return "redirect:show?personId="+p.getId();
    }
    
    // controleur de soumission du formulaire d'edition avec validation 
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String savePerson(@ModelAttribute @Valid Person p, BindingResult result) {
        
        if (result.hasErrors()) {
           return "personForm";
        }
    	
        manager.savePerson(user, p);
        return "redirect:/actions/group/list";
    }
    
    // associe la date du formulaire a java.util.Date
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
    }
    
    // controleur d'affichage d'une personne
    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public String showPerson(@ModelAttribute Person p, final RedirectAttributes redirAtt) {
    	if (p == null) {
    		redirAtt.addFlashAttribute("errorPersonId", true);
    		return "redirect:/actions/user/home";
    	}
    	else if (p.getId().equals(user.getId())) {
    		
    		return "redirect:edit?personId="+p.getId();
    	}
    	
        return "personShow";
    }
    
}