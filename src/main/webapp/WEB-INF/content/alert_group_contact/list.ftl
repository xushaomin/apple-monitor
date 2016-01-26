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
	
	<#if EXIST_CONTACT_LIST??>
	<#list EXIST_CONTACT_LIST as contact>
		$("#contact_${contact.id}").attr("checked",'true');
	</#list>
	</#if>
});
</script>

</head>

<body>
<form id="inputForm" method="post" action="update">
	<input id="groupId" name="groupId" type="hidden" value="${GROUP_ID}" />
    <div id="auditTab" class="pop_main" style="width:600px;border: 0px solid;">

       <div class="pop_information_mod">
            <ul class="pop_list merchant_type_add">
                	<li class="clearfix">
                		<label for="contactIds require" class="tit">联系人：<span class=" red">*</span></label>
                		<#list ALL_CONTACT_LIST as all>
                			<input id="contact_${all.id}" name="contactIds" type="checkbox" value="${all.id}" />${all.name}
        				</#list>
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