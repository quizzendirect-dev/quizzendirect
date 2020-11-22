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
    $(document).find(".selected-questions").append("<li class=\"selected-question panel panel-primary\"><div class=\"move-buttons\"><button class=\"button-up btn btn-lg btn-info btn-block\"><span class=\"glyphicon glyphicon-chevron-up\"></span></button><button class=\"button-down btn btn-lg btn-info btn-block\"><span class=\"glyphicon glyphicon-chevron-down\"></button></div><div class=\"intitule-question\">" + intituleQuestion + "</div><button class=\"button-supprimer btn btn-lg btn-info btn-block\">Supprimer</button></li>")
    let lastSelectedQuestion = $(document).find(".selected-question").last()
    lastSelectedQuestion.find(".button-down").toggleClass("disabled")
    if($(document).find(".selected-question").length == 1){
        lastSelectedQuestion.find(".button-up").toggleClass("disabled")
    }
    $(document).find(".button-demarrer").toggleClass("disabled", false)
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
    let intitule = $(this).parent().find(".intitule-question").text()
    let quest = $(document).find(".quest:contains(" + intitule + ")")
    quest.parent().find(".button-ajouter").css("display", "block")
    $(document).find(".intitule-question:contains(" + intitule + ")").parent().remove()
    updateUpDownButton()
    if($(document).find(".selected-question").length <= 0){
        $(document).find(".button-demarrer").toggleClass("disabled", true)
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