package com.example.sciencecalc.controller;

import com.example.sciencecalc.service.CalculatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CalculatorController {

    private final CalculatorService svc;

    public CalculatorController(CalculatorService svc) {
        this.svc = svc;
    }

    @GetMapping("/sqrt")
    public ResponseEntity<Map<String, Object>> sqrt(@RequestParam double x) {
        double r = svc.sqrt(x);
        return ResponseEntity.ok(Map.of("operation", "sqrt", "input", x, "result", r));
    }

    @GetMapping("/factorial")
    public ResponseEntity<Map<String, Object>> factorial(@RequestParam int n) {
        BigInteger r = svc.factorial(n);
        return ResponseEntity.ok(Map.of("operation", "factorial", "input", n, "result", r.toString()));
    }

    @GetMapping("/ln")
    public ResponseEntity<Map<String, Object>> ln(@RequestParam double x) {
        double r = svc.ln(x);
        return ResponseEntity.ok(Map.of("operation", "ln", "input", x, "result", r));
    }

    @GetMapping("/power")
    public ResponseEntity<Map<String, Object>> power(@RequestParam double x, @RequestParam double b) {
        double r = svc.power(x, b);
        return ResponseEntity.ok(Map.of("operation", "power", "base", x, "exponent", b, "result", r));
    }
}
