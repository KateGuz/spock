function signUp() {
    // $("#dataForm").validate();

    var name = $("#inputNameSignUp").val();
    var email = $("#inputEmailSignUp").val();
    var password = $("#inputPasswordSignUp").val();

    var json = '{"name" :"' + name +
        '", "email" :"' + email +
        '", "password" :"' + password + '"}';

    $.ajax({
        url: '/registration',
        type: 'POST',
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

