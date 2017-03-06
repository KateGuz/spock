$(document).ready(function () {
    $('#btnCurrencyUAH').click(function () {
        if (window.location.href.indexOf('currency') !== -1) {
            if (window.location.href.indexOf('sortType') !== -1) {
                window.location.href = window.location.href.substring(0, window.location.href.indexOf('&')) + "&currency=UAH";
            } else {
                window.location.href = window.location.pathname + "?currency=UAH";
            }
        }
        else {
            if (window.location.href.indexOf('sortType') !== -1) {
                window.location.href = window.location + "&currency=UAH";
            } else {
                window.location.href = window.location + "?currency=UAH";
            }
        }
    });

    $('#btnCurrencyEUR').click(function () {
        if (window.location.href.indexOf('currency') !== -1) {
            if (window.location.href.indexOf('sortType') !== -1) {
                window.location.href = window.location.href.substring(0, window.location.href.indexOf('&')) + "&currency=EUR";
            } else {
                window.location.href = window.location.pathname + "?currency=EUR";
            }
        }
        else {
            if (window.location.href.indexOf('sortType') !== -1) {
                window.location.href = window.location + "&currency=EUR";
            } else {
                window.location.href = window.location + "?currency=EUR";
            }
        }
    });

    $('#btnCurrencyUSD').click(function () {
        if (window.location.href.indexOf('currency') !== -1) {
            if (window.location.href.indexOf('sortType') !== -1) {
               window.location.href = window.location.href.substring(0, window.location.href.indexOf('&')) + "&currency=USD";
            } else {
                window.location.href = window.location.pathname + "?currency=USD";
            }
        }
        else {
            if (window.location.href.indexOf('sortType') !== -1) {
                window.location.href = window.location + "&currency=USD";
            } else {
                window.location.href = window.location + "?currency=USD";
            }
        }
    });
});
