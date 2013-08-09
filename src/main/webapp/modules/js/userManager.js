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
		title:'模块配置',
		modal: true,
		maximizable:false,
		minimizable:false
	});
	form = win.find('form');
	grid=$('#userManager-table').datagrid({
		title:'用户列表',
		url:'getJsonString.action?modelName=MonitorUser&typeName=queryListMonitorUser&serverName=service.monitorUserService',
		sortName: 'real_name',
		sortOrder: 'asc',       
		remoteSort: false,
		idField:'real_name',
		columns:[[
			{field:'real_name',title:'姓名',width:100,align:'center',sortable:true},	
			{field:'account',title:'登陆账号',width:150,align:'center',sortable:true,
				formatter:function(value,rec){
						return value;
				}
			},
			{field:'mobile',title:'手机',width:100,align:'center',sortable:true},
			{field:'role_id',title:'身份',width:100,align:'center',sortable:true,
				formatter:function(value,rec){
					if("0"==value)return "管理员";
					else return "维护员";
				}
			},
			{field:'update_time',title:'更新时间',width:150,align:'center',sortable:true,
				formatter:function(value,rec){
					return utc2Date(value);
				}
			}
		]],
		
		pagination:true,
		singleSelect:true,
		toolbar:[{
			text:'新增',
			iconCls:'icon-add',
			handler:addUser
		},'-',{
			text:'修改',
			iconCls:'icon-edit',
			handler:editUser
		},'-',{
			text:'删除',
			iconCls:'icon-remove',
			handler:deleteUser
		}]
		
	});
});

function showAll(){
	grid.datagrid('reload');
}
function addUser(){
	document.location.href='addUser.html?actionType=add';
}

function editUser(){
	var row = grid.datagrid('getSelected');
	if (row){
		document.location.href = 'addUser.html?op_id='+row.op_id+"&actionType=edit";
	}else {
		$.messager.show({
			title:'警告', 
			msg:'请先选择要修改的用户'
		});
	}
}

function closeWindow(){
	win.window('close');
}
function deleteUser(){
	var row = grid.datagrid('getSelected');	
	grid.datagrid('clearSelections');
	if(row){
		$.messager.confirm('提示信息', '您确认要删除 '+row.real_name+' 用户吗?', function(data){
			if(data){
				$.ajax({
					type: "GET",
					dataType: "json",
					url: "getJsonString.action",
					data:{modelName:"MonitorUser",typeName:"deleteMonitorUser",serverName:"service.monitorUserService",op_id:row.op_id},
					cache: false,
					beforeSend: function(){
					},
					success: function(rel,text){
						if(rel[0].result=="1")
							grid.datagrid('reload');
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