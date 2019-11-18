package dev.web;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.business.IDirectoryManager;
import dev.model.Group;
import dev.model.Person;

@Controller()
@RequestMapping("/group")
public class GroupController {

    @Autowired
    IDirectoryManager manager;
    
    @Autowired
    User user;
    
    @ModelAttribute("user")
    public User getUser() {
        return user;
    }

    protected final Log logger = LogFactory.getLog(getClass());

    // methode de creation de la liste des groupes
    @ModelAttribute("groups")
    Collection<Group> groups() {
        logger.info("Making list of groups");
        return manager.findAllGroups();
    }
    
    // methode de creation de la liste des groupes
    @ModelAttribute("groupsSearch")
    Collection<Group> groupsSearch(@RequestParam(value = "groupName", required = false) String input) {
    	if (input != null) {
         	Collection<Group> groups = manager.findAllGroups(input);
         	
         	if (!groups.isEmpty()) {
         		logger.info("Groups like " + input + " found");
         	}
         	else {
         		logger.info("No groups found");
         	}
         	return groups;
         }

         return null;
    }
    
    // controleur d'envoi des groupes a la vue
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listGroups() {
        logger.info("List of groups");
        return "groupsList";
    }
    
    // controleur d'envoi des groupes a la vue
    @RequestMapping(value = "/searchGroups", method = RequestMethod.GET)
    public String searchGroups(@ModelAttribute("groupsSearch") Collection<Group> groupsSearch, final RedirectAttributes redirAtt) {
    	if (groupsSearch == null) {
    		redirAtt.addFlashAttribute("errorGroupName", true);
    		return "redirect:/actions/user/home";
    	}
    	
        return "groupsSearch";
    }
}