<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>Spock</title>

    <!-- Bootstrap -->
    <link href="/css/bootstrap/bootstrap.min.css" rel="stylesheet">
    <!-- Custom styles -->
    <link href="/css/styles.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <![endif]-->
</head>
<body>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/">Spok</a>
        </div>

        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <form class="navbar-form navbar-left block2" role="search" action="">
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="search">
                </div>
                <button type="submit" class="btn btn-default">Search</button>
            </form>


            <ul class="nav navbar-nav navbar-right block1">
                <ul class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" role="button"
                       aria-haspopup="true" aria-expanded="false">UAH<span
                            class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a role="button" onclick="">UAH</a></li>
                        <li><a role="button" onclick="">USD</a></li>
                        <li><a role="button" onclick="">EUR</a></li>
                    </ul>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <c:choose>
                        <c:when test="${empty loggedUser}">
                            <li><a data-toggle="modal" href="#signIn" data-target="#signIn">Sign In / Sign up</a></li>
                        </c:when>
                        <c:otherwise>
                            <li><a data-toggle="modal" href="/user">${loggedUser.name}</a></li>
                            <li><a data-toggle="modal" href="/logout">Sign out</a></li>
                        </c:otherwise>
                    </c:choose>

                </ul>
            </ul>
        </div>
    </div><!-- /.container-fluid -->
</nav>
<div class="btn-group" role="group" aria-label="sorting">
    <button type="button" class="btn btn-default " id="btnSortAsc">Sort by price ▲</button>
    <button type="button" class="btn btn-default " id="btnSortDesc">Sort by price ▼</button>
    <button type="button" class="btn btn-default " id="btnSortSoonest">Soonest</button>
    <button type="button" class="btn btn-default " id="btnSortClear">Clear filters</button>
</div>
<div class="container">
    <div class="row">
        <div class="col-md-1">
            <c:forEach items="${categories}" var="parent">
                <div class="btn-group">
                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown"
                            aria-haspopup="true" aria-expanded="false">
                        "${parent.name} <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu">
                        <c:forEach items="${parent.children}" var="category">
                            <li><a href="/category/${category.id}">${category.name}</a></li>
                        </c:forEach>
                    </ul>
                </div>
            </c:forEach>
        </div>
        <div class="col-md-11">
            <c:forEach items="${lots}" var="lot" begin="0" end="8">
                <div class="col-md-4">

                    <a class="btn btn-default" onclick="">"${lot.quickBuyPrice} ,${lot.currency}"<br>Купить сейчас</a>
                    <img class="img-thumbnail img" width="300" height="225"
                         src="data:image/jpeg;base64,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">

                    <c:out value="${lot.title}"/><br>
                    <c:out value="окончание торгов: ${lot.endDate}"/><br>
                    <c:choose>
                        <c:when test="${lot.maxBid==null}">
                            <c:out value="цена сейчас: ${lot.startPrice} ,${lot.currency}"/>
                        </c:when>
                        <c:otherwise>
                            <c:out value="цена сейчас: ${lot.maxBid.value} ,${lot.currency}"/>
                        </c:otherwise>
                    </c:choose>
                    <br>
                    <br>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
<!-- Sign in -->
<div id="signIn" class="modal fade registration-form" role="dialog">
    <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Sign in</h4>
            </div>
            <div class="modal-body">
                <input type="text" name="email" placeholder="Email" id="inputEmailSignIn"><br><br>
                <input type="password" name="password" placeholder="Password" id="inputPasswordSignIn"><br><br>
                <button class="btn-success enter-button" data-dismiss="modal" onclick="signIn()">Enter</button>
                <br>
                <br>
                Have no account yet?
                <a data-toggle="modal" href="#signUp" data-target="#signUp" data-dismiss="modal">Sign up</a>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
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
                <h4 class="modal-title">Sign up</h4>
            </div>
            <div class="modal-body">
                <form id="dataForm">
                    <input type="text" required name="name" placeholder="Name" id="inputNameSignUp"><br><br>
                    <input type="email" required name="email" placeholder="email" id="inputEmailSignUp"><br><br>
                    <input type="password" required name="password" placeholder="Password" id="inputPasswordSignUp"><br><br>
                    <button class="btn-success enter-button" data-dismiss="modal" onclick="signUp()">Enter</button>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
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
<script src="/js/sort.js"></script>
</body>
</html>