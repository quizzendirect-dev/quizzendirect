$(function() {

    $('#login-form-link, #login-form-link2').click(function(e) {
        $("#login-form").delay(100).fadeIn(100);
        $("#register-form").fadeOut(100);
        $('#register-form-link').removeClass('active');
        $('#login-form-link').addClass('active');
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
        //var prenom = document.getElementById("register-prenom");
        //var nom = document.getElementById("register-name");

        var email = document.getElementById("register-email");
        var email_value = email.value;
        var mdp1 = document.getElementById("register-password");
        var mdp1_value = mdp1.value;
        var mdp2 = document.getElementById("register-confirm-password");
        var mdp2_value = mdp2.value;

        if(!email_value.endsWith("@univ-angers.fr")) {
            e.preventDefault();
            alert("l'email doit étre une adresse de l'université d'Angers");
        }


        if(mdp1_value.length < 6) {
            e.preventDefault();
            alert("Le mot de passe doit contenir au minimum 6 caractéres");
        }

        if(mdp1_value != mdp2_value) {
            e.preventDefault();
            alert("Les mots de passe ne correspondent pas");
        }
    });

});