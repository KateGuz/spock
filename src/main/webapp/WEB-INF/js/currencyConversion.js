$(document).ready(function () {
    $('#btnCurrencyUAH').click(function () {
        if (window.location.href.indexOf('currency') !== -1) {
            window.location.href = window.location.pathname + "?currency=UAH";
        }
        else {
            window.location.href = window.location + "?currency=UAH";
        }
    });

    $('#btnCurrencyEUR').click(function () {
        if (window.location.href.indexOf('currency') !== -1) {
            window.location.href = window.location.pathname + "?currency=EUR";
        }
        else {
            window.location.href = window.location + "?currency=EUR";
        }
    });

    $('#btnCurrencyUSD').click(function () {
        if (window.location.href.indexOf('currency') !== -1) {
            window.location.href = window.location.pathname + "?currency=USD";
        }
        else {
            window.location.href = window.location + "?currency=USD";
        }
    });
    
});
