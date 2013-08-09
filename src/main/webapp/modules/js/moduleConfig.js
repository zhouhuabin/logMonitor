// JavaScript Document
var grid;
var win;
var form;
var base64 = new Base64();
$(function(){
	$.ajaxSetup ({ 
		cache: false 
		});
	
	 win = $('#editPartnersDiv').window({
		closed:true,
		title:'模块配置',
		modal: true,
		maximizable:false,
		minimizable:false
	});
	form = win.find('form');
	grid=$('#moduleTypeConfig-table').datagrid({
		title:'模块详细配置',
		url:'ModuleInfo.action?method=list',
		sortName: 'id',
		sortOrder: 'desc',       
		remoteSort: false,
		idField:'id',
		columns:[[
			{field:'module_Desc',title:'模块名称',width:150,align:'center',sortable:true},	
			{field:'url',title:'日志地址',width:100,align:'center',sortable:true,
				formatter:function(value,rec){
						return base64.decode(value);
				}
			},
			{field:'errorValve',title:'错误次数',width:100,align:'center',sortable:true},
			{field:'timeValue',title:'超时时间(秒)',width:100,align:'center',sortable:true},
			{field:'scanState',title:'扫描状态',width:100,align:'center',sortable:true,
				formatter:function(value,rec){
					if(value==1)
						return '开启';
					else
						return '<span style="color:red">关闭</span>';
				}
			}
		]],
		
		pagination:true,
		singleSelect:true,
		toolbar:[{
			text:'新增',
			iconCls:'icon-add',
			handler:addModule
		},'-',{
			text:'修改',
			iconCls:'icon-edit',
			handler:editModule
		},'-',{
			text:'删除',
			iconCls:'icon-remove',
			handler:deleteModules
		}]
		
	});
});

function showAll(){
	grid.datagrid('reload');
}
function addModule(){
	document.location.href='addModuleInfo.html';
}

function editModule(){
	var row = grid.datagrid('getSelected');
	if (row){
		document.location.href = 'editordModuleInfo.html?module_Type_Id='+row.module_Type_Id+"&module_ID="+row.module_ID;
	}else {
		$.messager.show({
			title:'警告', 
			msg:'请先选择要修改的模块'
		});
	}
}

function closeWindow(){
	win.window('close');
}
function deleteModules(){
	var row = grid.datagrid('getSelected');	
	grid.datagrid('clearSelections');
	if(row){
		$.messager.confirm('提示信息', '您确认要删除 '+row.module_Desc+' 模块吗?', function(data){
			if(data){
				$.ajax({
					type: "GET",
					dataType: "json",
					url: "ModuleInfo.action?method=delete&module_type_id="+row.module_Type_Id+"&Module_ID="+row.module_ID,
					cache: false,
					beforeSend: function(){
					},
					success: function(rel,text){
						
						if(rel.result>0)
							document.location.href='moduleConfig.html';
						else {
							$.messager.alert('错误',rel.msg,'error');
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