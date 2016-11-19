function edit() {
    // $("#dataForm").validate();

    var name = $("#inputNameEdit").val();
    var email = $("#inputEmailEdit").val();
    var password = $("#inputPasswordEdit").val();
    var user = $("#userId").val();
alert(user);
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
            alert("User with name or email already exist or your data is not passed validation");
        }
    })
}
