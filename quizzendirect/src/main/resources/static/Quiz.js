/* WebSocket */
var stompClient = null;
var laquestion = null;

/* Connecte le webSocket dés l'arrivée de la page */
(function connect() {
    var socket = new SockJS('http://localhost:20020/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        // ajout du code d'accés selon la variable en get dans l'url
        stompClient.subscribe('/quiz/salon/' + getQueryVariable("codeAcces"), function (question) {
            getQuestion(JSON.parse(question.body));
        });
    });
})();


function getQuestion(question) {

    laquestion = question;
    /* Cache le chargement et affiche la question */
    $('#loadbar').hide();
    $("#quiz").fadeIn();

    /* Remplis les informations des questions */
    $("#enonce").html(question.intitule);
    $("#timer").html(question.time);

    /* Récupére toutes les réponses (bonnes et mauvaises) dans un tableau de propositions */
    var propositions = (question.reponsesBonnes).concat(question.reponsesFausses);
    /* Shuffle le tableau de propositions afin de ne pas avoir toujours l'ordre des bonnes réponses suivis des mauvaises réponses  */
    propositions.sort(() => Math.random() - 0.5);

    /* Remplis les propositions des questions */
    for (var i = 0; i < propositions.length; i++) {
        $("#proposition" + (i + 1) + "").html(propositions[i]);
    }

    /* décrémente le timer */
    var time = question.time;

    function sleep(ms) {
        return new Promise(resolve => setTimeout(resolve, ms));
    }

    async function reduceTime() {
        while (time != 0) {
            await sleep(1000);
            $("#timer").html(time);
            time--;
        }
        $('#loadbar').show();
        $("#quiz").fadeOut();

    }

    reduceTime();
}

/* Au chargement */
$(function () {
    /* Au démarrage, en attente d'une question */
    $('#loadbar').show();
    $("#quiz").fadeOut();

    /* Quand un étudiant clique sur une réponse, le chargement s'affiche */
    $("label").click(function () {
        var reponseValue = $(this).children(4)[2].innerHTML
        console.log(reponseValue)
        sendReponse(reponseValue);
        $('#loadbar').show();
        $("#quiz").fadeOut();
    });
});

function sendReponse(reponseVal) {
    if (laquestion != null) {
        //TODO ajouter IF() ELSE
        if (reponseIsGood(laquestion.reponsesBonnes, reponseVal)) {
            let query = "mutation{\n" +
                "  updateReponse(reponse : \""+ reponseVal +"\" , BonneReponse: 1 , id_quest: " + laquestion.id_quest + " ){\n" +
                "id_quest" +
                "  }\n" +
                "}"
            console.log(query)
            const donnee = callAPI(query);
            console.log(donnee.data.updateReponse.id_quest)

        }
        else {
            let query = "mutation{\n" +
                "  updateReponse(reponse : \""+ reponseVal +"\" , MauvaiseReponse: 1 , id_quest:" + laquestion.id_quest + "){\n" +
                "id_quest" +
                "  }\n" +
                "}"
            console.log(query)
            const donnee = callAPI(query);
            console.log(donnee.data.updateReponse.id_quest)

        }

    }

}

function reponseIsGood(listeReponsesBonnes, reponseVal){
    for(var i = 0 ; i<listeReponsesBonnes.length ; i++){
        if(reponseVal === listeReponsesBonnes[i]){
            return true
        }
    }
    return false
}

$.getScript("callAPI.js", function () {
});

// fonction qui récupére une variable get pour récupérer le code d'accés entré par l'étudiant
function getQueryVariable(variable) {
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i = 0; i < vars.length; i++) {
        var pair = vars[i].split("=");
        if (pair[0] == variable) {
            return pair[1];
        }
    }
    return (false);
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