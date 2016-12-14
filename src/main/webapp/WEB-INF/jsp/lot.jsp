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
    <link href="/css/bootstrap/bootstrap.min.css" rel="stylesheet">
    <link href="/css/styles.css" rel="stylesheet">
    <link href="/css/ninja-slider.css" rel="stylesheet">
    <link href="/css/media.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->

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
                           aria-expanded="false">UAH<span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="">USD</a></li>
                            <li><a href="">EUR</a></li>
                            <li><a href="">UAH</a></li>
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
                <div class="col-md-5">
                    <div class="lot-image-slider-wrapper">
                        <div id="ninja-slider">
                            <div class="inner">
                                <ul>
                                    <c:forEach var="i" begin="0" end="3">
                                        <li><a class="ns-img" href="../img/logo.png"></a></li>
                                    </c:forEach>
                                </ul>
                            </div>
                        </div>
                    </div>
                        <c:if test="${!empty loggedUser}">
                            <div class="lot-quick-buy">
                                <button type="button" class="btn btn-default" id="quickBuy" value="${lot.id}" onclick="quickBuy()">Выкупить сейчас за ${lot.quickBuyPrice} UAH
                                </button>
                            </div>
                            <div class="lot-subscribtion">
                                <button type="button" class="btn btn-default">Подписаться на обновления</button>
                            </div>
                        </c:if>
                </div>
                <div class="col-md-7">
                    <div class="lot-details-wrapper">
                        <div class="lot-title">
                            <span>${lot.title}</span>
                        </div>
                        <div class="lot-category">
                            Категория: ${lot.category.name}
                        </div>
                        <div class="lot-seller">
                            Продавец: <a class="lot-author-link" href="/user/${user.id}">${user.name}</a>
                        </div>
                        <div class="lot-description">
                            <span>${lot.description}</span>
                        </div>

                        <div class="lot-price">
                            <span>Максимальная ставка: ${lot.maxBid.value} UAH </span>
                            <span class="lot-start-price">Стартовая цена: ${lot.startPrice} UAH</span>
                        </div>
                        <c:if test="${empty loggedUser}">
                            <div class="lot-price">
                                <span>Купить сейчас за: ${lot.quickBuyPrice} UAH</span>
                            </div>
                        </c:if>
                        <div class="lot-time-left-and-participants">
                            <span>До конца аукциона осталось ${timeLeft} (всего было размещено ${bidCount} ставок).</span>
                        </div>

                    </div>
                    <div class="place-bid thumbnail">
                        <c:choose>
                            <c:when test="${empty loggedUser}">
                                <p><a data-toggle="modal" href="#signIn" data-target="#signIn">Войдите/зарегистрируйтесь</a>, если хотите сделать ставку или купить лот сейчас.</p>
                            </c:when>
                            <c:otherwise>
                                <p class="place-bid-title">Понравился товар - разместите свою ставку!</p>
                                <form class="navbar-form navbar-nav">
                                    <div class="form-group">
                                        <input type="text" class="form-control" placeholder="${currentPrice}">
                                    </div>
                                    <button type="submit" class="btn btn-default">Сделать ставку</button>
                                    <p class="place-bid-min-step">*минимальный шаг: ${lot.minStep} UAH</p>
                                </form>
                            </c:otherwise>
                        </c:choose>

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
<script src="/js/jquery-3.1.1.min.js"></script>
<script src="/js/jquery.validate.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="/js/bootstrap/bootstrap.min.js"></script>
<script src="/js/signIn.js"></script>
<script src="/js/signUp.js"></script>
<script src="/js/ninja-slider.js"></script>
<script src="/js/quickBuy.js"></script>
</body>
</html>
