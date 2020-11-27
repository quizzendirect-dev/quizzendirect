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
});

$.getScript("callAPI.js",function (){
});

$(document).ready(function () {
    let userId_ens = getCookie("userId_ens")
    if(userId_ens == null) return
    let query = "{" +
        "   allEnseignants{" +
        "       id_ens" +
        "       repertoires{" +
        "           nom" +
        "           questions{" +
        "               intitule" +
        "               time" +
        "           }" +
        "       }" +
        "   }" +
        "}"
    const donnees = callAPI(query)
    donnees.then((object) => {
        afficherRepertoires(object.data.allEnseignants, userId_ens)
    });
})

function afficherRepertoires(data, userId_ens){
    for(let i = 0; i < data.length; i++){
        console.log(data[i].id_ens);
        if(data[i].id_ens == userId_ens){
            for(let j = 0; j < data[i].repertoires.length; j++){
                ajouterRepertoire(data[i].repertoires[j].nom);
                for(let k = 0; k < data[i].repertoires[j].questions.length; k++){
                    ajouteQuestions(data[i].repertoires[j].nom, data[i].repertoires[j].questions[k].intitule );
                }
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
function createRepertoire(nomRepertoire)
{
    let email = getCookie("userEmail") ;

    let query = "mutation{\n" +
        "  createRepertoire(nom: \""+nomRepertoire+"\",enseignant:{mail:\""+email+"\"}){\n" +
        "  ...on Repertoire\n" +
        "    {\n" +
        "      nom:nom \n" +
        "    } ... on Error{ " +
        "message " +
        "}\n" +
        "  }\n" +
        "}"
    const donnee = callAPI(query);
}


function ajouteQuestions(nomRepertoire,enonce)
{
    let question = "<button type=\"button\" class=\"btn btn-lg btn-info btn-block\" >" + enonce + "</button>";
    let list_question = "#list_" + nomRepertoire;

    $(question).appendTo(list_question);
}

function updateRepository(id_rep,enonce,choix,questions){
    let updateRepertoire = "mutation {" +
        "updateRepertoire(id_rep:"+id_rep+",questions:["+questions+",{intitule:\"" + enonce + "\",choixUnique:"+choix+",reponsesBonnes:[\"A\"],reponsesFausses:[\"B\"],time:10 } ] ){\n" +
        "    __typename\n" +
        "  ... on Error{\n" +
        "    message\n" +
        "  }\n" +
        "  }\n" +
        "}";
    callAPI(updateRepertoire);

}

function enregistrementQuestion(enonce,choix,reponseBonnes,reponseFausses)
{
    let enregistrementQuestion = "mutation{\n" +
        "  createQuestion(intitule:\"" + enonce + "\",choixUnique:"+choix+",reponsesBonnes:[\"A\"],reponsesFausses:[\"B\"],time:10){\n" +
        "    __typename\n" +
        "  ...on Error{" +
        "message " +
        "}" +
        "}\n" +
        "}"
    callAPI(enregistrementQuestion);
}

function getRepertory(data,userId_ens,nomrepository)
{
    for(let i = 0; i < data.length; i++) {
        if (data[i].id_ens == userId_ens) {
            for (let j = 0; j < data[i].repertoires.length; j++) {
                if (data[i].repertoires[j].nom == nomrepository) {
                    let id_rep = data[i].repertoires[j].id_rep;
                    let questions = data[i].repertoires[j].questions;
                    updateRepository(id_rep,enonce,choix,questions);
                }
            }
        }
    }
}

//Renvoie true si une question existe , false sinon
function questionExiste(nomRepertoire,question)  {
    let list_question = "#list_" + nomRepertoire;
    let eachbutton = list_question + " button";
    let exist = false;
    $(eachbutton).each(function(){
        if($(this).html() == question) {
            exist = true;
        }
    })
    return exist;
}


/*************************************************************************************************************************************************/
function deleteSpace(string) {
    let i=0;
    while(string[i] ==' ') {
        i++;
    }
    return string.substr(i);
}
//Création d'un répertoire
$(document).on('click','#modalRep',function (){

    let value = deleteSpace($('#NomRepertoire').val().toString());
    let nomNouveauRep = value.substr(0,1).toUpperCase() + value.substr(1);
    let repexist = false;

   $('h3').each(function (){
       if($(this).html() == nomNouveauRep) {
           $('#NomRepertoire').css('border-color','red');
           $('#NomRepertoire').val(' ');
           repexist=true;
       }
   });
    //Erreur si creation d'un repertoire existant
    if(repexist == true){
        let parent = $('#NomRepertoire').parent();
        $(parent).append("<Label id=\"error\" style=\"color: #8b0000; font-size:10px;\">Nom de repertoire existant : Choississez s\'en un autre</Label>");
    }
    else
    {
        ajouterRepertoire(nomNouveauRep);
        $('#error').remove();
    }
});

function ajouterRepertoire(nomNouveauRep)
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
    createRepertoire(nomNouveauRep);


    let cpt = $('.row').children().length;
    let mod = cpt % 3;
    if (mod == 2) $("#nouveauRep > div").attr('class', "panel panel-primary");
    else if (mod == 1) $("#nouveauRep > div").attr('class', "panel panel-success");
    else if (mod == 0) $("#nouveauRep > div").attr('class', "panel panel-warning");


    $("#nouveauRep button").attr("data-target", "#modalPoll-1");
    $("#nouveauRep button").attr("data-toggle", "modal");
    let id_rep = "id" + nomNouveauRep;
    let id_rep_quest = "question_" + nomNouveauRep;
    let id_list_quest = "list_" + nomNouveauRep;

    $("#nouveauRep").attr("id", id_rep);
    $("#plusquestion").attr("id", id_rep_quest);
    $("#listQuestion").attr("id", id_list_quest);
    $(id_rep).val(' ');
}


//Modification du NomduRepertoire lors d'un clique sur +Question
$(document).on('click','.row button',function () {
    let id_bouton_cliquer= $(this).attr('id');
    let tab = id_bouton_cliquer.split("_");
    let idrep = "#id"+tab[1]+"> div";
    let classe = $(idrep).attr('class');

    let parent = $('#NomRepertoiremodal').parent();

    $("#NomRepertoiremodal").html(tab[1]);
    $("#NomRepertoiremodal").css('text-align','center');
    $("#NomRepertoiremodal").css('font-size','30px');
    $("#NomRepertoiremodal").css('margin-top','20px');

    // Changement de la couleur du titre de la modal
    if(classe == "panel panel-primary" )
       $(parent).css('background-color','#3498db');
    else if( classe == "panel panel-success" )
       $(parent).css('background-color','#58d68d');
    else
       $(parent).css('background-color','#fcf3cf');

   // Initialisation des différentes champs dans la modal
    $('#enonceQuestion').val(' ');
    $('#TypeChoix').val('unique');
    let i = 0;
    $('input[name="group1"]').each(function () {
        let label_next = $(this).next();
        let input_into_the_label = label_next.children();
        if( i == 2)
        {
            $(this).attr('checked','true');
            input_into_the_label.css('background-color', '#22d0ae');
        }
        else {
            input_into_the_label.css('background-color', 'white');
        }
        i++;
    });
});


//Ajout des questions à un repertoire
$(document).on('click','#AjoutQuestion',function () {

    let enonce = $("#enonceQuestion").val().toString();
    let nomRepertoire = $("#NomRepertoiremodal").html();
    let choix = true;
    if( $('#TypeChoix').val().toString() == "multiple") choix = false;
    let questions = " ";

    if( questionExiste(nomRepertoire,enonce) )
    {
        alert("Question existe déja ");
        //$("#enonceQuestion").append("<Label id=\"error\" style=\"color: #8b0000; font-size:10px;\">Nom de repertoire existant : Choississez s\'en un autre</Label>");
    }
    else {

        enregistrementQuestion(enonce,choix,"[A,B,C]","[D,E]");

        let query ="{\n" +
            " \tallRepertoires {\n" +
            " \t  nom\n" +
            "    id_rep\n" +
            "    questions {\n" +
            "      intitule\n" +
            "      choixUnique\n" +
            "      time\n" +
            "    }\n" +
            " \t}\n" +
            "}\n "
        let userId_ens = getCookie("userId_ens")

        const donnee  = callAPI(query);
        donnee.then((object) =>
          getRepertory(object.data.allRepertoires,userId_ens));


        let question = "<button type=\"button\" class=\"btn btn-lg btn-info btn-block\" >" + enonce + "</button>";

        let list_question = "#list_" + nomRepertoire;
        $(question).appendTo(list_question);

    }
});

//Fonction qui change le type de box pour les réponses : Checkbox où RadioButton
$(document).on('click',"#TypeChoix",function ()
{
    $choix = $(this).val().toString();
    if( $choix == "multiple") $('.form-check-input').attr('type','checkbox');
    else
    {
        $('.form-check-input').attr('type','radio');
        let i=0;
        $('input[name="group1"]').each(function (){
            let label_next = $(this).next();
            if( i == 2)
                $(this).attr('checked','true');
            else
                $(this).attr('checked','false');

            i++;
        })
    }
});

//Function qui gère le changement de couleur des reponses : Vert => Bonne reponse ,Rouge => mauvaise réponse
$(document).on('click','input[name="group1"]',function (){
    $choix = $('#TypeChoix').val().toString();
    if( $choix == "multiple") {
        let checked = $('input:checked').map(function (){ return $(this).val();}).get();
      $('input[name="group1"]').each(function () {
            let label_next = $(this).next();
            let input_into_the_label = label_next.children();
            if (  isChecked(checked,$(this).val()) ) {
                input_into_the_label.css('background-color', '#22d0ae');
            }
            else {
                $(this).attr('checked', 'false');
                input_into_the_label.css('background-color', 'red');
            }
        });
    }
    else
    {
         $(this).attr('checked', 'true');
         let input_selected = $(this);
        $('input[name="group1"]').each(function () {
            let label_next = $(this).next();
            let input_into_the_label = label_next.children();
            if ($(this).val() == input_selected.val() ) {
                $(this).attr('checked', 'true');
                input_into_the_label.css('background-color', '#22d0ae');
            }
            else {
                $(this).attr('checked', 'false');
                input_into_the_label.css('background-color', 'red');
            }
        });
    }
});


function isChecked(checked, value)
{
    for(let i=0 ; i < checked.length ; i++) {
        if( checked[i] == value ) return  true;
    }
    return false;
}

