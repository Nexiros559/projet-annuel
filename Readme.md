# 🖥️ System Resource Monitor (Java)

Une application de surveillance système modulaire développée en Java. Elle simule la capture de métriques matérielles (Température CPU, Charge CPU), les enregistre et déclenche des alertes en temps réel.

Le projet est en cours d'évolution vers une interface graphique moderne avec **JavaFX**.

---

## 🚀 Fonctionnalités Clés

* **Simulation de Capteurs :** Génération réaliste de données CPU (Température, Charge, Timestamp).
* **Monitoring Planifié :** Utilisation de `ScheduledExecutorService` pour une boucle de surveillance précise et non-bloquante (1 tick/seconde).
* **Architecture Découplée (Observer Pattern) :** Séparation stricte entre la logique métier (`MonitorApp`) et l'affichage via l'interface `MonitorListener`.
* **Persistance des Données :** Sauvegarde automatique des métriques au format **JSON** via la bibliothèque Jackson.
* **Système d'Alertes :** Détection automatique des surchauffes (>95°C) et surcharges (>85%).
* **Statistiques :** Calcul de moyennes glissantes sur des fenêtres de temps définies.

---

## 🛠️ Stack Technique

* **Langage :** Java 17+ (Utilisation des `Records` et `Streams`).
* **Build Tool :** Maven.
* **Bibliothèques :**
    * `Jackson` (Traitement JSON).
    * `JUnit 5` (Tests Unitaires & Intégration).
    * `JavaFX` (Interface Graphique - En cours d'intégration).
* **Concepts Clés :** Concurrency, Design Patterns (Observer), File I/O.

---

## 📂 Architecture du Projet

```text
src/main/java/org/example
├── CpuMetric.java         # Record (DTO) immuable pour les données.
├── MetricEngine.java      # Générateur de données simulées.
├── JsonFileManager.java   # Gestion de la lecture/écriture disque.
├── MonitorApp.java        # Cœur de l'application (Orchestrateur & Scheduler).
├── MonitorListener.java   # Interface (Contrat) pour le pattern Observer.
└── MonitorWindow.java     # (WIP) Future interface graphique JavaFX.