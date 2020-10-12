let Interval;
let Choix = 2;
let Checked = false;


function start() {
    return setInterval(changeChrono, 1000);
}

Interval = start();

function changeChrono()
{
    let x = $('#timer').html();
    x--;
    if(x >= 0) $('#timer').html(x);
    else if ( x < 0 || Checked ) {
        $('#loadbar').show();
        $('#quiz').fadeOut();
        $('#quiz2q').fadeOut();

        clearInterval(Interval);
        setTimeout(changeQuestion,5000);
    }
}
function changeQuestion() {
    $('#loadbar').hide();

    //Récuperation des données
    let i = $('#qid').html();

    //Modification des données dans le modal
    i++;
    $('#qid').html(i);
    if( Choix == 2) {
        $('#quiz2q').show();
        $('#enonce').html(questions[i-1]);
        Choix = 4;
    }
    else {
        $('#quiz').show();
        $('#enonce').html(questions[i-1]);
        Choix = 2;
    }
    $('#timer').html(10);
    Interval = start();
}


$(function(){
    let loading = $('#loadbar').hide();
    $(document)
        .ajaxStart(function () {
            loading.show();
        }).ajaxStop(function () {
            loading.hide();
    });

    $("label.btn").on('click',function () {
        let choice = $(this).find('input:radio').val();
        Checked = true;
        $('#loadbar').show();
        $('#quiz').fadeOut();

      /*  setTimeout(function(){
            $( "#answer" ).html(  $(this).checking(choice) );
            $('#quiz').show();
            $('#loadbar').fadeOut();
        }, 1500);*/
    });

    $ans = 3;

    $.fn.checking = function(ck) {
        if (ck != $ans)
            return 'INCORRECT';
        else
            return 'CORRECT';
    };
});
