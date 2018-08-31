<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8">
<title>添加联系人</title>
<#include "../commons/page_css.ftl" />
<#include "../commons/page_js.ftl" />

<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");
		
	// 表单验证
	$inputForm.validate({
		rules: {
			"name": {
				required: true,
				remote: "check_name"
			}
		},
		messages: {
			"name": {
				required: "必填",
				remote: "联系人已存在"
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
<form id="inputForm" method="post" action="save">
	
    <div id="auditTab" class="pop_main" style="width:600px;border: 0px solid;">

       <div class="pop_information_mod">
            <ul class="pop_list merchant_type_add">
            
                	<li class="clearfix">
                		<label for="clusterName require" class="tit">名称：<span class=" red">*</span></label>
                		<input class="c_input_text required" type="text" style="width:200px;" name="name" value="${(info.name)!}" realValue="请输入名称" maxlength="200" />
               		</li>

                	<li class="clearfix">
                		<label for="clusterName require" class="tit">手机：<span class=" red">*</span></label>
                		<input class="c_input_text required" type="text" style="width:200px;" name="mobile" value="${(info.mobile)!}" realValue="请输入手机" maxlength="200" />
               		</li>
               		
               		<li class="clearfix">
                		<label for="clusterName require" class="tit">邮件：<span class=" red">*</span></label>
                		<input class="c_input_text required email" type="text" style="width:200px;" name="email" value="${(info.email)!}" realValue="请输入邮件" maxlength="200" />
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