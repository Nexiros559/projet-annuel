package org.example;

/**
 * Représente une mesure instantanée des performances du processeur.
 * Ce record est utilisé pour le transport des données vers le stockage JSON.
 *
 * @param temperature La température du processeur en degrés Celsius.
 * @param cpuLoad La charge du processeur (entre 0.0 et 1.0).
 * @param timestamp Le moment de la mesure en millisecondes (Epoch time).
 * @param isOverheating Indique si la température dépasse le seuil critique.
 * @param isOverloaded Indique si la charge dépasse le seuil critique.
 */
public record CpuMetric(double temperature, double cpuLoad, long timestamp,
                        boolean isOverheating, boolean isOverloaded) {
}

