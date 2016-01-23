<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8">
<title>选择分组</title>
<link rel="stylesheet" type="text/css" href="/css/common.css" />
<link rel="stylesheet" type="text/css" href="/css/style.css" />

<script type="text/javascript" src="/js/jquery-1.12.0.min.js"></script>

<script type="text/javascript" src="/js/jquery.pager.js"></script>
<script type="text/javascript" src="/js/jquery.validate.js"></script>
<script type="text/javascript" src="/js/jquery.tools.js"></script>

<script type="text/javascript" src="/js/common.js"></script>
<script type="text/javascript" src="/js/list.js"></script>
<script type="text/javascript" src="/js/input.js"></script>
<script type="text/javascript" src="/js/admin.js"></script>

<script type="text/javascript" src="/js/prompt.js"></script>
<script type="text/javascript" src="/js/artDialog/artDialog.source.js?skin=glsx"></script>
<script type="text/javascript" src="/js/artDialog/plugins/iframeTools.source.js"></script>
<script type="text/javascript" src="/js/pop_all.js"></script>

</head>

<body>
<form id="inputForm" method="post" action="/app_alert/alert_group_save">
	
    <div id="auditTab" class="pop_main" style="border:none;height:58px;">
	   	<input type="hidden" name="ids" value="${IDS}" />	
	   
       <div class="pop_grouprmation_mod">
            <ul class="search_list_n clearfix">
            		
            	<li class="clear">
            		<label class="n"><span class="red">*</span> 分组：</label>
            		<select class="c_input_text_n required" name="groupId" id="groupId">
						<option value="">选择分类</option>
						<#list ALERT_GROUP_LIST as group>
						<option value="${group.id}">${group.name}</option>
						</#list>
					</select>
            	</li>
            	
            </ul>

        </div>

    </div>
    <div class="pop_footer">
        <a id="btn_pop_submitA" class="btn_pop_submitA" href="javascript:void(0)">确定</a>
        <a id="btn_pop_submitB" class="btn_pop_submitB" href="javascript:void(0)" onclick="art.dialog.close();">取消</a>
    </div>


</form>

 

<script>
    var $inputForm = $("#inputForm");
		
	// 表单验证
	$inputForm.validate({
		rules: {
			
		},
		messages: {
			
		},
		submitHandler:function(form){
            form.submit();
        }
	});
	
	$("#btn_pop_submitA").click(function(){
 		$inputForm.submit();
	});
</script>

</body>
</html>