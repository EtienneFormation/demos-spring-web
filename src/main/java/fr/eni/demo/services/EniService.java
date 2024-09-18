package fr.eni.demo.services;

import fr.eni.demo.entities.Crayon;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class EniService {
    // Données bouchonnées : à ne pas reproduire quand vous serez en TP
    private List<Crayon> crayonsBouchonnes = Arrays.asList(
            Crayon.builder().id(1).type("Feutre").couleur("Rouge").build(),
            Crayon.builder().id(2).type("Stylo").couleur("Bleu").build(),
            Crayon.builder().id(3).type("Stabilo").couleur("Rose").build()
    );

    public List<Crayon> listerCrayons() {
        return crayonsBouchonnes;
    }

    public Crayon getCrayonById(int id) {
        return crayonsBouchonnes.get(id);
    }
}
