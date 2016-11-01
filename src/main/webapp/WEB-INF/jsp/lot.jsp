<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../favicon.ico">

    <title>Spock</title>

    <!-- Bootstrap core CSS -->
    <link href="/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <style type="text/css">body{padding-top:20px;padding-bottom:40px;}
    .container-narrow{margin:0 auto;max-width:700px;}.container-narrow>hr{margin:30px 0;}
    .jumbotron{margin:60px 0;text-align:center;}.jumbotron h1{font-size:72px;line-height:1;}
    .jumbotron .btn{font-size:21px;padding:14px 24px;}.marketing{margin:60px 0;}.marketing p+h4{margin-top:28px;}</style>
    <!-- Custom styles for this template -->
    <link href="jumbotron-narrow.css" rel="stylesheet">
</head>

<body>
<div class="container container-narrow">
    <div class="header clearfix">
        <nav>
            <ul class="nav nav-pills pull-right">
                <li role="presentation" class="active"><a href="#">Register/Sign in</a></li>
            </ul>
        </nav>
        <h3 class="text-muted">Spock</h3>
    </div>
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
                <dd>${timeLeft}</dd>
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

</body>
</html>