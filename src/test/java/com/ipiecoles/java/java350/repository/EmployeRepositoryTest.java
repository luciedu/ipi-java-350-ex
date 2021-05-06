package com.ipiecoles.java.java350.repository;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;


// Tests d'intégration//
// Créer la classe de test et les méthodes permettant de tester la méthode findLastMatricule de EmployeRepository.

@SpringBootTest
//@TestInstance(TestInstance.Lifecycle.PER_CLASS) // utile pour faire fonctionner le @BeforeAll
class EmployeRepositoryTest {

    @Autowired
    private EmployeRepository employeRepository;

    @AfterEach
    //@BeforeAll -- peut remplacer le @BeforeEach
    @BeforeEach
    public void cleanUp(){
        employeRepository.deleteAll();
    }

    @Test
    void testfindLastMatricule1EmployeM12345() {
        //Given avec de vraies données d'entrées
        Employe employe = employeRepository.save(new Employe("Doe", "John", "M12345",
                LocalDate.now(), Entreprise.SALAIRE_BASE, Entreprise.PERFORMANCE_BASE, 1.0));

        //When avec appel des vraies méthodes de repository...
        String lastMatricule = employeRepository.findLastMatricule();

        //Then avec de vraies vérifications...
        Assertions.assertThat(lastMatricule).isEqualTo("12345");
    }

    @Test
    void testfindLastMatricule3Employes() {
        //Given avec de vraies données d'entrées
        employeRepository.save(new Employe("Doe", "John", "C11032",
                LocalDate.now(), Entreprise.SALAIRE_BASE, Entreprise.PERFORMANCE_BASE, 1.0));
        employeRepository.save(new Employe("Doe", "John", "M12345",
                LocalDate.now(), Entreprise.SALAIRE_BASE, Entreprise.PERFORMANCE_BASE, 1.0));
       employeRepository.save(new Employe("Doe", "John", "T12000",
                LocalDate.now(), Entreprise.SALAIRE_BASE, Entreprise.PERFORMANCE_BASE, 1.0));

        //When avec appel des vraies méthodes de repository...
        String lastMatricule = employeRepository.findLastMatricule();

        //Then avec de vraies vérifications...
        Assertions.assertThat(lastMatricule).isEqualTo("12345");
    }

    @Test
    public void testFindLastMatricule0Employe(){
        //Given
        //When
        String lastMatricule = employeRepository.findLastMatricule();

        //Then
        Assertions.assertThat(lastMatricule).isNull();
    }
}

