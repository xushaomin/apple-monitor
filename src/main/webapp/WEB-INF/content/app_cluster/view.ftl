<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8">
<title>集群详情</title>
<#include "../commons/page_css.ftl" />
<#include "../commons/page_js.ftl" />


</head>

<body>
	
    <div id="auditTab" class="pop_main" style="width:600px;border: 0px solid;">

       <div class="pop_information_mod">
            <ul class="pop_list merchant_type_add">
            
                	<li class="clearfix">
                		<label for="systemName require" class="tit">名称：<span class=" red">*</span></label>
                		<span>${(info.clusterName)!}</span>
               		</li>

                	<li class="clearfix">
                		<label for="systemCode" class="tit">描述：<span class=" red">*</span></label>
                		<span>${(info.clusterDesc)!}</span>
                	</li>
                	
                	<li class="clearfix">
                		<label for="systemCode" class="tit">是否集群：<span class=" red">*</span></label>
                		<span>
                			<#if info.isCluster == 1>是</#if>
                			<#if info.isCluster == 0>否</#if>
                		</span>
                	</li>
                	
                	<li class="clearfix">
                		<label for="systemCode" class="tit">应用数量：<span class=" red">*</span></label>
                		<span>${(info.appNum)!}</span>
                	</li>

					<li class="clearfix">
                		<label for="platform" class="tit">集群状态：<span class=" red">*</span></label>
               			<#if info.state == 1>启用</#if>
                		<#if info.state == 0>停用</#if>
                	</li>
                	
                	<li class="clearfix">
                		<label for="platform" class="tit">监控状态：<span class=" red">*</span></label>
                		<span>
               			<@clusterStatus clusterId = info.id>
							<#if isUp == true>
								<img src="/images/green.gif"/>
							<#else>
								<img src="/images/red.gif"/>
							</#if>
						</@clusterStatus>
						</span>
                	</li>
            
                	<li class="clearfix">
	                    <label for="systemDesc" class="tit">备注：</label>
	                    <span class="textarea_show">
	                    	${(info.remark)!}
	                    </span>
                	</li>
            </ul>

        </div>

    </div>
    <div class="pop_footer">
        <a id="btn_pop_submitB" class="btn_pop_submitB" href="javascript:void(0)" onclick="art.dialog.close();">取消</a>
    </div>




</body>
</html>