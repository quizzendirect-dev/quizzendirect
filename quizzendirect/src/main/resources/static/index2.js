var stompClient = null;

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




function getQuestion(message) {
    $("#greetings").append("<p> Question en cours" + message.intitule + "</p>");
    $("#greetings").append("<p> Question en cours" + message.time + "</p>");
    $("#greetings").append("<p> Question en cours" + message.choixUnique + "</p>");
}

