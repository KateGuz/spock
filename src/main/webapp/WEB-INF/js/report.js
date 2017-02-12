function report() {
    var startDate = $("#startDate").val();
    var endDate = $("#endDate").val();
    var type = document.getElementById("ended").checked ? document.getElementById("ended").value : document.getElementById("started").value ;
    var userEmail = $("#userEmail").val();
    
    
    var json = '{"startDate" :"' + startDate +
        '", "type" :"' + type +
        '", "userEmail" :"' + userEmail +
        '", "endDate" :"' + endDate + '"}';

    $.ajax({
        url: '/report',
        type: 'POST',
        data: json,
        contentType: "application/json",
        success: function () {
            location.reload();
        },
        error: function () {
            alert("Не удолось составить отчет");
        }
    })
}