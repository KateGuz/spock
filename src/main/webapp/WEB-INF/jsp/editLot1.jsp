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
    <a class="navbar-brand logo" href="/"><img src="/img/logo.png"></a>
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
    <form class="navbar-form navbar-nav">
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
                    <div class="lot-load-new-main-image">
                        <input type="button" class="btn btn-default" value="Загрузить новое основное фото">
                    </div>
                    <div class="lot-load-new-additional-images">
                        <input type="button" class="btn btn-default" value="Загрузить новые дополнительные фото">
                    </div>
                </div>
                <div class="col-md-7">
                    <div class="edit-lot-details-wrapper">
                        <div class="edit-field-wrapper">
                            <span>ID лота:</span>
                            <input type="number" class="form-control" value="${lot.id}" size="60" required id="lotId">
                        </div>
                        <div class="edit-field-wrapper">
                            <span>ID пользователя:</span>
                            <input type="number" class="form-control" value="${lot.user.id}" size="60" required id="userId">
                        </div>
                        <div class="edit-field-wrapper">
                            <input type="text" class="form-control" value="${lot.title}" size="60" required id="title">
                        </div>
                        <div class="edit-field-wrapper">
                            <span>ID категории:</span>
                            <input type="text" class="form-control" value="${lot.category.id}" size="60" required id="categoryId">
                        </div>
                        <!-- Categories dropdown -->
                        <div class="category-pick-wrapper edit-field-wrapper">
                            <span>Категория:</span>
                            <select class="form-control">
                                <c:forEach items="${categories}" var="parent">
                                    <optgroup label="${parent.name}">
                                        <c:forEach items="${parent.children}" var="category">
                                            <option value="${category.id}" id="categoryId_">${category.name}</option>
                                        </c:forEach>
                                    </optgroup>
                                </c:forEach>
                            </select>
                        </div>
                        <!-- End of categories dropdown -->
                        <div class="edit-field-wrapper">
                            <textarea class="form-control" rows="7" cols="60" required id="description">${lot.description}</textarea>
                        </div>
                        <div class="edit-field-wrapper">
                            <span>Начальная ставка (UAH):</span>
                            <input type="number" class="form-control" value="${lot.startPrice}" size="10" required id="startPrice">
                        </div>
                        <div class="edit-field-wrapper">
                            <span>Минимальный шаг (UAH):</span>
                            <input type="number" class="form-control" value="${lot.minStep}" size="10" required id="minStep">
                        </div>
                        <div class="edit-field-wrapper">
                            <span>Мгновенный выкуп (UAH):</span>
                            <input type="number" class="form-control" value="${lot.quickBuyPrice}" size="10" id="quickBuyPrice">
                            <span>*опционально</span>
                        </div>
                        <div class="edit-field-wrapper">
                            <span>Старт аукциона:</span>
                            <input type="datetime-local" class="form-control" value="${lot.startDate}" required id="startDate">
                        </div>
                        <div class="edit-field-wrapper">
                            <span>Конец аукциона:</span>
                            <input type="datetime-local" class="form-control" value="${lot.endDate}"required id="endDate">
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="container">
            <div class="row">
                <div class="save-changes-button">
                    <button type="submit" class="btn btn-success save-changes-button" onclick="editLot()">Сохранить изменения</button>
                </div>
            </div>
        </div>
    </form>
    <script src="/js/ninja-slider.js"></script>
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
<script src="/js/jquery-3.1.1.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="/js/bootstrap/bootstrap.min.js"></script>
<script src="/js/editLot.js"></script>


</body>
</html>