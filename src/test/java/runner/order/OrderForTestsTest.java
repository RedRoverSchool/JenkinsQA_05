package runner.order;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class OrderForTestsTest {

    private static final List<Integer> EXPECTED_LIST_INTEGER = List.of(4, 5);
    private static final List<Integer> EXPECTED_LIST_INTEGER_2 = List.of(4, 6);
    private static final List<List<Integer>> LIST_INTEGER = List.of(List.of(1, 2, 3), EXPECTED_LIST_INTEGER, List.of(6));
    private static final List<List<Integer>> LIST_INTEGER_2 = List.of(List.of(1, 2, 3), EXPECTED_LIST_INTEGER, EXPECTED_LIST_INTEGER_2, List.of(6));
    private static final List<Double> EXPECTED_LIST_DOUBLE = List.of(4.00, 5.00);
    private static final List<List<Double>> LIST_DOUBLE = List.of(List.of(1.00, 2.00, 3.00), EXPECTED_LIST_DOUBLE, List.of(6.00));
    private static final List<String> EXPECTED_LIST_STRING = List.of("String 4", "String 5");
    private static final List<List<String>> LIST_STRING = List.of(List.of("String 1", "String 2", "String 3"), EXPECTED_LIST_STRING, List.of("String 6"));

    @Test
    public void findIntegerTest() {
        Optional<List<Integer>> result = OrderUtils.find(LIST_INTEGER, 4);
        Assert.assertEquals(result.orElse(null), EXPECTED_LIST_INTEGER);
    }

    @Test
    public void findInteger2Test() {
        Optional<List<Integer>> result = OrderUtils.find(LIST_INTEGER_2, 4);
        Assert.assertEquals(result.orElse(null), EXPECTED_LIST_INTEGER);
    }

    @Test
    public void findDoubleTest() {
        Optional<List<Double>> result = OrderUtils.find(LIST_DOUBLE, 4.00);
        Assert.assertEquals(result.orElse(null), EXPECTED_LIST_DOUBLE);
    }

    @Test
    public void findStringTest() {
        Optional<List<String>> result = OrderUtils.find(LIST_STRING, "String 4");
        Assert.assertEquals(result.orElse(null), EXPECTED_LIST_STRING);
    }

    @Test
    public void findEmptyTest() {
        Optional<List<Integer>> result = OrderUtils.find(LIST_INTEGER, 0);
        Assert.assertFalse(result.isPresent());
    }

    private static class Item {
        public String name;
        public String[] dependencies;

        public Item(String name, String... dependencies) {
            this.name = name;
            this.dependencies = dependencies;
        }
    }

    @Test
    public void orderMethodsTest() {
        final Item one = new Item("1. one");
        final Item two = new Item("2. two", "1. one");
        final Item three = new Item("3. three", "1. one");
        final Item five = new Item("5. five", "3. three");
        final Item six = new Item("6. six", "3. three");
        final Item four = new Item("4. four");
        final List<Item> sourceList = new ArrayList<>(new ArrayList<>(List.of(one, two, three, four, five, six)));
        final List<List<Item>> expectedList = new ArrayList<>(List.of(
                new ArrayList<>(List.of(one, two, three, five, six)),
                new ArrayList<>(List.of(four))));
        final List<List<Item>> expectedList2 = new ArrayList<>(List.of(
                new ArrayList<>(List.of(one, three, five, six, two)),
                new ArrayList<>(List.of(four))));
        final List<List<Item>> expectedList3 = new ArrayList<>(List.of(
                new ArrayList<>(List.of(one, two, three, six, five)),
                new ArrayList<>(List.of(four))));
        final List<List<Item>> expectedList4 = new ArrayList<>(List.of(
                new ArrayList<>(List.of(one, three, six, five, two)),
                new ArrayList<>(List.of(four))));
        List<List<Item>> result = OrderUtils.orderMethods(sourceList,
                (Item item) -> item.name,
                (Item item) -> item.dependencies);

        Assert.assertTrue(result.equals(expectedList) || result.equals(expectedList2)
                || result.equals(expectedList3) || result.equals(expectedList4));
    }

    @Test
    public void orderMethodsOneGroupDepTest() {
        final Item one = new Item("1. one");
        final Item two = new Item("2. two", "1. one");
        final Item three = new Item("3. three", "2. two");
        final Item four = new Item("4. four");
        final List<Item> sourceList = new ArrayList<>(new ArrayList<>(List.of(one, two, three, four)));
        final List<List<Item>> expectedList1 = new ArrayList<>(List.of(
                new ArrayList<>(List.of(one, two, three)),
                new ArrayList<>(List.of(four))));

        List<List<Item>> result = OrderUtils.orderMethods(sourceList,
                (Item item) -> item.name,
                (Item item) -> item.dependencies);

        result.sort(Comparator.comparing(List::size));
        expectedList1.sort(Comparator.comparing(List::size));
        Assert.assertEquals(expectedList1, result);
    }

    @Test
    public void orderMethodsTwoDifferentGroupsDepTest() {
        final Item one = new Item("1. one");
        final Item two = new Item("2. two", "1. one");
        final Item three = new Item("3. three");
        final Item four = new Item("4. four", "3. three");
        final Item five = new Item("5. five");
        final List<Item> sourceList = new ArrayList<>(new ArrayList<>(List.of(one, two, three, four, five)));
        final List<List<Item>> expectedList1 = new ArrayList<>(List.of(
                new ArrayList<>(List.of(one, two)),
                new ArrayList<>(List.of(three, four)),
                new ArrayList<>(List.of(five))));

        final List<List<Item>> expectedList2 = new ArrayList<>(List.of(
                new ArrayList<>(List.of(three, four)),
                new ArrayList<>(List.of(one, two)),
                new ArrayList<>(List.of(five))));

        List<List<Item>> result = OrderUtils.orderMethods(sourceList,
                (Item item) -> item.name,
                (Item item) -> item.dependencies);

        result.sort(Comparator.comparing(List::size));
        expectedList1.sort(Comparator.comparing(List::size));
        Assert.assertTrue(result.equals(expectedList1) || result.equals(expectedList2));
    }

    @Test
    public void orderMethodsTwoGroupsDepTest() {
        final Item one = new Item("1. one");
        final Item two = new Item("2. two", "1. one");
        final Item three = new Item("3. three", "1. one");
        final Item four = new Item("4. four");
        final List<Item> sourceList = new ArrayList<>(new ArrayList<>(List.of(one, two, three, four)));
        final List<List<Item>> expectedList1 = new ArrayList<>(List.of(
                new ArrayList<>(List.of(one, two, three)),
                new ArrayList<>(List.of(four))));
        final List<List<Item>> expectedList2 = new ArrayList<>(List.of(
                new ArrayList<>(List.of(one, three, two)),
                new ArrayList<>(List.of(four))));
        List<List<Item>> result = OrderUtils.orderMethods(sourceList,
                (Item item) -> item.name,
                (Item item) -> item.dependencies);

        Assert.assertTrue(result.equals(expectedList1) || result.equals(expectedList2));
    }

    @Test
    public void orderMethodsNoDepTest() {
        final Item one = new Item("1. one");
        final Item two = new Item("2. two");
        final Item three = new Item("3. three");
        final Item four = new Item("4. four");
        final Item five = new Item("5. five");
        final List<Item> sourceList = new ArrayList<>(new ArrayList<>(List.of(one, two, three, four, five)));
        final List<List<Item>> expectedList1 = new ArrayList<>(List.of(
                new ArrayList<>(List.of(one)),
                new ArrayList<>(List.of(two)),
                new ArrayList<>(List.of(three)),
                new ArrayList<>(List.of(four)),
                new ArrayList<>(List.of(five))));

        List<List<Item>> result = OrderUtils.orderMethods(sourceList,
                (Item item) -> item.name,
                (Item item) -> item.dependencies);

        result.sort(Comparator.comparing(List::size));
        expectedList1.sort(Comparator.comparing(List::size));
        Assert.assertEquals(expectedList1, result);
    }

    @Test
    public void orderMethodsCycleDepTest() {
        final Item one = new Item("1. one", "5. five");
        final Item two = new Item("2. two", "1. one");
        final Item three = new Item("3. three", "2. two");
        final Item four = new Item("4. four", "3. three");
        final Item five = new Item("5. five", "4. four");
        final List<Item> sourceList = new ArrayList<>(new ArrayList<>(List.of(one, two, three, four, five)));
        final List<List<Item>> expectedList1 = new ArrayList<>(List.of(
                new ArrayList<>(List.of(one, two, three, four, five, one))));

        List<List<Item>> result = OrderUtils.orderMethods(sourceList,
                (Item item) -> item.name,
                (Item item) -> item.dependencies);

        result.sort(Comparator.comparing(List::size));
        expectedList1.sort(Comparator.comparing(List::size));
    }
}
