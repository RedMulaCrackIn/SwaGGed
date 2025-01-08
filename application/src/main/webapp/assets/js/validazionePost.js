$(document).ready(function(){

    let titoloValidator = (title) => title.trim() !== "";
    let validTitolo = false;
    let slideTitolo = 0; // Per gestire la visualizzazione dell'errore

    let validNomeCommunity = false;
    let slideCommunity = 0; // Per la gestione della visualizzazione dell'errore

    // Gestisce l'input dell'utente per il nome della community
    $("#communityNome").keyup(function() {
        validateNomeCommunity();
    });

    // Gestisce l'input dell'utente per il titolo
    $("#titolo").keyup(function() {
        validateTitolo();
    });

    // Funzione di validazione del titolo
    function validateTitolo() {
        let titolo = $("#titolo").val();

        if (titoloValidator(titolo)) {
            validTitolo = true;
            if (slideTitolo === 1) {
                slideTitolo = 0;
                $("#error-titolo").slideUp();
            }
            $("#error-titolo").text(""); // Pulisce il messaggio di errore
        } else {
            validTitolo = false;
            if (slideTitolo === 0) {
                $("#error-titolo").slideDown();
                slideTitolo = 1;
            }
            $("#error-titolo").html("Il titolo non pu&ograve; essere vuoto");
        }
    }

    function validateNomeCommunity() {
        let communityNome = $("#communityNome").val();

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
                        validNomeCommunity = true;
                        slideCommunity = 0;
                        $("#error-communityPost").slideUp();
                        $("#error-communityPost").text(""); // Nessun errore, community esistente

                    } else if (data.match("disponibile")) {
                        validNomeCommunity = false;
                        if (slideCommunity === 0) {
                            $("#error-communityPost").slideDown();
                            slideCommunity = 1;
                        }
                        $("#error-communityPost").html("La community non esiste");

                    }
                }
            });
        } else {
            validNomeCommunity = false;
            if (slideCommunity === 0) {
                $("#error-communityPost").slideDown();
                slideCommunity = 1;
            }
            $("#error-communityPost").text("Inserisci un nome valido per la community");
        }
    }

    // Funzione di validazione generale dei campi prima dell'invio del form
    function validateAllFields(event) {
        validateTitolo();

        // Verifica se il nome della community è valido solo se è visibile (non è hidden)
        let isHidden = $("#communityNome").closest('div').hasClass('hidden');
        if (!isHidden) {
            validateNomeCommunity();
        }

        if (!validTitolo || !validNomeCommunity) {
            event.preventDefault(); // Impedisce l'invio del form se il titolo non è valido
        }

        // Aggiungere altre validazioni qui, come validateEmail() per l'email
    }


    // Impedisce l'invio del form se ci sono errori
    $("#creaPost").submit(function(event) {
        validateAllFields(event);
    });
});
