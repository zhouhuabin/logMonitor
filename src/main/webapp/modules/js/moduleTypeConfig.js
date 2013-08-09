// JavaScript Document
var grid;
var win;
var form;
$(function(){
	$.ajaxSetup ({ 
		cache: false 
		});
	
	 win = $('#editPartnersDiv').window({
		closed:true,
		title:'模板配置',
		modal: true,
		maximizable:false,
		minimizable:false
	});
	form = win.find('form');
	grid=$('#moduleTypeConfig-table').datagrid({
		title:'模板配置',
		url:'ModuleType.action?method=list',
		sortName: 'module_Type_Name',
		sortOrder: 'desc',       
		remoteSort: false,
		idField:'module_Type_Name',
		columns:[[
		    {field:'module_Type_Name',title:'模板名称',width:50,align:'center',sortable:true},
			{field:'module_Type_Static',title:'统计字段',width:150,align:'center',sortable:true},	
			{field:'module_Type_Field',title:'日志字段',width:100,align:'center',sortable:true},
			{field:'module_Type_Field_Zn',title:'字段中文描述',width:100,align:'center',sortable:true},
			{field:'threadInterval',title:'线程休眠间隔(秒)',width:100,align:'center',sortable:true}
		]],
		
		pagination:true,
		singleSelect:true,
		toolbar:[{
			text:'新增',
			iconCls:'icon-add',
			handler:addPartner
		},'-',{
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
		$.messager.confirm('提示信息', '您确认要删除 '+row.module_Type_Name+'模板吗?', function(data){
			if(data){
				$.ajax({
					type: "GET",
					dataType: "json",
					url: "ModuleType.action?method=delete&module_Type_Id="+row.module_Type_Id,
					cache: false,
					beforeSend: function(){
					},
					success: function(msg){
						if(msg.result>0){
							$.messager.alert('提示','删除成功','info');
							showAll();
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