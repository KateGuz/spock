function quickBuy() {
    var lotId = $("#quickBuy").val();
    var json = "nothing";
    $.ajax({
        url: '/lot/'+lotId+'/quickBuy',
        type: 'POST',
        success: function () {
            alert("Лот куплен!");
            window.location.href = "http://localhost:8080/";
        },
        error: function () {
            alert("что-то пошло нетак :(");
        }
    })
}