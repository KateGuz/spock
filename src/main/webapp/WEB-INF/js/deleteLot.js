function deleteLot(lotId,userId) {
    var verification = confirm("Вы хотите удалить лот?");
    if (verification == true) {
        $.ajax({
            url: '/lot/' + lotId,
            type: 'DELETE',
            success: function () {
                alert("Лот удален");
                top.location.href = '/user/'+userId;
            }
        })

    }
}