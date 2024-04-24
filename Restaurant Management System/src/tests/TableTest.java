package tests;

import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Assertions;

import Backend.Table; 


public class TableTest {
    @Test
    void testGetOrder() {
        // Posponed
    }

    @Test
    void testGetTable_capacity() {
        Table testTable1 = new Table();
        Table testTable2 = new Table(10);
        Assertions.assertEquals(5, testTable1.getTable_capacity());
        Assertions.assertEquals(10, testTable2.getTable_capacity());
    }

    @Test
    void testGetTable_id() {
        Table.resetTable_num();
        Table testTable1 = new Table();
        Table testTable2 = new Table(10);
        Assertions.assertEquals(1, testTable1.getTable_id());
        Assertions.assertEquals(2, testTable2.getTable_id());
    }

    @Test
    void testGetTable_num() {
        Table.resetTable_num();
        Table testTable1 = new Table();
        Table testTable2 = new Table(10);
        Assertions.assertEquals(2, Table.getTable_num());
    }

    @Test
    void testIsAvailable1() {
        Table testTable1 = new Table();
        Table testTable2 = new Table(10);
        Assertions.assertEquals(true, testTable1.isAvailable());
        testTable2.preserve();
        Assertions.assertEquals(false, testTable2.isAvailable());
    }

    @Test
    void testIsAvailable2() {
        Table testTable1 = new Table();
        Table testTable2 = new Table(10);
        Assertions.assertEquals(true, testTable1.isAvailable(1));
        Assertions.assertEquals(true, testTable1.isAvailable(5));
        Assertions.assertEquals(false, testTable1.isAvailable(10));
        testTable2.setAvailable(true);
        Assertions.assertEquals(true, testTable2.isAvailable(10));
    }

    @Test
    void testRemove_table() {
        Table.resetTable_num();
        Table testTable1 = new Table();
        Table testTable2 = new Table(10);
        Table.remove_table();
        Assertions.assertEquals(1, Table.getTable_num());
    }

    @Test
    void testSetAvailable() {
        Table testTable1 = new Table();
        Table testTable2 = new Table(10);
        testTable1.setAvailable(false);
        Assertions.assertEquals(false, testTable1.isAvailable());
        testTable1.setAvailable(true);
        Assertions.assertEquals(true, testTable1.isAvailable());
    }

    @Test
    void testSetOrder() {
        // Posponed
    }

    @Test
    void testSetTable_capacity() {
        Table testTable1 = new Table();
        Table testTable2 = new Table(10);
        testTable1.setTable_capacity(6);
        Assertions.assertEquals(6, testTable1.getTable_capacity());
        testTable2.setTable_capacity(10);
        Assertions.assertEquals(10, testTable1.getTable_capacity());
    }
}
