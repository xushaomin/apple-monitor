<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8">
<title>分组管理</title>
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
    <div id="auditTab" class="pop_main" style="width:500px;border: 0px solid;">

       <div class="pop_information_mod">

					 	<!-- start of table_list -->
					    <table id="listTable" class="table_list list">
					        <tr>
					        	<th width="5%">序号</th>
					        	<th width="10%">名称</th>
								<th width="10%">操作</th>
					        </tr>
					        <#list ALL_CONTACT_LIST as all>
					        <tr class="even">
					        	<td><input type="checkbox" name="ids" value="${all.id}" />${all.id}</td>
					        	<td>${(all.name)!}</td>
								<td>
									<a class="btn_icon btn_edit"   href="javascript:;" operatId="${all.id}" title="编辑"></a>
					           		<a class="btn_icon btn_delete" href="javascript:;" operatId="${all.id}" title="删除"></a>
					           		<a class="btn_icon btn_online" href="javascript:;" operatId="${all.id}" title="联系人"></a>
								</td>
					        </tr>
					        </#list>
					        
					    </table>
					    <!-- end of table_list -->			
    

        </div>

    </div>
    <div class="pop_footer">
        <a id="btn_pop_submitA" class="btn_pop_submitA" href="javascript:void(0)">确定</a>
        <a id="btn_pop_submitB" class="btn_pop_submitB" href="javascript:void(0)" onclick="art.dialog.close();">取消</a>
    </div>

</form>


</body>
</html>