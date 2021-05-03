package com.ipiecoles.java.java350.model;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;


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
}