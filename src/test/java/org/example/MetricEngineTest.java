package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MetricEngineTest {

    private MetricEngine engine;

    @BeforeEach
    void setUp(){
        engine = new MetricEngine();
    }

    @Test
    public void shouldReturnTrueWhenOverHeat(){
        CpuMetric result = engine.createMeasure(96.0, 0.80);
        Assertions.assertTrue(result.isOverheating());
    }
    @Test
    public void shouldReturnTrueWhenOverLoad(){
        CpuMetric result = engine.createMeasure(94.0, 0.92);
        Assertions.assertTrue(result.isOverloaded());
    }
    @Test
    public void shouldReturnFalseWhenTempIsNormal(){
        CpuMetric result = engine.createMeasure(94.0, 0.80);
        Assertions.assertFalse(result.isOverheating());
    }
    @Test
    public void shouldReturnFalseWhenLoadIsNormal(){
        CpuMetric result = engine.createMeasure(94.0, 0.80);
        Assertions.assertFalse(result.isOverloaded());
    }

    @Test
    public void shouldGenerateValidTimestamp(){
        long before = System.currentTimeMillis();

        CpuMetric result = engine.createMeasure(50, .05);

        Assertions.assertTrue(result.timestamp() >= before);

        Assertions.assertTrue(result.timestamp() <= System.currentTimeMillis());
    }

}
