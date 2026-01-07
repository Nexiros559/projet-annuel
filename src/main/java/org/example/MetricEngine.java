package org.example;

import java.util.Random;

public class MetricEngine {
    private final Random random = new Random();
    private static final float TEMP_LIMIT = 95;
    private static final double LOAD_LIMIT = 0.85;

    public CpuMetric generateMeasure(){
        double load = random.nextDouble();
        double temp = 40.0 + (load * 40.0) + (random.nextDouble() * 5.0);
        boolean isOverheating = temp > TEMP_LIMIT;
        boolean isOverloading = load > LOAD_LIMIT;

        return new CpuMetric(temp, load, System.currentTimeMillis(), isOverheating, isOverloading);
    }
}
