<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>TPEDU</title>

<!-- Bootstrap Core CSS -->
<link href="../resources/css/bootstrap.min.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="../resources/css/grayscale.css" rel="stylesheet">

<!-- Custom Fonts -->
<link href="http://fonts.googleapis.com/css?family=Lora:400,700,400italic,700italic" rel="stylesheet" type="text/css">
<link href="http://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css">
<!-- <script src="../resources/js/jquery-1.10.2.js"></script>	 
<script src="../resources/js/jquery-ui-1.11.1.js"></script> -->
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->
</head>

<body id="page-top" data-spy="scroll" data-target=".navbar-fixed-top">

    <!-- Navigation -->
    <nav class="navbar navbar-custom navbar-fixed-top" role="navigation">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-main-collapse">
                    <i class="fa fa-bars"></i>
                </button>
                <a class="navbar-brand page-scroll" href="#page-top">
                    <i class="fa fa-play-circle"></i>  <span class="light">TPE</span> EDU
                </a>
            </div>
        </div>
    </nav>

    <!-- Intro Header -->
    <header class="intro">
        <div class="intro-body">
            <div class="container">
                <div class="row">
                    <div class="col-md-8 col-md-offset-2">
                        <h1 class="brand-heading">TPEDU</h1>
                        <p class="intro-text">Input Sentence .</p>
                        <!-- <a href="#about" class="btn btn-circle page-scroll"><i class="fa fa-angle-double-down animated"></i></a> -->
                        <%-- <h1> ${message } </h1> --%>
						
						<form name="s_form" onsubmit="return false;" method="post" action="result.do">
							<input type="text" id="search_txt" name="search_txt" style="width:400px; height:45px; color: black;">
							<span><a href="#" id="search_btn" class="btn btn-default btn-lg">Search</a></span>
						</form>
                    </div>
                </div>
            </div>
        </div>
    </header>


    <!-- Footer -->
    <footer>
        <div class="container text-center">
            <p>Copyright &copy; Your Website 2016</p>
        </div>
    </footer>

    <!-- jQuery -->
    <script src="../resources/js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="../resources/js/bootstrap.min.js"></script>

    <!-- Plugin JavaScript -->
    <script src="../resources/js/jquery.easing.min.js"></script>

    <!-- Google Maps API Key - Use your own API key to enable the map feature. More information on the Google Maps API can be found at https://developers.google.com/maps/ -->
    <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCRngKslUGJTlibkQ3FkfTxj3Xss1UlZDA&sensor=false"></script>

    <!-- Custom Theme JavaScript -->
    <script src="../resources/js/grayscale.js"></script>
	
	<script type="text/javascript">
	
	//조회 버튼
	$("#search_btn").click(function(){
		var formname = document.s_form;
		formname.action = "result.do";
		formname.submit();
	});	
	
	function search(){
		document.s_form.submit();
	}
	</script> 

</body>
</html>




