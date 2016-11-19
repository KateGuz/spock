function signIn() {
    var name = $("#inputNameSignIn").val();
    var password = $("#inputPasswordSignIn").val();


    var json = '{"name" :"' + name +
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
            alert("Invalid username or password");
        }
    })
}
