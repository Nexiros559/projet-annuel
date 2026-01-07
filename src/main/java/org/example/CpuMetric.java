package org.example;

public record CpuMetric(double temperature, double cpuLoad, long timestamp,
                        boolean isOverheating, boolean isOverloaded) {

}

