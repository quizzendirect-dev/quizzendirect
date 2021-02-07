


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
    let environement = window.location.hostname
    if (environement == "localhost"){
        environement = "://" + environement + ":20020";
    }
    else {
        environement = "s://" +environement
    }
    console.log(environement)
    try {
        result = await $.ajax({
            method: "POST",
            url: "http"+ environement + "/graphql",
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

function verifyCookie(){
    console.log("cookie token "+getCookie("token") )
    console.log("cookie nom"+ getCookie("userName") )
    console.log("cookie userEmail" + getCookie("userEmail"))
    console.log("cookie userId_ens"+getCookie("userId_ens"))
}


function getCookie(name) {
    if (document.cookie.length == 0) return null;

    var regSepCookie = new RegExp('(; )', 'g');
    var cookies = document.cookie.split(regSepCookie);

    for (var i = 0; i < cookies.length; i++) {
        if (cookies[i].startsWith(name)) {
            return cookies[i].split("=")[1];
        }
    }
    return null;
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





