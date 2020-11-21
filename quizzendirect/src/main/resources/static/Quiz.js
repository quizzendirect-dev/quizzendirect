/* WebSocket */
var stompClient = null;

/* Connecte le webSocket dés l'arrivée de la page */
(function connect() {
    var socket = new SockJS('http://localhost:20020/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/quiz/salon', function (question) {
            getQuestion(JSON.parse(question.body));
        });
    });
})();


function getQuestion(question) {

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
    for (var i = 0; i < propositions.length ; i++) {
        $("#proposition" + (i+1) + "").html(propositions[i]);
    }

    /* décrémente le timer */
    var time = question.time;
    function sleep(ms) {
        return new Promise (resolve => setTimeout(resolve,ms));
    }
    async function reduceTime() {
        while (time!=0) {
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
$(function() {
    /* Au démarrage, en attente d'une question */
    $('#loadbar').show();
    $("#quiz").fadeOut();

    /* Quand un étudiant clique sur une réponse, le chargement s'affiche */
    $("label").click(function(){
        $('#loadbar').show();
        $("#quiz").fadeOut();
    });
});