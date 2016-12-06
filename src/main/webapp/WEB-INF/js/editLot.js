
function editLot() {
    var id = $("#lotId").val();
    console.log(id);
    var title = $("#title").val();
    console.log(title);
    var description = $("#description").val();
    var startPrice = $("#startPrice").val();
    var minStep = $("#minStep").val();
    var categoryId = $("#categoryId").val();
    var startDate = $("#startDate").val();
    var endDate = $("#endDate").val();
    var quickBuyPrice = $("#quickBuyPrice").val();
    var userId = $("#userId").val();
    console.log(startDate);

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
        url: '/lot/' + id,
        type: 'PUT',
        data: json,
        contentType: "application/json",
        success: function () {
            location.reload();
        },
        error: function () {
            alert("Invalid data");
        }
    })
}