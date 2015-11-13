<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Monitor</title>
<!--
[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
<![endif]
-->
<link href="./favicon.ico" type="image/x-icon" rel=icon>
<script src="/js/ie-emulation-modes-warning.js"></script>
<link href="/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container">
			<ul class="nav navbar-nav">
				<li role="presentation"><a href="view_monitor?id=${app.appId}">实时监控</a></li>
				<li role="presentation" class="active"><a href="view_runtime?id=${app.appId}">系统参数</a></li>
				<li role="presentation"><a href="view_thread?id=${app.appId}">线程信息</a></li>
			</ul>
		</div>
	</nav>
	<div class="container">
		<div class="row ">
			<div class="col-sm-10">
				<div class="row">
					<label class="label label-success" id="app"></label> <span
						class="badge" id="title">启动时间</span>
				</div>
				<div class="row tableTitile" id="tableHead">
					<div class="col-lg-4 label label-default">键</div>
					<div class="col-lg-8 label label-default">值</div>
				</div>
				<div class="row" id="vminfoTbl">
					<div class="container-fluid" id="context"></div>
				</div>
			</div>
		</div>
	</div>
	<script src="/js/jquery-1.11.2.min.js"></script>
	<script src="/js/bootstrap.min.js"></script>
	<script src="/js/monitor.min.js"></script>
	<script src="/js/highcharts.js"></script>
	<script src="/js/bootstrap-treeview.js"></script>
	<script type="text/javascript">
		$(function() {
			app = '${app.appId}';
			updateTitle('${(app.name)!}','${(app.host)!}','${(app.port)!}');
			RuntimeServer.loadData('${app.appId}');
		});
	</script>
</body>
</html>