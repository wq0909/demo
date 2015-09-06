/*
 * ------------------------------------------
 * 后台公共方法文件
 * @version  1.0
 * @author   guping(guping@corp.netease.com)
 * ------------------------------------------
 */
//通过ID获取节点
function $(str) {
    return document.getElementById(str);
}
//bind
if(!Function.prototype.bind){
    Function.prototype.bind = function(){
        var fn = this, args = Array.prototype.slice.call(arguments), object = args.shift(); 
        return function(){ 
            return fn.apply(object, args.concat(Array.prototype.slice.call(arguments))); 
        }
    }
}
//innerText
if (!('innerText' in document.body)){
    HTMLElement.prototype['__defineGetter__']("innerText",function(){return this.textContent;});
    HTMLElement.prototype['__defineSetter__']("innerText",function(_content){this.textContent = _content;});
}
//trim
if (!String.prototype.trim) {
	String.prototype.trim = function() {
		return this.replace(/^\s+|\s+$/g, '');
	};
}
function getLength(str) {
    return str.replace(/[^\x00-\xff]/g,"**").length;
}
function forEach(list,fn){
    for(var i=0,len=list.length;i<len;i++){
        fn(list[i],i);
    }
}
function stopDefault(event){
    if (!!event){
        !!event.preventDefault
        ? event.preventDefault()
        : event.returnValue = !1;
    }
}
function addEvent(_object,_type,_fn,_fn2){
    if(window.addEventListener){
        if(_type == "hover"){
            _object.addEventListener("mouseover",_fn,false);
            _object.addEventListener("mouseout",_fn2,false);
        }else{
            _object.addEventListener(_type,_fn,false);
        }
    }else{
    	if(_type == "hover"){
            _object.attachEvent("onmouseover",_fn,false);
            _object.attachEvent("onmouseout",_fn2,false);
        }else{
            _object.attachEvent("on"+_type,_fn);
        }
    }
}
function getByClassName(id,klass){
    var node = typeof(id)=="string" ? $(id) : id;
    var list = [];
    forEach(node.getElementsByTagName("*"),function(item,index){
        if((" "+item.className+" ").indexOf(" "+klass+" ")!=-1){
            list.push(item);
        }
    });
    return list;
}
function highlightWord(node, word, klass) {
    if (node.hasChildNodes) {
    	var i;
        for (i=0;i<node.childNodes.length;i++) {
            highlightWord(node.childNodes[i],word,klass);
        }
    }
    if (node.nodeType == 3) {
        tempNodeVal = node.nodeValue.toLowerCase();
        tempWordVal = word.toLowerCase();
        if (tempNodeVal.indexOf(tempWordVal) != -1) {
            pn = node.parentNode;
            if (pn.className != klass) {
                nv = node.nodeValue;
                ni = tempNodeVal.indexOf(tempWordVal);
                before = document.createTextNode(nv.substr(0, ni));
                docWordVal = nv.substr(ni, word.length);
                after = document.createTextNode(nv.substr(ni + word.length));
                hiwordtext = document.createTextNode(docWordVal);
                hiword = document.createElement("span");
                hiword.className = klass;
                hiword.appendChild(hiwordtext);
                pn.insertBefore(before, node);
                pn.insertBefore(hiword, node);
                pn.insertBefore(after, node);
                pn.removeChild(node);
            }
        }
    }
}
//模糊匹配
function highlight (elements,key,klass){
    if (key && key.length === 0) return false;
    forEach(elements,function(item2,index2){
        highlightWord(item2, key, klass);
    });
}
//精确匹配
function highlight2 (elements,key,klass){
    if (key && key.length === 0) return false;
    forEach(elements,function(item2,index2){
        var text = item2.innerText;
        if(text == key){
            var hiword = document.createElement("span");
            hiword.className = klass;
            hiword.innerText = text;
            item2.innerHTML = "";
            item2.appendChild(hiword);
        }
    });
}
//通用验证对象
var checker = {
        /**
         * 字符串是否是合法url
         * @param  {String} str 待检测字符串
         * @return {Boolean}    是否合法
         */
        url: function(str) {
            if (!str) return;
            //return /^https?:\/\/[\w\-_]+(\.[\w\-_]+)+([!\w\-\.,@?^=%&amp;:/~\+#]*[\w\-\@?^=%&amp;/~\+#])?$/.test(str);
            //中文url的可能性
            return  /^(https?:\/\/([-\w]+\.)+[-\w]+(?:\:\d+)?(\/[\w\u4e00-\u9fa5\-\.\/?\@\%\!\&=\+\~\:\#\;\,]*)?)$/i.test(str);
        },
        /**
         * 字符串是否是合法email
         * @param  {String} str 待检测字符串
         * @return {Boolean}    是否合法
         */
        email: function(str) {
            if (!str) return;
            return /^\w[\w\.-]*[a-zA-Z0-9]?@[a-zA-Z0-9][\w\.-]*[a-zA-Z0-9]\.[a-zA-Z][a-zA-Z\.]*[a-zA-Z]$/.test(str);
        },
        /**
         * 字符串是否是合法的手机号
         * @param  {String} str 待检测字符串
         * @return {Boolean}    是否合法
         */
        mobile: function(str) {
            if (!str) return;
            return /^1\d{10}$/.test(str);
        },
        /**
         * 字符串是否是合法的固定电话号
         * @param  {String} str 待检测字符串
         * @return {Boolean}    是否合法
         */
        phone: function(str) {
            if (!str) return;
            return /^0\d{2,3}\-\d{7,8}$/.test(str);
        },
        /**
         * 字符串是否是合法的身份证号
         * @param  {String} str 待检测字符串
         * @return {Boolean}    是否合法
         */
        id: function(str) {
            if (!str) return;
            return /^\d{15}(?:\d{2}[\dX])?$/.test(str.toUpperCase());
        },
        /**
         * 字符串是否是合法的密码(数字/字母/英文符号，最少6位)
         * @param  {String} str 待检测字符串
         * @return {Boolean}    是否合法
         */
        password: function(str) {
            if (!str) return;
            return /^[-~`!@#$%^&*()_+=|{}\[\]:;"'<,>.?\/\d\w]{6,18}$/.test(str);
        }
    };

/**
 * 字符串正则表达式关键字转化
 */
var transReg = function(str) {
    var reg = /[\^\.\\\|\(\)\*\+\-\$\[\]\?]/g;
    var map = {
        "^":"\\^",
        ".":"\\.",
        "\\":"\\\\",
        "|":"\\|",
        "(":"\\(",
        ")":"\\)",
        "*":"\\*",
        "+":"\\+",
        "-":"\\-",
        "$":"\$",
        "[":"\\[",
        "]":"\\]",
        "?":"\\?"
    };
    str = str.replace(reg,function(s){
        return map[s];
    });
    return str;
};

function replaceParamVal (paramName,replaceWith,url) {
    var url = url ? url : location.href.toString(),
        reg = RegExp('('+ paramName+'=)([^&]*)', 'gi');
    if(reg.test(url)){
        url = url.replace(reg, paramName+'='+replaceWith);
    } else {
        if(replaceWith){
            url += url.indexOf('?') >= 0 ? '&' : '?';
            url += (paramName + '=' + replaceWith);
        }
    }
    return url;
}

