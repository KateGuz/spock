function addLot() {
    var formData = new FormData();
    formData.append('title', $('#title').val());
    formData.append('description', $("#description").val());
    formData.append('startPrice', $("#startPrice").val());
    formData.append('minStep', $("#minStep").val());
    formData.append('categoryId', document.getElementById("categories").value);
    formData.append('startDate', $("#startDate").val());
    formData.append('endDate', $("#endDate").val());
    formData.append('quickBuyPrice', $("#quickBuyPrice").val());
    formData.append('userId', $("#userId").val());
    formData.append('primaryImage', $('#primaryLotImageId')[0].files[0]);
    formData.append('secondaryImage', $('#secondaryLotImageId')[0].files[0]);

    $.ajax({
        headers: {
            Accept: "application/json; charset=utf-8"
        },
        url: '/lot',
        data: formData,
        processData: false,
        contentType: false,
        type: 'POST',
        success: function () {
            top.location.href = 'user/' + $("#userId").val();
        },
        error: function () {
            alert("Вы внесли некорректные данные.");
        }
    })
}