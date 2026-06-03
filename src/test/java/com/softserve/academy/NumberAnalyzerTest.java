package com.softserve.academy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NumberAnalyzerTest {

    // AAA: Arrange-Act-Assert pattern
    // Arrange - підготовка даних (BeforeEach створює базову колекцію)
    // Act - виконання дії
    // Assert - перевірка результату

    private NumberAnalyzer analyzer;
    private List<Integer> testNumbers;

    @BeforeEach
    void setUp() {
        // Arrange - створюємо фіксовану колекцію для тестування
        testNumbers = new ArrayList<>(Arrays.asList(
                45, 12, 78, 23, 56, 89, 34, 67, 90, 15,
                42, 8, 73, 29, 61, 5, 38, 82, 50, 19
        ));
        analyzer = new NumberAnalyzer(testNumbers);
    }

    // ===== Constructor tests =====

    @Test
    void shouldCreateAnalyzerWithValidNumbers() {
        // Arrange
        List<Integer> numbers = Arrays.asList(1, 2, 3);

        // Act
        NumberAnalyzer newAnalyzer = new NumberAnalyzer(numbers);

        // Assert
        assertEquals(3, newAnalyzer.size());
    }

    @Test
    void shouldThrowExceptionWhenNumbersListIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new NumberAnalyzer(null));
    }

    // ===== findMinimum() tests =====

    @Test
    void shouldFindMinimumNumber() {
        int min = analyzer.findMinimum();
        assertEquals(5, min);
    }

    @Test
    void shouldThrowExceptionWhenFindingMinimumInEmptyCollection() {
        NumberAnalyzer emptyAnalyzer = new NumberAnalyzer(new ArrayList<>());
        assertThrows(IllegalStateException.class, () -> emptyAnalyzer.findMinimum());
    }

    // ===== findMaximum() tests =====

    @Test
    void shouldFindMaximumNumber() {
        int max = analyzer.findMaximum();
        assertEquals(90, max);
    }

    @Test
    void shouldThrowExceptionWhenFindingMaximumInEmptyCollection() {
        NumberAnalyzer emptyAnalyzer = new NumberAnalyzer(new ArrayList<>());
        assertThrows(IllegalStateException.class, () -> emptyAnalyzer.findMaximum());
    }

    // ===== calculateAverage() tests =====

    @Test
    void shouldCalculateAverageValue() {
        double average = analyzer.calculateAverage();
        assertEquals(45.8, average);
    }

    @Test
    void shouldThrowExceptionWhenCalculatingAverageOfEmptyCollection() {
        NumberAnalyzer emptyAnalyzer = new NumberAnalyzer(new ArrayList<>());
        assertThrows(IllegalStateException.class, () -> emptyAnalyzer.calculateAverage());
    }

    @Test
    void shouldCalculateAverageForSingleNumber() {
        NumberAnalyzer singleAnalyzer = new NumberAnalyzer(List.of(42));
        double average = singleAnalyzer.calculateAverage();
        assertEquals(42.0, average);
    }

    // ===== removeEvenNumbers() tests =====
    @Test
    void shouldRemoveAllEvenNumbers() {
        assertEquals(20, analyzer.size());
        analyzer.removeEvenNumbers();
        assertEquals(10, analyzer.size());
    }

    @Test
    void shouldLeaveEmptyCollectionWhenAllNumbersAreEven() {
        NumberAnalyzer evenAnalyzer = new NumberAnalyzer(Arrays.asList(2, 4, 6, 8, 10));
        assertEquals(5, evenAnalyzer.size());
        evenAnalyzer.removeEvenNumbers();
        assertEquals(0, evenAnalyzer.size());
    }

    @Test
    void shouldNotChangeCollectionWhenAllNumbersAreOdd() {
        NumberAnalyzer oddAnalyzer = new NumberAnalyzer(Arrays.asList(1, 3, 5, 7, 9));
        assertEquals(5, oddAnalyzer.size());
        oddAnalyzer.removeEvenNumbers();
        assertEquals(5, oddAnalyzer.size());
    }

    // ===== contains() tests =====

    @Test
    void shouldReturnTrueWhenNumberExists() {
        boolean result = analyzer.contains(45);
        assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenNumberDoesNotExist() {
        boolean result = analyzer.contains(1000);
        assertFalse(result);
    }

    @Test
    void shouldReturnFalseForEmptyCollection() {
        NumberAnalyzer emptyAnalyzer = new NumberAnalyzer(new ArrayList<>());
        boolean result = emptyAnalyzer.contains(5);
        assertFalse(result);
    }

    // ===== sortAscending() tests =====

    @Test
    void shouldSortNumbersInAscendingOrder() {
        // Assert
        List<Integer> beforeSort = analyzer.getNumbers();
        assertEquals(45, beforeSort.get(0));
        assertEquals(19, beforeSort.get(19));
        analyzer.sortAscending();
        List<Integer> sorted = analyzer.getNumbers();
        assertEquals(5, sorted.get(0));
        assertEquals(90, sorted.get(19));
    }

    @Test
    void shouldNotChangeAlreadySortedCollection() {
        NumberAnalyzer sortedAnalyzer = new NumberAnalyzer(Arrays.asList(1, 2, 3, 4, 5));
        sortedAnalyzer.sortAscending();
        List<Integer> result = sortedAnalyzer.getNumbers();
        assertEquals(5, result.size());
        assertEquals(1, result.get(0));
        assertEquals(5, result.get(4));
    }

    @Test
    void shouldSortCollectionWithDuplicates() {
        NumberAnalyzer dupAnalyzer = new NumberAnalyzer(Arrays.asList(3, 1, 2, 3, 1, 2));
        List<Integer> beforeSort = dupAnalyzer.getNumbers();
        assertEquals(3, beforeSort.get(0));
        dupAnalyzer.sortAscending();
        List<Integer> result = dupAnalyzer.getNumbers();
        assertEquals(1, result.get(0));
        assertEquals(1, result.get(1));
        assertEquals(3, result.get(5));
    }

    // ===== size() tests =====

    @Test
    void shouldReturnCorrectSize() {
        int size = analyzer.size();
        assertEquals(20, size);
    }

    @Test
    void shouldReturnZeroForEmptyCollection() {
        NumberAnalyzer emptyAnalyzer = new NumberAnalyzer(new ArrayList<>());
        int size = emptyAnalyzer.size();
        assertEquals(0, size);
    }

    // ===== getNumbers() tests =====

    @Test
    void shouldReturnCopyOfNumbers() {
        List<Integer> originalList = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        NumberAnalyzer testAnalyzer = new NumberAnalyzer(originalList);
        List<Integer> returned = testAnalyzer.getNumbers();
        assertEquals(originalList.size(), returned.size());
        returned.remove(0);
        returned.remove(0);
        assertEquals(5, originalList.size());
        assertEquals(5, testAnalyzer.size());
        assertEquals(3, returned.size());
    }

    // ===== Integration tests =====

    @Test
    void shouldRemoveEvenNumbersAndThenSort() {
        analyzer.removeEvenNumbers();
        analyzer.sortAscending();
        List<Integer> expected = List.of(5, 15, 19, 23, 29, 45, 61, 67, 73, 89);
        assertEquals(expected, analyzer.getNumbers());
    }

    @Test
    void shouldFindMinMaxAfterRemovingEvenNumbers() {
        analyzer.removeEvenNumbers();
        assertEquals(89, analyzer.findMaximum());
        assertEquals(5, analyzer.findMinimum());
    }

    // ===== Edge cases =====

    @Test
    void shouldHandleNegativeNumbers() {
        testNumbers = new ArrayList<>(Arrays.asList(-2, 1, -6, 20));
        analyzer = new NumberAnalyzer(testNumbers);
        assertEquals(20, analyzer.findMaximum());
        assertEquals(-6, analyzer.findMinimum());
        assertEquals(3.25, analyzer.calculateAverage());
    }

    @Test
    void shouldHandleZeroInCollection() {
        testNumbers = new ArrayList<>(Arrays.asList(0, 2, 1, 6, 20));
        analyzer = new NumberAnalyzer(testNumbers);
        assertEquals(20, analyzer.findMaximum());
        assertEquals(0, analyzer.findMinimum());
        assertEquals(5.8, analyzer.calculateAverage());
    }
}