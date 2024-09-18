package fr.eni.demo.controllers;

import fr.eni.demo.entities.Crayon;
import fr.eni.demo.services.EniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/eniecole")
public class EniController {
	private EniService eniService;

	public EniController(EniService eniService) {
		this.eniService = eniService;
	}

	@GetMapping
	public String welcome() {
		return "Bienvenue sur l'API ENI Ecole";
	}

	@GetMapping(path = "/crayons")
	public ResponseEntity<?> getCrayons() {
		List<Crayon> resultats = eniService.listerCrayons();
		if (resultats == null || resultats.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(resultats);
	}

	@GetMapping("/crayons/{id}")
	public ResponseEntity<?> getCrayonById(@PathVariable int id) {
		// Ce try/catch est une triche liée au bouchon !
		// Avec une BDD derrière, le traitement se ferait avec un if/else
		try {
			Crayon crayon = eniService.getCrayonById(id);
			return ResponseEntity.ok(crayon);
		} catch (Exception e) {
			return ResponseEntity.noContent().build();
		}
	}
}
