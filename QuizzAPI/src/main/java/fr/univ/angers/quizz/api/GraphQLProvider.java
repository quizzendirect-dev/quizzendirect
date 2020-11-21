package fr.univ.angers.quizz.api;

import java.io.IOException;
import java.net.URL;

import javax.annotation.PostConstruct;

import fr.univ.angers.quizz.api.datasfetchers.HistoriqueDataFetcher;
import fr.univ.angers.quizz.api.datasfetchers.QuestionDataFetcher;
import fr.univ.angers.quizz.api.repository.HistoriqueRepository;
import fr.univ.angers.quizz.api.repository.RepertoireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import fr.univ.angers.quizz.api.datasfetchers.ProfesseurDataFetcher;
import fr.univ.angers.quizz.api.datasfetchers.RepertoireDataFetcher;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;



@Component
public class GraphQLProvider { 

  
  @Autowired
  private ProfesseurDataFetcher professeurDataFetcher;
  @Autowired
  private QuestionDataFetcher questionDataFetcher;
  @Autowired
  private RepertoireDataFetcher repertoireDataFetcher;
  @Autowired
  private HistoriqueDataFetcher historiqueDataFetcher;
  
  private GraphQL graphQL;
  

  @PostConstruct
  public void init() throws IOException {
      URL url = Resources.getResource("schema.graphqls");
      String sdl = Resources.toString(url, Charsets.UTF_8);
      GraphQLSchema graphQLSchema = buildSchema(sdl);
      this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
  }

  private GraphQLSchema buildSchema(String sdl) {
      TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
      RuntimeWiring runtimeWiring = buildWiring();
      SchemaGenerator schemaGenerator = new SchemaGenerator();
      return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
  }


  
   
  private RuntimeWiring buildWiring() {
      return RuntimeWiring.newRuntimeWiring()
              .type("Query", typeWiring-> typeWiring
                      .dataFetcher("allProfesseurs", professeurDataFetcher.getAllProfesseurs())
                      .dataFetcher("allQuestions", questionDataFetcher.getAllProduct())
                      .dataFetcher("allRepertoires", repertoireDataFetcher.getAllProduct())
                      .dataFetcher("allHistoriques", historiqueDataFetcher.getAllProduct())
                      .dataFetcher("getProfesseurByNom", professeurDataFetcher.getProfesseurByNom())
                      .dataFetcher("getQuestionByIntitule", questionDataFetcher.getQuestionByIntitule())
                      .dataFetcher("getRepertoireByNom", repertoireDataFetcher.getRepertoireByNom())
                      .dataFetcher("getHistoriqueByDate", historiqueDataFetcher.getHistoriqueByDate())
              )
              .type("Mutation", typeWiring-> typeWiring
                      //Créations
                      .dataFetcher("createProfesseur", professeurDataFetcher.createProfesseur())
                      .dataFetcher("createQuestion", questionDataFetcher.createQuestion())
                      .dataFetcher("createRepertoire", repertoireDataFetcher.createRepertoire())
                      .dataFetcher("createHistorique", historiqueDataFetcher.createHistorique())

                      //Modifications
                      //    Professeur
                      .dataFetcher("updateProfesseurNom", professeurDataFetcher.updateNom())
                      .dataFetcher("updateProfesseurMail", professeurDataFetcher.updateMail())
                      .dataFetcher("updateProfesseurMotDePasse", professeurDataFetcher.updateMotDePasse())
                      .dataFetcher("ajouterRepertoireProfesseur", professeurDataFetcher.ajouterRepertoire())
                      .dataFetcher("supprimerRepertoireProfesseur", professeurDataFetcher.supprimerRepertoire())
                      //    Question
                      .dataFetcher("updateQuestionIntitule", questionDataFetcher.updateIntitule())
                      .dataFetcher("updateQuestionChoixUnique", questionDataFetcher.updateChoixUnique())
                      .dataFetcher("updateQuestionReponsesBonnes", questionDataFetcher.updateReponsesBonnes())
                      .dataFetcher("updateQuestionReponsesFausses", questionDataFetcher.updateReponsesFausses())
                      .dataFetcher("updateQuestionTime", questionDataFetcher.updateTime())
                      .dataFetcher("updateQuestionRepertoire", questionDataFetcher.updateRepertoire())
                      //    Repertoire
                      .dataFetcher("updateRepertoireNom", repertoireDataFetcher.updateNom())
                      .dataFetcher("ajouterQuestionRepertoire", repertoireDataFetcher.ajouterQuestion())
                      .dataFetcher("supprimerQuestionRepertoire", repertoireDataFetcher.supprimerQuestion())
                      .dataFetcher("updateRepertoireProfesseur", repertoireDataFetcher.updateProfesseur())
                      //    Historique
                      .dataFetcher("updateHistoriqueDate", historiqueDataFetcher.updateDate())

                      //Suppressions
                      .dataFetcher("removeProfesseur", professeurDataFetcher.removeProfesseur())
                      .dataFetcher("removeQuestion", questionDataFetcher.removeQuestion())
                      .dataFetcher("removeRepertoire", repertoireDataFetcher.removeRepertoire())
                      .dataFetcher("removeHistorique", historiqueDataFetcher.removeHistorique())
              )
              .build();
  }

  @Bean
  public GraphQL graphQL() {
      return graphQL;
  }



}
