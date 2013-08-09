// JavaScript Document
var grid;
var win;
var form;

var AnalysisAlarm=1;
$(function(){
	$.ajaxSetup ({ 
		cache: false 
		});
	
	 win = $('#editPartnersDiv').window({
		closed:true,
		title:'模块类型配置',
		modal: true,
		maximizable:false,
		minimizable:false
	});
	form = win.find('form');
	grid=$('#analysisAlarm-table').datagrid({
		title:'软分析报警配置',
		url:'../../testJson/analysisAlarm.json',
		sortName: 'id',
		sortOrder: 'desc',       
		remoteSort: false,
		idField:'id',
		columns:[[
		    {field:'moduleCode',title:'模块名称',width:300,align:'center',sortable:true},
			{field:'condition',title:'报警条件',width:300,align:'center',sortable:true},	
			{field:'conditionCode',title:'条件代码',width:300,align:'center',sortable:true}	    	
		]],
		
		pagination:true,
		singleSelect:true,
		toolbar:[{
			text:'删除',
			iconCls:'icon-remove',
			handler:deletePartners
		}]
		
	});
});

function showAll(){
	grid.datagrid('reload');
}
function addPartner(){
	document.location.href='addModuleType.html';
}

function editPartner(){
	var row = grid.datagrid('getSelected');
	if (row){
		document.location.href = 'editpartner.jsp?id='+row.id;
	}else {
		$.messager.show({
			title:'警告', 
			msg:'请先选择要修改的地图配置信息'
		});
	}
}

function closeWindow(){
	win.window('close');
}
function deletePartners(){
	var row = grid.datagrid('getSelected');	
	grid.datagrid('clearSelections');
	if(row){
		$.messager.confirm('提示信息', '您确认要删除 '+row.partnerName+'渠道吗?', function(data){
			if(data){
				$.ajax({
					type: "GET",
					dataType: "text",
					url: "deletepartner.action?id="+row.id,
					cache: false,
					beforeSend: function(){
					},
					success: function(msg){
						if($.trim(msg)=="success"){
							$.messager.alert('提示','删除成功','info');
							showAll();
						}else if($.trim(msg)=="have"){
							$.messager.alert('提示','此渠道在使用，不能删除','info');
						}else{
							$.messager.alert('错误','删除失败，请重新操作','error');
						}
					}
				});
			}
		});
	}else{
		$.messager.show({
			title:'警告', 
			msg:'请先选择要删除的记录'
		});
	}
}

function getAnalysisAlarmStr()
{
	var analysisAlarmStr="";
	for(var i=0;i<AnalysisAlarm;i++)
	{
		var pro=$('#analysisPRO'+i).val();
		var con=$('#analysisCON'+i).val();
		var value=$('#analysisCONVALUE'+i).val();
		if(pro==-1||con==-1)
			continue;
		if(analysisAlarmStr!="")
			analysisAlarmStr+=";"
		analysisAlarmStr+=pro+con+value;
	}
	return analysisAlarmStr;
}


function addAnalysisAlarm(){
	
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
	AnalysisAlarm++;
	//初始化表单中的验证器  
	$('input[type!="hidden"],select,textarea',$('#addModuleInfoform') ).each(function(){  
	    $(this).validatebox();  
	});
}