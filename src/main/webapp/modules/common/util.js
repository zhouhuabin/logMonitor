// 系统最原始函数定义开始

/*
 * ajax调用方法 全局
 * 
 */
function JAjax(u, d, h, fn, er, sync){
    $.ajax({
        url: u,
        type: "POST",
        data: d || {},
        dataType: h || "html",
        cache: false,
        async: sync ? false : true,
        success: function(req, err){
            if (fn) 
                if (fn instanceof Function) {
                    fn.call(this, req);
                }
        },
        error: function(e, s){
            // alert("与服务器通讯错误");
        	window.top.location.href="../index.html";
            if (er) 
                if (er instanceof Function) {
                    er.call(this, e);
                }
        }
    });
};
$(document).ajaxComplete(
		function(event, request, settings) {
			if(request.getResponseHeader('islogined')=="noLogin"){
				window.location.href = "index.html";
			}
				
			if ((request.statusText == "Unknown" && request.responseText == "")
					|| request.status == 404||request.responseText.login==false) {
				if(PMS.AJAX){
					PMS.AJAX = false;
					window.location.href = "login.html";
					alert("连接服务器出错,请重新登陆！");
				}
			}
		});

/*
 * 字符串增加全部替换方法
 */
String.prototype.replaceAll = function(s1, s2){
    return this.replace(new RegExp(s1, "gm"), s2);
};
String.prototype.contains = function(str){
	return this.indexOf(str) > -1 ? true : false; 
};
/*
 * 查询时过滤特殊字符串
 */
replacesstr = function(str){
    return str.replace(/\%/g, "");
};
filtrates = function(name, strvalue, bu){
    // debugger;
    if (!strvalue) 
        return true;
    var result = true;
    
    str = "<,>,#,%,(,)"; // "%" has been removed for chinese characters =>
    // GBK encoding.
    arr = str.split(",");
    value = strvalue;
    for (var i = 0; i < arr.length; i++) {
        if (!(value.indexOf(arr[i]) == -1)) {
            $("input[name='" + name + "']").attr("class", "input_change");
            alert('包含特殊字符');
            result = false;
            // 设置失去焦点事件
            if (!bu) {
                $("input[name='" + name + "']").blur(function(){
                    if (filtrates(name, $(this).val())) {
                        $(this).removeClass("input_change");
                        $(this).addClass("input_init");
                        $(this).addClass("no-filtrates");
                    }
                });
            }
            break;
        }
        
    }
    return result;
};
/*
 * **********************************************日期扩展完成
 */
/*
 * UTC和date 转换
 */
date2utc = function(c_date){
    if (!c_date) 
        return "";
    var tempArray = c_date.split("-");
    if (tempArray.length != 3) {
        alert("你输入的日期格式不正确,正确的格式:2000-05-01");
        return 0;
    }
    var date = new Date(tempArray[0], tempArray[1] - 1, tempArray[2], 00, 00, 01);
    return parseInt("" + date.getTime() / 1000);
};
/*
 * UTC和date 转换 开始时间 为 yyyy-mm-dd 00-00-00
 */
date2utcBegin = function(c_date){
    if (!c_date) 
        return "";
    var tempArray = c_date.split("-");
    if (tempArray.length != 3) {
        alert("你输入的日期格式不正确,正确的格式:2000-05-01");
        return 0;
    }
    var date = new Date(tempArray[0], tempArray[1] - 1, tempArray[2], 00, 00, 00);
    return parseInt("" + date.getTime() / 1000);
};
/*
 * UTC和date 转换 开始时间 为 yyyy-mm-dd 23-59-59
 */
date2utcEnd = function(c_date){
    if (!c_date) 
        return "";
    var tempArray = c_date.split("-");
    if (tempArray.length != 3) {
        alert("你输入的日期格式不正确,正确的格式:2000-05-01");
        return 0;
    }
    var date = new Date(tempArray[0], tempArray[1] - 1, tempArray[2], 23, 59, 59);
    return parseInt("" + date.getTime() / 1000);
};
date2utcs = function(c_date){
    if (!c_date) 
        return "";
    var tempArray2 = c_date.split(" ")[1].split(":");
    var tempArray = c_date.split(" ")[0].split("-");
    if (tempArray.length != 3) {
        alert(c_date);
        alert("你输入的日期格式不正确,正确的格式:2000-05-01 23:23:23");
        return 0;
    }
    var date = new Date(tempArray[0], tempArray[1]-1, tempArray[2], tempArray2[0], tempArray2[1], tempArray2[2]);
    return parseInt("" + date.getTime() / 1000);
    
};

utc2Date = function(n_utc){
    if (!n_utc || n_utc == null || n_utc == "null" || n_utc == "无"||n_utc == -1)
    		return "";
    var date = new Date();
    date.setTime((parseInt(n_utc) + 8 * 3600) * 1000);
    var s = date.getUTCFullYear() + "-";
    if ((date.getUTCMonth() + 1) < 10) {
        s += "0" + (date.getUTCMonth() + 1) + "-";
    }
    else {
        s += (date.getUTCMonth() + 1) + "-";
    }
    if (date.getUTCDate() < 10) {
        s += "0" + date.getUTCDate();
    }
    else {
        s += date.getUTCDate();
    }
    if (date.getUTCHours() < 10) {
        s += " 0" + date.getUTCHours() + ":";
    }
    else {
        s += " " + date.getUTCHours() + ":";
    }
    if (date.getMinutes() < 10) {
        s += "0" + date.getUTCMinutes() + ":";
    }
    else {
        s += date.getUTCMinutes() + ":";
    }
    if (date.getUTCSeconds() < 10) {
        s += "0" + date.getUTCSeconds();
    }
    else {
        s += date.getUTCSeconds();
    }
    
    return s;
};
utcToDate = function(n_utc){
    if (!n_utc) 
        return "";
    var date = new Date();
    date.setTime((parseInt(n_utc) + 8 * 3600) * 1000);
    
    var s = date.getUTCFullYear() + "-";
    if ((date.getUTCMonth() + 1) < 10) {
        s += "0" + (date.getUTCMonth() + 1) + "-";
    }
    else {
        s += (date.getUTCMonth() + 1) + "-";
    }
    if (date.getUTCDate() < 10) {
        s += "0" + date.getUTCDate();
    }
    else {
        s += date.getUTCDate();
    }
    
    return s;
};
/*
 * UTC和date 转换 完成
 */
// 下拉框选中
selector = function(id, selectvalue){
    // debugger;
    for (var i = 0; i < $("#" + id).children().length; i++) {
        if ($("#" + id).children().eq(i).val() == selectvalue) {
            $("#" + id).children().eq(i).attr({
                selected: true
            });
        }
    }
};
getnowTime = function(){
    var date = new Date(); // 日期对象
    return date.Format("yyyy-MM-dd hh:mm:ss");
};
getdaydate = function(add){
    var date = new Date(); // 日期对象
    if (add) 
        date.addDays(add);// 加减日期操作
    return date.Format("yyyy-MM-dd");
};
getpreday = function(){
    var now = getdaydate(-1);
    return now;
};


var null2blank = function(v){
    if (v == undefined || v == null || v == "null") {
        v = "";
    }
    return v;
};



/**
 * 将json字符串转换为对象
 */
function strJson2Object(jsonStr){
	var obj = eval(jsonStr);
	return obj;
}

/**
 * 将元素列表转换为对象列表
 * @param list
 * @returns {Array}
 */
function listElements2ObjArray(list){
	var ret = [];
	for(var i=0;i<list.length;i++){
		ret.push(eleAll2Object(list[i]));
	}
	return ret;
}
/**
 * 根据属性数组解析元素为对象
 */
function eleAll2Object(ele){
	 var obj = {};
	 if(!ele)
		 return;
	 var atts = ele.attributes;
	 for(var i=0;i<atts.length;i++){
		 obj[atts[i].name] = ele.getAttribute(atts[i].name);
	}
	return obj;
}
/**
 * 根据属性数组解析元素为对象
 */
function element2Object(ele,atts){
	 var obj = {};
	 if(!ele)
		 return;
	 for(var i=0;i<atts.length;i++){
		 obj[atts[i]] = ele.getAttribute(atts[i]);
	}
	return obj;
}
/** 将数组转换为url请求串*/
function buildParam(param){
	var ret='';
	for(var i=0;i<param.length;i++){
		var obj=param[i];
		if(i==0) ret +=obj.name+"="+obj.value;
		else ret +="&"+obj.name+"="+obj.value;
	}
	return ret;
}
/**合并两个map*/
function mergeMap(map1,map2){
	var ret=map1;
	for(var i in map2){
		map1[i]=map2[i];
	}
	return ret;
}
/** 将url传的参数解析成map */
function parseUrl2Map(url){
    var i=url.indexOf('?');
    if(i==-1)return;
    var querystr=url.substr(i+1);
    var arr1=querystr.split('&');
    var arr2=new Object();
    for (var i=0;i<arr1.length;i++){
        var ta=arr1[i].split('=');
        arr2[ta[0]]=ta[1];
    }
    return arr2;
}
/** 如果为空则用默认值，否则用实际值
 * @param obj
 * @param defaultVal
 * @returns
 */
function null2default(obj,defaultVal){
	return obj==null?defaultVal:obj;
}
/**
 * 更新对应模块名称选项值
*/
function initSelectOptionByDomId(dom_id,map) {
	var obj = $("#" + dom_id);
	if (obj&&map) {
		obj.append("<option value=''>全部</option>");
		for ( var key in map) {
			obj.append("<option value='"+key+"'>"+map[key]+"</option>");
		}
	}
}
// 判断一个月有多少天
function getCountDays(year, month){
    var curDate = new Date(year, month - 1);
    /* 获取当前月份 */
    var curMonth = curDate.getMonth();
    /* 生成实际的月份: 由于curMonth会比实际月份小1, 故需加1 */
    curDate.setMonth(curMonth + 1);
    /* 将日期设置为0, 这里为什么要这样设置, 我不知道原因, 这是从网上学来的 */
    curDate.setDate(0);
    /* 返回当月的天数 */
    
    return curDate.getDate();
}

// 显示遮罩
// i 为false时隐藏 true 显示
function mask(id, l, i){
    if (!l) {
        l = "&nbsp;";
    }
    if (i) {
        $("#" + id).loadMask(l);
    }
    else {
        $("#" + id).unLoadMask(l);
    }
}


function checkeURL(URL){
    var str = URL;
    // 在JavaScript中，正则表达式只能使用"/"开头和结束，不能使用双引号
    // 判断URL地址的正则表达式为:http(s)?://([\w-]+\.)+[\w-]+(/[\w- ./?%&=]*)?
    // 下面的代码中应用了转义字符"\"输出一个字符"/"
    var Expression = /http(s)?:\/\/([\w-]+\.)+[\w-]+(\/[\w- .\/?%&=]*)?/;
    var objExp = new RegExp(Expression);
    if (objExp.test(str) == true) {
        return true;
    }
    else {
        return false;
    }
}

/**
 * 检查是否手机号,13/15/18开头
 * 
 * @param {Object}
 *            _string
 */
function isTelphone(_string){
    // var istel = /^13\d{9}$/g.test(_string) || /^15[8,9]\d{8}$/g.test(_string)
    // || /^18\d{9}$/g.test(_string);
    var istel = /^[0-9]\d{10}$/g.test(_string);
    return istel;
};

/**
 * 验证字符长度
 */
function validateCharLength(str){
    var l = 0;
    var chars = str.split("");
    for (var i = 0; i < chars.length; i++) {
        if (chars[i].charCodeAt(0) < 299) 
            l++;
        else 
            l += 2;
    }
    return l;
};
