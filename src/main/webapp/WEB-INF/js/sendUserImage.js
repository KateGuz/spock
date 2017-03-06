function uploadImage(){
    var userId = $("#userId").val();
    var oMyForm = new FormData();
    oMyForm.append("file", userImage.files[0]);

    $.ajax({
        url: '/uploadUserImage/='+userId ,
        data: oMyForm,
        dataType: 'text',
        processData: false,
        contentType: false,
        type: 'POST',
        success: function () {
            window.location.href = "http://localhost:8080/user/"+userId+"/edit";
        }

    });
}
