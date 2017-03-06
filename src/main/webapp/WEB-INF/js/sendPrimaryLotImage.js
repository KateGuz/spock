function uploadPrimaryLotImage(){
    var lotId = $("#lotId").val();
    var oMyForm = new FormData();
    oMyForm.append("file", primaryLotImageId.files[0]);

    $.ajax({
        url: '/uploadPrimaryLotImage/'+lotId ,
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