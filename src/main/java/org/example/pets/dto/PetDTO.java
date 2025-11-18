package org.example.pets.dto;

import jakarta.validation.constraints.*;

public class PetDTO {
    private Long id;

    @NotBlank //Krav att inte vara null.
    private String name;

    @NotBlank
    private String species;

    @Min(0)
    @Max(100)
    private int hungerLevel;

    @Min(0)
    @Max(100)
    private int happiness;

    public PetDTO() {}  //Tom för att json ska kunna skapa objekt.

    // getters & setters
    public Long getId() { return id; } //GET
    public void setId(Long id) { this.id = id; } //SET

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSpecies() { return species; }
    public void setSpecies(String species) { this.species = species; }

    public int getHungerLevel() { return hungerLevel; }
    public void setHungerLevel(int hungerLevel) { this.hungerLevel = hungerLevel; }

    public int getHappiness() { return happiness; }
    public void setHappiness(int happiness) { this.happiness = happiness; }
}
