package de.metacoder.blog.services;

import de.metacoder.blog.persistence.entities.UserBO;
import de.metacoder.blog.persistence.repositories.UserRepository;
import de.metacoder.blog.security.DynamicSaltedUserDetailsService;
import de.metacoder.blog.transferobjects.CreateUserTO;
import de.metacoder.blog.transferobjects.UserTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequestMapping("/users")
@Controller
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DynamicSaltedUserDetailsService userDetailsService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Collection<UserTO> getAllUsers(){

        Collection<UserTO> userTOs = new ArrayList<>();

        for(UserBO bo : userRepository.findAll()) {
            UserTO userTO = new UserTO();
            userTO.setName(bo.getName());
            userTO.setRoles(bo.getRoles());
            userTOs.add(userTO);
        }

        return userTOs;
    }

    @RequestMapping(method=RequestMethod.PUT, value="/{id}/password")
    @ResponseBody
    public void setUserPassword(@PathVariable String id, @RequestBody String newPassword){
        UserBO userBO = userRepository.findOne(id);
        userDetailsService.changePasswordOfUser(userBO, newPassword);
    }


    @RequestMapping(method=RequestMethod.PUT)
    @ResponseBody
    public void createNewUser(@RequestBody CreateUserTO createUserTO){
        userDetailsService.createUser(createUserTO.getUsername(), createUserTO.getPassword());
    }
}
