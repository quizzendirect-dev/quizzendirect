$(function() {

    $('#login-form-link').click(function(e) {
        $("#login-form").delay(100).fadeIn(100);
        $("#register-form").fadeOut(100);
        $('#register-form-link').removeClass('active');
        $(this).addClass('active');
        e.preventDefault();
    });
    $('#register-form-link').click(function(e) {
        $("#register-form").delay(100).fadeIn(100);
        $("#login-form").fadeOut(100);
        $('#login-form-link').removeClass('active');
        $(this).addClass('active');
        e.preventDefault();
    });

    document.getElementById("register-submit").addEventListener("click", function (e) {
        var mdp1 = document.getElementById("register-password");
        var mdp2 = document.getElementById("register-confirm-password");

        if(mdp1.value != mdp2.value) {
            e.preventDefault();
            alert("Les mots de passe ne correspondent pas");
        }
    })

});