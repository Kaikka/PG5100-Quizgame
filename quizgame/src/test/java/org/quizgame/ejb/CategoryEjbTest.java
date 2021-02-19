package org.quizgame.ejb;


import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quizgame.entity.Category;
import org.quizgame.entity.SubCategory;

import javax.ejb.EJB;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class CategoryEjbTest {


    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackages(true, "org.quizgame")
                .addAsResource("META-INF/persistence.xml");
    }

    @EJB
    private CategoryEjb categoryEjb;

    @EJB
    private ResetEjb resetEjb;

    @Before
    public void init() {
        resetEjb.deleteDb();
    }

    @Test
    public void testNoCategory() {
        List<Category> list = categoryEjb.getAllCategories(false);
        assertEquals(0, list.size());
    }

    @Test
    public void testCreateCategory() {

        String name = "testCreateCategory";

        Long category = categoryEjb.createCategory(name);

        assertNotNull(category);
    }

    @Test
    public void testGetCategory() {

        String name = "testGetCategory";

        Long id = categoryEjb.createCategory(name);
        Category category = categoryEjb.getCategory(id, false);

        assertEquals(name, category.getName());
    }

    @Test
    public void testCreateSubCategory() {

        String catName = "testCreateCategory";
        String subCatName = "testCreateSubCategory";

        Long categoryId = categoryEjb.createCategory(catName);
        Long subCategoryId = categoryEjb.createSubCategory(categoryId, subCatName);

        SubCategory subCategory = categoryEjb.getSubCategory(subCategoryId);

        assertEquals(subCatName, subCategory.getName());
        assertEquals(categoryId, subCategory.getParent().getId());
    }

    @Test
    public void testGetAllCategories() {

        String cat1 = "Category 1";
        String cat2 = "Category 2";
        String cat3 = "Category 3";

        String subCat1 = "SubCategory 1";
        String subCat2 = "SubCategory 2";
        String subCat3 = "SubCategory 3";

        Long categoryId1 = categoryEjb.createCategory(cat1);
        Long categoryId2 = categoryEjb.createCategory(cat2);
        Long categoryId3 = categoryEjb.createCategory(cat3);

        Long subCategoryId1 = categoryEjb.createSubCategory(categoryId1, subCat1);
        Long subCategoryId2 = categoryEjb.createSubCategory(categoryId2, subCat2);
        Long subCategoryId3 = categoryEjb.createSubCategory(categoryId3, subCat3);

        List<Category> list = categoryEjb.getAllCategories(false);
        assertEquals(3, list.size());

        //Category category1 = categoryEjb.getCategory(categoryId1, false);
        list = categoryEjb.getAllCategories(false);

        Category first = list.get(0);

        try {
            //assertNull(category1.getSubcategories());
            first.getSubcategories().size();
            fail();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        list = categoryEjb.getAllCategories(true);
        first = list.get(0);
        assertEquals(1, first.getSubcategories().size());
    }

    @Test
    public void testCreateTwice() {

        String name = "testCreateTwice";

        categoryEjb.createCategory(name);

        try {
            categoryEjb.createCategory(name);
            fail();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
