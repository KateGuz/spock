function unSubscribe() {
    $.ajax({
        url: window.location.href + '/unsubscribe',
        type: 'POST',
        success: function () {
            alert("Уведомления о новых ставках больше не будут приходить к вам на email.");
            location.reload();
        },
        error: function() {
            alert("что-то пошло нетак :(");
            location.reload();
        }
    })
}
