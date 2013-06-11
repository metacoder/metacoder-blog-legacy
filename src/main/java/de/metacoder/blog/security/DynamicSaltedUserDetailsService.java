package de.metacoder.blog.security;

import de.metacoder.blog.persistence.entities.UserBO;
import de.metacoder.blog.persistence.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DynamicSaltedUserDetailsService implements UserDetailsService, InitializingBean {

    @Autowired private UserRepository userRepository;
    @Autowired private ShaPasswordEncoder passwordEncoder;

    private static final Logger LOGGER = LoggerFactory.getLogger(DynamicSaltedUserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserBO user = userRepository.findOne(username);
        if(user != null){
            return new DynamicSaltedUserDetails(user);
        }

        throw new UsernameNotFoundException("user " + username + " not found!");
    }


    public void createUser(String username, String password){

        final UserBO userBO = new UserBO();
        userBO.setName(username);

        setPasswordWithRandomSaltForUser(userBO, password);

        // TODO currently each userBO is an admin. Fix this in later versions when you need a more granular role concept.
        userBO.getRoles().add(BlogRoles.ADMIN);

        userRepository.save(userBO);
    }

    public void changePasswordOfUser(UserBO userBO, String newPassword){
        setPasswordWithRandomSaltForUser(userBO, newPassword);
        userRepository.save(userBO);
    }

    private void setPasswordWithRandomSaltForUser(UserBO userBO, String newPassword){
        String salt = UUID.randomUUID().toString();
        userBO.setPassword(passwordEncoder.encodePassword(newPassword, salt));
        userBO.setSalt(salt);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if(userRepository.count() == 0){
            final String password = "admin"+System.currentTimeMillis();
            createUser("admin", password);

            LOGGER.info("No user found in database. Creating default admin user with username 'admin' and password '" + password + "'. You should definitly change the password immediately!");
        }
    }
}
