
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
    let rep = "<div class=\"col-md-7\" id=\"nouveauRep\">\n" +
        "            <div class=\"panel panel-success\">\n" +
        "                <div class=\"panel-heading\">\n" +
        "                    <h3 class=\"panel-title\">Questions MangoDB</h3>\n" +
        "                    <span class=\"pull-right clickable\"><i class=\"glyphicon glyphicon-chevron-up\"></i></span>\n" +
        "                </div>\n" +
        "                <div class=\"panel-body\">\n" +
        "                    <button type=\"button\" class=\"btn btn-sm btn-secondary\">+ Question</button>\n" +
        "                </div>\n" +
        "            </div>\n" +
        "        </div>";

    $('.row').append(rep);
    $("#nouveauRep h3").html($('#NomRepertoire').val().toString());
    let cpt = $('.row').children().length;
    let mod = cpt%3;
    console.log(mod);
    if  ( mod == 2) $("#nouveauRep > div").attr('class',"panel panel-primary");

    else if( mod == 1) $("#nouveauRep > div").attr('class',"panel panel-success");
    else if (mod == 0) $("#nouveauRep > div").attr('class',"panel panel-warning");


    $("#nouveauRep").attr("data-target","#modalPoll-1");
    $("#nouveauRep").attr("data-toggle","modal");
    let id_rep = "id"+$('#NomRepertoire').val().toString();
    $("#nouveauRep").attr("id",id_rep);


});
