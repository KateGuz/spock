function subscribe() {
    $.ajax({
        url: window.location.href + '/subscribe',
        type: 'POST',
        success: function () {
            alert("Уведомления о новых ставках будут приходить к вам на email.");
            location.reload();
        },
        error: function(xhr) {
            alert("что-то пошло нетак :(");
            document.body.innerHTML = xhr.responseText;
        }
    })
}