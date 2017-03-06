<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>Spock - лучший аукцион в мире!</title>
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/styles.css" rel="stylesheet">
    <link href="/css/ninja-slider.css" rel="stylesheet">
    <link href="/css/media.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>

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
                <a class="navbar-brand logo" href="/"><img src="../img/logo.png"></a>
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
                           aria-expanded="false">${currency}<span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li>
                                <button id="btnCurrencyUSD" type="button" class="btn btn-link">USD
                                </button>
                            </li>
                            <li>
                                <button id="btnCurrencyEUR" type="button" class="btn btn-link">EUR
                                </button>
                            </li>
                            <li>
                                <button id="btnCurrencyUAH" type="button" class="btn btn-link">UAH
                                </button>
                            </li>
                        </ul>
                    </li>
                    <c:choose>
                        <c:when test="${empty loggedUser}">
                            <li><a data-toggle="modal" href="#signIn" data-target="#signIn">Вход / Регистрация</a></li>
                        </c:when>
                        <c:otherwise>
                            <li><a data-toggle="modal" href="/user/${loggedUser.id}">${loggedUser.name}</a></li>
                            <li><a data-toggle="modal" href="/logout">Выход</a></li>
                        </c:otherwise>
                    </c:choose>

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
                                    <input type="text" class="form-control" placeholder="Заголовок" size="50" required
                                           id="title">
                                </div>
                                <!-- Categories dropdown -->
                                <div class="category-pick-wrapper edit-field-wrapper">
                                    <span>Категория:</span>
                                    <select class="form-control" id="categories">
                                        <c:forEach items="${categories}" var="parent">
                                            <optgroup label="${parent.name}">
                                                <c:forEach items="${parent.children}" var="category">
                                                    <option value="${category.id}"
                                                            id="categoryId">${category.name}</option>
                                                </c:forEach>
                                            </optgroup>
                                        </c:forEach>
                                    </select>
                                </div>
                                <!-- End of categories dropdown -->
                                <div class="edit-field-wrapper">
                                    <textarea class="form-control" rows="7" cols="50" required placeholder="Описание"
                                              id="description"></textarea>
                                </div>

                                <span>Загрузить основное фото:</span>
                                    <form  enctype="multipart/form-data">
                                        <input  name="lotImage" id="primaryLotImageId" type="file" /><br/>
                                    </form>

                                <span>Загрузить дополнительное фото:</span>
                                <form  enctype="multipart/form-data">
                                    <input  name="lotImage" id="secondaryLotImageId" type="file" /><br/>
                                </form>

                            </div>


                        </div>
                        <div class="col-md-6 add-lot-right-column">
                            <div class="edit-field-wrapper">
                                <span>Начальная ставка (UAH):</span>
                                <input type="number" class="form-control" size="10" required id="startPrice">
                            </div>
                            <div class="edit-field-wrapper">
                                <span>Минимальный шаг (UAH):</span>
                                <input type="number" class="form-control" size="10" required id="minStep">
                            </div>
                            <div class="edit-field-wrapper">
                                <span>Мгновенный выкуп (UAH):</span>
                                <input type="number" class="form-control" size="10" id="quickBuyPrice">
                                <span>*опционально</span>
                            </div>
                            <div class="edit-field-wrapper">
                                <span>Старт аукциона:</span>
                                <input type="datetime-local" class="form-control" required id="startDate">
                            </div>
                            <div class="edit-field-wrapper">
                                <span>Конец аукциона:</span>
                                <input type="datetime-local" class="form-control" required id="endDate">
                            </div>
                        </div>
                        <div class="container">
                            <div class="row">
                                <div class="save-changes-button col-md-12">
                                    <button type="submit" class="btn btn-success save-changes-button"
                                            value="${loggedUser.id}" id="userId"
                                            onclick="addLot();uploadPrimaryLotImage();uploadSecondaryLotImage()">Создать лот
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


<!-- Sign in -->
<div id="signIn" class="modal fade registration-form" role="dialog">
    <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Вход</h4>
            </div>
            <div class="modal-body">
                <input type="text" required name="name" placeholder="Имя" id="inputNameSignIn"><br><br>
                <input type="password" name="password" placeholder="Пароль" id="inputPasswordSignIn"><br><br>
                <button class="btn-success enter-button" data-dismiss="modal" onclick="signIn()">Войти</button>
                <br>
                <br>
                Вы еще не зарегистрированны?
                <a data-toggle="modal" href="#signUp" data-target="#signUp" data-dismiss="modal">Зарегистрироватся</a>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Выйти</button>
            </div>
        </div>

    </div>
</div>

<!-- Sign up -->
<div id="signUp" class="modal fade" role="dialog">
    <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Регистрация</h4>
            </div>
            <div class="modal-body">
                <form id="dataForm">
                    <input type="text" required name="name" placeholder="Имя" id="inputNameSignUp"><br><br>
                    <input type="email" required name="email" placeholder="Почта" id="inputEmailSignUp"><br><br>
                    <input type="password" required name="password" placeholder="Пароль"
                           id="inputPasswordSignUp"><br><br>
                    <button class="btn-success enter-button" data-dismiss="modal" onclick="signUp()">Зарегистрироватся
                    </button>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Закрыть</button>
            </div>
        </div>

    </div>
</div>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="/js/jquery-3.1.1.js"></script>
<script src="/js/jquery.validate.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="/js/bootstrap/bootstrap.min.js"></script>
<script src="/js/signIn.js"></script>
<script src="/js/signUp.js"></script>
<script src="/js/addLot.js"></script>
<script src="/js/currencyConversion.js"></script>
<script src="/js/sendPrimaryLotImage.js"></script>
<script src="/js/sendSecondaryLotImage.js"></script>
</body>
</html>
