package com.ipiecoles.java.java350.model;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;


class EmployeTest {

    //Scénarios de test, 1 scénario = 1 test

    // Date d'embauche à aujourd'hui => Nombre année ancienneté : 0
    @Test
    public void testGetNbAnneesAncienneteDateEmbaucheToday() {

        //Given
        LocalDate dateEmbaucheToday = LocalDate.now();
        Employe employe = new Employe();
        employe.setDateEmbauche(dateEmbaucheToday);

        // When
        Integer nbAnneesAnciennete = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(nbAnneesAnciennete).isEqualTo(0);
    }

    // Date d'embauche dans le futur => Nombre années ancienneté : null
    @Test
    public void testGetNbAnneesAncienneteDateEmbaucheFuture() {

        //Given
        LocalDate dateEmbaucheToday = LocalDate.now().plusYears(5);
        Employe employe = new Employe();
        employe.setDateEmbauche(dateEmbaucheToday);

        // When
        Integer nbAnneesAnciennete = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(nbAnneesAnciennete).isNull();
    }

    // Date d'embauche null => Nombre années ancienneté : null
    @Test
    public void testGetNbAnneesAncienneteDateEmbaucheNull() {

        //Given
        Employe employe = new Employe();
        employe.setDateEmbauche(null);

        // When
        Integer nbAnneesAnciennete = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(nbAnneesAnciennete).isNull();
    }

    // Date d'embauche 5 ans dans le passé => Nombre années ancienneté : 5
    @Test
    public void testGetNbAnneesAncienneteDateEmbauchePast() {

        //Given
        LocalDate dateEmbaucheToday = LocalDate.now().minusYears(5);
        Employe employe = new Employe();
        employe.setDateEmbauche(dateEmbaucheToday);

        // When
        Integer nbAnneesAnciennete = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(nbAnneesAnciennete).isEqualTo(5);
    }

    // Données d'entrée : Matricule, date d'embauche, performance, temps partiel
    @Test
    public void testgetPrimeAnnuelleManagerSansAnciennete() {

        //Given - 4 données d'entrée
        LocalDate dateEmbauche = LocalDate.now();
        Integer performance = null;
        String matricule = "M12345";
        Double tempsPartiel = 1.0;
        //Initialise l'employé à partie des données d'entrée
        Employe employe = new Employe("Doe", "John", matricule,
                dateEmbauche, Entreprise.SALAIRE_BASE, performance, tempsPartiel);

        // When
        Double primeCalculee = employe.getPrimeAnnuelle();

        //Then
        // 1000 * 1.7 = 1700
        Assertions.assertThat(primeCalculee).isEqualTo(1700.0);

    }

    /// ON TRANSFORME LE DERNIER TEST EN TEST PARAMETRE ///

    @ParameterizedTest(name = "Employé ancienneté {0}, performance {1}, matricule {2}, temps partiel {3} => Prime {4} ")
    // On renseigne les valeurs que l'on souhaite pour les paramètres - Pour avoir "null", ne rien mettre
    @CsvSource({
            "0,,'M12345',1.0,1700.0", // Manager à plein temps sans ancienneté
            "0,,'T12345',1.0,1000.0", // Technicien à plein temps sans ancienneté
            "0,,'M12345',0.5,850.0", // Manager à mi-temps sans ancienneté
            "5,,'M12345',1.0,2200.0", // Manager à plein temps avec 5 années d'ancienneté

    })

    // 4 données d'entrée => remplacer par les paramètres
    // 1 donnée en sortie => remplacer par un paramètre
    public void testGetPrimeAnnuelle(Integer nbAnneesAnciennete, Integer performance, String matricule, Double tempsPartiel,
                                     Double primeObtenue){

        //Given
        LocalDate dateEmbauche = LocalDate.now().minusYears(nbAnneesAnciennete);

        //Initialise l'employé à partir des données d'entrée
        Employe employe = new Employe("Doe", "John", matricule,
                dateEmbauche, Entreprise.SALAIRE_BASE, performance, tempsPartiel);
        //When
        Double primeCalculee = employe.getPrimeAnnuelle();

        //Then
        //Remplace la valeur de sortie en dur par le paramètre de sortie
        Assertions.assertThat(primeCalculee).isEqualTo(primeObtenue);
    }


}