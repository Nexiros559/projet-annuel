package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class JsonFileManager {
    private final String filePath;
    ObjectMapper objectMapper;

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

    public void saveMetric(CpuMetric metric){
        try{
            File file = new File(this.filePath);
            TypeReference<List<CpuMetric>> typeRef = new TypeReference<List<CpuMetric>>() {};
            List<CpuMetric> metrics = objectMapper.readValue(file, typeRef);
            metrics.add(metric);
            objectMapper.writeValue(file, metrics);
        }catch(IOException e){
            System.err.println("Une erreur a été rencontrée" + e.getMessage());
            e.printStackTrace();
        }
    }

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
