$(document).ready(function() {

    let validNomeCommunity = false;
    let slideCommunity = 0; // Per la gestione della visualizzazione dell'errore

    // Gestisce l'input dell'utente per il nome della community
    $("#communityNomeCreazione").keyup(function() {
        validateNomeCommunity();
    });

    // Funzione di validazione del nome della community
    function validateNomeCommunity() {
        let communityNome = $("#communityNomeCreazione").val();

        if (communityNome.trim() !== "") {
            $.ajax({
                type: "POST",
                url: "community", // Il URL dove si trova la servlet o controller che gestisce la richiesta
                data: {
                    mode: "checkNome", // Modalità per verificare il nome della community
                    nome: communityNome,
                },
                dataType: "html",
                success: function(data) {
                    if (data.match("non disponibile")) {
                        validNomeCommunity = false;
                        if (slideCommunity === 0) {
                            $("#error-communityNomeCreazione").slideDown();
                            slideCommunity = 1;
                        }
                        $("#error-communityNomeCreazione").html("Il nome della community non &egrave; disponibile");
                    } else if (data.match("disponibile")) {
                        validNomeCommunity = true;
                        slideCommunity = 0;
                        $("#error-communityNomeCreazione").slideUp();
                        $("#error-communityNomeCreazione").text(""); // Nessun errore, nome disponibile
                    }
                }
            });
        } else {
            validNomeCommunity = false;
            if (slideCommunity === 0) {
                $("#error-communityNomeCreazione").slideDown();
                slideCommunity = 1;
            }
            $("#error-communityNomeCreazione").text("Inserisci un nome valido per la community");
        }
    }

    // Funzione di validazione generale dei campi prima dell'invio del form
    function validateAllFields(event) {
        // Verifica se il nome della community è valido
        validateNomeCommunity();

        if (!validNomeCommunity) {
            event.preventDefault(); // Impedisce l'invio del form se il nome della community non è valido
        }
    }

    $("#creaCommunity").submit(function(event) {
        validateAllFields(event);
    });
});