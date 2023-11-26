package com.example.Offreproject.models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

@Setter
@Getter
public class Offre {

    @Id
    @GeneratedValue(strategy =
            GenerationType.IDENTITY)
    private int Code;
    private String intitule;
    private String specialite;
    private String societe;
    private int nbPostes;
    private String pays;



}
