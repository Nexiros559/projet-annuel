package org.example;

import java.util.Random;

public class MetricEngine {
    private final Random random = new Random();

    public CpuMetric generateMeasure(){
        double load = random.nextDouble();

        double temp = 40.0 + (load * 40.0) + (random.nextDouble() * 5.0);

        return new CpuMetric(temp, load, System.currentTimeMillis());
    }
}
