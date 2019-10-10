package com.jindanupajit.javabootcamp.bullhorn.controller;

import com.jindanupajit.javabootcamp.bullhorn.entity.Message;
import com.jindanupajit.javabootcamp.bullhorn.entity.PeopleName;
import com.jindanupajit.javabootcamp.bullhorn.entity.User;
import com.jindanupajit.javabootcamp.bullhorn.repository.MessageCrudRepository;
import com.jindanupajit.javabootcamp.bullhorn.repository.UserCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Date;
import java.util.List;

@Controller
public class MessageController {
    @PersistenceContext
    EntityManager manager;

    @Autowired
    MessageCrudRepository messageCrudRepository;

    @GetMapping(value = {"/message/new/form", "/add"} )
    public String Form (Model model) {

        model.addAttribute("message",
                new Message(
                        "",
                        (new Date((new java.util.Date()).getTime())),
                        // todo
                        getUserByUsername("jindanupajit")
                )
        );

        return "message_form";
    }

    @PostMapping("/message/process")
    public String FromProcessor (@ModelAttribute Message message, Model model) {

        // todo
        message.setPostedDate(new Date((new java.util.Date()).getTime()));
        message.setUser(getUserByUsername("jindanupajit"));
        messageCrudRepository.save(message);
        return "redirect:/message/view/"+message.getUser().getUsername();

    }

    @GetMapping("/message/view/{username}")
    public String ViewUserMessages(@PathVariable("username") String username, Model model) {
        User user =   getUserByUsername(username);
        model.addAttribute("user", user);
        model.addAttribute("MessageCollection", getMessageByUser(user));
        return "message_view";
    }

    private User getUserByUsername(String username) {

        List<User> UserCollection = manager.createQuery("select u from User u where u.username = :username")
                                            .setParameter("username", username)
                                            .setMaxResults(1)
                                            .getResultList();

        if (UserCollection.isEmpty()) {
            return  new User(
                        username,
                        ( (new BCryptPasswordEncoder())
                                      .encode("password") ),
                        new PeopleName("Autogenerated", null, username)
                    );
        } // if

        return UserCollection.get(0);
    }

    private List<Message> getMessageByUser(User user) {

        return (List<Message>) manager.createQuery("select m from Message m where m.user = :user")
                .setParameter("user", user)
                .setMaxResults(20)
                .getResultList();
    }

}