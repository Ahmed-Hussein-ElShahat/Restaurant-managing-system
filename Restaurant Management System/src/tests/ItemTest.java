package tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import Backend.Item;
import javafx.scene.image.Image;

public class ItemTest {
    // @Test
    // void testSetImage() {
    //     Item testItem = new Item("test1", 100, "test");
    //     testItem.setImage("Assets/food.jpg");
    //     Assertions.assertEquals(new Image("Assets/food.jpg"), testItem.getImage());
    // }
    @Test
    void testGetImageNull() {
        Item testItem = new Item("test1", 100, "test");
        Assertions.assertEquals(null, testItem.getImage());
    }
}
