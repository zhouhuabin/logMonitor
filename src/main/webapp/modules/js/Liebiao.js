var base64 = new Base64();

//定时器组件
var timer1=null;
var i=0;

var str=AjaxRequest("ModuleData.action","mothod=getcitylist",null);
		
		function initAccord()
		{
			var parend=$("#dataBody");
			
			for(var i=0;i<str.length;i++)
			{
				var accord='<div id="aa'+i+'" class="easyui-accordion" style="width:800px;">'+
				'<div title="'+str[i]+'"  style="overflow:auto;padding:10px;">'+
					'<table id="tt'+i+'"></table>'+
				'</div>'+
				'</div>';
				parend.append(accord); 
				$("#aa"+i).accordion();
			}
		}
		
		function startRequest()
		{
			var interval=$("#interval").val()
			if(interval>0)
			{
				
				timer1 = window.setInterval(select,interval);
				$("#requestStatus").html("<font color='green'>启动成功！</font>");
				select();
			}
			else
				{
				 $("#requestStatus").html("<font color='red'>启动失败，请选择请求间隔！</font>");
				}
		}
		
		function endRequest()
		{
			if(timer1==null)
				 $("#requestStatus").html("<font color='red'>尚未启动！</font>");
			else
				{
					clearInterval(timer1);
					$("#requestStatus").html("<font color='green'>成功停止！</font>");
				}
		}
		function select(){
			for(var i=0;i<str.length;i++)
			{
				if(i==str.length)
					i=0;
				$('#aa'+i).accordion('select',str[i]);
				var pp = $('#aa'+i).accordion('getSelected'); // 获取选中的 panel
				 if (pp){
					  getData(base64.encode(str[i]),'#tt'+i);
				  }
			}
			
		}
		
		
		
		function getData(cityname,tableID)
		{
			$(tableID).datagrid({
				url: 'ModuleData.action?mothod=getcitydata&cityname='+cityname,
				width: 700,
				height: 'auto',
				fitColumns: true,
				columns:[[
					{field:'moduleName',title:'模块名称',width:100,align:'center'},
					{field:'status',title:'状态',width:100,align:'center'},
					{field:'dateTime',title:'时间',width:220,align:'center'},
					{field:'ststicInfo',title:'统计信息',width:600,align:'center'},
					{field:'text',title:'状态描述',width:250,align:'center'}
				]],
				onHeaderContextMenu: function(e, field){
					e.preventDefault();
					if (!$('#tmenu').length){
						createColumnMenu();
					}
					$('#tmenu').menu('show', {
						left:e.pageX,
						top:e.pageY
					});
				}
			});
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