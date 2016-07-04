<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8">
<title>部署管理</title>

<#include "../commons/page_css.ftl" />
<#include "../commons/page_js.ftl" />

<script type="text/javascript"> 
            if (!window.WebSocket) {
                alert("WebSocket not supported by this browser!");
            } 
             
			function display() {
            	var valueLabel = $("#valueLabel");
                valueLabel.innerHTML = "";
                var ws = new WebSocket("${websocketUrl}/push/command");
                ws.onmessage = function(evt) {
                    valueLabel.append(evt.data + "<br>");
                    GoBottom();
                }; 

                ws.onclose = function() {
                };

                ws.onopen = function() {
                    ws.send("taskId=${taskId}");
                };
            } 
            
            
            
			var currentPosition,timer; 
			function GoBottom(){ 
				timer = setInterval("runToBottom()",50); 
			} 
			function runToBottom(){ 
				currentPosition = document.documentElement.scrollTop || document.body.scrollTop; 
				currentPosition += 30; 
				if(currentPosition<document.body.scrollHeight && (document.body.clientHeight + document.body.scrollTop < document.body.scrollHeight)) 
				{ 
					//window.scrollTo(0,currentPosition); 
					//alert(document.documentElement.clientHeight + " " + document.documentElement.scrollTop + " " + document.documentElement.scrollHeight + "#" +document.body.clientHeight + " " + document.body.scrollTop + " " + document.body.scrollHeight); 
					document.body.scrollTop = currentPosition; 
				} 
				else 
				{ 
					document.body.scrollTop = document.body.scrollHeight; 
					clearInterval(timer); 
				} 
			}
</script> 


</head>

<body onload="display();"> 
	
    <div id="auditTab" class="pop_main" style="width:96%;border: 0px solid;">

       <div class="pop_information_mod" id="valueLabel">

        </div>

    </div>

</body>
</html>