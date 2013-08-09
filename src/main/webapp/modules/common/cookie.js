/****************************************
function SetCookie
function GetCookie
function DelCookie
cookie
 ****************************************/
function SetCookie(cookieName, cookieValue, path, domain, secure){
 var expires = new Date();
 expires.setTime(expires.getTime() + 100000000);
 document.cookie = escape(cookieName) + '=' + escape(cookieValue)
 + (expires ? '; expires=' + expires.toGMTString() : '')
 + (path ? '; path=' + path : '/')
 + (domain ? '; domain=' + domain : '')
 + (secure ? '; secure' : '');
}
function GetCookie(name){ 
 var cookie_start = document.cookie.indexOf(name);
 var cookie_end = document.cookie.indexOf(";", cookie_start);
 return cookie_start == -1 ? '' : unescape(document.cookie.substring(cookie_start + name.length + 1, (cookie_end > cookie_start ? cookie_end : document.cookie.length)));
} 
function DelCookie(cookieName, cookieValue, path, domain, secure){ 
 var cookieValue="hello";
 var expires = new Date();
 expires.setTime(expires.getTime() - 100000);
 document.cookie = escape(cookieName) + '=' + escape(cookieValue)
 + (expires ? '; expires=' + expires.toGMTString() : '')
 + (path ? '; path=' + path : '/')
 + (domain ? '; domain=' + domain : '')
 + (secure ? '; secure' : '');
} 