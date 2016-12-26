function currencyConversion(currency) {
    var url = window.location.pathname;
    var json = '{"url" :"' + url +
        '", "currency" :"' + currency + '"}';
    console.info(json);
    $.ajax({
        url: '/currency',
        type: 'POST',
        data: json,
        contentType: "application/json"
    })
}
