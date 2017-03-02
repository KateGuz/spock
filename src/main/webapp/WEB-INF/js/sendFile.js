function SendFile() {

    var files;

// Вешаем функцию на событие
// Получим данные файлов и добавим их в переменную

    $('input[type=file]').change(function(){
        files = this.files;
    });


    $('.submit.button').click(function( event ){
        event.stopPropagation(); // Остановка происходящего
        event.preventDefault();  // Полная остановка происходящего

        // Создадим данные формы и добавим в них данные файлов из files

        var data = new FormData();
        $.each( files, function( key, value ){
            data.append( key, value );
        });

        // Отправляем запрос

        $.ajax({
            url: '/load',
            type: 'POST',
            data: data,
            cache: false,
            dataType: 'json',
            processData: false, // Не обрабатываем файлы (Don't process the files)
            contentType: false, // Так jQuery скажет серверу что это строковой запрос
            success: function( ){
            },
            error: function( ){

            }
        });

    });

}


