$(document).ready(function () {
    $('#btnCurrencyUAH').click(function () {
        window.location.href = changeCurrency("UAH");
    });

    $('#btnCurrencyEUR').click(function () {
        window.location.href = changeCurrency("EUR");
    });

    $('#btnCurrencyUSD').click(function () {
        window.location.href = changeCurrency("USD");
    });

    function changeCurrency(currency) {
        var url = window.location.pathname + "?";
        var name = null;
        var params = window.location.search.substr(1).split("&");
        params.forEach(function(param) {
            name = param.split("=");
            if (name[0] != "currency") {
                url += param + "&";
            }
        });
        return url + "currency=" + currency;
    }
});
