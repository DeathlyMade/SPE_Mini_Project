package com.example.sciencecalc.service;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorServiceTest {

    private final CalculatorService svc = new CalculatorService();

    @Test
    void sqrtPositive() {
        assertEquals(4.0, svc.sqrt(16.0));
        assertEquals(Math.sqrt(2.0), svc.sqrt(2.0), 1e-12);
    }

    @Test
    void sqrtNegativeThrows() {
        assertThrows(IllegalArgumentException.class, () -> svc.sqrt(-1.0));
    }

    @Test
    void factorialWorks() {
        assertEquals(BigInteger.ONE, svc.factorial(0));
        assertEquals(BigInteger.valueOf(120), svc.factorial(5));
    }

    @Test
    void factorialNegative() {
        assertThrows(IllegalArgumentException.class, () -> svc.factorial(-3));
    }

    @Test
    void lnWorks() {
        assertEquals(1.0, svc.ln(Math.E), 1e-12);
        assertEquals(0.0, svc.ln(1.0), 1e-12);
    }

    @Test
    void lnNonPositive() {
        assertThrows(IllegalArgumentException.class, () -> svc.ln(0.0));
    }

    @Test
    void powerWorks() {
        assertEquals(8.0, svc.power(2.0, 3.0), 1e-12);
        assertEquals(3.0, svc.power(9.0, 0.5), 1e-12);
    }
}
