package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

public class MonitorAppTest {
    JsonFileManager jsonFileManagerTest;
    MonitorApp engineTest;

    @BeforeEach
    void setUp(){
        jsonFileManagerTest = new JsonFileManager("Test.json");
        engineTest = new MonitorApp(jsonFileManagerTest);
    }

    AtomicBoolean receivedAMeasure = new AtomicBoolean(false);

    @Test
    public void shouldReceiveAMeasure(){
        engineTest.addListener(cpuMetric -> {receivedAMeasure.set(true);});
        engineTest.start();
        try{
            Thread.sleep(1500);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        engineTest.stop();
        Assertions.assertTrue(receivedAMeasure.get());
    }
}
