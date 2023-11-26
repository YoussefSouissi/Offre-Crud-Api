package com.example.Offreproject.Controller;

import com.example.Offreproject.Repository.OffreRepo;
import com.example.Offreproject.models.Offre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/offre")
public class RestOffre {

    @Autowired
    OffreRepo offreRepo;

    @GetMapping("/{id}")
    public Offre getbyId(@PathVariable Integer id) {
        return offreRepo.findById(id).get() ;
    }

    @GetMapping
    public List<Offre> getAll() {
        return offreRepo.findAll();
    }

    @PostMapping
    public Offre AddOffre(@RequestBody Offre offre)
    {
        return offreRepo.save(offre);
    }

    @DeleteMapping("/{id}")
    public void deleteOffre(@PathVariable Integer id) {
        offreRepo.deleteById(id);
    }

    @PutMapping("/{id}")
    public Offre UpdateOffre(@PathVariable Integer id,@RequestBody Offre offre) {
        offre.setCode(id);
        return offreRepo.save(offre);
    }

}