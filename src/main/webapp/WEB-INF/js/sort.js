$(document).ready(function () {
    $('#btnSortAsc').click(function () {
        if (window.location.href.indexOf('sortType') !== -1) {
            if (window.location.href.indexOf('currency') !== -1) {
                window.location.href = window.location.pathname + window.location.substring(0, window.location.indexOf('&')) + "&sortType=priceAsc";
            } else {
                window.location.href = window.location.pathname + "?sortType=priceAsc";
            }
        }
        else {
            if (window.location.href.indexOf('currency') !== -1) {
                window.location.href = window.location + "&sortType=priceAsc";
            } else {
                window.location.href = window.location + "?sortType=priceAsc";
            }
        }
    });

    $('#btnSortDesc').click(function () {
        if (window.location.href.indexOf('sortType') !== -1) {
            if (window.location.href.indexOf('currency') !== -1) {
                window.location.href = window.location.pathname + window.location.substring(0, window.location.indexOf('&')) + "&sortType=priceDesc";
            } else {
                window.location.href = window.location.pathname + "?sortType=priceDesc";
            }
        }
        else {
            if (window.location.href.indexOf('currency') !== -1) {
                window.location.href = window.location + "&sortType=priceDesc";
            } else {
                window.location.href = window.location + "?sortType=priceDesc";
            }
        }
    });

    $('#btnSortSoonest').click(function () {
        if (window.location.href.indexOf('sortType') !== -1) {
            if (window.location.href.indexOf('currency') !== -1) {
                window.location.href = window.location.pathname + window.location.substring(0, window.location.indexOf('&')) + "&sortType=endSoonest";
            } else {
                window.location.href = window.location.pathname + "?sortType=endSoonest";
            }
        }
        else {
            if (window.location.href.indexOf('currency') !== -1) {
                window.location.href = window.location + "&sortType=endSoonest";
            } else {
                window.location.href = window.location + "?sortType=endSoonest";
            }
        }
    });

    $('#btnSortClear').click(function () {
        window.location.href = window.location.pathname
    });
});
