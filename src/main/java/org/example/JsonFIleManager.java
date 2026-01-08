package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Gère la persistance des données au format JSON.
 * <p>
 * Cette classe utilise la bibliothèque Jackson pour lire et écrire des listes
 * d'objets {@link CpuMetric} dans un fichier local.
 * </p>
 */
class JsonFileManager {
    private final String filePath;
    ObjectMapper objectMapper;
    List<CpuMetric> metrics = null;
    File file = null;

    /**
     * Initialise le gestionnaire et vérifie l'existence du fichier.
     * Si le fichier n'existe pas, il est créé avec une liste vide [].
     * * @param filePath Le chemin vers le fichier JSON de stockage.
     */
    JsonFileManager(String filePath) {
        this.filePath = filePath;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        File file = new File(this.filePath);

        if(!file.exists()){
            try {
                objectMapper.writeValue(file, new ArrayList<>());
                System.out.println("Fichier crée avec succès" + filePath);
            }catch(IOException e){
                e.printStackTrace();
                System.err.println("Erreur lors de la création du fichier JSON" + e.getMessage());
            }
        }
    }

    /**
     * Enregistre une nouvelle métrique en l'ajoutant à l'historique existant.
     * La méthode charge le fichier, ajoute l'élément, puis réécrit tout le contenu.
     * * @param metric L'objet {@link CpuMetric} à sauvegarder.
     */
    public void saveMetric(CpuMetric metric){
        try{
            file = new File(this.filePath);
            TypeReference<List<CpuMetric>> typeRef = new TypeReference<List<CpuMetric>>() {};
            metrics = objectMapper.readValue(file, typeRef);

        }catch(IOException e){
            System.err.println("Une erreur a été rencontrée" + e.getMessage());
            e.printStackTrace();
            metrics = new ArrayList<CpuMetric>();
        }
        metrics.add(metric);
        try {
            objectMapper.writeValue(file, metrics);
        }catch (IOException e){
            return;
        }
    }

    /**
     * Lit l'intégralité du fichier JSON et le transforme en liste d'objets Java.
     * * @return Une {@link List} contenant toutes les métriques enregistrées.
     * Retourne une liste vide en cas d'erreur de lecture.
     */
    public List<CpuMetric> readAllMetrics() {
        File file = new File(this.filePath);
        try{
            TypeReference<List<CpuMetric>> typeRef = new TypeReference<List<CpuMetric>>() {};
            return objectMapper.readValue(file, typeRef);
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du JSON : " + e.getMessage());
            return new ArrayList<>();
        }
    }
}