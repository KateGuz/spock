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
    <div class="user-wrapper">
        <div class="user-details">
            <div class="container">
                <div class="row">
                    <div class="col-md-4 user-details-wrapper">
                        <div class="thumbnail user-photo">
                            <img src="/img/1.jpg">
                        </div>
                        <div class="user-info">
                            <div class="user-name-wrapper">
                                <span class="user-name">${user.name}</span>

                                <c:choose>
                                    <c:when test="${loggedUser.id==user.id}">
                                        <form class="user-edit-profile-button" action="/user/${user.id}/edit">
                                            <input type="submit" class="btn btn-default" value="Редактировать профиль"/>
                                        </form>
                                    </c:when>
                                </c:choose>
                            </div>
                            <hr class="user-name-divider">
                            <div class="user-email">
                                <span>Контакты: <span class="user-email-title">${user.email}</span></span>
                            </div>
                            <c:choose>
                                <c:when test="${loggedUser.id==user.id}">
                                    <div class="user-pass">
                                        <span>Пароль: <span class="user-pass-title">${user.password}</span></span>
                                    </div>
                                </c:when>
                            </c:choose>
                            <div class="user-registration-date">
                                <span>На сайте с ${user.registrationDate}</span>
                            </div>
                            <c:choose>
                                <c:when test="${loggedUser.id==user.id}">
                                    <form class="user-add-lot" action="/lot">
                                        <a class="btn btn-success" href="/lot">Добавить новый лот</a>
                                    </form>
                                    <div class="user-report-generation">
                                        <button type="button" class="btn btn-default">Генерировать отчет</button>
                                    </div>
                                </c:when>
                            </c:choose>
                        </div>
                    </div>
                    <div class="col-md-8">
                        <div class="lots-preview-wrapper user-lots-preview-wrapper">
                            <c:forEach items="${lots}" var="lotDto" begin="0" end="8">
                                <div class="col-md-4 col-sm-4">
                                    <a class="lot-preview-link" href="/lot/${lotDto.lot.id}">
                                        <div class="lot-preview-item jumbotron">
                                            <div class="lot-preview-top">
                                                <c:choose>
                                                    <c:when test="${lotDto.lot.type.id == 'I'}">
                                                        <span class="lot-preview-status">Активно</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="lot-preview-status">Планируется</span>
                                                    </c:otherwise>
                                                </c:choose>
                                                <c:choose>
                                                    <c:when test="${!empty lotDto.lot.quickBuyPrice}">
                                                        <span class="lot-preview-quick-buy-price"
                                                              title="Стоимость мгновенного выкупа">
							                                <span class="glyphicon glyphicon-gift"></span>
							                                    ${lotDto.lot.quickBuyPrice} ${currency}
					                                	</span>
                                                    </c:when>
                                                </c:choose>
                                            </div>
                                            <div class="thumbnail lot-preview-thumbnail">
                                                <img src="/image/${lotDto.lot.imageIds[0]}">
                                                <div class="lot-preview-title">
                                                    <span>${lotDto.lot.title}</span>
                                                </div>
                                            </div>
                                            <div class="lot-preview-current-price">
                                                <c:choose>
                                                    <c:when test="${lotDto.lot.maxBid==null}">
                                                        <span>Максимальная ставка:  ${lotDto.lot.startPrice} ${currency}</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span>Максимальная ставка: ${lotDto.currentPrice} ${currency}</span>
                                                    </c:otherwise>
                                                </c:choose>

                                            </div>
                                            <div>
                                                <span>Всего ставок: ${lotDto.lot.bidCount}</span>
                                            </div>
                                            <c:choose>
                                                <c:when test="${lotDto.lot.type.id == 'C'}">
                                                    <span>Торги окончены</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:choose>
                                                        <c:when test="${lotDto.lot.type.id == 'I'}">
                                                            <span>Осталось: ${lotDto.timeLeft}</span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span>Начало: ${lotDto.lot.startDate}</span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:otherwise>
                                            </c:choose>

                                        </div>
                                    </a>
                                    <c:choose>
                                        <c:when test="${loggedUser.id==user.id}">
                                            <div class="user-lot-controls">
                                                <form class="user-lot-edit-button" action="/lot/${lotDto.lot.id}/edit">
                                                    <a class="btn btn-default"
                                                       href="/lot/${lotDto.lot.id}/edit">Редактировать</a>
                                                </form>
                                                <button type="button" class="btn btn-danger"
                                                        onclick="deleteLot(${lotDto.lot.id},${user.id})">Закрыть
                                                </button>
                                            </div>
                                        </c:when>
                                    </c:choose>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
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
<script src="/js/editUser.js"></script>
<script src="/js/deleteLot.js"></script>
<script src="/js/currencyConversion.js"></script>
</body>
</html>
