var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
};

function connect() {
    var socket = new SockJS('http://localhost:20020/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/quiz/salon', function (question) {
            getQuestion(JSON.parse(question.body));
        });
    });
};

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
};

function sendQuestion() {
    stompClient.send("/app/salon", {}, JSON.stringify(
        {
            'id_quest': 1,
            'intitule': 'questionX',
            'choixUnique': false,
            'reponsesBonnes': ["A" , "B"],
            'reponsesFausses': ["C","D"],
            'time': 15
        }
        )
    );
};


function getQuestion(message) {
    $("#greetings").append("<p> Question en cours" + message.id_quest + "</p>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendQuestion(); });
});