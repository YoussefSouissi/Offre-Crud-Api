package com.example.Offreproject.Repository;

import com.example.Offreproject.models.Offre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OffreRepo extends JpaRepository<Offre, Integer> {

}
