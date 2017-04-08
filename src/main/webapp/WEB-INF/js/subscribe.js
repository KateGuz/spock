function subscribe() {
    var request = new XMLHttpRequest();
    request.open('POST', window.location.href + '/subscribe', false);
    request.onreadystatechange = function () {
        if(request.status == 200) {
            if(request.responseText > 0) {
                alert("что-то пошло нетак :(");
                document.body.innerHTML = request.responseText;
            } else {
                alert("Уведомления о новых ставках будут приходить к вам на email.");
                location.reload();
            }
        } else {
            alert("что-то пошло нетак :(");
            location.reload();
        }
    };
    request.send();
}