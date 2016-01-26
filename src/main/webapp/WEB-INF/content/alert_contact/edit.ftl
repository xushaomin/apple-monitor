<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8">
<title>编辑集群</title>
<#include "../commons/page_css.ftl" />
<#include "../commons/page_js.ftl" />

<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");
		
	// 表单验证
	$inputForm.validate({
		rules: {
			"clusterName": {
				required: true,
				remote: "check_clusterName?oldClusterName=${(info.clusterName)!}"
			}
		},
		messages: {
			"clusterName": {
				remote: "集群已存在"
			}
		},
		submitHandler:function(form){
            form.submit();
        }
	});
	
	$("#btn_pop_submitA").click(function(){
 		$inputForm.submit();
	});
	
	
});
</script>

</head>

<body>
<form id="inputForm" method="post" action="update">
	<input type="hidden" name="id" value="${info.id}" />
	<input type="hidden" name="disorder" value="0" />
	
    <div id="auditTab" class="pop_main" style="width:600px;border: 0px solid;">

       <div class="pop_information_mod">
            <ul class="pop_list merchant_type_add">
            
                	<li class="clearfix">
                		<label for="clusterName require" class="tit">名称：<span class=" red">*</span></label>
                		<input class="c_input_text required" type="text" style="width:200px;" name="clusterName" value="${(info.clusterName)!}" realValue="请输入名称" maxlength="200" />
               		</li>

                	<li class="clearfix">
                		<label for="clusterDesc" class="tit">简述：<span class=" red">*</span></label>
                		<input class="c_input_text required" type="text" style="width:200px;" name="clusterDesc" value="${info.clusterDesc}" realValue="请输入描述" maxlength="200" />
                	</li>

					<li class="clearfix">
                		<label for="platform" class="tit">状态：<span class=" red">*</span></label>
               			<input type="radio" name="state" value="1" <#if info.state == 1> checked="checked" </#if> />启用
                		<input type="radio" name="state" value="0" <#if info.state == 0> checked="checked" </#if> />停用
                	</li>
            
                	<li class="clearfix">
	                    <label for="remark" class="tit">备注：</label>
	                    <span class="textarea_show">
	                    	<textarea class="c_textarea wordCount" name="remark" cols="" id="remark" rows="" maxlength="100" showCount="remarkLen">${(info.remark)!}</textarea>
	                    	<span class="in_num_text" style="color:red;" id="remarkLen">0/100</span>
	                    	<span class="in_num_text" >/100</span>
	                    </span>
                	</li>
            </ul>

        </div>

    </div>
    <div class="pop_footer">
        <a id="btn_pop_submitA" class="btn_pop_submitA" href="javascript:void(0)">确定</a>
        <a id="btn_pop_submitB" class="btn_pop_submitB" href="javascript:void(0)" onclick="art.dialog.close();">取消</a>
    </div>

</form>


</body>
</html>