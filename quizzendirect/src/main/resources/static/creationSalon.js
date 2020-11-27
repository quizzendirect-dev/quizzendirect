
var codeAcces = "";

$(document).ready(function () {
    let userId_ens = getCookie("userId_ens")
    if(userId_ens == null) return

    let query = "{" +
        "   allEnseignants{" +
        "       id_ens" +
        "       repertoires{" +
        "           nom" +
        "           questions{" +
        "               id_quest intitule reponsesBonnes reponsesFausses choixUnique" +
        "               time" +
        "           }" +
        "       }" +
        "   }" +
        "}"
    const donnees = callAPI(query)
    donnees.then((object) => {
        afficherRepertoires(object.data.allEnseignants, userId_ens)
    })
})

function afficherRepertoires(data, userId_ens){
    for(let i = 0; i < data.length; i++){
        if(data[i].id_ens == userId_ens){
            for(let j = 0; j < data[i].repertoires.length; j++){
                let stringRepertoire =
                    "   <li class=\"panel panel-primary\">" +
                    "        <div class=\"repertoire panel-heading\">" + data[i].repertoires[j].nom + "</div>" +
                    "        <ul class=\"questions hide panel-body\">"
                for(let k = 0; k < data[i].repertoires[j].questions.length; k++){
                    stringRepertoire +=
                        "        <li class=\"question\">" +
                        "            <input class='id_quest' type='hidden' value='"+data[i].repertoires[j].questions[k].id_quest+"'>"+
                        "            <div class=\"response-time hide\">" + data[i].repertoires[j].questions[k].time + "</div>" +
                        "            <div class=\"quest\">" + data[i].repertoires[j].questions[k].intitule + "</div>" +
                        "            <button class=\"button-ajouter btn btn-lg btn-info btn-block\">Ajouter</button>" +
                        "        </li>"
                }
                stringRepertoire +=
                    "        </ul>" +
                    "    </li>"
                $(document).find(".repertoires").append(stringRepertoire)
            }
        }
    }
}

function getCookie(name){
    if(document.cookie.length == 0) return null;

    var regSepCookie = new RegExp('(; )', 'g');
    var cookies = document.cookie.split(regSepCookie);

    for(var i = 0; i < cookies.length; i++){
        if(cookies[i].startsWith(name)){
            return cookies[i].split("=")[1];
        }
    }
    return null;
}

$(document).on("click",".repertoire", function() {
    if($(this).parent().find(".question").length > 0){
        $(this).parent().find("ul.questions").toggleClass("hide")
    }
});

$(document).on("click", ".button-ajouter", function() {
    let intituleQuestion = $(this).parent().find(".quest").text();
    let id_quest = $(this).parent().find(".id_quest").val();
    $(this).css("display", "none")
    if($(document).find(".selected-question").length > 0){
        let lastSelectedQuestion = $(document).find(".selected-question").last()
        lastSelectedQuestion.find(".button-down").toggleClass("disabled")
    }
    let stringQuestion =
        "   <li class=\"selected-question panel panel-primary\">" +
        "       <div class=\"time-line\"></div>" +
        "       <div class=\"move-buttons\">" +
        "           <button class=\"button-up btn btn-lg btn-info btn-block\"><span class=\"glyphicon glyphicon-chevron-up\"></span></button>" +
        "           <button class=\"button-down btn btn-lg btn-info btn-block\"><span class=\"glyphicon glyphicon-chevron-down\"></span></button>" +
        "        </div>" +
        "        <div class=\"info-question\">" +
        "            <div class=\"intitule-question\">" + intituleQuestion + "</div>" +
        "            <form class=\"time-info\">" +
        "                <input class=\"id_quest\" type=\"hidden\" value="+id_quest+">"+
        "                <label class=\"time\">Temps de réponse :</label>" +
        "                <input class=\"choose-time\" type=\"text\" size=\"1\" id=\"time\" name=\"time\">" +
        "            </form>" +
        "        </div>" +
        "        <div class=\"question-buttons\">" +
        "            <button class=\"button-supprimer btn btn-lg btn-warning btn-block\">Supprimer</button>" +
        "            <button class=\"button-lancer btn btn-lg btn-success btn-block disabled\">Lancer</button>" +
        "        </div>" +
        "    </li>"
    $(document).find(".selected-questions").append(stringQuestion)
    let lastSelectedQuestion = $(document).find(".selected-question").last()
    lastSelectedQuestion.find(".choose-time").val($(this).parent().find(".response-time").text())
    lastSelectedQuestion.find(".button-down").toggleClass("disabled")
    if($(document).find(".selected-question").length == 1){
        lastSelectedQuestion.find(".button-up").toggleClass("disabled")
    }
    if($(document).find(".button-demarrer").hasClass("used")){
        lastSelectedQuestion.find(".button-lancer").toggleClass("disabled", false)
    }
    $(document).find(".button-supprAll").toggleClass("hide", false)
})

$(document).on("click", ".button-up", function() {
    let selected_question = $(document).find(".selected-question")
    let index = selected_question.index($(this).parent().parent())
    let selected_questions = $(document).find(".selected-questions")
    selected_questions.empty()
    for(let i = 0; i < selected_question.length; i++){
        if(i == index){
            continue
        }
        if((i + 1) == index){
            selected_questions.append(selected_question[i + 1])
        }
        selected_questions.append(selected_question[i])
    }
    updateUpDownButton()
})

$(document).on("click", ".button-down", function() {
    let selected_question = $(document).find(".selected-question")
    let index = selected_question.index($(this).parent().parent())
    let selected_questions = $(document).find(".selected-questions")
    selected_questions.empty()
    for(let i = 0; i < selected_question.length; i++){
        if((i - 1) == index){
            continue
        }
        if(i == index && (i + 1) < selected_question.length){
            selected_questions.append(selected_question[i + 1])
        }
        selected_questions.append(selected_question[i])
    }
    updateUpDownButton()
})

$(document).on("click", ".button-supprimer", function() {
    let intitule = $(this).parent().parent().find(".intitule-question").text()
    let quest = $(document).find(".quest:contains(" + intitule + ")")
    quest.parent().find(".button-ajouter").css("display", "block")
    $(document).find(".intitule-question:contains(" + intitule + ")").parent().parent().remove()
    updateUpDownButton()
    if($(document).find(".selected-question").length <= 0){
        $(document).find(".button-supprAll").toggleClass("hide", true)
    }
})

$(document).on("click", ".button-supprAll", function () {
    while($(document).find(".selected-question").length > 0){
        let firstSelectedQuestion = $(document).find(".selected-question").first()
        let intitule = firstSelectedQuestion.find(".intitule-question").text()
        let quest = $(document).find(".quest:contains(" + intitule + ")")
        quest.parent().find(".button-ajouter").css("display", "block")
        firstSelectedQuestion.remove()
    }
    $(document).find(".button-supprAll").toggleClass("hide", true)
})

$(document).on("click", ".button-demarrer", function () {
    // ré/initialise le code d'accés (variable global afin d'y avoir accés dans les fonctions websocket )
    codeAcces="";
    for(let i = 0; i < 4; i++){
        codeAcces += Math.floor((Math.random() * 9) + 1)
    }

    // connecte le websocket avec le code d'access
    connect(codeAcces);

    let query =
        "   mutation {" +
        "       createSalon(codeAcces: " + parseInt(codeAcces) + ", enseignant:{mail:\"" + getCookie("userEmail") + "\"}){" +
        "           type: __typename" +
        "           ... on Salon {" +
        "               id_salon" +
        "           }" +
        "           ... on Error {" +
        "               message" +
        "           }" +
        "       }" +
        "   }"
    const donnees = callAPI(query)
    donnees.then((object) => {
        if(object.data.createSalon.type == "Error"){
            alert(object.data.createSalon.message)
            return
        }else{
            $(this).toggleClass("disabled", true)
            $(this).toggleClass("used", true)
            $(document).find(".code-acces").text("Code d'accès : " + codeAcces)
            $(document).find(".code-acces").toggleClass("hide", false)
            let selected_question = $(document).find(".selected-question")
            for(let i = 0; i < selected_question.length; i++){
                selected_question.eq(i).find(".button-lancer").toggleClass("disabled", false)
            }
        }
    })
})

$(document).on("click", ".button-lancer", function () {
    let time = $(this).parent().parent().find(".choose-time").val()
    let timer = 0
    let time_line = $(this).parent().parent().find(".time-line")
    let interval = setInterval(function(){
        time_line.css("width", (timer / 1000) * 100 / time + "%")
        if(timer >= time * 1000) clearInterval(interval)
        timer += 1000
    }, 1000)

    let id_quest = $(this).parent().parent().find(".id_quest").val();
    let query = "query{  " +
        "  getQuestionById(id_quest:"+id_quest+")" +
        "  { " +
        "    ...on Question{id_quest intitule choixUnique reponsesBonnes reponsesFausses time}\n" +
        "  }" +
        "}";
    const donnees = callAPI(query);
    donnees.then(object => {
        var question={
            'id_quest': object.data.getQuestionById.id_quest,
            'intitule': object.data.getQuestionById.intitule,
            'choixUnique': object.data.getQuestionById.choixUnique,
            'reponsesBonnes': object.data.getQuestionById.reponsesBonnes,
            'reponsesFausses': object.data.getQuestionById.reponsesFausses,
            'time': time
        };
        sendQuestion(question);
    });
})

function updateUpDownButton() {
    let selected_question = $(document).find(".selected-question")
    for(let i = 0; i < selected_question.length; i++){
        if(i == 0){
            let firstSelectedQuestion = selected_question.first()
            firstSelectedQuestion.find(".button-up").toggleClass("disabled", true)
            if(selected_question.length == 1){
                firstSelectedQuestion.find(".button-down").toggleClass("disabled", true)
            }else{
                firstSelectedQuestion.find(".button-down").toggleClass("disabled", false)
            }
        }else if(i == (selected_question.length - 1)){
            let lastSelectedQuestion = selected_question.last()
            lastSelectedQuestion.find(".button-up").toggleClass("disabled", false)
            lastSelectedQuestion.find(".button-down").toggleClass("disabled", true)
        }else{
            let currentSelectedQuestion = selected_question.eq(i)
            currentSelectedQuestion.find(".button-up").toggleClass("disabled", false)
            currentSelectedQuestion.find(".button-down").toggleClass("disabled", false)
        }
    }
}

var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
};

function connect(codeAcces) {
    var socket = new SockJS('http://localhost:20020/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        // ajout dans l'url le code d'accéss ( variable globale ) qui a été affecté lors de l'ouverture du salon
        stompClient.subscribe('/quiz/salon/'+codeAcces);
    });
};

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
};

function sendQuestion(question) {
    // ajout dans l'url le code d'accéss ( variable globale ) qui a été affecté lors de l'ouverture du salon
    stompClient.send("/app/salon/"+codeAcces, {}, JSON.stringify(question)
    );
};


$(function () {
    $( "#disconnect" ).click(function() { disconnect(); });
});