$(function() {
    if  (window.oauth == undefined) { window.oauth = {} };

    oauth.getTokenResponse = function() {
        var fragment = window.location.hash;
        var atributos = fragment.slice(1).split('&');
        var resposta = {};

        $(atributos).each(function(idx, atributo) {
            var chaveValor = atributo.split('=');
            resposta[chaveValor[0]] = chaveValor[1];
        });

        return resposta;
    }

});
