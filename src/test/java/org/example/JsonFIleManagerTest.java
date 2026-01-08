package org.example;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.api.Assertions;

import java.nio.file.Path;
import java.util.List;

public class JsonFIleManagerTest {

    @TempDir
    static Path path;
    static Path completePath;
    static String folder;

    JsonFileManager fileManagerTest;

    @BeforeEach
    void setUp(){
        completePath = path.resolve("MetricsTest.json");
        folder = completePath.toString();

        fileManagerTest =  new JsonFileManager(folder);
    }

    @Test
    public void shouldBeEmptyAtStart(){
        List<CpuMetric> allMetrics = fileManagerTest.readAllMetrics();
        Assertions.assertTrue(allMetrics.isEmpty());
    }

    @Test
    public void shouldSaveAndReadOneMetric(){
        CpuMetric engine = new CpuMetric(50, 0.4, System.currentTimeMillis(), false, false);
        this.fileManagerTest.saveMetric(engine);
        List<CpuMetric> allMetrics = fileManagerTest.readAllMetrics();
        Assertions.assertEquals(1, allMetrics.size());
    }

    @Test
    public void shouldSaveAndReadMultipleMetrics(){
        CpuMetric m1 = new CpuMetric(50, 0.4, System.currentTimeMillis(), false, false);
        this.fileManagerTest.saveMetric(m1);
        CpuMetric m2 = new CpuMetric(60, 0.5, System.currentTimeMillis(), false, false);
        this.fileManagerTest.saveMetric(m2);

        List<CpuMetric> allMetrics = fileManagerTest.readAllMetrics();
        Assertions.assertEquals(2, allMetrics.size());
        Assertions.assertEquals(m1, allMetrics.getFirst());
        Assertions.assertEquals(m2, allMetrics.getLast());
    }
}
