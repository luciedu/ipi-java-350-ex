package com.ipiecoles.java.java350.model;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.time.LocalDate;

//Créer la classe permettant de tester la méthode getNombreAnneeAnciennete de la classe Employe.

class EmployeTest {

            // Tests unitaires classiques //

// Mettre en place les tests unitaires nécessaires pour tester le plus exhaustivement possible cette méthode.
// Bien penser à tous les cas possibles, notamment les cas aux limites. Ne pas hésiter à corriger le code de la méthode initiale si besoin.

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
        LocalDate dateEmbauche = LocalDate.now().plusYears(5);
        Employe employe = new Employe();
        employe.setDateEmbauche(dateEmbauche);

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


// Créer une méthode de test paramétré permettant de tester le plus exhaustivement possible la méthode getPrimeAnnuelle de la classe Employe et corriger les éventuels problème de cette méthode


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

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            // Tests paramétrés //

    /// ON TRANSFORME LE DERNIER TEST EN TEST PARAMETRE ///

    @ParameterizedTest(name = "Employé ancienneté {0}, performance {1}, matricule {2}, temps partiel {3} => Prime {4} ")
    // On renseigne les valeurs que l'on souhaite pour les paramètres - Pour avoir "null", ne rien mettre
    @CsvSource({
            "0,,'M12345',1.0,1700.0", // Manager à plein temps sans ancienneté
            "0,,'M12345',0.5,850.0", // Manager à mi-temps sans ancienneté
            "5,,'M12345',1.0,2200.0", // Manager à plein temps avec 5 années d'ancienneté
            "0,,'T12345',1.0,1000.0", // Technicien à plein temps sans ancienneté
            "0,3,'T12345',1.0,3300.0", // Technicien à plein temps sans ancienneté avec une performance 3
            "0,1,'T12345',1.0,1000.0", // Technicien à plein temps sans ancienneté avec une performance de base
            "2,1,'T12345',1.0,1200.0", // Technicien à plein temps avec 2 ans d'ancienneté avec performance de base
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

    // => la methode public Double getPrimeAnnuelle() est couverte à 100% avec les différents scénarios de test


// Tester de manière unitaire le plus exhaustivement possible la méthode augmenterSalaire d'Employe en essayant de faire du TDD.

    @Test
    public void testAugmenterSalaireManager(){
        //Given
        Poste poste = Poste.MANAGER;
        Double salaire = 3000.0;
        LocalDate dateEmbauche = LocalDate.now().minusYears(5);

        //Initialise l'employé à partir des données d'entrée
        Employe employe = new Employe("Doe", "John", "M00012",
                dateEmbauche, salaire, 1, 1.0);

        //When

        Double newSalaire = employe.augmenterSalaire(10.0);

        //Then
        // 3000 + 10%
        Assertions.assertThat(newSalaire).isEqualTo(3300);
    }

    @Test
    public void testAugmenterSalaireTechnicien(){
        //Given
        Poste poste = Poste.TECHNICIEN;
        Double salaire = 1000.0;
        LocalDate dateEmbauche = LocalDate.now().minusYears(10);

        //Initialise l'employé à partir des données d'entrée
        Employe employe = new Employe("Amstrong", "Toto", "T00012",
                dateEmbauche, salaire, 1, 1.0);

        //When
        Double newSalaire = employe.augmenterSalaire(5.0);

        //Then
        // 1000 + 5%
        Assertions.assertThat(newSalaire).isEqualTo(1050);
    }

    @Test
    public void testAugmenterSalaireCommercial(){
        //Given
        Poste poste = Poste.COMMERCIAL;
        Double salaire = 2000.0;
        LocalDate dateEmbauche = LocalDate.now().minusYears(1);

        //Initialise l'employé à partir des données d'entrée
        Employe employe = new Employe("Durand", "Pauline", "C00012",
                dateEmbauche, salaire, 1, 1.0);

        //When
        Double newSalaire = employe.augmenterSalaire(8.0);

        //Then
        // 2000 + 8%
        Assertions.assertThat(newSalaire).isEqualTo(2160);
    }

    @Test
    public void testAugmenterSalaireNouvelEmploye(){
        //Given
        Poste poste = Poste.COMMERCIAL;
        Double salaire = 2000.0;
        LocalDate dateEmbauche = LocalDate.now();

        //Initialise l'employé à partir des données d'entrée
        Employe employe = new Employe("Delacoline", "Marie", "C00014",
                dateEmbauche, salaire, 1, 1.0);

        //When
        Double newSalaire = employe.augmenterSalaire(0.0);

        //Then
        // 2000 + 0%
        Assertions.assertThat(newSalaire).isEqualTo(2000);
    }

    @ParameterizedTest(name = "matricule {0}, salaire {1}, pourcentage d'augmentation {2}, Integer nbAnneeAnciennete {3} => Salaire final {3}")
    //Rajoute l'annotation contenant les scénarios de test  (réflechir aux dfférents scénarios possibles)
    @CsvSource({
            "'M00012',3000,10.0,5,3300.0", //Manager avec ancienneté
            "'T00012',1000,5.0,10,1050.0", //Technicien avec ancienneté
            "'C00012',2000,8.0,1,2160.0", //Commercial avec ancienneté
            "'C00014',2000,8.0,0,2000.0", //Commercial sans ancienneté

    })
    public void testAugmenterSalaire(String matricule, Double salaire, Double pourcentage, Integer nbAnneesAnciennete, Double newSalaire) {

        //Given
        // 4 données d'entrée => remplacer par les paramètres
        LocalDate dateEmbauche = LocalDate.now().minusYears(nbAnneesAnciennete);
        //Initialise l'employé à partir des données d'entrée
        Employe employe = new Employe("Pierre", "Richard", matricule,
                dateEmbauche, salaire, 1, 1.0);
        ///When
        Double salaireEstime = employe.augmenterSalaire(pourcentage);

        //Then
        Assertions.assertThat(salaireEstime).isEqualTo(newSalaire);
    }

}