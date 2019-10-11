package com.jindanupajit.javabootcamp.bullhorn.dao;

import com.jindanupajit.javabootcamp.bullhorn.entity.SpringUser;
import com.jindanupajit.javabootcamp.bullhorn.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class UserDAO   {

    @Autowired
    private EntityManager manager;


    public User getUserByUsername(String username) throws UsernameNotFoundException{
            User user;
            try {
                user = (User) manager.createQuery("select u from User u where u.username = :username")
                        .setParameter("username", username)
                        .getSingleResult();
            }
            catch (NoResultException e) {
                throw new UsernameNotFoundException(username);
            }

            return user;
        }


    public SpringUser getSpringUserByUsername(String username) throws UsernameNotFoundException {
        User dbUser = getUserByUsername(username);

        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        grantedAuthorities.add(new SimpleGrantedAuthority("AUTHENTICATED_USER"));

        SpringUser springUser = new SpringUser(
                dbUser.getUsername(),
                dbUser.getPassword(),
                true,
                true,
                true,
                true,
                grantedAuthorities);
        return springUser;

    }
}
