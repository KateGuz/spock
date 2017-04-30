function editUser(id) {
    var formData = new FormData();
    formData.append('name', $("#inputNameEdit").val());
    formData.append('email', $("#inputEmailEdit").val());
    formData.append('password', $("#inputPasswordEdit").val());
    formData.append('userImage', $('#userImage')[0].files[0]);
    
    $.ajax({
        headers: {
            Accept: "application/json; charset=utf-8"
        },
        url: '/user/' + id + '/edit',
        data: formData,
        processData: false,
        contentType: false,
        type: 'POST',
        success: function () {
            location.reload();
        },
        error: function () {
            alert("Пользователь с таким именем или почтой уже есть.");
        }
    })
}
