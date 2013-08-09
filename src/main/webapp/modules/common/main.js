$(function(){	
	$.ajaxSetup ({ 
		cache: false 
		});
	InitLeftMenu();
	tabClose();
	tabCloseEven();
	clockon();
	initLogInfo();
});


//初始化请求登陆信息
function initLogInfo(){
	 $.ajax({
	     url: 'get_loginInfo.action',
	     type: "POST",
		 async:false,
	     data: {},
	     dataType:'xml'||"json",
	     success: function(data, textStatus)
	     {
	    	 data=data.documentElement.firstChild.firstChild;
	    	 var user = element2Object(data,["account","op_id","password","real_name","role_id","update_time"]);
	    	$("#username").html(user.real_name);
	     },
	     error: function()
	     {
	    	 result="请求后台异常erro";
	     }
	 });
	
	
};
function logout(){
	  JAjax('logout.action', {}, 'json', function(x){
	        // 解释xml
	    	if(x.success==true||x.success=="true"){
	    		window.location.href = "index.html";
	    	}else{;
	    		$.messager.alert('错误','退出失败','error');
	    	}
	    });
}

//初始化左侧
function InitLeftMenu() {
	$('.easyui-accordion li a').click(function(){
		//var tabTitle = $(this).text();
		//var url = $(this).attr("href");
		//addTab(tabTitle,url);
		$('.easyui-accordion li div').removeClass("selected");
		$(this).parent().addClass("selected");
	}).hover(function(){
		$(this).parent().addClass("hover");
	},function(){
		$(this).parent().removeClass("hover");
	});
	$(".easyui-accordion").accordion();
}
var count=0;
//function addTab(subtitle,url){
//	if(!$('#tabs').tabs('exists',subtitle)){
//		$('#tabs').tabs('add',{
//			title:subtitle,
//			content:createFrame(url),
//			closable:true,
//			width:$('#mainPanle').width()-10,
//			height:$('#mainPanle').height()-26
//		});
//	}else{
//		$('#tabs').tabs('select',subtitle);
//	}
//	tabClose();
//}

function addTab(subtitle,url){
	var tab = $('#tabs');
	if(!tab.tabs('exists',subtitle)){
		tab.tabs('add',{
			title:subtitle,
			content:createFrame(url),
			closable:true,
			width:$('#mainPanle').width()-10,
			height:$('#mainPanle').height()-26
		});
	}else{
		tab.tabs('select',subtitle);
	}
	tabClose();
}
function createFrame(url){
	var s = '<iframe name="mainFrame" scrolling="auto" src="'+url+'" frameborder="0" style="width:100%; height:100%"></iframe>';
	return s;
}
function tabClose(){
	/*双击关闭TAB选项卡*/
	$(".tabs-inner").dblclick(function(){
		var subtitle = $(this).children("span").text();
		$('#tabs').tabs('close',subtitle);
	});
	$(".tabs-inner").bind('contextmenu',function(e){
		$('#mm').menu('show', {
			left: e.pageX,
			top: e.pageY
		});
		
		var subtitle =$(this).children("span").text();
		$('#mm').data("currtab",subtitle);
			
		return false;
	});
}
//绑定右键菜单事件
function tabCloseEven(){
	//关闭当前
	$('#mm-tabclose').click(function(){
		var currtab_title = $('#mm').data("currtab");
		$('#tabs').tabs('close',currtab_title);
	});
	//全部关闭
	$('#mm-tabcloseall').click(function(){
		$('.tabs-inner span').each(function(i,n){
			var t = $(n).text();
			$('#tabs').tabs('close',t);
		});	
	});
	//关闭除当前之外的TAB
	$('#mm-tabcloseother').click(function(){
		var currtab_title = $('#mm').data("currtab");
		$('.tabs-inner span').each(function(i,n){
			var t = $(n).text();
			if(t!=currtab_title)
				$('#tabs').tabs('close',t);
		});	
	});
	//关闭当前右侧的TAB
	$('#mm-tabcloseright').click(function(){
		var nextall = $('.tabs-selected').nextAll();
		if(nextall.length==0){
			msgShow('系统提示','后边没有啦~~','error');
			return false;
		}
		nextall.each(function(i,n){
			var t=$('a:eq(0) span',$(n)).text();
			$('#tabs').tabs('close',t);
		});
		return false;
	});
	//关闭当前左侧的TAB
	$('#mm-tabcloseleft').click(function(){
		var prevall = $('.tabs-selected').prevAll();
		if(prevall.length==0){
			msgShow('系统提示','到头了，后边没有啦~~','error');
			return false;
		}
		prevall.each(function(i,n){
			var t=$('a:eq(0) span',$(n)).text();
			$('#tabs').tabs('close',t);
		});
		return false;
	});
	//退出
	$("#mm-exit").click(function(){
		$('#mm').menu('hide');
	});
}
//弹出信息窗口 title:标题 msgString:提示信息 msgType:信息类型 [error,info,question,warning]
function msgShow(title, msgString, msgType) {
	$.messager.alert(title, msgString, msgType);
}
function clockon() {
    var now = new Date();
    var year = now.getFullYear(); //getFullYear getYear
    var month = now.getMonth();
    var date = now.getDate();
    var day = now.getDay();
    var hour = now.getHours();
    var minu = now.getMinutes();
    var sec = now.getSeconds();
    var week;
    month = month + 1;
    if (month < 10) month = "0" + month;
    if (date < 10) date = "0" + date;
    if (hour < 10) hour = "0" + hour;
    if (minu < 10) minu = "0" + minu;
    if (sec < 10) sec = "0" + sec;
    var arr_week = new Array("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六");
    week = arr_week[day];
    var time = "";
    time = year + "年" + month + "月" + date + "日" + " " + hour + ":" + minu + ":" + sec + " " + week;

    $("#bgclock").html(time);
    /**var timer =*/ setTimeout("clockon()", 200);
}

function getDate() {
    var now = new Date();
    var year = now.getFullYear(); //getFullYear getYear
    var month = now.getMonth();
    var date = now.getDate();
    var hour = now.getHours();
    parTime = year + month + date + hour;
    return hex_sha1(parTime);
    setTimeout("getDate()", 200);
}

function getAuthorityList(){
	$.ajax({
		url: 'getLoginInfo.action?loginName='+golLoginName,
		type: 'GET',
		error: function(){
			$.messager.alert('错误','请检查参数是否正确!','error');
		},
		success: function(data){
			if (data!=null){
			    var l = data.rows.length;
			    if(l==0){
			    	
			    }else{
					createTree(data);	
					InitLeftMenu();	
			    }
			} else {
				$.messager.alert('错误','获取数据失败','error');
			}
		}
	});
}

function createTree(authority){
	var html='';
	for(var i=0;i<authority.rows.length;i++){

		 html+="<div title='"+authority.rows[i].authorityName+"' style='overflow:auto;'><ul>";
	     	  for(var j=0;j<authority.rows[i].listPubMenu.length;j++){
	     		 html+= "<li><div><a href=\"javascript:addTab('"+authority.rows[i].listPubMenu[j].menuName+"','"+authority.rows[i].listPubMenu[j].menuUrl+"')\" ><span style='width:10px;height:10px;' class='"+authority.rows[i].listPubMenu[j].menuStyle+"' ></span>"+authority.rows[i].listPubMenu[j].menuName+"</a></div></li>";
	     	  }
	     html+="</ul></div>";

	}
	
	$('.easyui-accordion').append(html);
	$('#aa').accordion('add',{
		content:html
	});
}


