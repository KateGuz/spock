function signIn() {
    var email = $("#inputEmailSignIn").val();
    var password = $("#inputPasswordSignIn").val();


    var json = '{"email" :"' + email +
        '", "password" :"' + password + '"}';

    $.ajax({
        url: '/login',
        type: 'POST',
        data: json,
        contentType: "application/json",
        success: function () {
            location.reload();
        },
        error: function () {
            alert("Invalid username or password.");
        }
    })
}
