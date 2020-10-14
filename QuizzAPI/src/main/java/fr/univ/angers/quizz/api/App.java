package fr.univ.angers.quizz.api;


import fr.univ.angers.quizz.api.model.Professeur;
import fr.univ.angers.quizz.api.repository.ProfesseurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class App
{
    @Autowired
    private ProfesseurRepository professeurRepository;
    
    public static void main( String[] args )
    {
        Professeur prof = new Professeur();
        prof.setUserName("Prof1");
        SpringApplication.run(App.class, args);
    }
}
