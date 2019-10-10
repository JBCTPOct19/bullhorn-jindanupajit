package com.jindanupajit.javabootcamp.bullhorn.entity;



import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class PeopleName {
    @TableGenerator(name = "PeopleName_TG", initialValue = 1000, allocationSize = 1)

    @Id
    @GeneratedValue (generator = "PeopleName_TG", strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 64)
    @Size(max = 64)
    private String first;

    @Column(length = 64)
    @Size(max = 64)
    private String middle;

    @Column(length = 64)
    @NotNull
    @Size(min = 1, max = 64)
    private String last;

    public PeopleName(@Size(max = 64) String first, @Size(max = 64) String middle, @NotNull @Size(min = 1, max = 64) String last) {
        this.first = first;
        this.middle = middle;
        this.last = last;
    }

    public PeopleName() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getMiddle() {
        return middle;
    }

    public void setMiddle(String middle) {
        this.middle = middle;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }
}
