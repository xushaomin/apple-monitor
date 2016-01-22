<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8">
<title>集群管理</title>

<#include "../commons/page_css.ftl" />
<#include "../commons/page_js.ftl" />
<script language="javascript" type="text/javascript" src="/js/My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript">
	function callback() {
	 	$("#searchButton").click();
	}
	
	$().ready( function() {
		$("#listTable .btn_online").bind("click", function(){
			var id = $(this).attr("operatId");
			var title = $(this).attr("title");
			
			art.dialog.open('/app_info/list_for_cluster?clusterId=' + id, {
				id: 'viewFrame',
				title: title,
				close: function () {}
			}, false);
		});
	});
	
</script>


</head>

<body>
<!-- start of con_right_main -->
<div class="con_right_main">

	<form id="listForm" action="list" method="post">
	
		<select class="c_select required" id="clusterId" name="clusterId" style="width:150px;">
	  		<option value="">请选择</option>
	     		<#list ALERT_GROUP_LIST as group>
	       			<option value="${group.id}">${group.name}</option>
	      		</#list>
		</select>
	
    </form>
</div>
<!-- end of con_right_main -->
</body>
</html>