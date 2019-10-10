package com.jindanupajit.javabootcamp.bullhorn.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Date;

@Entity
public class Message {
    @TableGenerator(name = "Message_TG", initialValue = 1000, allocationSize = 1)

    @Id
    @GeneratedValue(generator = "Message_TG", strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 1024)
    @Size(max = 1024)
    private String content;


    private Date postedDate;


    @ManyToOne(
            // fetch when needed
            fetch = FetchType.LAZY,

            // auto-save modified user
            cascade = CascadeType.PERSIST
    )
    @JoinColumn(name = "user_id")
    private User user;

    public Message(@Size(max = 1024) String content, Date postedDate, User user) {
        this.content = content;
        this.postedDate = postedDate;
        this.user = user;
    }

    public Message() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(Date postedDate) {
        this.postedDate = postedDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
