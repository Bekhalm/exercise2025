package org.example.pets.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException; //För att ge automatiskt 404-svar.
import org.example.pets.dto.PetDTO;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@ApplicationScoped
public class PetService {
    private final ConcurrentHashMap<Long, PetDTO> store = new ConcurrentHashMap<>(); //Lagrar objekt i minnet.
    private final AtomicLong seq = new AtomicLong(1); //Genererar nya id:n

    public Collection<PetDTO> findAll() {  //GET, hämtar lista över alla djur.
        return store.values();
    }

    public Optional<PetDTO> find(long id) {
        return Optional.ofNullable(store.get(id));
    }

    public PetDTO create(PetDTO dto) { //Skapa nytt djur.
        long id = seq.getAndIncrement();
        dto.setId(id);
        store.put(id, dto); //Lagra djuret.
        return dto;
    }

    public PetDTO feed(long id, int amount) { //Matning.
        return store.compute(id, (k, v) -> {
            if (v == null) throw new NotFoundException(); //Om djuret inte finnns, ge 404 error.
            v.setHungerLevel(Math.max(0, v.getHungerLevel() - amount)); //Uppdatera lagret automatiskt efter matning.
            return v; //Nya värdet retuernas efter matningen.
        });
    }

    public PetDTO play(long id, int amount) {  //Lek, likt matning med max-tak.
        return store.compute(id, (k, v) -> {
            if (v == null) throw new NotFoundException();
            v.setHappiness(Math.min(100, v.getHappiness() + amount)); //Ökar värdet.
            return v;
        });
    }

    public void delete(long id) { //Ta bort djur.
        if (store.remove(id) == null) throw new NotFoundException();
    }
}