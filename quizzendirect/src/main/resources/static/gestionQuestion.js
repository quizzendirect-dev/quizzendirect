$(document).on('click', '.panel-heading span.clickable', function(e){
    var $this = $(this);
    if(!$this.hasClass('panel-collapsed')) {
        $this.parents('.panel').find('.panel-body').slideUp();
        $this.addClass('panel-collapsed');
        $this.find('i').removeClass('glyphicon-chevron-up').addClass('glyphicon-chevron-down');
    } else {
        $this.parents('.panel').find('.panel-body').slideDown();
        $this.removeClass('panel-collapsed');
        $this.find('i').removeClass('glyphicon-chevron-down').addClass('glyphicon-chevron-up');
    }
})

//Création d'un répertoire
$(document).on('click','#modalRep',function (){

    let nomNouveauRep = $('#NomRepertoire').val().toString();
    let repexist = false;


    if(repexist == true){
        alert("Nom de Répertoire existant!!");
    }
    else
    {
        let rep = "<div class=\"col-md-7\" id=\"nouveauRep\">\n" +
            "            <div class=\"panel panel-success\">\n" +
            "                <div class=\"panel-heading\">\n" +
            "                    <h3 class=\"panel-title\">Questions MangoDB</h3>\n" +
            "                    <span class=\"pull-right clickable\"><i class=\"glyphicon glyphicon-chevron-up\"></i></span>\n" +
            "                </div>\n" +
            "                <div class=\"panel-body\" id=\"listQuestion\">\n" +
            "                    <button type=\"button\" class=\"btn btn-sm btn-secondary\" id=\"plusquestion\">+ Question</button>\n" +
            "                </div>\n" +
            "            </div>\n" +
            "        </div>";

        $('.row').append(rep);

        $("#nouveauRep h3").html(nomNouveauRep);
        let cpt = $('.row').children().length;
        let mod = cpt % 3;
        if (mod == 2) $("#nouveauRep > div").attr('class', "panel panel-primary");
        else if (mod == 1) $("#nouveauRep > div").attr('class', "panel panel-success");
        else if (mod == 0) $("#nouveauRep > div").attr('class', "panel panel-warning");


        $("#nouveauRep button").attr("data-target", "#modalPoll-1");
        $("#nouveauRep button").attr("data-toggle", "modal");
        let id_rep = "id" + nomNouveauRep;
        let id_rep_quest = "question_" + nomNouveauRep;
        let id_list_quest = "list" + nomNouveauRep;

        $("#nouveauRep").attr("id", id_rep);
        $("#plusquestion").attr("id", id_rep_quest);
        $("#listQuestion").attr("id", id_list_quest);

    }
});

//Modification du NomduRepertoire lors d'un clique sur +Question
$(document).on('click','.row button',function () {
    let id_bouton_cliquer= $(this).attr('id');
    console.log(id_bouton_cliquer);
    let tab = id_bouton_cliquer.split("_");
    $("#NomRepertoiremodal").html(tab[1]);

});


//Ajout des questions à un repertoire
$(document).on('click','#AjoutQuestion',function () {
    let enonce = $("#enonceQuestion").val().toString();
    let question = "<button type=\"button\" class=\"btn btn-lg btn-info btn-block\">" + enonce + "</button>";

    let nomRepertoire = $("#NomRepertoiremodal").html();
    let list_question= "#list"+nomRepertoire;
    $(question).appendTo(list_question);
});


console.log(getAllQuestions());