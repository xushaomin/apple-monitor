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
<style type="text/css">
.threadTitle {
	margin: 0px;
	padding: 1px 0px;
	height: 20px;
	color: #8000ff;
}

#detailPnl {
	display: none;
}

.infoBottom {
	margin: 0px;
}
</style>
</head>
<body>
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container">
			<ul class="nav navbar-nav">
				<li role="presentation"><a href="view_monitor?id=${app.appId}">实时监控</a></li>
				<li role="presentation"><a href="view_runtime?id=${app.appId}">系统参数</a></li>
				<li role="presentation" class="active"><a href="view_thread?id=${app.appId}">线程信息</a></li>				
			</ul>
		</div>
	</nav>
	<div class="container">
		<div class="row">
			<div class="col-sm-10">
				<div class="row">
					<div class="col-sm-12 nomargin" id="top">
						<div class="col-sm-4" id="timemenu"></div>
						<div class="col-sm-4">
							<label class="label label-success" id="app"></label>
						</div>
						<div class="col-sm-4  text-right">
							<button class="btn btn-danger btn-sm"
								onclick="ThreadServer.checkDeadLock(this)">死锁检测</button>
							<button class="btn btn-danger btn-sm"
								onclick="ThreadServer.dumpThead(this)">Dump线程</button>
						</div>
					</div>
					<div class="col-sm-12">
						<div class="panel panel-info infoBottom">
							<div class="panel-heading" id="thinfoTitle"></div>
							<div class="panel-body" id="threadCount">
								<div class="container-fluid" id="context"></div>
							</div>
						</div>
					</div>
					<div class="col-sm-12 tableTitile" id="tableHead">
						<div class="col-sm-6 label label-default">名称</div>
						<div class="col-sm-2 label label-default">状态</div>
						<div class="col-sm-2 label label-default">CPU(ms)</div>
						<div class="col-sm-2 label label-default">操作</div>
					</div>
					<div class="col-sm-12" id="tableBody"></div>
					<div class="col-sm-12 navbar-fixed-bottom">
						<div class="container">
							<div class="row  text-center">
								<span class="badge" onclick="ThreadServer.showHiddenDetail()">Close/Open</span>
							</div>
							<div class="row">
								<div class="col-lg-3"></div>
								<div class="col-lg-8">
									<div class="panel panel-info infoBottom">
										<div class="panel-body" id="detailPnl">
											<textarea class="col-lg-12" id="detailBody" rows="10"></textarea>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
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
			ThreadServer.loadData(app);
		});
	</script>
</body>
</html>