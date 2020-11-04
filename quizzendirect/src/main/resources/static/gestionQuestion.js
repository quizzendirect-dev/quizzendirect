
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

    let value = $('#NomRepertoire').val().toString();
    let nomNouveauRep = value.substr(0,2).toUpperCase() + value.substr(2);

    let repexist = false;

   $('h3').each(function (){
       if($(this).html() == nomNouveauRep) {
           $('#NomRepertoire').css('border-color','red');
           $('#NomRepertoire').val(' ');
           repexist=true;
       }
   });

    if(repexist == true){
        let parent = $('#NomRepertoire').parent();
        $(parent).append("<Label id=\"error\" style=\"color: darkred; font-size:10px;\">Nom de repertoire existant : Choississez s\'en un autre</Label>");
    }
    else
    {
        $('#NomRepertoire').css('border-color','black');

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

        $(id_rep).val(' ');
        $('#error').remove();


    }
});

//Modification du NomduRepertoire lors d'un clique sur +Question
$(document).on('click','.row button',function () {
    let id_bouton_cliquer= $(this).attr('id');
    let tab = id_bouton_cliquer.split("_");
    $("#NomRepertoiremodal").html(tab[1]);
    $("#NomRepertoiremodal").css('text-align','center');
    $("#NomRepertoiremodal").css('font-size','30px');
    $("#NomRepertoiremodal").css('margin-top','20px');


});


//Ajout des questions à un repertoire
$(document).on('click','#AjoutQuestion',function () {

    $choix = $('#TypeChoix').val().toString();
    if( $choix == "multiple") $('.form-check-input').attr('type','checkbox');
    else $('.form-check-input').attr('type','radio');

    let enonce = $("#enonceQuestion").val().toString();


    let question = "<button type=\"button\" class=\"btn btn-lg btn-info btn-block\">" + enonce + "</button>";

    let nomRepertoire = $("#NomRepertoiremodal").html();

    let list_question= "#list"+nomRepertoire;
    $(question).appendTo(list_question);
});

$(document).on('click',"#TypeChoix",function ()
{
    $choix = $(this).val().toString();
    if( $choix == "multiple") $('.form-check-input').attr('type','checkbox');
    else $('.form-check-input').attr('type','radio');
});

