<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>TPEDU</title>

<!-- Custom Fonts -->
<link href="http://fonts.googleapis.com/css?family=Lora:400,700,400italic,700italic" rel="stylesheet" type="text/css">
<link href="http://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css">

<!-- Bootstrap Core CSS -->
<link href="../resources/css/bootstrap.min.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="../resources/css/grayscale.css" rel="stylesheet">
<link href="../resources/css/bootstrap.css" rel="stylesheet" type="text/css">
<link href="../resources/css/style.css" rel="stylesheet" type="text/css">
<link href="../resources/css/font-awesome.css" rel="stylesheet" type="text/css">
<link href="../resources/css/responsive.css" rel="stylesheet" type="text/css">
<link href="../resources/css/animate.css" rel="stylesheet" type="text/css">

<!-- jQuery -->
<script src="../resources/js/jquery.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="../resources/js/bootstrap.min.js"></script>

<!-- Plugin JavaScript -->
<script src="../resources/js/jquery.easing.min.js"></script>
<script type="text/javascript" src="../resources/js/jquery-scrolltofixed.js"></script>
<script type="text/javascript" src="../resources/js/jquery.isotope.js"></script>
<script type="text/javascript" src="../resources/js/wow.js"></script>
<script type="text/javascript" src="../resources/js/classie.js"></script>

<script src="../resources/js/chart/highchart/highcharts.js"></script>
<script src="../resources/js/chart/highchart/highcharts-more.js"></script>
<script src="../resources/js/chart/highchart/modules/exporting.js"></script>

</head>
<body>
    <!-- Intro Header -->
    <header class="intro">
        <div class="intro-body">
            <div class="container">
                <div class="row">
                    <div>
                        <%-- <h1 class="brand-heading">${grade }</h1> --%>
                        <p class="intro-text">입력하신 문장의 예상 등급은 </p>
                        <h1 class="brand-heading"> ${grade }</h1><p class="intro-text"> 입니다.</p>
						<p class="intro-text"><a href="/iis/tpe/search_main.do"><img src="../resources/img/back-icon.png" alt="back" style="width:50px;"></a></p>
                    </div>
                </div>
            </div>
        </div>
    </header>
	<section class="main-section team" id="team"><!--main-section team-start-->
	<div class="container">
        <h2>결과</h2>
        <h6>////////////////////////////////////</h6>
        <h6>입력하신 문장 : [ ${input_txt } ]</h6>
        
        <!-- Chart 영역 -->
		<div id="container" style="min-width: 400px; max-width: 600px; height: 400px; margin: 0 auto">
		<script type="text/javascript">
		$(function () {
		
		    $('#container').highcharts({
		
		        chart: {
		            polar: true,
		            type: 'line'
		        },
		
		        title: {
		        	align: "center",
		            text: 'TPEDU',
		            x: 0,
		            style: { "color": "#333333", "fontSize": "22px" }
		        },
		
		        pane: {
		            size: '80%'
		        },
		
		        xAxis: {
		            categories: ['Word of Count', 'Sentence Length', 'Count of Advp', 'Count of Adjp',
		                    'Sentence Pattern', 'Voca Score'],
		            tickmarkPlacement: 'on',
		            lineWidth: 0
		        },
		
		        yAxis: {
		            gridLineInterpolation: 'polygon',
		            lineWidth: 0,
		            min: 0
		        },
		
		        tooltip: {
		            shared: true,
		            pointFormat: '<span style="color:{series.color}">{point.y} <b></b><br/>'
		        },
		
		        legend: {
		            align: 'right',
		            verticalAlign: 'top',
		            borderColor: "#909090",
		            enabled: false,
		            y: 70,
		            layout: 'vertical'
		        },
		
		        series: [{
		            name: 'Sentence Features',
		            data: ['${sentence_word}'*1, '${sentence_length}'*1, '${sentence_advp}'*1, '${sentence_adjp}'*1, '${sentence_pattern}'*1, '${sentence_voca}'*1	],
		            pointPlacement: 'off'
		        }]
				
		    });
		});
		</script>
		
		</div>        
        <div class="team-leader-block clearfix">
            <div class="team-leader-box">
                <div class="team-leader wow fadeInDown delay-03s"> 
                    <div class="team-leader-shadow"><a href="#"></a></div>
                    <h1>MLP</h1>
                    <ul>
                        <li>MLP</li>
                    </ul>
                </div>
                <h3 class="wow fadeInDown delay-03s">MLP</h3>
                <span class="wow fadeInDown delay-03s">예상 적중 확률 : 88%</span>
                <p class="wow fadeInDown delay-03s">예상 결과 : ${mlp_grade }</p>
            </div>
            <div class="team-leader-box">
                <div class="team-leader  wow fadeInDown delay-06s"> 
                    <div class="team-leader-shadow"><a href="#"></a></div>
                    <h1>SVM</h1>
                    <ul>
                        <li>SVM</li>
                    </ul>
                </div>
                <h3 class="wow fadeInDown delay-06s">SVM</h3>
                <span class="wow fadeInDown delay-06s">예상 적중 확률 : 77%</span>
                <p class="wow fadeInDown delay-06s">예상 결과 : ${svm_grade }</p>
            </div>
            <div class="team-leader-box">
                <div class="team-leader wow fadeInDown delay-09s"> 
                    <div class="team-leader-shadow"><a href="#"></a></div>
                    <h1>NAIVE</h1>
                    <ul>
                        <li>NAIVE</li>
                    </ul>
                </div>
                <h3 class="wow fadeInDown delay-09s">Naive</h3>
                <span class="wow fadeInDown delay-09s">예상 적중 확률 : 66%</span>
                <p class="wow fadeInDown delay-09s">예상 결과 : ${naive_grade }</p>
            </div>
        </div>
    </div>
	</section><!--main-section team-end-->
</body>
  <script>
  	/* animate */
    wow = new WOW(
      {
        animateClass: 'animated',
        offset:       100
      }
    );
    wow.init();
 
  </script>

</html>