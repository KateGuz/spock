$(document).ready(function () {
    $('#btnSortAsc').click(function () {
        window.location.href = window.location.pathname + "?sortType=priceAsc";
    });

    $('#btnSortDesc').click(function () {
        window.location.href = window.location.pathname + "?sortType=priceDesc";
    });

    $('#btnSortSoonest').click(function () {
        window.location.href = window.location.pathname + "?sortType=endSoonest";

    });
    
    $('#btnSortClear').click(function () {
        window.location.href = window.location.origin + window.location.pathname;
    });
});
