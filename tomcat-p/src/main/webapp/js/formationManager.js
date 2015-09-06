//增加实例
function addInstance(productId,moduleId,envId){
    var value= "productId="+productId+"&"+"moduleId="+moduleId+"&"+"envId="+envId;
    location.href = "/runtime/addFormation?" + value;
}

//刷新操作
function flashInstanceManager(url){
    location.href=url;
 }
//修改实例配置
function modifyInstanceConfig(productId,moduleId,envId) {
   var optIns = $("input[name=showbox]:checked").val();
   if(!optIns)
    {
        alert("请先选择实例");
        return;
    }
    var url = "productId="+productId+"&"+"moduleId="+moduleId+"&"+"envId="+envId+"&"+"formationId="+optIns;
    location.href="/runtime/modifyFormationConfig?"+url;
}

function resizeFormation(productId,moduleId,envId) {
   var formationId = $("input[name=showbox]:checked").val();
   if(!formationId) {
        alert("请先选择实例");
        return;
    }
    $("#resizeDiv").show();
    $("select#replicas option[value="+$("#replicas"+formationId).val()+"]").attr('selected',true);
    $("#closeMeBtn").click(function(){
    	$("#resizeDiv").hide();
    });
    $("#resizeConfirmBtn").unbind('click').click(function(){
    	var postfix = "?productId="+productId+"&"+"moduleId="+moduleId+"&"+"envId="+envId+"&"+"formationId="+formationId;
    	var url = "/runtime/resize" + postfix;
    	ajaxPost("formationForm",url,function(data){
    		if (!!data.params.uniqueId){
     			refreshLog(data.params.uniqueId);
     			refreshStatus(null,"/runtime/getStatus" + postfix,"list");
    			$("#resizeDiv").hide();   			
    		}
    	});
    });
    $("#formationSpan").text($("#formationName"+formationId).val());
    return ;
}

var deployTimerId;
 
var refreshLog = function(uniqueId) {
	var socket,hostname, port, protocol;
	protocol = location.protocol, hostname = location.hostname, port = 9091;
	socket = io.connect("http://"+hostname+":" + port,{forceNew:true}).on('data', function(data) {
		var result = JSON.parse(data);
		var target = $("#"+uniqueId).find(".notify_content");
		target.append("<p>"+result.msg+"</p>");
		target.scrollTop(target.prop("scrollHeight"),500);
		if (result.code==205) {
			setTimeout(function(){clearInterval(deployTimerId)},5000);
		}
		return ;
	}).emit('announce_weblog_client', uniqueId).on('disconnect',function(){
		console.log('disconnetct....................');
	});
    socket.on('connect', function(data) {
 		notify(uniqueId);
    });
}
//选择实例删除
function deleteInstance(productId,moduleId,envId) {
    var optIns = $("input[name=showbox]:checked").val();
    var value = "productId="+productId+"&"+"moduleId="+moduleId+"&"+"envId="+envId+"&"+"formationId="+optIns;
    if(!optIns)
    {
        alert("请先选择实例");
        return;
    }
    if (confirm('确定删除实例?')){
    	 $("#deleteops").prop("disabled",true).delay(5000).queue(function(next) {
		    $(this).prop('disabled', false);
		    next();
		 });
         refreshStatus("/runtime/deleteFormation?"+value,"/runtime/getStatus?" + value,"list");
        return;
    }
}

//选择实例start
function restartInstance(productId,moduleId,envId) {
	var optIns = $("input[name=showbox]:checked").val();
    var value = "productId="+productId+"&"+"moduleId="+moduleId+"&"+"envId="+envId+"&"+"formationId="+optIns;
    if(!optIns)
    {
        alert("请先选择实例");
        return;
    }
    var w = confirm('确定启动实例?');
    if(w) {
        refreshStatus("/runtime/restartFormation?"+value,"/runtime/getStatus?"+value,"list");
        return;
    }
}

//选择实例stop
function stopInstance(productId,moduleId,envId) {
	var optIns = $("input[name=showbox]:checked").val();
    var value = "productId="+productId+"&"+"moduleId="+moduleId+"&"+"envId="+envId+"&"+"formationId="+optIns;
    if(!optIns)
    {
        alert("请先选择实例");
        return;
    }
    var w = confirm('确定停止实例?');
    if(w) {
    	$("#stopops").prop("disabled",true).delay(5000).queue(function(next) {
		    $(this).prop('disabled', false);
		    next();
		});
        //location.href="/runtime/stopInstance?"+value;
        refreshStatus("/runtime/stopFormation?"+value,"/runtime/getStatus?"+value,"list");
        return;
    }
}

var deployTimerId;

var envStatus = function(refresh,div){
	var target = $("#"+div);
	var orgHtml = target.find(".icon32x32");
 	$.get(refresh, function(data) {
 		$("#"+div).html(data);
		deployTimerId = setTimeout(envStatus.bind(null,refresh,div),2000);
	});
}

var refreshStatus = function(req,refresh,div) {
	if (!!req) {
	   ajaxGet(req,{},function(){
	   	   deployTimerId = setTimeout(envStatus.bind(null,refresh,div),2000);
	   });
	} else {
		deployTimerId = setTimeout(envStatus.bind(null,refresh,div),2000);
	}
}

//选择实例部署
function deployInstance(productId,moduleId,envId) {
	var optIns = $("input[name=showbox]:checked").val();
    var value = "productId="+productId+"&"+"moduleId="+moduleId+"&"+"envId="+envId+"&"+"formationId="+optIns;
    if(!optIns) {
        alert("请先选择实例");
        return;
    }
    $("#immedExec").prop("disabled",true).delay(5000).queue(function(next) {
	    $(this).prop('disabled', false);
	    next();
	}); 
    //location.href="/runtime/deployInstance?"+value;
    refreshStatus("/runtime/deployFormation?"+value,"/runtime/getStatus?"+value,"list");
    return;
}

function copyInstance(productId,moduleId,envId) {
	var optIns = $("input[name=showbox]:checked").val();
    var value = "productId="+productId+"&"+"moduleId="+moduleId+"&"+"envId="+envId+"&"+"formationId="+optIns;
    if(!optIns) {
        alert("请先选择实例");
        return;
    }
    refreshStatus("/runtime/copyFormation?"+value,"/runtime/getStatus?"+value,"list");
    return;
}

function trclick(event,obj){
	if (event.target.type !== 'radio') {
      $(obj).find('[type="radio"]').prop('checked','ture');
      changeInstanceClassName('u-btn-1-dis')
    }
}

function trclick2(event,obj){
	if (event.target.type !== 'radio') {
      $(obj).find('[type="radio"]').prop('checked','ture');
      showFormationDetail('u-btn-1-dis')
    }
}

function showFormationDetail(className){
	showInsTabInfo('instanceTabInfo0');
	var containerId = $("[name='showbox']:checked").val();
    $('.'+className).each(function(){
    	$(this).removeClass(className);
    });
     if (containerId>0){
    	  ajaxGet('/runtime/getContainerDetail', {"containerId":containerId}, function(data) {
             if (!data.params.tc){
             	return ;
             }
             var container = data.params.tc;
             console.log(container.createTime);
             $("#instanceId").html(container.formationId);
             $("#containerId").html(container.id);
             $("#instanceCreateTime").html($.format.date(container.createTime,"MM/dd/yyyy HH:mm:ss"));
             $("#instanceUpdateTime").html($.format.date(container.updateTime,"MM/dd/yyyy HH:mm:ss"));
             $("#podId").html(container.podId);
             $("#podId").html(container.podId);
             $("#rcName").html(container.rcName);
             $("#lanIp").html(container.lanIp);
             $("#nodeId").html(container.node);
             $("#tenantId").html(container.tenantId);
        });
     }
}
//实例管理页面，点击不同按钮显示操作
function changeInstanceClassName(className) {
    clearInterval(deployTimerId);
    var formationId = $("[name='showbox']:checked").val();
    $('.'+className).each(function(){
    	$(this).removeClass(className);
    });
    if (formationId>0){
    	  ajaxGet('/runtime/getReason', {"formationId":formationId}, function(data) {
             if (!data.params.formation){
             	return ;
             }
             var formation = data.params.formation;
             $("#instanceId").html(formation.formationId);
             $("#instanceCreateTime").html($.format.date(formation.createTime,"MM/dd/yyyy HH:mm:ss"));
             $("#instanceUpdateTime").html($.format.date(formation.updateTime,"MM/dd/yyyy HH:mm:ss"));
             $("#instanceUniqueId").html(formation.lastCmdUniqueId);
             $("#instanceDockerImageUri").html(formation.dockerImageUri);
        });
        ajaxGet('/runtime/getContainer', {"formationId":formationId}, function(data) {
        	 $("#instanceOpIp").html('');
             if (!data.params.nlb) return;
             $("#instanceOpIp").html(data.params.nlb.vip);
             $("#tab2info").html("");
             var html = "";
             for (var i in data.params.lcs){
             	var t = data.params.lcs[i];
             	html+="<li>"
             	html+="<span onclick=xopen('"+t.containerId+"','"+t.node+"')>ip:"+t.lanIp+"</span>&nbsp;&nbsp;"
             	html+="<span onclick=xopen('"+t.containerId+"','"+t.node+"')>性能</span>&nbsp;&nbsp;"
             	html+="<span onclick=xopen2('"+t.containerId+"','"+t.node+"')>日志</span>&nbsp;&nbsp;"
             	html+="</li>";
             }
             $("#tab2info").html(html);
        });
        ajaxGet('/runtime/getLogList', {"formationId":formationId}, function(data) {
        	$("select#instanceLogFiles option").remove();
            $('#instanceLogFiles').data(data.params.cmds);
            $.each(data.params.cmds,function(index,val){
				var text = val.start;
            	var value = val.recordId;
				$('#instanceLogFiles').append($('<option/>', {value:value, text:text}));
			});
			var d = data.params.cmds[0];
			$("#instanceOpTime").html(d.start);
			$("#instanceOpEndTime").html(d.end);
			$("#instanceDetail").html(d.msg);
			return ;  
			$('#instanceLogFiles').click(function(){
				var v = $('#instanceLogFiles').val();
				if (v>=0) {
				  var params={envId:envId,logId:v};
				  ajaxGet('/env/getEnvDetailLog', params, function(data) {
				  		var b = data.params.params;
 				  	    envBuildUUID.innerHTML = b.uniqueId;
						envBuildStartTime.innerHTML = b.start;
            			envBuildEndTime.innerHTML =  b.end;
            			envBuildPath.innerHTML = b.buildPath;
            			envBuildVersion.innerHTML = b.buildVersion;
            			var log = b.detail.replace(new RegExp(/\n/g),"<br/>");
            			envBuildLog.innerHTML= log;
					});  
 				}
			});	
			return ;
            var logs = data.params.logList;
            if (!logs) return;
             $("select#instanceLogFiles option").remove();
            $.each(logs, function (index, value) {
		    $('#instanceLogFiles').append($('<option/>', { 
		        value: value,
		        text : value 
		    	}));
			});
			$("#logViewBtn").unbind('click').click(function(){
				clearInterval(logTimer);
				var fileName = $("#instanceLogFiles").val();
				if (fileName==0) return;
				var lines = $("#logLinesTxt").val();
				if (isNaN(lines) || lines<1 || lines>1000){
					alert("行数输入错误");
					return ;
				}
				var params = {"formationId":formationId,"fileName":fileName,"lines":lines};
				var getLog = function(params) {
					ajaxGet('/runtime/getLog',params , function(data) { console.log(data.msg); $("#logContentSpan").html(data.msg); });
				};
				if ($("#logRefreshBox").is(':checked')){
					var times = $("#logTimeTxt").val();
					if (isNaN(times) || times<2000) {
						alert("时间输入错误");
						return;
					}
					logTimer = setInterval(function(){getLog(params);},times);
				} else {
					getLog(params);
				}
				
			});
        });
    } else {
    }
}

var logTimer;

function showInsTabInfo(id) {
	$( ".hd" ).each(function(target){
		$(this).removeClass('crt')
	});
	$( ".info" ).each(function(target){
		$(this).hide()
	});
	$( "#"+id).addClass('crt').show();
	$(".info."+id).each(function(target){
		$(this).show();
	});
}




function postFormDirectInstance(formId, url, callBackUrl) {
    var form = $("#" + formId);
    var params = form.serialize();
    var tparams = $('.empty-tparam');
	for (var i=0;i<tparams.length;i++){
		var tp = tparams[i];
		if (!$.trim($(tp).siblings().val())){
			$(tp).show().delay(3000).fadeOut();
			return false;
		}
	}
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
     	if (!!data.msg) {
     		alert(data.msg);
     	} else {
         	alert('服务器连接失败!');
     	}     });
    return false;
}


function showInsPerform() {
	showInsTabInfo('instanceTabInfo1');
	var containerId = $("[name='showbox']:checked").val();
	if (!containerId) return ;
 	var url = '/runtime/cadvisor/?containerId='+containerId;
	$("#perfFrame").attr('src',url);
}

function showInsLog() {
	showInsTabInfo('instanceTabInfo2');
	var containerId = $("[name='showbox']:checked").val();
	if (!containerId) return ; 
 	var url = '/runtime/terminal/'+containerId;
	$("#perfFrame2").attr('src',url);
}

function showVNC() {
	showInsTabInfo('instanceTabInfo3');
	var formationId = $("[name='showbox']:checked").val();
	if (!formationId) return ;
	var cip = $("#cip" +formationId).val();
	var cid = $("#cid" +formationId).val();
	var url = 'http://localhost:8181//static/terminal.html?ip='+cip;
	$("#perfFrame3").attr('src',url);
}
 
function xopen2(id,ip){
}

