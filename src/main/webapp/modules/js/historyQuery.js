// JavaScript Document
var grid;
var win;
var form;
$(function(){
	
	$('#startTime').datebox({  
		formatter: function(date){ return date.getFullYear()+'-'+(date.getMonth()+1)+'-'+date.getDate(); }  
		
		});
	$('#endTime').datebox({  
		formatter: function(date){ return date.getFullYear()+'-'+(date.getMonth()+1)+'-'+date.getDate(); }  
		});
	
	$.ajaxSetup ({ 
		cache: false 
		});
	initSelect();
	 win = $('#editPartnersDiv').window({
		closed:true,
		title:'历史报警',
		modal: true,
		maximizable:false,
		minimizable:false
	});
	form = win.find('form');
});

function submitForm()
{	
	var Module_Type_Id=$("#Module_Type_Id").combobox('getValue');
	var Module_ID=$("#Module_ID").combobox('getValue');
	var startTime=date2utcBegin($("#startTime").datebox('getValue'));
	var endTime=date2utcEnd($("#endTime").datebox('getValue'));
	var status=$("#status").combobox('getValue');
	var report_way=$("#report_way").combobox('getValue');
	var parm="";
	if(Module_Type_Id!="null")
		parm+="Module_Type_Id="+Module_Type_Id;
	if(Module_ID!="null")	
		parm+="&Module_ID="+Module_ID;
	if(status!="null")
		parm+="&status="+status;
	if(report_way!="null")
		parm+="&report_way="+report_way;
	parm+="&startTime="+startTime+"&endTime="+endTime;
	initGrid(parm);
}

function initGrid(parm)
{
	grid=$('#alarmHistory-table').datagrid({
		title:'历史报警',
		url:'AlarmService.action?method=list&'+parm,
		sortName: 'id',
		sortOrder: 'desc',       
		remoteSort: false,
		idField:'Module_ID',
		columns:[[
			{field:'module_Desc',title:'模块名称',width:50,align:'center',sortable:true},	
			{field:'data_time',title:'报警时间',width:100,align:'center',sortable:true,
				formatter:function(value,rec){
					if(value!=0)
						return utc2Date(value);
				}
			},
			
			{field:'deal_time',title:'处理时间',width:100,align:'center',sortable:true,
				formatter:function(value,rec){
					if(value!=0)
						return utc2Date(value);
				}
			},
			{field:'finish_time',title:'恢复时间',width:100,align:'center',sortable:true,
				formatter:function(value,rec){
					if(value!=0)
						return utc2Date(value);
				}
			},
			{field:'status',title:'报警状态',width:100,align:'center',sortable:true,
				formatter:function(value,rec){
					if(value==0)
						return '报警中';
					if(value==1)
						return '处理中';
					if(value==2)
						return '恢复正常';
				}
			},
			{field:'report_way',title:'报警类别',width:100,align:'center',sortable:true,
				formatter:function(value,rec){
					if(value==0)
						return '文件找不到';
					if(value==1)
						return '程序报警';
					if(value==2)
						return '软分析报警';
					if(value==3)
						return '超时报警';
				}
			},
			{field:'text',title:'状态描述',width:100,align:'center',sortable:true},
			{field:'update_time',title:'更新时间',width:100,align:'center',sortable:true,
				formatter:function(value,rec){
					if(value!=0)
						return utc2Date(value);
				}
			},
			{field:'deal_opname',title:'处理人',width:80,align:'center',sortable:true}
		]],
		pagination:true,
		singleSelect:true
	});
}



function initSelect(){
	  var deptSelect = $('#Module_Type_Id');
	  if( $('#Module_Type_Id option').length==0){
	   deptSelect.combobox({
	    url:'ModuleType.action?method=moduletypecombobox',
	    valueField:'id',
	    textField:'text',
	    onChange:function(){
	     var deptId = $('#Module_Type_Id').combobox('getValue');//注意这里不能使用$('#deptId').val()方法来获取下拉框的值，因为这里使用的是easyui的combobox组件，所以只能使用easyui提供的getValue方法
	     $('#Module_ID').combobox({
	      url:'ModuleInfo.action?method=moduleinfocombobox&Module_Type_Id='+deptId,
	      valueField:'id',
	      textField:'text'
	     });
	    }
	   });
	 }
}  