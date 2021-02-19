package org.quizgame.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class SubCategory {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Size(max = 128)
    String name;

    @ManyToOne
    @NotNull
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

    public Category getParent() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
