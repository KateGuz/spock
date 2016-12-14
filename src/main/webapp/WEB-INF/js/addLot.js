
function addLot() {
    var title = $("#title").val();
    var description = $("#description").val();
    var startPrice = $("#startPrice").val();
    var minStep = $("#minStep").val();
    var categoryId = $("#categoryId").val();
    var startDate = $("#startDate").val();
    var endDate = $("#endDate").val();
    var quickBuyPrice = $("#quickBuyPrice").val();
    var userId = $("#userId").val();
    
    var json = '{"title" :"' + title +
        '", "description" :"' + description +
        '", "startPrice" :"' + startPrice +
        '", "minStep" :"' + minStep +
        '", "categoryId" :"' + categoryId +
        '", "startDate" :"' + startDate +
        '", "endDate" :"' + endDate +
        '", "quickBuyPrice" :"' + quickBuyPrice +
        '", "userId" :"' + userId + '"}';

    $.ajax({
        url: '/lot',
        type: 'POST',
        data: json,
        contentType: "application/json",
        success: function () {
            top.location.href = 'user/'+userId;
        },
        error: function () {
            alert("Не удалось создать лот");
        }
    })
}