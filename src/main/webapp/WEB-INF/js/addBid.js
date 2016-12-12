function addBid() {
    var bid = $("#bid").val();
    var lotId = $("#lotId").val();
    var userId = $("#userId").val();
   
    var json = '{"bid" :"' + bid +
        '", "lotId" :"' + lotId +
        '", "userId" :"' + userId + '"}';

    $.ajax({
        url: '/bid',
        type: 'POST',
        data: json,
        contentType: "application/json",
        success: function () {
            top.location.href = 'lot/'+lotId;
        },
        error: function () {
            alert("Не удалось сделать ставку");
        }
    })
}
