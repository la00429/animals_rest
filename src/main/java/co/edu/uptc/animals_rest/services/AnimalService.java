package co.edu.uptc.animals_rest.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.uptc.animals_rest.exception.CategoryNotFoundException;
import co.edu.uptc.animals_rest.exception.InvalidRangeException;
import co.edu.uptc.animals_rest.exception.InvalideLengthNameException;
import co.edu.uptc.animals_rest.models.Animal;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
@Service
public class AnimalService {
     private static final Logger logger = LoggerFactory.getLogger(AnimalService.class);
    @Value("${animal.file.path}")
    private String filePath;

    
    public List<Animal> getAnimalInRange(int from, int to) throws IOException {
        List<String> listAnimal = Files.readAllLines(Paths.get(filePath));
        List<Animal> animales = new ArrayList<>();
        if (from < 0 || to >= listAnimal.size() || from > to) {
            logger.warn("Invalid range: Please check the provided indices. Range: 0 to {}",listAnimal.size());
             throw new InvalidRangeException("Invalid range: Please check the provided indices.");
        }
        for (String line : listAnimal) {
            String[] parts = line.split(",");
            if (parts.length == 2) {
                String categoria = parts[0].trim();
                String nombre = parts[1].trim();                
                animales.add(new Animal(nombre, categoria));
            }
        }
        return animales.subList(from, to + 1);
    }

    public List<Animal> getAnimalAll() throws IOException {
        List<String> listAnimal = Files.readAllLines(Paths.get(filePath));
        List<Animal> animales = new ArrayList<>();
        for (String line : listAnimal) {
            String[] parts = line.split(",");
            if (parts.length == 2) {
                String category = parts[0].trim();
                String name = parts[1].trim();                
                animales.add(new Animal(name, category));
            }
        }
    
        return animales;
    }

    public List<Animal> getAnimalByCategory(String categoryFound) throws IOException {
        List<String> listAnimal = Files.readAllLines(Paths.get(filePath));
        List<Animal> animales = new ArrayList<>();
        boolean categoryExists = false;

        for (String line : listAnimal) {
            String[] parts = line.split(",");
            if (parts.length == 2 &&parts[0].trim().equals(categoryFound)) {
                categoryExists = true;
                String category = parts[0].trim();
                String name = parts[1].trim();                
                animales.add(new Animal(name, category));
                
            }
        }

        if (!categoryExists) {
        throw new CategoryNotFoundException("Category not found: " + categoryFound);
    }
    
        return animales;
        
    }

    public List<Animal> getAnimalNameLength(int length) throws IOException {
        List<String> listAnimal = Files.readAllLines(Paths.get(filePath));
        List<Animal> animales = new ArrayList<>();
        boolean lenghtName = false;
        for (String line : listAnimal) {
            String[] parts = line.split(",");
            if (parts.length == 2 && parts[1].trim().length() < length) {
                lenghtName = true;
                String category = parts[0].trim();
                String name = parts[1].trim();                
                animales.add(new Animal(name, category));
                }
        }

        if(!lenghtName){
            throw new InvalideLengthNameException("No animals were found with names shorter than" + length +" letters");
        }
    
        return animales;
    }
}

