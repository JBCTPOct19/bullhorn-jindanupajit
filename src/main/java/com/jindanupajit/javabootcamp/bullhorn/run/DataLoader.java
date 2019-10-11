package com.jindanupajit.javabootcamp.bullhorn.run;

import com.jindanupajit.javabootcamp.bullhorn.entity.Message;
import com.jindanupajit.javabootcamp.bullhorn.entity.PeopleName;
import com.jindanupajit.javabootcamp.bullhorn.entity.User;
import com.jindanupajit.javabootcamp.bullhorn.repository.MessageCrudRepository;
import com.jindanupajit.javabootcamp.bullhorn.repository.UserCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Timestamp;


@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    MessageCrudRepository messageRepository;

    @Autowired
    UserCrudRepository userRepository;

    @Override
    public void run(String... args) throws Exception {

        messageRepository.save (
            new Message(
                    "Hello, Batman!",
                    (new Timestamp((new java.util.Date()).getTime())),
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
                        "https://res.cloudinary.com/jindanupajit/image/upload/v1570754276/nd4ym7crkctpitww49xw.png",
                        (new Timestamp((new java.util.Date()).getTime())),
                        (new User(
                                "batman",
                                ( (new BCryptPasswordEncoder())
                                        .encode("password") ),
                                new PeopleName("Bruce", "Thomas", "Wayne")
                        )
                        )
                )
        );

        userRepository.save (new User(
                        "john",
                        ( (new BCryptPasswordEncoder())
                                .encode("password") ),
                        new PeopleName("John", "Spring", "Boot")
                )
        );

        userRepository.save (new User(
                        "jane",
                        ( (new BCryptPasswordEncoder())
                                .encode("password") ),
                        new PeopleName("Jane", "Thyme", "Leaf")
                )
        );



    }
}
