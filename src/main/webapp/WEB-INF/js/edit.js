function edit() {
    // $("#dataForm").validate();

    var name = $("#inputNameEdit").val();
    var email = $("#inputEmailEdit").val();
    var password = $("#inputPasswordEdit").val();
    var user = $("#userId").val();
    var json = '{"name" :"' + name +
        '", "email" :"' + email +
        '", "password" :"' + password + '"}';

    $.ajax({
        url: '/user/'+user,
        type: 'PUT',
        data: json,
        contentType: "application/json",
        success: function () {
            location.reload();
        },
        error: function () {
            alert("Пользователь с таким именем или почтой уже есть.");
        }
    })
}
