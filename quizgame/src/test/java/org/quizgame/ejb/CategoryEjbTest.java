package org.quizgame.ejb;


import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quizgame.entity.Category;

import javax.ejb.EJB;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
}
