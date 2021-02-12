package org.quizgame;

import org.junit.jupiter.api.Test;
import org.quizgame.entity.Category;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CategoryEntityTest extends EntityTestBase {

    @Test
    public void testTooLongName() {

        Category category = createCategory("ABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJ");
        assertFalse(persistInATransaction(category));

        Category category2 = createCategory("ABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJ");
        assertTrue(persistInATransaction(category2));
    }

    @Test
    public void testUniqueName() {

        String name = "Category";

        Category category = createCategory(name);
        assertTrue(persistInATransaction(category));

        Category category2 = createCategory(name);
        assertFalse(persistInATransaction(category2));
    }
}
