//表格提交
function postForm(formId, url, callBackUrl) {
    //提交之前检查
    if (formId == 'createProduct'){
        if (checkProductSubmit() == false){
            return;
        }
    }
    if (formId == 'createModule'){
        if (checkModuleSubmit() == false){
            return;
        }
    }
    if (formId == 'addPerm'){
        if (checkAddPermSubmit() == false){
            return;
        }
    }
    var form = $("#" + formId);
    var params = form.serialize();

    //for(var k in form) {
    //    alert(form[k]);
    //}
    //alert(params);
    $.post(url, params)
     .done(function(responseText){
            data = jQuery.parseJSON(responseText);
            if(data.code==400 || data.code<0) {
                alert(data.msg);
            } else if(callBackUrl){
            	if (!!data.msg && data.msg.length>1) {
            		alert(data.msg);
            	}
                window.location = callBackUrl;
            } else {
                alert(data.msg);
            }
        })
     .fail(function(res){
     	var data = jQuery.parseJSON(res.responseText);
     	if (!!data.msg) {
     		alert(data.msg);
     	} else {
         	alert('服务器连接失败!');
     	}
     });
    return false;
}


function postFormDirect(formId, url, callBackUrl) {
    var form = $("#" + formId);
    var params = form.serialize();
    $.post(url, params)
     .done(function(responseText){
            data = jQuery.parseJSON(responseText);
            if(data.code<=0) {
                alert(data.msg);
            } else if(callBackUrl){
                window.location = callBackUrl;
            } else {
                alert(data.msg);
            }
        })
     .fail(function(res){
        var data = jQuery.parseJSON(res.responseText);
     	console.log(data)
     	if (!!data.msg) {
     		alert(data.msg);
     	} else {
         	alert('服务器连接失败!');
     	}
     });
    return false;
}

function postFormTaskId(formId, url, callBackUrl) {
    var form = $("#" + formId);
    var params = form.serialize();
    $.post(url, params)
     .done(function(responseText){
            data = jQuery.parseJSON(responseText);
            if(data.code<=0) {
                alert(data.msg);
            } else if(callBackUrl){
               if (!!data.msg) {
                	window.location = callBackUrl+"&uniqueId="+data.msg;
                } else {
                	window.location = callBackUrl;
                }
            } else {
                alert(data.msg);
            }
        })
     .fail(function(res){
        var data = jQuery.parseJSON(res.responseText);
      	if (!!data.msg) {
     		alert(data.msg);
     	} else {
         	alert('服务器连接失败!');
     	}
     });
    return false;
}


// 左侧导航栏显示应用管理和环境管理操作
function showManager(thisbutton,moduleManagerId,envManagerId) {
    var mpId = document.getElementById(moduleManagerId);
    if (mpId.style.display == "none"){
        //显示下拉图标
        $(thisbutton).removeClass().addClass("netname-crt");
        //显示应用管理和环境管理按钮
        $("#" + moduleManagerId).show();
        $("#" + envManagerId).show();
    }
    else{
        $(thisbutton).removeClass().addClass("netname");
        $("#" + moduleManagerId).hide();
        $("#" + envManagerId).hide();
    }
}

//操作转移
function moveIn(url) {
    if(url) {
        window.location=url;
    }
}

//增加css class
function addClassName(opsid, className) {
    $("#" + opsid).addClass(className);
}

//删除 css class
function removeClassName(opsid, className) {
    $("#" + opsid).removeClass(className);
}

//环境全选操作
function selectAllCheckbox(ParentID, bool){
    var pid = document.getElementById(ParentID);
    var cb = pid.getElementsByTagName("input");
    var cbl = cb.length;
    for (var i = 0; i < cbl; i++ ){
        if ( cb[i].type == "checkbox" ){
            cb[i].checked = bool;
        }
        var immedExecId = 'immedExec'+(i+1);
        var chkBoxId = 'chkBox'+(i+1);
        changeClassName(chkBoxId, 'modifyconfig', 'instancemanager', 'startops', 'stopops', 'restartops', 'deleteops', 'crontabjob', immedExecId, 'u-btn-1-dis');
    }
}

//动态创建option的操作
//清空option
function clearOption(selectId){
    var selectObj = document.getElementById(selectId);
    for(var i = 0,len = selectObj.options.length; i < len; i++){
        selectObj.options[0] = null;
    }
}

//firstOption 为默认首选项
function setSelectOption(selectId, optionList, firstOption, selected,key,v,firstValue){
    clearOption(selectId)
    var selectObj = document.getElementById(selectId);
    var cnt = 0;
    if(firstOption){
    	console.log(firstValue)
    	if (firstValue!=undefined){
        	selectObj.options[0] = new Option(firstOption,firstValue);
    	} else {
			selectObj.options[0] = new Option(firstOption,"");
		}
        cnt++;
    }
    for(var i = 0;i < optionList.length; i++){
        console.log(optionList[i]);
        if(key && v){
            selectObj.options[cnt] = new Option(optionList[i][key],optionList[i][v]);
        }
        else{
            selectObj.options[cnt] = new Option(optionList[i],optionList[i]);
        }
        if(selected == optionList[i]){
            selectObj.options[cnt].selected = true;
        }
        cnt++
    }
}

var notify = function(uniqueId){
	$.notify({
        inline: true,
        html: '',
        id:uniqueId,
        close: '<a href="#" style="color:#fff">关闭</a>',
        onComplete: function(){setTimeout(function(){$("#"+uniqueId).find('.notify_content').scrollTop($("#"+uniqueId).find('.notify_content')[0].scrollHeight);},500)}
 	});
}


function ajaxPost(formId,url,callBack) {
    var form = $("#" + formId);
    var params = form.serialize();
    $.post(url, params).done(function(responseText){
            data = jQuery.parseJSON(responseText);
            if(data.code<=0) {
                alert(data.msg);
            } else if(!!callBack){
            	if (typeof callBack=='string') {
                	window.location = callBack;
            	} else {
            		callBack(data);
            	}
            } else {
                console.log(data.msg);
            }
       })
    .fail(function(res){
     	var data = jQuery.parseJSON(res.responseText);
     	if (!!data.msg) {
     		alert(data.msg);
     	} else {
         	alert('服务器连接失败!');
     	}    
     });
    return false;
}

function ajaxGet(url,params,callBack) {
	 $.get(url,params).done(function(responseText){
	        data = jQuery.parseJSON(responseText);
	        if(data.code<=0) {
	            alert(data.msg);
	        } else if(!!callBack){
	        	if (typeof callBack=='string') {
	            	window.location = callBack;
	        	} else {
	        		callBack(data);
	        	}
	        } else {
	            console.log(data.msg);
	        }
	   })
	.fail(function(res){
	 	var data = jQuery.parseJSON(res.responseText);
	 	if (!!data.msg) {
	 		alert(data.msg);
	 	} else {
	     	alert('服务器连接失败!');
	 	}    
	 });
    return false;
}