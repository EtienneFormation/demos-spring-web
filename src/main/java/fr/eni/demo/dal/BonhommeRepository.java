package fr.eni.demo.dal;

import fr.eni.demo.entities.Bonhomme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BonhommeRepository extends JpaRepository<Bonhomme, Integer> {
}
