


/*
var callAPI = function (query) {
            var result = null;
            $.ajax({
            method: "POST",
            url: "http://localhost:20020/graphql",
            headers: {'Content-Type': 'application/json'},
            async : false,
            data: JSON.stringify({
                query: query
            })
        }).done(function(data){
            result = data.data;
        })
        .fail(function(data) {
            console.log("Ajax Echec");
        });
        return result;
}
*/

var callAPI = async function (query) {
    let result;

    try {
        result = await $.ajax({
            method: "POST",
            url: "http://localhost:20020/graphql",
            headers: {'Content-Type': 'application/json'},
            data: JSON.stringify({
                query: query
            })

        });
        return result;
    }
    catch(error) {
        console.error(error);
    }
};


var getAllQuestions = function () {
    var query = '{' +
        '  allQuestions {' +
        '    id_quest' +
        '    intitule' +
        '    reponsesBonnes' +
        '    reponsesFausses' +
        '  }' +
        '}';

    const data =  callAPI(query);
    return data;
}


/********** REMARQUE **************/
/* Pour accéder aux données (avec l'API) :
1) Ajouter la balise <script src="CHEMIN/callAPI.js" ></script> dans le fichier html
2) Faire appel à la fonction callAPI(query), qui prend en parametre une variable query, qui contiendra votre requete
Exemple : On recupére toutes les questions
    var query = '{' +
        '  allQuestions {' +
        '    id_quest' +
        '    intitule' +
        '    reponsesBonnes' +
        '    reponsesFausses' +
        '  }' +
        '}';
   const donnees = callAPI(query);
   donnees.then((data) =>
            anotherFunction(donnees.data.allQuestions)
            );

 */





