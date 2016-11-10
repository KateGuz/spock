$(document).ready(function () {
    $('#btnSortAsc').click(function () {
        if (window.location.href.indexOf('sortType') !== -1) {
            window.location.href = window.location.pathname + "?sortType=priceAsc";
        }
        else {
            window.location.href = window.location + "?sortType=priceAsc";
        }
    });

    $('#btnSortDesc').click(function () {
        if (window.location.href.indexOf('sortType') !== -1) {
            window.location.href = window.location.pathname + "?sortType=priceDesc";
        }
        else {
            window.location.href = window.location + "?sortType=priceDesc";
        }
    });

    $('#btnSortSoonest').click(function () {
        if (window.location.href.indexOf('sortType') !== -1) {
            window.location.href = window.location.pathname + "?sortType=endSoonest";
        }
        else {
            window.location.href = window.location + "?sortType=endSoonest";
        }
    });

    $('#btnSortClear').click(function () {
        window.location.href = window.location.pathname
    });
});