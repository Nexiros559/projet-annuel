package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Orchestrateur principal de l'application de monitoring.
 * <p>
 * Cette classe gère la boucle principale, l'intervalle de mesure (1s)
 * et la fréquence de génération des rapports statistiques (5s).
 * </p>
 */
public class MonitorApp {
    private final MetricEngine engine = new MetricEngine();
    private final JsonFileManager jsonFileManager;
    int tickCount = 0;
    ScheduledExecutorService scheduler;
    List<MonitorListener> listeners = new ArrayList<>();


    public MonitorApp(JsonFileManager jsonFileManager) {
        this.jsonFileManager = jsonFileManager;
    }

    public void addListener(MonitorListener listener){
        listeners.add(listener);
    }

    /**
     * Récupère l'historique et calcule les moyennes de température et de charge.
     * Utilise les Java Streams pour le calcul statistique.
     */
    void calculateAndDisplayAverage(){
        List<CpuMetric> allMetrics = jsonFileManager.readAllMetrics();

        if(allMetrics.isEmpty()) return;

        double avgTemp = allMetrics.stream().mapToDouble(CpuMetric::temperature).average().orElse(0.0);
        double avgLoad = allMetrics.stream().mapToDouble(CpuMetric::cpuLoad).average().orElse(0.0);

        System.out.println("=== Rapport de Moyenne ===");
        System.out.printf("Température Moyenne : %.2f°C %n", avgTemp);
        System.out.printf("Charge Moyenne : %.2f°C %n", avgLoad * 100);
        System.out.println("============================== \n");
    }


    /**
     * Démarre la boucle infinie de monitoring.
     * <p>
     * À chaque itération, l'application capture une donnée, l'enregistre via le
     * {@link JsonFileManager} et incrémente un compteur pour afficher une
     * moyenne glissante toutes les 5 secondes.
     * </p>
     */
    public void start() {

        this.scheduler = Executors.newScheduledThreadPool(1);

        System.out.println("--- Démarrage du Monitoring Simulé ---");

        final Runnable Instructions = new Runnable() {
            @Override
            public void run() {
                try{
                    CpuMetric machine = engine.generateMeasure();
                    for (MonitorListener listener : listeners){
                        listener.onMeasureReceived(machine);
                    }
                    jsonFileManager.saveMetric(machine);
                    System.out.printf("Température : %.2f°C | CpuLoad: %.1f%% | [%tT] %n",
                            machine.temperature(), machine.cpuLoad() * 100, machine.timestamp());
                    if (machine.temperature() > 95.0) {
                        System.err.println("La Température est trop élevée. La machine s'arrête."
                                + machine.temperature() + "°C");
                        scheduler.shutdown();
                    } else if (machine.cpuLoad() * 100 > 85.0) {
                        System.err.println("L'utilisation du CPU est trop élévée. La machine s'arrête."
                                + machine.cpuLoad() + "%");
                        scheduler.shutdown();
                    }
                    if (tickCount == 5) {
                        calculateAndDisplayAverage();
                        tickCount = 0;
                    }
                    tickCount++;
            }catch (Exception e){
                    System.err.println("Erreur");
                }
            } // Public void Run
        }; //Final Runnable

        this.scheduler.scheduleAtFixedRate(Instructions, 1, 1, TimeUnit.SECONDS);
    }
}
