package com.jindanupajit.javabootcamp.bullhorn.run;

import com.jindanupajit.javabootcamp.bullhorn.entity.Message;
import com.jindanupajit.javabootcamp.bullhorn.entity.PeopleName;
import com.jindanupajit.javabootcamp.bullhorn.entity.User;
import com.jindanupajit.javabootcamp.bullhorn.repository.MessageCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Date;


@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    MessageCrudRepository messageRepository;

    @Override
    public void run(String... args) throws Exception {

        messageRepository.save (
            new Message(
                    "Hello, Batman!",
                    (new Date((new java.util.Date()).getTime())),
                    (new User(
                            "jindanupajit",
                            ( (new BCryptPasswordEncoder())
                                    .encode("password") ),
                            new PeopleName("Krissada", null, "Jindanupajit")
                        )
                    )
            )
        );

        messageRepository.save (
                new Message(
                        "Hello, Krissada!",
                        (new Date((new java.util.Date()).getTime())),
                        (new User(
                                "batman",
                                ( (new BCryptPasswordEncoder())
                                        .encode("password") ),
                                new PeopleName("Bruce", null, "Wayne")
                        )
                        )
                )
        );


    }
}
