function uploadSecondaryLotImage(){
    var lotId = $("#lotId").val();
    var oMyForm = new FormData();
    oMyForm.append("file", secondaryLotImageId.files[0]);

    $.ajax({
        url: '/uploadSecondaryLotImage/'+lotId ,
        data: oMyForm,
        dataType: 'text',
        processData: false,
        contentType: false,
        type: 'POST',
        success: function () {
            window.location.href = "http://localhost:8080/user/"+lotId+"/edit";
        }

    });
}