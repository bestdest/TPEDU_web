<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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


<style type="text/css">
::selection {
    background:#99CC00;
    color: #FFF;
    text-shadow: none;
}
::-moz-selection {
    background:#99CC00;
    color: #FFF;
    text-shadow: none;
}
::-webkit-selection {
    background:#99CC00;
    color: #FFF;
    text-shadow: none;
}
div.feature {
	font-size: 17px;
	margin: 0 0 5px 0;
	text-transform: uppercase;
	font-weight: 400;
	font-family: 'Montserrat', sans-serif;
	margin-bottom: 15px;
}
div.feature span{
	font-size: 14px;
	margin: 0 0 5px 0;
	text-transform: uppercase;
	font-weight: 400;
	color: #888888;
	font-family: 'Montserrat', sans-serif;
	padding-left: 15px;
}
</style>
</head>
<body>
    <!-- Intro Header -->
    <header class="intro">
        <div class="intro-body">
            <div class="container">
                <div class="row">
                    <div>
                        <%-- <h1 class="brand-heading">${grade }</h1> --%>
                        <p class="intro-text"><h6 style="color: #fff;">입력하신 문장 : ${input_txt }</h6> </p>
                        <p class="intro-text">입력하신 문단의 예상 등급은 </p>
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
        <h6><hr/></h6>
        <div style="height: 660px;">
	        <!-- Chart 영역 -->
			<div id="spider-chart" style="min-width: 400px; max-width: 600px; height: 400px; margin: 0 auto; float:left;">
			<script type="text/javascript">
			$(function () {
			
			    $('#spider-chart').highcharts({
			
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
			            categories: ['${sentence_feature1_str}', '${sentence_feature2_str}', '${sentence_feature3_str}', '${sentence_feature4_str}', '${sentence_feature5_str}', '${sentence_feature6_str}'	],
			            tickmarkPlacement: 'on',
			            lineWidth: 0
			        },
			
			        yAxis: {
			            gridLineInterpolation: 'polygon',
			            lineWidth: 0,
			            labels: {
			                formatter: function() {
			                    return '';
			                }
			            },
			            min: 0
			        },
			
			        tooltip: {
			            shared: true,
			            formatter: function() {
			            	var tmp_str = '초급';
			            	if(this.y == 2){
			            		tmp_str = '중급';
			            	}else if(this.y == 3){
			            		tmp_str = '고급';
			            	}
			                //return 'The value for <b>' + this.x + '</b> is <b>' + this.y + '</b>, in series '+ this.series.name;
			                return '<span style="color:{series.color}">'+this.x+' : '+this.y+'<b></b><br/>';
			            }
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
			            data: ['${sentence_feature1}'*1, '${sentence_feature2}'*1, '${sentence_feature3}'*1, '${sentence_feature4}'*1, '${sentence_feature5}'*1, '${sentence_feature6}'*1	],		//voca score, patter_score, modifire, struct_type, ttr, cli
			            pointPlacement: 'off'
			        }]
					
			    });
			});
			</script>
			</div>   
			 <!-- 설명영역 -->
			<div style=" width: 590px;; min-width: 400px; max-width: 600px; height: auto; border: 2px solid gray; border-radius: 10px;  float:right; padding:20px;">
				<div class="feature"> * 문장 개수: ${sentence_origin.numSen }개 <br></div>
				<div class="feature"> * 전체 문장 길이 : ${sentence_origin.length } 	<br></div> 
				<div class="feature"> * 전체 단어 개수 : ${sentence_origin.word }개 	<br></div>
				<div class="feature"> 
					* 수식어 전체 개수 (전체 : ${sentence_origin.cnt_modifier }개)<br> 
					<c:if test="${sentence_modifier.modifier0 ne 0 }">
					<span> - ${sentence_modifier.modifier_sen0 } : ${sentence_modifier.modifier0 } 개<br>	</span> 
					</c:if>
					<c:if test="${sentence_modifier.modifier1 ne 0 }">
					<span> - ${sentence_modifier.modifier_sen1 } : ${sentence_modifier.modifier1 } 개<br>	</span> 
					</c:if>
					<c:if test="${sentence_modifier.modifier2 ne 0 }">
					<span> - ${sentence_modifier.modifier_sen2 } : ${sentence_modifier.modifier2 } 개<br>	</span> 
					</c:if>
					<c:if test="${sentence_modifier.modifier3 ne 0 }">
					<span> - ${sentence_modifier.modifier_sen3 } : ${sentence_modifier.modifier3 } 개<br>	</span> 
					</c:if>
					<c:if test="${sentence_modifier.modifier4 ne 0 }">
					<span> - ${sentence_modifier.modifier_sen4 } : ${sentence_modifier.modifier4 } 개<br>	</span> 
					</c:if>
				</div>
				
				<div class="feature"> 
					* 문장 구조 점수 : 타입 ${sentence_origin.type } <br>
					<span>
					- 1: 단문, 2: 중문, 3: 복문, 4: 중복문<br>
					</span>
				</div>
				<div class="feature"> 
					* 단어 점수 : ${sentence_origin.voca_score }점 <br>
					<span>- Collins Dictionary 에 나온 단어 기준 <br></span>
					<span> (http://collinsdictionary.com/dictionary)<br></span>
				</div>
				<div class="feature"> * 문단 유사 단어 사용비율 : ${sentence_origin.TTR } <br>(동일하지않은 단어수 / 전체 단어 수)<br></div>
				<div class="feature"> 
					* 문단 가독성 점수(Coleman-Liau Index) : ${sentence_origin.CLI }    <br>
					<span>	- CLI = 0.0588L - 0.296S - 15.8	<br></span>
					<span>	- L : 글자수 / 단어수 * 100 (100개 단어당 평균 글자 수)	<br></span>
					<span>	- S : 문장수 / 단어수 * 100 (100개 단어당 문장 개수)	</span>	
				</div>
				<div>  </div>
				
			</div>   
		</div>
		<h6><hr/></h6>
        <div class="team-leader-block clearfix">
            <div class="team-leader-box" style="margin-top:20px;">
                <div class="team-leader wow fadeInDown delay-03s"> 
                    <div class="team-leader-shadow"><a href="#"></a></div>
                    <h1>MLP</h1>
                    <ul>
                        <li>MLP</li>
                    </ul>
                </div>
                <h3 class="wow fadeInDown delay-03s">MLP</h3>
                <span class="wow fadeInDown delay-03s">88%</span>	<!-- 예상 적중 확률 :  -->
                <p class="wow fadeInDown delay-03s">분류 : level ${mlp_grade }</p>
            </div>
            <div class="team-leader-box" style="margin-top:20px;">
                <div class="team-leader  wow fadeInDown delay-06s"> 
                    <div class="team-leader-shadow"><a href="#"></a></div>
                    <h1>SVM</h1>
                    <ul>
                        <li>SVM</li>
                    </ul>
                </div>
                <h3 class="wow fadeInDown delay-06s">SVM</h3>
                <span class="wow fadeInDown delay-06s">77%</span>
                <p class="wow fadeInDown delay-06s">분류 : level ${svm_grade }</p>
            </div>
            <div class="team-leader-box" style="margin-top:20px;">
                <div class="team-leader wow fadeInDown delay-09s"> 
                    <div class="team-leader-shadow"><a href="#"></a></div>
                    <h1>NAIVE</h1>
                    <ul>
                        <li>NAIVE</li>
                    </ul>
                </div>
                <h3 class="wow fadeInDown delay-09s">Naive</h3>
                <span class="wow fadeInDown delay-09s">66%</span>
                <p class="wow fadeInDown delay-09s">분류 : level ${naive_grade }</p>
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