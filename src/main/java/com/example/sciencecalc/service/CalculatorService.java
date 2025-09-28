package com.example.sciencecalc.service;

import java.math.BigInteger;

import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

    public double sqrt(double x) {
        if (Double.isNaN(x)) throw new IllegalArgumentException("Input is NaN");
        if (x < 0.0) throw new IllegalArgumentException("sqrt: input must be >= 0");
        return Math.sqrt(x);
    }

    public BigInteger factorial(int n) {
        if (n < 0) throw new IllegalArgumentException("factorial: input must be >= 0");
        BigInteger result = BigInteger.ONE;
        for (int i = 2; i <= n; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }
        return result;
    }

    public double ln(double x) {
        if (Double.isNaN(x)) throw new IllegalArgumentException("Input is NaN");
        if (x <= 0.0) throw new IllegalArgumentException("ln: input must be > 0");
        return Math.log(x);
    }

    public double power(double x, double b) {
        return Math.pow(x, b);
    }
}
