<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1"><!-- 
<meta name="Access-Control-Allow-Origin" content="http://localhost:8888"> -->
 <%response.setHeader("Access-Control-Allow-Origin", "*"); %>
<%-- 静态资源 --%> 
<script src="http://cdn.static.runoob.com/libs/jquery/1.10.2/jquery.min.js"></script>
<link href="${pageContext.request.contextPath}/statics/bootstrap.min.css" rel="stylesheet"></link>
<link href="${pageContext.request.contextPath}/statics/font-awesome.min.css" rel="stylesheet"></link>
<link href="${pageContext.request.contextPath}/statics/bootstrap-theme-platform.css" rel="stylesheet"></link>
<link href="${pageContext.request.contextPath}/statics/built-in-icon-background-colors.css" rel="stylesheet"></link>
<link href="${pageContext.request.contextPath}/statics/overview.css" rel="stylesheet"></link>
<script src="${pageContext.request.contextPath}/statics/UrlManager.js"></script>
<script src="${pageContext.request.contextPath}/statics/echarts.min.js" ></script>
<script src="${pageContext.request.contextPath}/statics/echarts-liquidfill.min.js"></script>
<script src="${pageContext.request.contextPath}/statics/vendor.js"></script>
<script src="${pageContext.request.contextPath}/statics/vue.js" ></script>
<script src="${pageContext.request.contextPath}/statics/vue-resource.js"></script>

</head>
<body> 
	<div id="overview-app" class="container-fluid">

		<div class="row text-center">
			<div class="col-md-3">

				<div class="basis-overview-top-details-block">
					<span class="details-block-title">CPU分配比</span>

					<div id="cpu-pie-chart" class="details-block-chart"></div>

					<div class="details-block-list">
						<div class="details-block-list-title">主机CPU总核数</div>
						<div class="details-block-list-content">{{cpuTotalCount}}</div>
					</div>
					<div class="details-block-list">
						<div class="details-block-list-title">虚拟机CPU总核数</div>
						<div class="details-block-list-content">{{jvmCpuTotalCount}}</div>
					</div>
					<div class="details-block-list">
						<div class="details-block-list-title">CPU分配比例</div>
						<div class="details-block-list-content">{{cpuUsedPercent}}</div>
					</div> 
				</div>
			</div>
			<div class="col-md-3">
				<div class="basis-overview-top-details-block">
					<span class="details-block-title">内存分配比</span>
					<div id="memory-pie-chart" class="details-block-chart"></div>
					<div class="details-block-list">
						<div class="details-block-list-title">主机内存总数</div>
						<div class="details-block-list-content">{{parseInt(memoryTotalSize / 1024 / 1024)}} M</div>
					</div>
					<div class="details-block-list">
						<div class="details-block-list-title">主机内存使用数</div>
						<div class="details-block-list-content">{{parseInt(memoryUsedSize / 1024 / 1024)}} M</div>
					</div>
					<div class="details-block-list">
						<div class="details-block-list-title">内存分配比例</div>
						<div class="details-block-list-content">{{memoryUsedPercent}}</div>
					</div>
				</div>
			</div>
			<div class="col-md-3">
				<div class="basis-overview-top-details-block">
					<span class="details-block-title">储存分配比</span>
					<div id="storage-pie-chart" class="details-block-chart"></div>
					<div class="details-block-list">
						<div class="details-block-list-title">存储总数</div>
						<div class="details-block-list-content">{{parseInt(storageTotalSize / 1024)}} M</div>
					</div>
					<div class="details-block-list">
						<div class="details-block-list-title">可用存储</div>
						<div class="details-block-list-content">{{parseInt(storageSpareSize / 1024)}} M</div>
					</div>
					<div class="details-block-list">
						<div class="details-block-list-title">存储分配比例</div>
						<div class="details-block-list-content">{{storageUsedPercent}}</div>
					</div>
				</div>
			</div>
			<div class="col-md-3">
				<div class="basis-overview-top-details-block">
					<span class="details-block-title">JVM分配比</span>
					<div id="jvm-pie-chart" class="details-block-chart"></div>

					<div class="details-block-list">
						<div class="details-block-list-title">虚拟机内存总数</div>
						<div class="details-block-list-content">{{parseInt(jvmTotalSize / 1024 / 1024)}} M</div>
					</div>
					<div class="details-block-list">
						<div class="details-block-list-title">虚拟机内存空闲数</div>
						<div class="details-block-list-content">{{parseInt(jvmFreeSize / 1024 / 1024)}} M</div>
					</div>
					<div class="details-block-list">
						<div class="details-block-list-title">虚拟机内存最大数</div>
						<div class="details-block-list-content">{{parseInt(jvmMaxSize / 1024 / 1024)}} M</div>
					</div>
				</div>
			</div>
		</div>

		<div class="row text-center">
			<div class="col-md-6">
				<div class="basis-overview-bottom-details-block">
					<div class="details-block-title">系统健康</div>
					<div id="health-fill-chart" class="details-block-chart" style="width: 350px; height: 180px; margin-left: 100px;"></div>

				</div>
			</div>
			<div class="col-md-6">

				<div class="basis-overview-bottom-details-block">
					<div class="details-block-title">网络流量</div>
					<!-- <div id="network-pie-chart" class="details-block-chart"></div> -->
					<div id="network-line-chart" class="details-block-chart" style="width: 500px; height: 220px; margin-left: 20px;"></div>

				</div>
			</div>

		</div>

	</div>


	<script type="text/javascript">
		var sourceOption = {
			series : [{
				hoverAnimation : false,
				animation : false,
				type : 'pie',
				radius : ['0', '60%'],
				center : ['50%', '50%'],
				label : {
					normal : {
						position : 'center',
						textStyle : {
							color : '#fff',
							fontSize : 30 
						}
					}
				},
				labelLine : {
					normal : {
						show : false
					}
				},
				data : [{
					value : 0,
					name : '0%',
					itemStyle : {
						normal : {}
					}
				}]
			}, {
				type : 'pie',
				hoverAnimation : false,
				radius : ['70%', '80%'],
				labelLine : {
					normal : {
						show : false
					}
				},
				data : [{
					value : 0,
					itemStyle : {
						normal : {}
					}
				}, {
					value : 100,
					itemStyle : {
						normal : {
							opacity : 0
						},
					}
				}]
			}]

		}

		/*
		 设置 CPU 信息
		*/
		var setCpuInfo = function(vue, cpuInfo) {

			vue.$data.cpuTotalCount = cpuInfo['totalCount'];
			var usedPercent = cpuInfo['usedPercent'];
			vue.$data.cpuUsedPercent = usedPercent + '%';
			var targetOption = Object.assign( {}, sourceOption );
			targetOption['series'][0]['data'][0]['name'] = usedPercent + '%';
			targetOption['series'][0]['data'][0]['itemStyle']['normal']['color'] = '#6FBEBA';
			targetOption['series'][1]['data'][0]['value'] = usedPercent;
			targetOption['series'][1]['data'][0]['itemStyle']['normal']['color'] = '#6FBEBA';
			targetOption['series'][1]['data'][1]['value'] = 100 - usedPercent;
			cpuPieChart.setOption( targetOption );

		};

		/*
		 设置内存信息
		*/
		var setMemoryInfo = function(vue, memoryInfo) {
			vue.$data.memoryTotalSize = memoryInfo['totalSize'];
			vue.$data.memoryUsedSize = memoryInfo['usedSize'];
			var usedPercent = memoryInfo['usedPercent'];
			vue.$data.memoryUsedPercent = usedPercent + '%';
			var targetOption = Object.assign( {}, sourceOption );
			targetOption['series'][0]['data'][0]['name'] = usedPercent + '%';
			targetOption['series'][0]['data'][0]['itemStyle']['normal']['color'] = '#B85154';
			targetOption['series'][1]['data'][0]['value'] = usedPercent;
			targetOption['series'][1]['data'][0]['itemStyle']['normal']['color'] = '#B85154';
			targetOption['series'][1]['data'][1]['value'] = 100 - usedPercent;
			memoryPieChart.setOption( targetOption );
		};

		var setStorageInfo = function(vue, storageInfo) {
			vue.$data.storageTotalSize = storageInfo['totalSize'];
			vue.$data.storageSpareSize = storageInfo['totalSize'] - storageInfo['usedSize'];
			var usedPercent = storageInfo['usedPercent'];
			vue.$data.storageUsedPercent = usedPercent + '%';
			var targetOption = Object.assign( {}, sourceOption );
			targetOption['series'][0]['data'][0]['name'] = usedPercent + '%';
			targetOption['series'][0]['data'][0]['itemStyle']['normal']['color'] = '#32A0C5';
			targetOption['series'][1]['data'][0]['value'] = usedPercent;
			targetOption['series'][1]['data'][0]['itemStyle']['normal']['color'] = '#32A0C5';
			targetOption['series'][1]['data'][1]['value'] = 100 - usedPercent;
			storagePieChart.setOption( targetOption );

		};

		var setJvmInfo = function(vue, jvmInfo) {
			vue.$data.jvmTotalSize = jvmInfo['totalSize'];
			vue.$data.jvmFreeSize = jvmInfo['freeSize'];
			vue.$data.jvmMaxSize = jvmInfo['maxSize'];
			var usedPercent = Math.round( (jvmInfo['freeSize'] / jvmInfo['maxSize']) * 100 );
			var targetOption = Object.assign( {}, sourceOption );
			targetOption['series'][0]['data'][0]['name'] = (usedPercent) + '%';
			targetOption['series'][0]['data'][0]['itemStyle']['normal']['color'] = '#68217A';
			targetOption['series'][1]['data'][0]['value'] = usedPercent;
			targetOption['series'][1]['data'][0]['itemStyle']['normal']['color'] = '#68217A';
			targetOption['series'][1]['data'][1]['value'] = 100 - usedPercent;
			jvmPieChart.setOption( targetOption );

		};

		var setHealthInfo = function(vue, healthInfo) {

			var percent = healthInfo['percent'];

			var color = '#6FBEBA';
			if ( 90 >= percent && percent > 60 ) {
				color = '#37B0D8';
			} else if ( 60 >= percent ) {
				color = '#CA595C';
			}

			var sourceNetOption = {
				series : [{
					color : [color],
					amplitude : 5,
					name : 'Liquid Fill',
					waveLength : '80%',
					type : 'liquidFill',
					data : [{
						name : '健康指数',
						value : percent / 100
					}],
					radius : '100%',
					direction : 'left',
					outline : {
						show : false
					},

					backgroundStyle : {
						shadowColor : 'rgba(234, 136, 206, 0.8)',
						color : 'white'
					},

					shape : 'path://M0,19.589C0,19.59,12.542,0.133,86.237,0.092c73.639-0.039,86.236,19.497,86.236,19.497v224.215H0V19.589L0,19.589z',
					label : {
						normal : {
							formatter : function(c) {
								return c.value * 100;
							},
							areaStyle : {
								color : 'green'
							},
							textStyle : {
								fontSize : 30
							}
						}
					}
				}]
			}

			var targetOption = Object.assign( {}, sourceNetOption );
			healthFillChart.setOption( targetOption );

		}

		var netData = [];
		var initNetworkInfo = function(vue) {

			var now = new Date();
			now.setSeconds( now.getSeconds() - 20 );
			function randomData() {
				now.setSeconds( now.getSeconds() + 1 );
				var date = [now.getFullYear(), now.getMonth() + 1, now.getDate()].join( '/' ) + ' ' //
						+ [//
						now.getHours() < 10 ? '0' + now.getHours() : now.getHours(),//
						now.getMinutes() < 10 ? '0' + now.getMinutes() : now.getMinutes(),//
						now.getSeconds() < 10 ? '0' + now.getSeconds() : now.getSeconds()].join( ':' );
				return {
					value : [date, 0]
				}
			}

			for ( var i = 0; i < 20; i++ ) {
				netData.push( randomData() );
			}

			var option = {
				//symbolOffset : [80, 100],
				xAxis : {
					type : 'time',
					splitLine : {
						show : false
					}
				},
				yAxis : {
					type : 'value',
					boundaryGap : [0, '100%'],
					splitLine : {
						show : false
					}
				},
				series : [{
					name : '模拟数据',
					type : 'line',
					showSymbol : false,
					hoverAnimation : false,
					smooth : true,
					symbol : 'none',

					sampling : 'average',
					itemStyle : {
						normal : {
							color : 'rgb(255, 70, 131)'
						}
					},
					areaStyle : {
						normal : {
							color : new echarts.graphic.LinearGradient( 0, 0, 0, 1, [{
								offset : 0,
								color : 'rgb(255, 158, 68)'
							}, {
								offset : 1,
								color : 'rgb(255, 70, 131)'
							}] )
						}
					},
					data : netData
				}]
			};

			setTimeout( function() {
				networkLineChart.setOption( option );
			}, 100 );

		}
		var setNetworkInfo = function(vue, netInfo) {

			function getValueData() {
				var now = new Date();
				var date = [now.getFullYear(), now.getMonth() + 1, now.getDate()].join( '/' ) + ' ' //
						+ [now.getHours(), now.getMinutes(), now.getSeconds()].join( ':' );
				return {
					name : now.toString(),
					value : [date, netInfo['rxBytes']]
				}
			}

			netData.shift();
			netData.push( getValueData() );

			networkLineChart.setOption( {
				series : [{
					data : netData
				}]
			} );

		};

		var setHostInfo = function(vue) {
			
			
			var hostInfoUrl = "http://localhost:8888/basis/overview/queryHostInfoJsonp";//"http://localhost:8888/basis/overview/queryHostInfo";

			if ( hostInfoUrl == null || hostInfoUrl == "" ) {
				hostInfoUrl = urlManager.$modelUrls.$getUrl( 'http://localhost:8888/basis/overview/queryHostInfo.do' );
			}
			$.ajax({  
				  
			  dataType: 'jsonp',   
			  url: hostInfoUrl,  
			  success: function(responseData){ 
			  	console.log(responseData.data.cpuInfo);  
	            var hostInfo = responseData['data'];
				setCpuInfo( vue, hostInfo['cpuInfo'] );
				setMemoryInfo( vue, hostInfo['memoryInfo'] );
				setStorageInfo( vue, hostInfo['storageInfo'] );
				setJvmInfo( vue, hostInfo['jvmInfo'] );
				setNetworkInfo( vue, hostInfo['netInfo'] );
				setHealthInfo( vue, hostInfo['healthInfo'] ); 
			  }  
				  
			});    
		}

		// 使用 VueResource
		Vue.use( VueResource );
		new Vue( {
			el : '#overview-app',
			data : {

				cpuTotalCount : 0,
				jvmCpuTotalCount : 1,
				cpuUsedPercent : '0%',
				memoryTotalSize : 0,
				memoryUsedSize : 0,
				memoryUsedPercent : '0%',
				storageTotalSize : 0,
				storageSpareSize : 0,
				storageUsedPercent : '0%',
				jvmTotalSize : 0,
				jvmMaxSize : 0,
				jvmFreeSize : 0

			},
			created : function() {

				var vue = this;
				initNetworkInfo( vue );
				setHostInfo( vue );
				setInterval( function() {
					setHostInfo( vue );
					//setNetworkInfo( vue, networkPieChart );
				}, 3000 );

				// 
			}

		} );

		// 使用刚指定的配置项和数据显示图表。
		// 基于准备好的dom，初始化echarts实例
		var cpuPieChart = echarts.init( document.getElementById( 'cpu-pie-chart' ) );
		var memoryPieChart = echarts.init( document.getElementById( 'memory-pie-chart' ) );
		var storagePieChart = echarts.init( document.getElementById( 'storage-pie-chart' ) );
		var jvmPieChart = echarts.init( document.getElementById( 'jvm-pie-chart' ) );
		var healthFillChart = echarts.init( document.getElementById( 'health-fill-chart' ) );
		var networkLineChart = echarts.init( document.getElementById( 'network-line-chart' ) );
	</script>
</body>
</html>