function addBid() {
    var bid = $("#bid").val();
    var lotId = $("#lotId").val();
    var userId = $("#userId").val();
   
    var json = '{"bid" :"' + bid +
        '", "lotId" :"' + lotId +
        '", "userId" :"' + userId + '"}';

    $.ajax({
        url: '/bid',
        type: 'post',
        contentType: "application/json",
        dataType: 'json',
        timeout: 100000,
        data: json,
        success: function (data) {
            alert("Удалось");
        },
        error: function () {
            alert("Не удалось сделать ставку");
        }
    });
}
