package org.quizgame.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    @NotBlank
    @Size(max = 128)
    String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL) // Look into exactly what this is, yoinked from solution
    private final List<SubCategory> subcategories;

    public Category() {
        subcategories = new ArrayList<>();
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

    public List<SubCategory> getSubcategories() {
        return subcategories;
    }

    public void setSubcategory(SubCategory subcategory) {
        this.subcategories.add(subcategory);
    }

}
