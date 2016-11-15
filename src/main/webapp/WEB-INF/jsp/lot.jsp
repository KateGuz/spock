<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>User Details System</title>

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
<div class="container container-narrow">
    <hr>
    <div class="row">
        <div class="col-lg-4">
            <img src="..." alt="...">
        </div>
        <div class="col-lg-8">
            <h4>${lot.title}</h4>
            <dl class="dl-horizontal">
                <dt>description</dt>
                <dd>${lot.description}</dd>
                <dt>category</dt>
                <dd>${lot.category.parent.name}>${lot.category.name}</dd>
                <dt>currency</dt>
                <dd>${lot.currency}</dd>
                <dt>start price</dt>
                <dd>${lot.startPrice}</dd>
                <dt>current price</dt>
                <dd>${currentPrice} [${bidCount} bids total]</dd>
                <dt>min bid step</dt>
                <dd>${lot.minStep} ${lot.currency}</dd>
                <dt>time left</dt>
                <dd data-toggle="tooltip" title="${endDate}">${timeLeft}</dd>
                <dt>seller</dt>
                <dd>${lot.user.name}</dd>
            </dl>
            <form class="form-inline">
                <div class="form-group">
                    <label class="sr-only" for="exampleInputAmount">Amount</label>
                    <div class="input-group">
                        <div class="input-group-addon">$</div>
                        <input type="text" class="form-control" id="exampleInputAmount" placeholder="Amount">
                        <div class="input-group-addon">.00</div>
                    </div>
                </div>
                <button type="submit" class="btn btn-primary">Place bid</button>
            </form>
            <dl class="dl-horizontal">

                <dt>buy it now price</dt>
                <dd><div>${lot.quickBuyPrice}  ${lot.currency}</div>
                    <div>
                        <form class="form-inline">
                            <button type="submit" class="btn btn-primary">Buy it now</button>
                        </form>
                    </div>
                </dd>

            </dl>
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
</body>
</html>