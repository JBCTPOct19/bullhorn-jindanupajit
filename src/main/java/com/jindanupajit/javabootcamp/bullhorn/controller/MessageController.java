package com.jindanupajit.javabootcamp.bullhorn.controller;

import com.cloudinary.utils.ObjectUtils;
import com.jindanupajit.javabootcamp.bullhorn.component.CloudinaryUploader;
import com.jindanupajit.javabootcamp.bullhorn.entity.Message;
import com.jindanupajit.javabootcamp.bullhorn.entity.PeopleName;
import com.jindanupajit.javabootcamp.bullhorn.entity.SpringUser;
import com.jindanupajit.javabootcamp.bullhorn.entity.User;
import com.jindanupajit.javabootcamp.bullhorn.repository.MessageCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.security.Principal;

@Controller
public class MessageController {
    @PersistenceContext
    EntityManager manager;

    @Autowired
    CloudinaryUploader cloudinaryUploader;

    @Autowired
    MessageCrudRepository messageCrudRepository;

    public SpringUser init(Model model, Principal principal) {
        SpringUser springUser = (SpringUser) ((Authentication) principal).getPrincipal();

        model.addAttribute("spring_user", springUser);

        return springUser;
    }

    @GetMapping(value = {"/message/new/form", "/add"} )
    public String Form (Model model, Principal principal) {
        SpringUser springUser = init(model, principal);
        model.addAttribute("message",
                new Message(
                        "",
                        (new Timestamp((new java.util.Date()).getTime())),
                        // todo
                        getUserByUsername(springUser.getUsername())
                )
        );

        return "message_form";
    }

    @PostMapping("/message/process")
    public String FromProcessor (@ModelAttribute Message message, @RequestParam("file") MultipartFile file, Model model, Principal principal) {
        SpringUser springUser = init(model, principal);
        if (!file.isEmpty()) {
            try {
                Map uploadResult = cloudinaryUploader.upload(
                        file
                            .getBytes()
                            ,ObjectUtils
                                .asMap("resourcetype", "auto"));
                message.setImageUrl(uploadResult.get("url").toString());
            } catch (IOException e) {
                System.err.println("Cloud Upload Error: fallback!");
                e.printStackTrace();
                // fallback
                message.setImageUrl(null);
            }
        }
        else {
            message.setImageUrl(null);
        }
        message.setPostedDate(new Timestamp((new java.util.Date()).getTime()));
        message.setUser(getUserByUsername(springUser.getUsername()));
        messageCrudRepository.save(message);
        return "redirect:/message/view";

    }

    @GetMapping("/message/view")
    public String ViewUserMessages(Model model, Principal principal) {

        init(model, principal);
        model.addAttribute("whom", "all");
        model.addAttribute("MessageCollection",messageCrudRepository.findAll());

        return "message_view";

    }

    @GetMapping("/message/view/{username}")
    public String ViewUserMessages(@PathVariable("username") Optional<String> username, Model model, Principal principal) {
        init(model, principal);
        if (username.isPresent()) {
            User user =   getUserByUsername(username.get());
            model.addAttribute("whom", user.getUsername());
            model.addAttribute("MessageCollection", getMessageCollectionByUser(user));
        }
        else {
            model.addAttribute("whom", "all");
            model.addAttribute("MessageCollection",messageCrudRepository.findAll());
        }
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

    private List<Message> getMessageCollectionByUser(User user) {

        return (List<Message>) manager.createQuery("select m from Message m where m.user = :user")
                .setParameter("user", user)
                .setMaxResults(20)
                .getResultList();
    }

}
