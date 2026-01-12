package org.example;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.Sensors;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;


import static java.util.concurrent.TimeUnit.SECONDS;

public class OshiEngine {
    private final SystemInfo systemInfo;
    private final CentralProcessor processor;
    private final Sensors sensors;
    private  long [] previousTicks;
    ScheduledExecutorService scheduler;
    List<MonitorListener> listeners = new ArrayList<>();
    private MetricEngine engine;
    private JsonFileManager jsonFileManagerOshi;

    public OshiEngine(){
        this.systemInfo = new SystemInfo();
        this.processor = systemInfo.getHardware().getProcessor();
        this.sensors = systemInfo.getHardware().getSensors();
        this.previousTicks = processor.getSystemCpuLoadTicks();
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
        this.engine = new MetricEngine();
    }

    public void addListener(MonitorListener listener){
        listeners.add(listener);
    }

    public void start(){

        this.scheduler = Executors.newScheduledThreadPool(1);

        final Runnable instructionsOSHI = new Runnable() {
            @Override
            public void run() {
                double load = processor.getSystemCpuLoadBetweenTicks(previousTicks);
                previousTicks = processor.getSystemCpuLoadTicks();
                double temp = sensors.getCpuTemperature();
                engine.createMeasure(temp, load);
                for (MonitorListener listener : listeners ){
                    listener.onMeasureReceived(engine.createMeasure(temp, load));
                }
            }
        };

        this.scheduler.scheduleAtFixedRate(instructionsOSHI, 1, 1, SECONDS);
    }

    public void stopOshiEngine(){
        if (scheduler != null){
            scheduler.shutdownNow();
        }
    }
}
