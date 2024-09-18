package fr.eni.demo.services;

import fr.eni.demo.dal.BonhommeRepository;
import fr.eni.demo.entities.Bonhomme;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BonhommeService {
    private BonhommeRepository bonhommeRepository;

    public BonhommeService(BonhommeRepository bonhommeRepository) {
        this.bonhommeRepository = bonhommeRepository;
    }

    public List<Bonhomme> getAll() {
        return bonhommeRepository.findAll();
    }

    public Bonhomme getById(int id) {
        return bonhommeRepository.findById(id).orElse(null);
    }

    public Bonhomme save(Bonhomme bonhomme) {
        if (bonhomme.getNom() == null || bonhomme.getNom().isBlank()) {
            throw new RuntimeException("Il faut au moins renseigner le nom.");
        }

        return bonhommeRepository.save(bonhomme);
    }
}
