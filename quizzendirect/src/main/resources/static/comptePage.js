$(function () {

    // Pour gérer le visuel login/sign in
    $('#login-form-link, #login-form-link2').click(function (e) {
        $("#login-form").delay(100).fadeIn(100);
        $("#register-form").fadeOut(100);
        $('#register-form-link').removeClass('active');
        $('#login-form-link').addClass('active');
        e.preventDefault();
    });
    $('#register-form-link').click(function (e) {
        $("#register-form").delay(100).fadeIn(100);
        $("#login-form").fadeOut(100);
        $('#login-form-link').removeClass('active');
        $(this).addClass('active');
        e.preventDefault();
    });
    // Quand l'enseignant clique sur le boutton pour créer un compte
    document.getElementById("register-submit").addEventListener("click", function (e) {

        e.preventDefault();
        // on recupére ses informations
        var prenom = document.getElementById("register-prenom");
        var prenom_value = prenom.value;
        var nom = document.getElementById("register-name");
        var nom_value = nom.value;
        var email = document.getElementById("register-email");
        var email_value = email.value;
        var mdp1 = document.getElementById("register-password");
        var mdp1_value = mdp1.value;
        var mdp2 = document.getElementById("register-confirm-password");
        var mdp2_value = mdp2.value;

        // on impose le mail doit etre un mail de l'université d'Angers
        if (!email_value.endsWith("@univ-angers.fr")) {
            e.preventDefault();
            alert("l'email doit étre une adresse de l'université d'Angers");
        }

        // on impose que le mot de passe ait une longueur supérieur a 6 caractères
        if (mdp1_value.length < 6) {
            e.preventDefault();
            alert("Le mot de passe doit contenir au minimum 6 caractéres");
        }
        else {
            // on vérifie que les 2 mots de passes entrés sont égaux
            if (mdp1_value != mdp2_value) {
                e.preventDefault();
                alert("Les mots de passe ne correspondent pas");
            } else {
                // On recupére tous les enseignants avec un appel à l'API
                let nom_value_mutation = nom_value.toString();
                let email_value_mutation = email_value.toString();
                let mpd1_value_mutation = mdp1_value.toString();

                let query_allEns = "{" +
                    "  allEnseignants{" +
                    "    id_ens" +
                    "    mail" +
                    "  }" +
                    "}";
                const donnees_ens = callAPI(query_allEns)
                donnees_ens.then((object0) => {
                    var enseignantExist = false;
                    for (let i = 0; i < object0.data.allEnseignants.length; i++) {
                        // si on trouve que le mail existe déjà
                        if (object0.data.allEnseignants[i].mail == email_value) {
                            enseignantExist = true;
                        }
                    }
                    // si l email n'existe pas, on crée l'enseignant et les cookies/sessions
                    if (enseignantExist == false) {
                        let mutation = "mutation{ createEnseignant(nom:\"" + nom_value_mutation + "\"" +
                            "  mail:\"" + email_value_mutation + "\"" +
                            "  motdepasse:\"" + mpd1_value_mutation + "\")" +
                            "  {" +
                            "            __typename ...on Error {message}" +
                            "  }" +
                            "}";


                        const donnees = callAPI(mutation);
                        donnees.then((object) => {
                            document.cookie = "userName= " + nom_value_mutation;
                            document.cookie = "userEmail=" + email_value_mutation;
                            document.cookie = "userPrenom=" + prenom_value;

                            let query_allEnseignants = "{" +
                                "  allEnseignants{" +
                                "    id_ens" +
                                "    mail" +
                                "  }" +
                                "}";
                            const donnees_ens = callAPI(query_allEnseignants)
                            donnees_ens.then((object2) => {
                                creationCookieID(object2.data.allEnseignants, email_value_mutation);
                                window.location.href = "connection";
                            });


                        });
                    }
                    // sinon on notifie que cet enseignant existe déjà avec une alerte
                    else {
                        alert("Le mail de cet enseignant existe déjà.");
                    }
                })
            }
        }


    });

    // Quand l'enseignant clique sur le boutton pour se connecter
    document.getElementById("login-submit").addEventListener("click", function (e) {

        e.preventDefault();
        // on récupére les informations saisies
        var email_connexion = document.getElementById("login-email");
        var email_connexion_value = email_connexion.value;
        var mdp_email = document.getElementById("login-password");
        var mdp_email_value = mdp_email.value;

        // on fait appel à l'API pour récupérer tous les enseignants
        let query_allEns = "{" +
            "  allEnseignants{" +
            "    id_ens" +
            "    mail" +
            "    motdepasse" +
            "    nom" +
            "  }" +
            "}";
        const donnees_ens = callAPI(query_allEns)
        donnees_ens.then((object0) => {
            var enseignantExist = false;
            var mdpTrue = false;
            for (let i = 0; i < object0.data.allEnseignants.length; i++) {
                // si on trouve que le mail existe
                if (object0.data.allEnseignants[i].mail == email_connexion_value) {
                    enseignantExist = true;
                    if (object0.data.allEnseignants[i].motdepasse == mdp_email_value) {
                        mdpTrue = true;
                        // si l email et le mot de passe correspondent, on connecte l'enseignant et on crée les cookies/sessions
                        document.cookie = "userName= " + object0.data.allEnseignants[i].nom;
                        document.cookie = "userEmail=" + email_connexion_value;
                        document.cookie = "userId_ens=" + object0.data.allEnseignants[i].id_ens;
                        window.location.href = "connection";
                    }
                }
            }
            // On gére les erreurs
            if ((enseignantExist == true) && (mdpTrue == false)) {
                alert("Le mot de passe ne correspondant pas au mail saisis");
            }
            if (enseignantExist == false) {
                alert("Cet enseignant n'existe pas");
            }


        })
    });


});


// fonction pour créer la cookie ID de l'enseignant qui sera utile dans d'autre pages
function creationCookieID(data, userEmail) {
    for (let i = 0; i < data.length; i++) {
        if (data[i].mail == userEmail) {
            document.cookie = "userId_ens = " + data[i].id_ens;
        }
    }
}