<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8">
<title>应用部署管理</title>

<#include "../commons/page_css.ftl" />
<#include "../commons/page_js.ftl" />

<script type="text/javascript">
	function callback() {
	 	$("#searchButton").click();
	}
	$().ready( function() {
		$("#listTable .btn_examine").bind("click", function(){
			var id = $(this).attr("operatId");
			var title = $(this).attr("title");
			
			art.dialog.open('/jmx_log4j/view?id=' + id, {
				id: 'viewFrame',
				title: title,
				close: function () {}
			}, false);
		});
		
		$("#listTable .btn_effect").bind("click", function(){
			var id = $(this).attr("operatId");
			var title = $(this).attr("title");
			
			art.dialog.open('/jmx_log4j/edit?id=' + id, {
				id: 'viewFrame',
				title: title,
				close: function () {}
			}, false);
		});
		
		$("#listTable .btn_view").bind("click", function(){
			var id = $(this).attr("operatId");
			var title = $(this).attr("title");
			
			art.dialog.open('/jmx_view/view_monitor?id=' + id, {
				id: 'viewFrame',
				title: title,
				width: '100%',
    			height: '100%',
				close: function () {}
			}, false);
		});
		
		$("#startAlertAll").bind("click", function(){
		
			var chk_value =[]; 
			$('input[name="ids"]:checked').each(function(){ 
				chk_value.push($(this).val()); 
			}); 
			if(chk_value.length > 0) {
				art.dialog.open('/app_alert/alert_group_select?ids=' + chk_value, {
					id: 'viewFrame',
					title: '选择分组',
					close: function () {}
				}, false);
			}
			else {
				pop_warning("操作提示", "请选择应用！", false, function() {});
			}
			
		});
		
		$("#stopAlertAll").bind("click", function(){
			pop_warning("操作提示", "是否关闭报警？", true, function() {
						 $.ajax({
							type : "post",
							url : "/app_alert/alert_stops",
							dataType: "json",
							cache : false,
							data: $("#listForm").serialize(),
							success: function(data){
								console.log("return data of delete: %s", data.type);
								if(data.type == 'success') {
									pop_succeed("操作成功", "关闭成功。", function() {
										callback();
									}, false);
								}
								else {
									pop_error("操作失败", data.content,function() {
									} ,false);
								}
							}					
						});
						
			});
		});
	
	
		$("#logLevelAll").bind("click", function(){
			var ids= "";
            
             $("input[name='ids']:checked").each(function () {
                   ids += $(this).val() + ","
     		});

			art.dialog.open('/jmx_log4j/level_select?ids=' + ids, {
				id: 'logFrame',
				title: '批量修改日志',
				close: function () {}
			}, false);
			
		});
		
		$("#listTable .btn_up").bind("click", function(){
			var id = $(this).attr("operatId");
			var title = $(this).attr("title");
			
			art.dialog.open('command?command=restart&id=' + id, {
				id: 'restart',
				title: title,
				width: '100%',
    			height: '100%',
				close: function () {}
			}, false);
		});
		
		$("#listTable .btn_down").bind("click", function(){
			var id = $(this).attr("operatId");
			var title = $(this).attr("title");
			
			art.dialog.open('command?command=stop&id=' + id, {
				id: 'stop',
				title: title,
				width: '100%',
    			height: '100%',
				close: function () {}
			}, false);
		});
		
		$("#listTable .btn_online").bind("click", function(){
			var id = $(this).attr("operatId");
			var title = $(this).attr("title");
			
			art.dialog.open('command?command=restart&id=' + id, {
				id: 'restart',
				title: title,
				width: '100%',
    			height: '100%',
				close: function () {}
			}, false);
		});
		
		$("#restartCommandAll").bind("click", function(){
		
			var chk_value =[]; 
			$('input[name="ids"]:checked').each(function(){ 
				chk_value.push($(this).val()); 
			}); 
			if(chk_value.length > 0) {
				art.dialog.open('/app_info/batch_command?command=restart&ids=' + chk_value, {
					id: 'viewFrame',
					title: '批量重启',
					width: '100%',
    				height: '100%',
					close: function () {}
				}, false);
			}
			else {
				pop_warning("操作提示", "请选择应用！", false, function() {});
			}
			
		});
		
	});
</script>


</head>

<body>
<!-- start of con_right_main -->
<div class="con_right_main">

	<form id="listForm" action="list" method="post">
	<input type="hidden" id="orderField" name="orderField" value="${(so.orderField)!'info.id'}" />
	<input type="hidden" id="orderDirection" name="orderDirection" value="${(so.orderDirection)!'desc'}" />
	
	
    <!-- start of con_search -->
	<div class="con_search">
    	<span class="con_box_BL"></span>
        
        <!-- start of con_search_top -->
        <div class="con_search_top clearfix">
        	<div class="con_search_top_L left">
                <p>
                    <span class="group"><label>关键字：</label>
                    	<input name="keyword" class="c_input_text" type="text" realValue="关键字" value="${(se.keyword)!''}" style="width:100px;" />
                    </span>
                    <span class="group"><label>版本：</label>
                    	<input name="appVersion" class="c_input_text" type="text" realValue="版本" value="${(so.appVersion)!''}"  style="width:100px;" />
                    </span>
                    <span class="group"><label>环境：</label>
                    	<input name="confEnv" class="c_input_text" type="text" realValue="环境" value="${(so.confEnv)!''}"  style="width:100px;" />
                    </span>
                    <span class="group"><label>节点：</label>
                    	<select class="c_select required" id="nodeId" name="nodeId" style="width:150px;">
	                    	<option value="">请选择</option>
	                    	<#list NODE_INFO_LIST as node>
	                    		<option value="${node.id}" <#if so.nodeId?exists && so.nodeId == node.id>selected</#if>>${node.host}</option>
	                    	</#list>
	                 	</select>
                    </span>
                    <span class="group"><label>应用：</label>
                    	<select class="c_select required" id="clusterId" name="clusterId" style="width:150px;">
	                    	<option value="">请选择</option>
	                    	<#list APP_CLUSTER_LIST as group>
	                    		<option value="${group.id}" <#if so.clusterId?exists && so.clusterId == group.id>selected</#if>>${group.clusterName}</option>
	                    	</#list>
	                 	</select>
                    </span>
                    <span class="group"><label>日志级别：</label>
                    	<select class="c_select required" id="logLevel" name="logLevel" style="width:150px;">
	                    	<option value="">请选择</option>
	                    	<#list LOG_LEVEL_TYPES as type>
	                    		<option value="${type.getName()}" <#if so.logLevel?exists && so.logLevel == type.getName()>selected</#if>>${type.getName()}</option>
							</#list>
	                 	</select>
                    </span>
            		<span class="group"><a id="searchButton" href="javascript:;" class="btn_search">搜索</a></span>
                </p>
            </div>
        </div>
        <!-- end of con_search_top -->
        
        <span class="con_box_BR"></span>
    </div>
    <!-- end of con_search -->
    
	<div class="c_tipsA">截止到今日：已记录统计 <span class="red">${page.totalCount}</span> 个 </div>
    
    
    <!-- start of table_list -->
    <table id="listTable" class="table_list list">
        <tr>
        	<th style="line-height:0px; width:5%;" rowspan="2">序号</th>
        	<th style="line-height:0px; width:10%;" rowspan="2" class="sort" orderField="asc" name="info.app_name">应用名称</th>
        	<th style="line-height:0px; width:8%;" rowspan="2" class="sort" orderField="asc" name="info.node_id">所在节点</th>
        	<th style="line-height:0px; width:15%;" rowspan="2" class="sort" orderField="asc" name="info.install_path">安装目录</th>
        	<th style="line-height:0px; width:6%;" rowspan="2" class="sort" orderField="asc" name="info.app_version">版本</th>
        	<th style="line-height:0px; width:6%;" rowspan="2" class="sort" orderField="asc" name="info.conf_env">环境</th>
        	<th style="line-height:0px; width:6%;" rowspan="2" class="sort" orderField="asc" name="info.log_level">日志</th>
        	<th style="line-height:0px; width:10%;" colspan="3">端口</th>
        	<th style="line-height:0px; width:4%;" rowspan="2">状态</th>
        	<th style="line-height:0px; width:4%;" rowspan="2" class="sort" orderField="desc" name="config.is_alert">监控</th>
        	<th style="line-height:0px; width:10%;" rowspan="2" class="sort" orderField="asc" name="info.update_time">更新时间</th>
        	<!--<th style="line-height:0px; width:8%;" rowspan="2" class="sort" orderField="asc" name="info.create_time">创建时间</th>-->
			<th style="line-height:0px; width:20%;" rowspan="2">操作</th>
        </tr>
        <tr>
          <th style="line-height:0px;" class="sort" orderField="desc" name="info.service_port">SERVICE</th>
          <th style="line-height:0px;" class="sort" orderField="desc" name="info.web_port">WEB</th>
          <th style="line-height:0px;" class="sort" orderField="desc" name="info.jmx_port">JMX</th>
        </tr>
        <#list page.list as info>
        <tr class="even">
        	<td><input type="checkbox" name="ids" value="${info.id}" />${info.id}</td>
        	<td style="text-align:left;">${APP_CLUSTER_MAP[info.clusterId?string].clusterName}</td>
			<td>${NODE_INFO_MAP[info.nodeId?string].host}</td>
			<td style="text-align:left;">${(info.installPath)!}</td>
			<td>${(info.appVersion)!}</td>
			<td>${(info.confEnv)!}</td>
			<td>${(info.logLevel)!}</td>
			<td><#if (info.servicePort > 0)>${info.servicePort}<#else>-</#if></td>
			<td><#if (info.webPort > 0)>${info.webPort}<#else>-</#if></td>
			<td><#if (info.jmxPort > 0)>${info.jmxPort}<#else>-</#if></td>
			<td>
				<@appStatus appId = info.id>
					<#if isUp == true>
						<img src="/images/green.gif"/>
					<#else>
						<img src="/images/red.gif"/>
					</#if>
				</@appStatus>
			</td>
			<td>${info.isAlert?string('启动','关闭')}</td>
			<td>
				<#if info.updateTime?exists>
				${info.updateTime?string('yyyy-MM-dd HH:mm:ss')}
				<#else>-</#if>
			</td>
			<!--<td>${info.createTime?string('yyyy-MM-dd')}</td>-->
			<td>
				<!--<a class="btn_icon btn_edit"   href="javascript:;" operatId="${info.id}" title="编辑"></a>
                <a class="btn_icon btn_detail" href="javascript:;" operatId="${info.id}" title="详情"></a>-->
                <a class="btn_icon btn_delete" href="javascript:;" operatId="${info.id}" title="删除"></a>
                <@appStatus appId = info.id>
					<#if isUp == true>
						<br />
						<a class="btn_icon btn_examine" href="javascript:;" operatId="${info.id}" title="日志信息"></a>
						<a class="btn_icon btn_effect" href="javascript:;" operatId="${info.id}" title="修改日志级别"></a>
						<a class="btn_icon btn_view" href="javascript:;" operatId="${info.id}" title="查看监控[${APP_CLUSTER_MAP[info.clusterId?string].clusterName},${NODE_INFO_MAP[info.nodeId?string].host}:${info.jmxPort}]"></a>
						
						<a class="btn_icon btn_online" href="javascript:;" operatId="${info.id}" title="重启"></a>
						<a class="btn_icon btn_down" href="javascript:;" operatId="${info.id}" title="停止"></a>
						
					<#else>
						<a class="btn_icon btn_up" href="javascript:;" operatId="${info.id}" title="启动"></a>
					</#if>
				</@appStatus>

				<!--
								
				.btn_edit{ background-position:0 0;}
				.btn_detail{ background-position:0 -50px;}
				.btn_online{ background-position:0 -100px;}
				.btn_delete{ background-position:0 -150px;}
				.btn_up{ background-position:0 -200px;}
				.btn_down{ background-position:0 -250px;}
				.btn_enable{ background-position:0 -300px;}
				.btn_disabled{ background-position:0 -350px;}
				.btn_empty{ background-position:0 -400px;}
				.btn_pass{ background-position:0 -450px;}
				.btn_refuse{ background-position:0 -500px;}
				.btn_add{ background-position:0 -550px;}
				.btn_view{ background-position:0 -600px;}
				.btn_offline{ background-position:0 -650px;}
				.btn_effect{ background-position:0 -700px;}
				.btn_examine{ background-position:0 -750px;}
				
				-->
			</td>
        </tr>
        </#list>
        
    </table>
    <!-- end of table_list -->			
	
	<#if (page.list?size > 0)>
    
    	<!-- start of table_bottom -->
	    <div class="table_bottom clearfix">
	    	<div class="table_bottom_checkbox left">
	    		<input id="selectAll" name="" type="checkbox" value="">
	    		<a id="deleteAll" class="btn" href="javascript:void(0);">删除选中</a>
	    		<a id="startAlertAll" class="btn" href="javascript:void(0);">启动报警</a>
	    		<a id="stopAlertAll" class="btn" href="javascript:void(0);">关闭报警</a>
	    		<a id="logLevelAll" class="btn" href="javascript:void(0);">日志级别</a>
	    		<a id="restartCommandAll" class="btn" href="javascript:void(0);">应用重启</a>
	    	</div>
	        
	         <!-- start of 分页 -->
	   		<@paging pageNumber = page.pageNo totalPages = page.totalPage>
				<#include "../commons/pager.ftl">
			</@paging>
	        <!-- end of 分页 -->
	    </div>
	    <!-- end of table_bottom -->
			
	<#else>
		<div class="noRecord">没有找到任何记录!</div>
	</#if>
	
    </form>
</div>
<!-- end of con_right_main -->
</body>
</html>