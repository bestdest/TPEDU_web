<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
<title>Home</title>


<!-- jQuery -->
<script src="../iis/resources/js/jquery.js"></script>
<script src="../iis/resources/js/chart/highchart/highcharts.js"></script>
<script src="../iis/resources/js/chart/highchart/highcharts-more.js"></script>
<script src="../iis/resources/js/chart/highchart/modules/exporting.js"></script>


</head>
<body>
<h1>
	Hello world!  
</h1>

<P>  The time on the server is ${serverTime}. </P>
<a href="tpe/search_main.do">Main</a>		
<a href="tpe/search_sub.do">SUB</a>         
<!-- Chart 영역 -->
		<input type="hidden" id="sentence_voca" name="sentence_voca" value="${sentence_voca }"/>
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
		            categories: ['Word of Count', 'Sentence Length', 'Count of Advp', 'Count of Adjp', 'Sentence Pattern', 'Voca Score'],
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
		            min: 1
		        },
		
		        tooltip: {
		            shared: false,
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
		            data: [0, 5, 3, 5, 1, 1],
		            //data: data_s,
		            //data: ['${sentence_word}'*1, '${sentence_length}'*1, '${sentence_advp}'*1, '${sentence_adjp}'*1, '${sentence_pattern}'*1, '${sentence_voca}'*1	],
		            color: 'red',
		            pointPlacement: 'off'
		        }]
		
		    });
		});
		</script>
		
		</div>        
</body>
</html>
