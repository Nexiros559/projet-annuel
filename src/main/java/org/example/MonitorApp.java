package org.example;

public class MonitorApp {
    private final MetricEngine engine = new MetricEngine();

    public void start() {
        System.out.println("--- Démarrage du Monitoring Simulé ---");

        while (true){
            try{
                CpuMetric machine = engine.generateMeasure();
                System.out.printf("Température : %.2f°C | CpuLoad: %.1f%% | [%tT] %n",
                        machine.temperature(), machine.cpuLoad() * 100, machine.timestamp());
                if(machine.temperature() > 95.0){
                    System.err.println("La Température est trop élevée. La machine s'arrête."
                            + machine.temperature() +"°C");
                    break;
                } else if (machine.cpuLoad() * 100 > 85.0) {
                    System.err.println("L'utilisation du CPU est trop élévée. La machine s'arrête."
                    + machine.cpuLoad() +"%");
                    break;
                }
                Thread.sleep(1000);
            }
            catch(InterruptedException e){
                System.err.println("Le Monitoring à été arrêté.");
                break;
            }
        }
    }
}
