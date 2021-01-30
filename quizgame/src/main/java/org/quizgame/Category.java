package org.quizgame;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category {
    @Id @GeneratedValue
    private Long id;

    String name;

    //OneToMany(mappedBy = "category", cascade = CascadeType.ALL) // Look into exactly what this is, yoinked from solution
    @OneToMany // Look into exactly what this is, yoinked from solution
    private List<SubCategory> subcategories;

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
