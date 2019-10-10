package com.jindanupajit.javabootcamp.bullhorn.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;


@Entity
public class User {
    @TableGenerator(name = "User_TG", initialValue = 1000, allocationSize = 1)

    @Id
    @GeneratedValue(generator = "User_TG", strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 128)
    @Size(min = 3, max = 128)
    private String username;

    // BCrypt is 59 to 60 characters base-64
    @Column(length = 60)
    private String password;


    @OneToOne(
        // born together, die together!
        cascade = CascadeType.ALL,

        // stick together
        fetch = FetchType.EAGER
    )
    @JoinColumn(name = "peoplename_id")
    private PeopleName name;

    public User(@Size(min = 3, max = 128) String username, String password, PeopleName name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public PeopleName getName() {
        return name;
    }

    public void setName(PeopleName name) {
        this.name = name;
    }
}
