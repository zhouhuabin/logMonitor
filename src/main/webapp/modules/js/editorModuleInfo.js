



var base64 = new Base64();
var emailAndSmsAlarmNum=0;
initModuleTypeSelect();

$.ajax({
    url: "ModuleInfo.action",
    type: "POST",
	 async:false,
    data: "method=detail&module_type_id="+getQueryString("module_Type_Id")+"&module_id="+getQueryString("module_ID")+"&radom="+Math.random(),
    dataType:"json",
    success: function(data, textStatus)
    {
   	  initForm(data);
    },
    error: function()
    {
    	$.messager.alert('请求服务器失败',rel.msg,'error');
    }
});





function initForm(obj)
{
	$('#cityName').val(obj.cityName);
	$('#module_Type_Id').val(obj.module_Type_Id);
	$('#Module_Desc').val(obj.module_Desc);
	$('#url').val(base64.decode(obj.url));
	$('#encoding').val(obj.encoding);
	$('#errorValve').val(obj.errorValve);
	$('#timeValue').val(obj.timeValue);
	$('#ScanState').val(obj.scanState);
	innitAlarmTime(obj.module_Info_AlarmTime);
	var moduleType=AjaxRequest("ModuleType.action","method=list&Module_Type_Id="+obj.module_Type_Id,null);
	var field=moduleType.module_Type_Field;
	var	field_zn=moduleType.module_Type_Field_Zn;
	innitSoftAlarmFiled(field,field_zn);
	
	innitAnalysisAlarm(base64.decode(obj.alarmCondition));
}



//所有select选项改变时依赖此方法
$("select").change(function(){
	
	if($(this).attr("id")=="module_Type_Id")
	{
		var moduleTypeid=$(this).val();
		var moduleType=AjaxRequest("ModuleType.action","method=list&Module_Type_Id="+moduleTypeid,null);
		var typeId=moduleType.module_Type_Id;
		var alarmtime=moduleType.module_type_alarmtime;
		var field=moduleType.module_Type_Field;
		var	field_zn=moduleType.module_Type_Field_Zn;
		innitAlarmTime(alarmtime);
		innitSoftAlarmFiled(field,field_zn);
	}
})


function initModuleTypeSelect()
{
	var moduleTypeList=AjaxRequest("ModuleType.action","method=list",null).rows;
	
	for(var i=0;i<moduleTypeList.length;i++)
	{
		var moduleType=moduleTypeList[i];
		var typeId=moduleType.module_Type_Id;
		var typename=moduleType.module_Type_Name;
		$("#module_Type_Id").append('<option value="'+typeId+'">'+typename+'</option>');
	}
}

//取得软分析报警字符串
function getAlarmStr()
{
	var MODULE_INFO_AlARMTIME="";
	
	for(var j=0;j<emailAndSmsAlarmNum;j++)
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
		MODULE_INFO_AlARMTIME+=alarmTime;
	}
	return MODULE_INFO_AlARMTIME;
}

//模块软分析报警信息载入
function innitAnalysisAlarm(Condition){
	
	AnalysisAlarm=Condition.split(";").length;
	for(var i=0;i<Condition.split(";").length;i++)
	{
		var alarmCondition=Condition.split(";")[i];
		var con="";
		if(alarmCondition.contains(">"))
			con=">";
		if(alarmCondition.contains("=="))
			con="==";
		if(alarmCondition.contains("<"))
			con="<";
		var pro=split=alarmCondition.split(con)[0];
		var val=split=alarmCondition.split(con)[1];
		if(i==0)
		{
			$("#analysisPRO0").val(pro);
			$("#analysisCON0").val(con);
			$("#analysisCONVALUE0").val(val);
			continue;
		}
		else
			{
				putAnalysicTr(i,pro,con,val);
			}
	}
	
	//初始化表单中的验证器  
	$('input[type!="hidden"],select,textarea',$('#addModuleInfoform') ).each(function(){  
	    $(this).validatebox();
	});
}


function putAnalysicTr(AnalysisAlarm,pro,con,val)
{
	var tr='<tr class="addAnalysisAlarm">'+	
	'<td>'+
	'<select id="analysisPRO'+AnalysisAlarm+'" style="border:1px solid #8bb3e4;width:150px; float:left; height:22px;">'+
			$("#analysisPRO0").html()+
                '</select>'+
	'</td>'+
	'<td>'+
 			'<select id="analysisCON'+AnalysisAlarm+'" style="border:1px solid #8bb3e4;width:150px; float:left; height:22px;">'+
                	'<option value="-1">请选择 </option>'+
                    '<option value=">">大于</option>'+
                    '<option value="==">等于</option>'+
                    '<option value="<">小于</option>'+
                '</select>'+
    '</td>'+
    
	'<td><input onblur="" class="text easyui-validatebox" id="analysisCONVALUE'+AnalysisAlarm+'" type="text" required="true" validType="length[1,25]" missingMessage="请输入软分析值" invalidMessage="渠道名称长度为1-25个中英文字符"/></td>'+
	'</tr>';
	$('#analysisAlarm').append(tr);
	
	$("#analysisPRO"+AnalysisAlarm).val(pro);
	$("#analysisCON"+AnalysisAlarm).val(con);
	$("#analysisCONVALUE"+AnalysisAlarm).val(val);
}
//select输入moduleTypeId加载默认报警
function innitAlarmTime(alarmtimeStr)
{
			var alarm =alarmtimeStr.split(";")
			emailAndSmsAlarmNum=alarm.length-1;
			if(emailAndSmsAlarmNum>0)
				$("tr.alarmtr").remove();
			for(var j=0;j<emailAndSmsAlarmNum;j++)
			{
				var timeAlarm=alarm[j].split(",");
				var start=timeAlarm[0].split("-")[0];
				var end=timeAlarm[0].split("-")[1];
				var email=timeAlarm[1];
				var sms=timeAlarm[2];
				var alarmTr='<tr class="alarmtr">'+
				'<td><input id="timeStart'+j+'" type="text" value="'+start+'" required="true" missingMessage="请选择时间"/></td>'+
				'<td><input id="timeEnd'+j+'" type="text" value="'+end+'" required="true" missingMessage="请选择时间"/></td>'+
				'<td><input type="checkbox" id="timeMail'+j+'" value ="1" '+(email==1?'checked':'')+'></td>'+
				'<td><input type="checkbox" id="timeSms'+j+'" value ="1" '+(sms==1?'checked':'')+'></td>'+
			'</tr>';
				$("#emailAndSmsAlarm").append(alarmTr);
			}
	
}

//select输入moduleTypeId加载软分析字段
function innitSoftAlarmFiled(field,field_zn)
{
	var field=field.split(",");
	var field_zn=field_zn.split(",");
	$("option.softfield").remove();
	$("tr.addAnalysisAlarm").remove();
	//初始化软分析报警条数
	AnalysisAlarm=1;
	for(var i=0;i<(field.length-1);i++)
	{
		var option='<option class="softfield" value="'+field[i]+'">'+field_zn[i]+'</option>';
		$("#analysisPRO0").append(option);
	}
}


function editorModuleInfo()
{
	var cityName=$('#cityName').val();
	var Module_Type_Id=$('#module_Type_Id').val();
	var Module_Desc=$('#Module_Desc').val();
	
	var url=base64.encode($('#url').val());
	var encoding=$('#encoding').val();
	var errorValve=$('#errorValve').val();
	
	
	var timeValue=$('#timeValue').val();
	var ScanState=$('#ScanState').val();
	
	var alarmCondition=base64.encode(getAnalysisAlarmStr());
	var module_Info_AlarmTime=getAlarmStr();
	var param="method=editor"+
	"&Module_ID="+getQueryString("module_ID")+
	"&cityName="+cityName+
	"&Module_Type_Id="+Module_Type_Id+
	"&Module_Desc="+Module_Desc+
	"&url="+url+
	"&encoding="+encoding+
	"&errorValve="+errorValve+
	"&timeValue="+timeValue+
	"&ScanState="+ScanState+
	"&alarmCondition="+alarmCondition+
	"&module_Info_AlarmTime="+module_Info_AlarmTime;
	var rel=AjaxRequest("ModuleInfo.action",param,null)
	
	
	if(rel.result>0)
		document.location.href='moduleConfig.html';
	else {
		$.messager.alert('错误',rel.msg,'error');
	}
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


function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
    }