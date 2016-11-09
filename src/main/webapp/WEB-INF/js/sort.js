$(document).ready(function () {
    $('#btnSortAsc').click(function () {

        if (window.location.href.indexOf('categoryId') !== -1) {
            if (window.location.href.indexOf('sortType') !== -1) {
                window.location.href = window.location.href.substring(0, window.location.href.indexOf('&')) + "&sortType=ASC";
            }
            else {
                window.location.href = window.location + "&sortType=ASC";
            }
        } else {
            if (window.location.href.indexOf('sortType') !== -1) {
                window.location.href = window.location.pathname + "?sortType=ASC";
            }
            else {
                window.location.href = window.location + "?sortType=ASC";
            }
        }
    });

    $('#btnSortDesc').click(function () {
        if (window.location.href.indexOf('categoryId') !== -1) {
            if (window.location.href.indexOf('sortType') !== -1) {
                window.location.href = window.location.href.substring(0, window.location.href.indexOf('&')) + "&sortType=DESC";
            }
            else {
                window.location.href = window.location + "&sortType=DESC";
            }
        } else {
            if (window.location.href.indexOf('sortType') !== -1) {
                window.location.href = window.location.pathname + "?sortType=DESC";
            }
            else {
                window.location.href = window.location + "?sortType=DESC";
            }
        }
    });
    $('#btnSortSoonest').click(function () {
        if (window.location.href.indexOf('categoryId') !== -1) {
            if (window.location.href.indexOf('sortType') !== -1) {
                window.location.href = window.location.href.substring(0, window.location.href.indexOf('&')) + "&sortType=SOONEST";
            }
            else {
                window.location.href = window.location + "&sortType=SOONEST";
            }
        } else {
            if (window.location.href.indexOf('sortType') !== -1) {
                window.location.href = window.location.pathname + "?sortType=SOONEST";
            }
            else {
                window.location.href = window.location + "?sortType=SOONEST";
            }
        }
    });

    $('#btnSortClear').click(function () {

        if (window.location.href.indexOf('categoryId') !== -1) {
            window.location.href = window.location.href.substring(0, window.location.href.indexOf('&'));
        } else {
            window.location.href = window.location.pathname
        }
    });
})