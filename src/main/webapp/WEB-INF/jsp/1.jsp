<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>Spock - лучший аукцион в мире!</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/styles.css" rel="stylesheet">
    <link href="css/ninja-slider.css" rel="stylesheet">
    <link href="css/media.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>
<!--Top section-->
<section class="top-section">
    <nav class="navbar navbar-default">
        <div class="container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header header-logo">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                        data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand logo" href="index.html"><img src="img/logo.png"></a>
            </div>
            <div class="collapse navbar-collapse main-navbar" id="bs-example-navbar-collapse-1">
                <form class="navbar-form navbar-nav">
                    <div class="form-group">
                        <input type="text" class="form-control" placeholder="Поиск">
                    </div>
                    <button type="submit" class="btn btn-default">Найти</button>
                </form>
                <ul class="nav navbar-nav navbar-right">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                           aria-expanded="false">UAH<span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="#">USD</a></li>
                            <li><a href="#">EUR</a></li>
                            <li><a href="#">UAH</a></li>
                        </ul>
                    </li>
                    <li><a href="user.html">Катя Гуз</a></li>
                    <li><a href="index.html">Выход</a></li>
                </ul>
            </div><!-- /.navbar-collapse -->
        </div><!-- /.container-fluid -->
    </nav>
</section>
<!--End of top section-->
<!--Main section-->
<section class="main-section">
    <div class="lot-wrapper">
        <div class="lot-details">
            <div class="container">
                <div class="row">
                    <form class="navbar-form navbar-nav">
                        <div class="col-md-6">
                            <div class="add-lot-details-wrapper">

                                <div class="edit-field-wrapper">
                                    <input type="text" class="form-control" placeholder="Заголовок" size="50" required>
                                </div>
                                <!-- Categories dropdown -->
                                <div class="category-pick-wrapper edit-field-wrapper">
                                    <span>Категория:</span>
                                    <select class="form-control">
                                    </select>
                                </div>
                                <!-- End of categories dropdown -->
                                <div class="edit-field-wrapper">
                                    <textarea class="form-control" rows="7" cols="50" required
                                              placeholder="Описание"></textarea>
                                </div>
                                <div class="lot-load-main-image">
                                    <button type="button" class="btn btn-default">Загрузить основное фото</button>
                                </div>
                                <div>
                                    <button type="button" class="btn btn-default">Загрузить дополнительные фото</button>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6 add-lot-right-column">
                            <div class="edit-field-wrapper">
                                <span>Начальная ставка (UAH):</span>
                                <input type="number" class="form-control" size="10" required>
                            </div>
                            <div class="edit-field-wrapper">
                                <span>Минимальный шаг (UAH):</span>
                                <input type="number" class="form-control" size="10" required>
                            </div>
                            <div class="edit-field-wrapper">
                                <span>Мгновенный выкуп (UAH):</span>
                                <input type="number" class="form-control" size="10">
                                <span>*опционально</span>
                            </div>
                            <div class="edit-field-wrapper">
                                <span>Старт аукциона:</span>
                                <input type="datetime-local" class="form-control" required>
                            </div>
                            <div class="edit-field-wrapper">
                                <span>Конец аукциона:</span>
                                <input type="datetime-local" class="form-control" required>
                            </div>
                        </div>
                        <div class="container">
                            <div class="row">
                                <div class="save-changes-button col-md-12">
                                    <button type="submit" class="btn btn-success save-changes-button">Создать лот
                                    </button>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

</section>
<!--End of main section-->
<!--Footer-->
<section class="footer">
    <div class="footer-content">
        <span>Spock</span></br>
        <span>Все права защищены</span>
    </div>
</section>
<!--End of footer-->


<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="js/jquery-3.1.1.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="js/bootstrap.min.js"></script>
<script src="js/handlebars.min-latest.js"></script>
<script src="js/editLot.js"></script>
<script src="js/helpers.js"></script>

<script id="categories-dropdown-template" type="text/x-handlebars-template">
    <optgroup label="{{name}}">
        {{#each children}}
        <option>{{name}}</option>
        {{/each}}
    </optgroup>
</script>

</body>
</html>