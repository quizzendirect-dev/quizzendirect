package fr.univ.angers.quizz.api;

import java.io.IOException;
import java.net.URL;

import javax.annotation.PostConstruct;

import fr.univ.angers.quizz.api.datasfetchers.*;
import fr.univ.angers.quizz.api.model.*;
import graphql.TypeResolutionEnvironment;
import graphql.schema.GraphQLObjectType;
import graphql.schema.TypeResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

@Component
public class GraphQLProvider { 

  @Autowired
  private EnseignantDataFetcher enseignantDataFetcher;
  @Autowired
  private QuestionDataFetcher questionDataFetcher;
  @Autowired
  private RepertoireDataFetcher repertoireDataFetcher;
  @Autowired
  private HistoriqueDataFetcher historiqueDataFetcher;
  @Autowired
  private SalonDataFetcher salonDataFetcher;
  @Autowired
  private EtudiantDataFetcher etudiantDataFetcher;
  
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
      TypeResolver typeResolver = new TypeResolver() {
          @Override
          public GraphQLObjectType getType(TypeResolutionEnvironment env) {
              Object javaObject = env.getObject();
              if (javaObject instanceof Enseignant) {
                  return env.getSchema().getObjectType("Enseignant");
              } else if (javaObject instanceof Question) {
                  return env.getSchema().getObjectType("Question");
              } else if (javaObject instanceof Repertoire) {
                  return env.getSchema().getObjectType("Repertoire");
              } else if (javaObject instanceof Historique) {
                  return env.getSchema().getObjectType("Historique");
              } else if (javaObject instanceof Salon) {
                  return env.getSchema().getObjectType("Salon");
              } else if (javaObject instanceof Etudiant) {
                  return env.getSchema().getObjectType("Etudiant");
              } else {
                  return env.getSchema().getObjectType("Error");
              }
          }
      };
      return RuntimeWiring.newRuntimeWiring()
              .type("Query", typeWiring-> typeWiring
                      .dataFetcher("allEnseignants", enseignantDataFetcher.getAllProduct())
                      .dataFetcher("allQuestions", questionDataFetcher.getAllProduct())
                      .dataFetcher("allRepertoires", repertoireDataFetcher.getAllProduct())
                      .dataFetcher("allHistoriques", historiqueDataFetcher.getAllProduct())
                      .dataFetcher("allSalons", salonDataFetcher.getAllProduct())
                      .dataFetcher("allEtudiants", etudiantDataFetcher.getAllProduct())
                      .dataFetcher("getEnseignantById", enseignantDataFetcher.getEnseignantById())
                      .dataFetcher("getQuestionById", questionDataFetcher.getQuestionById())
                      .dataFetcher("getRepertoireById", repertoireDataFetcher.getRepertoireById())
                      .dataFetcher("getHistoriqueById", historiqueDataFetcher.getHistoriqueById())
                      .dataFetcher("getSalonById", salonDataFetcher.getSalonById())
                      .dataFetcher("getEtudiantById", etudiantDataFetcher.getEtudiantById())
              )
              .type("Mutation", typeWiring-> typeWiring
                      //Créations
                      .dataFetcher("createEnseignant", enseignantDataFetcher.createEnseignant())
                      .dataFetcher("createQuestion", questionDataFetcher.createQuestion())
                      .dataFetcher("createRepertoire", repertoireDataFetcher.createRepertoire())
                      .dataFetcher("createHistorique", historiqueDataFetcher.createHistorique())
                      .dataFetcher("createSalon", salonDataFetcher.createSalon())
                      .dataFetcher("createEtudiant", etudiantDataFetcher.createEtudiant())

                      //Modifications
                      //    Enseignant
                      .dataFetcher("updateEnseignantNom", enseignantDataFetcher.updateNom())
                      .dataFetcher("updateEnseignantMail", enseignantDataFetcher.updateMail())
                      .dataFetcher("updateEnseignantMotDePasse", enseignantDataFetcher.updateMotDePasse())
                      .dataFetcher("ajouterRepertoireEnseignant", enseignantDataFetcher.ajouterRepertoire())
                      .dataFetcher("supprimerRepertoireEnseignant", enseignantDataFetcher.supprimerRepertoire())
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
                      .dataFetcher("updateRepertoireEnseignant", repertoireDataFetcher.updateEnseignant())
                      //    Historique
                      .dataFetcher("updateHistoriqueQuestion", historiqueDataFetcher.updateQuestion())
                      .dataFetcher("updateHistoriqueDate", historiqueDataFetcher.updateDate())
                      //    Salon
                      .dataFetcher("updateSalonCodeAcces", salonDataFetcher.updateCodeAcces())
                      .dataFetcher("updateSalonEnseignant", salonDataFetcher.updateEnseignant())
                      .dataFetcher("ajouterQuestionSalon", salonDataFetcher.ajouterQuestion())
                      .dataFetcher("supprimerQuestionSalon", salonDataFetcher.supprimerQuestion())
                      .dataFetcher("ajouterEtudiantSalon", salonDataFetcher.ajouterEtudiant())
                      .dataFetcher("supprimerEtudiantSalon", salonDataFetcher.supprimerEtudiant())
                      //    Étudiant
                      .dataFetcher("updateEtudiantPseudo", etudiantDataFetcher.updatePseudo())
                      .dataFetcher("updateEtudiantSalon", etudiantDataFetcher.updateSalon())
                      .dataFetcher("incrementerBonnesReponsesEtudiant", etudiantDataFetcher.incrementerBonnesReponses())
                      .dataFetcher("incrementerQuestionsReponduesEtudiant", etudiantDataFetcher.incrementerQuestionsRepondues())

                      //Suppressions
                      .dataFetcher("removeEnseignant", enseignantDataFetcher.removeEnseignant())
                      .dataFetcher("removeQuestion", questionDataFetcher.removeQuestion())
                      .dataFetcher("removeRepertoire", repertoireDataFetcher.removeRepertoire())
                      .dataFetcher("removeHistorique", historiqueDataFetcher.removeHistorique())
                      .dataFetcher("removeSalon", salonDataFetcher.removeSalon())
                      .dataFetcher("removeEtudiant", etudiantDataFetcher.removeEtudiant())
              )
              .type("EnseignantResult", typeWriting-> typeWriting.typeResolver(typeResolver))
              .type("QuestionResult", typeWriting-> typeWriting.typeResolver(typeResolver))
              .type("RepertoireResult", typeWriting-> typeWriting.typeResolver(typeResolver))
              .type("HistoriqueResult", typeWriting-> typeWriting.typeResolver(typeResolver))
              .type("SalonResult", typeWriting-> typeWriting.typeResolver(typeResolver))
              .type("EtudiantResult", typeWriting-> typeWriting.typeResolver(typeResolver))
              .build();
  }

  @Bean
  public GraphQL graphQL() {
      return graphQL;
  }



}
