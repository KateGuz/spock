function quickBuy() {
    var lotId = $("#quickBuy").val();
    var json = "nothing";
    $.ajax({
        url: '/lot/'+lotId+'/quickBuy',
        type: 'POST',
        success: function () {
            location.reload();
        },
        error: function () {
            alert("что-то пошло нетак :(");
        }
    })
}