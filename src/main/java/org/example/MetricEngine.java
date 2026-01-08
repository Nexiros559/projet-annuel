package org.example;

import java.util.Random;

/**
 * Moteur de capture des données système.
 * <p>
 * Actuellement, cette classe simule des mesures à l'aide de générateurs aléatoires,
 * en attendant l'intégration de bibliothèques matérielles (comme OSHI).
 * </p>
 */
public class MetricEngine {
    private final Random random = new Random();
    private static final float TEMP_LIMIT = 95;
    private static final double LOAD_LIMIT = 0.85;

    /**
     * Génère une mesure CPU complète avec calcul automatique des alertes.
     * <p>
     * La méthode calcule si les valeurs générées dépassent les limites définies
     * par {@code TEMP_LIMIT} et {@code LOAD_LIMIT}.
     * </p>
     * * @return Un nouvel objet {@link CpuMetric} contenant les données et les alertes.
     */
    public CpuMetric generateMeasure(){
        double load = random.nextDouble();
        double temp = 40.0 + (load * 40.0) + (random.nextDouble() * 5.0);
        boolean isOverheating = temp > TEMP_LIMIT;
        boolean isOverloading = load > LOAD_LIMIT;

        return new CpuMetric(temp, load, System.currentTimeMillis(), isOverheating, isOverloading);
    }

    /**
     * Méthode qui permet de faire les tests unitaires en attendant de pouvoir se séparer de l'aléatoire
     * L'aléatoire faussais les tests.
     */
    public CpuMetric createMeasure(double temp, double load){
        boolean isOverheating = temp > 95.0;
        boolean isOverloaded = load > 0.85;
        return new CpuMetric(temp, load, System.currentTimeMillis(), isOverheating, isOverloaded);
    }

}
