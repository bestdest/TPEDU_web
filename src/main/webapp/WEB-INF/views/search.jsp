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
                        <p class="intro-text">Please Input Sentence .</p>
                        <!-- <a href="#about" class="btn btn-circle page-scroll"><i class="fa fa-angle-double-down animated"></i></a> -->
                        <%-- <h1> ${message } </h1> --%>
						
						<form name="s_form" onsubmit="return false;" method="post" action="result.do">
							<!-- <input type="text" id="search_txt" name="search_txt" style="width:600px; height:45px; color: black;"> -->
							<div style="float:left;">
								<textarea id="search_txt" name="search_txt" rows="5" cols="80" style="color: black;background-color: rgb(250, 255, 255);"></textarea>
							</div>
							<div>
							</div>
							<span><a href="#" id="search_btn" class="btn btn-default btn-lg">Submit</a></span>
						</form>
                    </div>
                </div>
            </div>
        </div>
    </header>


    <!-- Footer -->
    <footer>
        <div class="container text-center">
            <p>Copyright &copy; Hanyang Artificial Intelligence Lab.</p>
        </div>
    </footer>

    <!-- jQuery -->
    <script src="../resources/js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="../resources/js/bootstrap.min.js"></script>

    <!-- Plugin JavaScript -->
    <script src="../resources/js/jquery.easing.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="../resources/js/grayscale.js"></script>
    
    <!-- textarea_autosize -->
    <script src="../resources/js/textarea_autosize.js"></script>
	<script>autosize(document.querySelectorAll('textarea'));</script> 
	
	<script type="text/javascript">
	$(document).ready(function(){
	    $("input[name=search_txt]").keydown(function (key) {
	        if(key.keyCode == 13){//키가 13이면 실행 (엔터는 13)
	        	$("#search_btn").trigger("click");
	        }
	    });
	});
	
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




