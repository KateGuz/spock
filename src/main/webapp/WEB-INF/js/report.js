function report() {
    var startDate = $("#startDate").val();
    var endDate = $("#endDate").val();
    var type = document.getElementById("ended").checked ? document.getElementById("ended").value : document.getElementById("started").value ;
    
    var json = '{"startDate" :"' + startDate +
        '", "type" :"' + type +
        '", "endDate" :"' + endDate + '"}';

    $.ajax({
        url: '/report',
        type: 'POST',
        data: json,
        contentType: "application/json",
        success: function () {
            alert("Запрос отправлен в обработку.");
            location.reload();
        },
        error: function () {
            alert("Не удолось составить отчет");
        }
    })
}