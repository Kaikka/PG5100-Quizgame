package org.quizgame;

import javax.persistence.*;

@Entity
public class SubCategory {
    @Id @GeneratedValue
    private Long id;

    String name;


    @ManyToOne
    private Category category;


    public SubCategory() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
