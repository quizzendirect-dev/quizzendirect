$(document).on("click",".repertoire", function() {
    if($(this).parent().find(".question").length > 0){
        $(this).parent().find("ul.questions").toggleClass("hide")
    }
});

$(document).on("click", ".button-ajouter", function() {
    let intituleQuestion = $(this).parent().find(".quest").text()
    $(this).css("display", "none")
    if($(document).find(".selected-question").length > 0){
        let lastSelectedQuestion = $(document).find(".selected-question").last()
        lastSelectedQuestion.find(".button-down").toggleClass("disabled")
    }
    $(document).find(".selected-questions").append("<li class=\"selected-question panel panel-primary\"><div class=\"move-buttons\"><button class=\"button-up btn btn-lg btn-info btn-block\"><span class=\"glyphicon glyphicon-chevron-up\"></span></button><button class=\"button-down btn btn-lg btn-info btn-block\"><span class=\"glyphicon glyphicon-chevron-down\"></span></button></div><div class=\"intitule-question\">" + intituleQuestion + "</div><div class=\"question-buttons\"><button class=\"button-supprimer btn btn-lg btn-warning btn-block\">Supprimer</button><button class=\"button-lancer btn btn-lg btn-success btn-block disabled\">Lancer</button></div></li>")
    let lastSelectedQuestion = $(document).find(".selected-question").last()
    lastSelectedQuestion.find(".button-down").toggleClass("disabled")
    if($(document).find(".selected-question").length == 1){
        lastSelectedQuestion.find(".button-up").toggleClass("disabled")
    }
    if($(document).find(".button-demarrer").hasClass("used")){
        lastSelectedQuestion.find(".button-lancer").toggleClass("disabled", false)
    } else {
        $(document).find(".button-demarrer").toggleClass("disabled", false)
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
    $(document).find(".intitule-question:contains(" + intitule + ")").parent().remove()
    updateUpDownButton()
    if($(document).find(".selected-question").length <= 0){
        $(document).find(".button-demarrer").toggleClass("disabled", true)
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
    $(document).find(".button-demarrer").toggleClass("disabled", true)
    $(document).find(".button-supprAll").toggleClass("hide", true)
})

$(document).on("click", ".button-demarrer", function () {
    $(this).toggleClass("disabled", true)
    $(this).toggleClass("used", true)
    $(document).find(".code-acces").toggleClass("hide", false)
    let selected_question = $(document).find(".selected-question")
    for(let i = 0; i < selected_question.length; i++){
        selected_question.eq(i).find(".button-lancer").toggleClass("disabled", false)
    }
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