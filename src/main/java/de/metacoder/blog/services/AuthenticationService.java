package de.metacoder.blog.services;

import de.metacoder.blog.persistence.entities.UserBO;
import de.metacoder.blog.persistence.repositories.UserRepository;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.authentication.encoding.BasePasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * UserBO: becker
 * Date: 6/11/13
 * Time: 4:24 AM
 * To change this template use File | Settings | File Templates.
 */
@Service("authenticationService")
public class AuthenticationService extends AbstractUserDetailsAuthenticationProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

        UserBO userBO = userRepository.findOne(username);

        if(userBO == null){
            throw new UsernameNotFoundException("user not found!");
        }

        ShaPasswordEncoder shaPasswordEncoder = new ShaPasswordEncoder(512);
        Sha512Hash sha256Hash = new Sha512Hash(authentication.getCredentials(), userBO.getSalt());

        if(!userBO.getPassword().equals(sha256Hash.toString())){
            throw new BadCredentialsException("wrong password!");
        }

        Collection<GrantedAuthority> roles = new ArrayList<>();
        for(String role : userBO.getRoles()){
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
            roles.add(authority);
        }
        return new User(userBO.getName(),authentication.getCredentials().toString(), roles);
    }

    @Override
    protected void doAfterPropertiesSet() throws Exception {
        super.doAfterPropertiesSet();    //To change body of overridden methods use File | Settings | File Templates.
        if (!userRepository.findAll().iterator().hasNext()) {

            // use a randomly generated base64 string as password (the base64 string only contains chars on your keyboard...)
            String password = new SecureRandomNumberGenerator().nextBytes().toBase64();
            userService.createUser("admin", password);

            LOGGER.info("No user found in database. Creating default admin user with username 'admin' and password '"+password+"'. You should definitly change the password immediately!");
        }
    }
}