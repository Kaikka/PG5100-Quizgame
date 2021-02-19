package org.quizgame.ejb;

import org.hibernate.Hibernate;
import org.quizgame.entity.Category;
import org.quizgame.entity.SubCategory;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class CategoryEjb {

    @PersistenceContext
    private EntityManager em;


    public Long createCategory(String name) {

        Category category = new Category();
        category.setName(name);

        em.persist(category);

        return category.getId();
    }

    public Long createSubCategory(long parentId, String name) {

        Category parent = em.find(Category.class, parentId);
        if (parent == null) {
            throw new IllegalArgumentException("No parent with ID " + parentId + " found");
        }

        SubCategory subCategory = new SubCategory();
        subCategory.setName(name);

        parent.getSubcategories().add(subCategory); // is this really needed?
        subCategory.setCategory(parent);

        em.persist(subCategory);

        return subCategory.getId();
    }

    public List<Category> getAllCategories(boolean withSub) {

        TypedQuery<Category> query = em.createQuery("select c from Category c", Category.class);
        List<Category> categories = query.getResultList();

        if (withSub) {
            //categories.forEach(Hibernate::initialize);
            // below is sexy way of Hibernate.initialize(c.getSubcategories()), but is this needed?
            //categories.stream().map(Category::getSubcategories).forEach(Hibernate::initialize);
            for (Category c : categories) {
                //Hibernate.initialize(c);
                c.getSubcategories().size();
            }
        }

        return categories;
    }

    public Category getCategory(long id, boolean withSub) {

        Category category = em.find(Category.class, id);

        if (withSub && category != null) {
            category.getSubcategories().size();
            //Hibernate.initialize(category);
        }

        return category;
    }

    public SubCategory getSubCategory(long id) {

        return em.find(SubCategory.class, id);
    }
}
