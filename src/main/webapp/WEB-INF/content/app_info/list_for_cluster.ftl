<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8">
<title>应用部署管理</title>

<#include "../commons/page_css.ftl" />
<#include "../commons/page_js.ftl" />
<script language="javascript" type="text/javascript" src="/js/My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript">
	function callback() {
	 	$("#searchButton").click();
	}
</script>


</head>

<body>
<!-- start of con_right_main -->
<div class="con_right_main">

	<form id="listForm" action="list" method="post">    
	<div class="c_tipsA">截止到今日：已记录统计 <span class="red">${list?size}</span> 个 </div>
    
    <!-- start of table_list -->
    <table id="listTable" class="table_list list">
        <tr>
        	<th width="5%">序号</th>
        	<th width="10%">应用名称</th>
        	<th width="12%">所在节点</th>
        	<th width="10%" class="sort" orderField="asc" name="totalSumNumber">端口(DUBBO/WEB/JMX/SOCKET)</th>
        	<th width="6%" class="sort" orderField="asc" name="totalSumNumber">状态</th>
        	<th width="8%">最新发布</th>
        </tr>
        <#list list as info>
        <tr class="even">
        	<td><!--<input type="checkbox" name="ids" value="${info.id}" />-->${info.id}</td>
        	<td style="text-align:left;">${APP_CLUSTER_MAP[info.clusterId?string].clusterName}</td>
			<td>${NODE_INFO_MAP[info.nodeId?string].host}</td>
			<td><#if (info.servicePort > 0)>${info.servicePort}/<#else>-/</#if><#if (info.webPort > 0)>${info.webPort}/<#else>-/</#if><#if (info.jmxPort > 0)>${info.jmxPort}/<#else>-/</#if>-
			</td>
			<td>
				<@appStatus appId = info.id>
					<#if isUp == true>
						<img src="/images/green.gif"/>
					<#else>
						<img src="/images/red.gif"/>
					</#if>
				</@appStatus>
			</td>
			<td>
				<#if info.updateTime?exists>
				${info.updateTime?string('yyyy-MM-dd')}
				<#else>-</#if>
			</td>
        </tr>
        </#list>
        
    </table>
    <!-- end of table_list -->			
	
	
    </form>
</div>
<!-- end of con_right_main -->
</body>
</html>