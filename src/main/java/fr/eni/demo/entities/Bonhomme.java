package fr.eni.demo.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
@Builder

@Entity @Table(name = "bonhommes")
public class Bonhomme {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "{bonhomme.nom.notnull}")
    @Size(min = 2, max = 50, message = "{bonhomme.nom.size}")
    private String nom;

    @Min(value = 18, message = "{bonhomme.age.min}")
    @Max(value = 140, message = "{bonhomme.age.max}")
    private int age;

    @OneToOne(cascade = CascadeType.ALL)
    private Animal animal;
}
