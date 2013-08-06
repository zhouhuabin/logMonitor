//软分析报警
function SoftAnalysis(obj,softAnalysis) 
{
		var ret="";
		var newObj=eval('('+obj+')');
		eval('('+softAnalysis+')');
		return ret;
}
//统计信息
function StaticInfo(obj,staticStr)
{ 
		var newObj=eval('('+obj+')');
		var ret=eval('('+staticStr+')');
		return ret;
}


//取得属性值
function getProInfo(obj,pro)
{ 
		var newObj=eval('('+obj+')');
		var ret=eval('('+"newObj."+pro+')');
		return ret;
}
