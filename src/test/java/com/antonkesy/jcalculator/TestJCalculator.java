package com.antonkesy.jcalculator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestJCalculator {

    @Test
    void addition() {
        JCalculator calculator = new JCalculator();
        assertNotNull(calculator);
    }

}
