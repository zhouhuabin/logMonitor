
var base64 = new Base64();

//默认报警时间段个数
var emailAndSmsAlarmNum=1;


//模板字段英文
var MODULE_TYPE_FIELD="";
//模板字段中文
var MODULE_TYPE_FIELD_ZN="";
//模板统计字段英文
var MODULE_TYPE_STATIC="";
//模板默认报警时间设置
var MODULE_TYPE_AlARMTIME="";


function sub_addModuleType()
{
	var fields=MODULE_TYPE_FIELD.split(",");	
	for(var i=0;i<fields.length-1;i++)
	{
		var field=fields[i];
		var FIELD_ZN=$("#"+field).val();
		MODULE_TYPE_FIELD_ZN+=FIELD_ZN+",";
		if($("#"+field+"ck").attr("checked")==true)
		{
			MODULE_TYPE_STATIC+=field+",";
		}
	}
	for(var j=1;j<=emailAndSmsAlarmNum;j++)
	{
		var timeMail=0;
		var timeSms=0;
		var timeStart=$("#timeStart"+j).val();
		var timeEnd=$("#timeEnd"+j).val();
		if($("#timeMail"+j).attr("checked")==true)
		{
			timeMail=1;
		}
		if($("#timeSms"+j).attr("checked")==true)
		{
			timeSms=1;
		}
		var alarmTime=timeStart+"-"+timeEnd+","+timeMail+","+timeSms+";";
		MODULE_TYPE_AlARMTIME+=alarmTime;
	}
	var Module_TYPE_NAME=$("#Module_Type_Name").val();
	var threadInterval=$("#threadInterval").val()
	
	var parm="method=add&threadInterval="+threadInterval+"&Module_TYPE_NAME="+Module_TYPE_NAME+"&MODULE_TYPE_FIELD="+MODULE_TYPE_FIELD+"&MODULE_TYPE_FIELD_ZN="+MODULE_TYPE_FIELD_ZN+"&MODULE_TYPE_STATIC="+MODULE_TYPE_STATIC+"&MODULE_TYPE_AlARMTIME="+MODULE_TYPE_AlARMTIME;
	var rel=AjaxRequest("ModuleType.action",parm,null);
	if(rel.result>0)
		document.location.href='moduleTypeConfig.html';
	else {
		$.messager.alert('错误',rel.msg,'error');
	}
		
		
}

function fileScan()
{
	var parm="encoding="+$('#encoding').val()+"&logurl="+base64.encode($('#logurl').val());
	
	$.ajax({
	     url: "LogVluesScan.action",
	     type: "POST",
		 async:false,
	     data: parm,
	     dataType:null||"json",
	     success: function(data, textStatus)
	     {
	    	var rel=data;
	    	 if(rel.erro_)
	    		{
	    			$('#fieldDefinition').append(rel.erro_);
	    		}
	    		else
	    		{
	    			for (var pro in rel)
	    			{
	    				var prostr=pro.split("|")[0];
	    				var protype=pro.split("|")[1];
	    				
	    				MODULE_TYPE_FIELD+=prostr+","
	    				var tr='<tr>'+
	    				'<td>'+prostr+'</td>'+
	    				'<td><input class="text easyui-validatebox" id="'+prostr+'" type="text" required="true" validType="length[1,25]" missingMessage="请输入'+prostr+'字段中文名称" invalidMessage="渠道名称长度为1-25个中英文字符"/></td>'+
	    				'<td><input type="checkbox" id="'+prostr+'ck" value ="1" title="'+prostr+'"></td>'+
	    				'</tr>';
	    				$('#fieldDefinition').append(tr);  
	    			}
	    			
	    			testForm = $('#moduleTypeForm');  
	    			//初始化表单中的验证器  
	    			$('input[type!="hidden"],select,textarea',testForm ).each(function(){  
	    			    $(this).validatebox();  
	    			});
	    			
	    		}
	    		$('#logurlDIV').hide();
	    		$('#showContent').show();
	     },
	     error: function()
	     {
	    	 $.messager.alert('错误',"请求服务器失败！",'error');
	     }
	 });
	
	
	
}



function addDateEmailAndSmsAlarm(){
	emailAndSmsAlarmNum++;
	var tr='<tr>'+
	'<td><input id="timeStart'+emailAndSmsAlarmNum+'" type="text" value="01:30:30" required="true" missingMessage="请选择时间"/></td>'+
	'<td><input id="timeEnd'+emailAndSmsAlarmNum+'" type="text" value="01:30:30" required="true" missingMessage="请选择时间"/></td>'+
	'<td><input type="checkbox" id="timeMail'+emailAndSmsAlarmNum+'" value ="1" ></td>'+
	'<td><input type="checkbox" id="timeSms'+emailAndSmsAlarmNum+'" value ="1" ></td>'+
'</tr>';
	$('#emailAndSmsAlarm').append(tr);  
}


function AjaxRequest(url,parm,dataType){
	 var result=null;
	 $.ajax({
		     url: url,
		     type: "POST",
			 async:false,
		     data: parm,
		     dataType:dataType||"json",
		     success: function(data, textStatus)
		     {
		    	 result=data;
		     },
		     error: function()
		     {
		    	 result="erro";
		     }
		 });
	 	return result;
}




