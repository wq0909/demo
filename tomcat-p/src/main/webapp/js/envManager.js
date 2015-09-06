/*------------------页面类操作-------------------------*/

//获取conf值
function getConfValue(v){
    $("#confid").val(v);
}

function trclick(event,obj){
	if (event.target.type !== 'radio') {
      $(obj).find('[type="radio"]').trigger('click');
    }
}

//选择环境类型，是已有列表中选择还是新建
function selectEnv(value,var1,var2) {
    if(value == 1) {
        var2.style.display = "none";
        var1.style.display = "";
    }
    else if(value == 2)  {
        var2.style.display = "";
        var1.style.display = "none";
    }
}

//选择仓库类型
function selectRepos(value,var1,var2,var3) {
    //var existEnv = document.getElementById("var1");
    //var nonExistEnv = document.getElementById("var2");
   if(value == 'svn') {
       var3.style.display = "none";
       var2.style.display = "none";
       var1.style.display = "";
    }
    else if(value == 'git')  {
       var3.style.display = "none";
       var2.style.display = "";
       var1.style.display = "none";
    }
    else {
       var3.style.display = "";
       var2.style.display = "none";
       var1.style.display = "none";
   }
}

//选择conf类型
function selectConf(value,targetVal,var1){
    if(value == targetVal) {
        $("#"+var1).show();
    } else {
      $("#"+var1).hide();
    }
}

function selectConf2(value,var1){
    var id = $("[name='buildVersionType']:checked").val();
    if(id == 2) {
    	$("#manVersion").show();
    } else {
        $("#manVersion").hide();
    }
}

//创建环境上一步按钮
function createEnvPrev(createEnvStep){
    if (createEnvStep == 'createEnvStep2'){
        $("#createEnvStep1").show();
        $("#createEnvStep2").hide();
        $("#createEnvStep3").hide();
        $("#createEnvStep4").hide();
    }
    else if (createEnvStep == 'createEnvStep3'){
        $("#createEnvStep1").hide();
        $("#createEnvStep2").show();
        $("#createEnvStep3").hide();
        $("#createEnvStep4").hide();
    }
    else if (createEnvStep == 'createEnvStep4'){
        $("#createEnvStep1").hide();
        $("#createEnvStep2").show();
        $("#createEnvStep4").hide();
    }
}

//创建环境下一步按钮
function createEnvNext(createEnvStep){
    if (createEnvStep == 'createEnvStep1'){
        /******环境管理异常处理******/
        //检查环境名称为空
        var selVal = $('[name="envType"]:checked').val();
        if (selVal==1 && !english.test($("#envName1").val())){
        	$("p.u-tip.u-tip-2.u-tip-w5.u-itmtip.error-envName").show().delay(3000).fadeOut();
         	return false;
        }
        if (selVal==2 && !english.test($("#envName2").val())){
    	 	$("p.u-tip.u-tip-2.u-tip-w5.u-itmtip.error-envName1").show().delay(3000).fadeOut();
         	return false;
    	}
        //检查环境负责人corp账号是否为空
        if ($("#directorAccount").val() == ''){
            $("p.u-tip.u-tip-2.u-tip-w5.u-itmtip.empty-directorAccount").show().delay(3000).fadeOut();
            return;
        }
        //点击下一步，子步骤的显示
        $("#createEnvStep1").hide();
        $("#createEnvStep2").show();
        $("#createEnvStep4").hide();
    }
    else if (createEnvStep == 'createEnvStep2'){
        /****** 异常检测并提示 ******/
        //当选择svn时，检查svn信息是否正确
        var svnRepoValue=$('input[name=vcPath]').get(0).value
        if ($("#svnRepos")[0].style.display == ''){
            if (svnRepoValue.replace(/\s*/g,"") == '' ) {
                $("p.u-tip.u-tip-2.u-tip-w5.u-itmtip.empty-vcPath").show().delay(3000).fadeOut();
                return;
            }
            //修复输入repos值时前后有空格或者空行的情况
            svnRepoValue=svnRepoValue.replace(/(^\s*)|(\s*$)/g,"");
            if (svnRepoValue) {
                var regx = /^(http|https|svn)/;
                if (!regx.test(svnRepoValue)) {
                    $("p.u-tip.u-tip-2.u-tip-w5.u-itmtip.error-vcPath").show().delay(3000).fadeOut();
                    return;
                }
            }
        }
        //当选择git时，检查git的信息是否正确
        if ($("#gitRepos")[0].style.display == ''){
        	var path = $('input[name=vcPath]').get(1).value;
            if (path == '') {
                $("p.u-tip.u-tip-2.u-tip-w5.u-itmtip.empty-vcPath").show().delay(3000).fadeOut();
                return;
            }
            if ($('input[name=vcPath]').get(1).value) {
                var regx = /^(ssh|http|git)/;
                if (!regx.test($('input[name=vcPath]').get(1).value)) {
                    $("p.u-tip.u-tip-2.u-tip-w5.u-itmtip.error-vcPath").show().delay(3000).fadeOut();
                    return;
                }
            }
            var key = $('.u-ipt-gitkey').val();
            if (!key) {
                var regx = /^(http)/;
                if (!regx.test(path)) {
                    $("p.u-tip.u-tip-2.u-tip-w5.u-itmtip.error-vcPathPublic").show().delay(3000).fadeOut();
                    return;
                }
            }
            
        }
        //当选择新建conf配置名称时，检查信息是否正确
        //if ($("#noConf")[0].style.display == ''){
            //if ($('input[name=conf]').get(0).value == '') {
               // $("p.u-tip.u-tip-2.u-tip-w5.u-itmtip.empty-conf").show();
               // return;
            //}
        //}
        $("#createEnvStep1").hide();
        $("#createEnvStep2").hide();
        $("#createEnvStep4").show();
        //修复选择器插件,无法设置css宽度的bug
        $("div.chosen-container").css("width","350px");
        var selVal = $('[name="envType"]:checked').val();
        $("#hintenvmsg").html($("#envName"+selVal).val()+" <br/>报警POPO:" + $("#directorAccount").val());
        var url = $('input[name=vcPath]').get(0).value;
         if($("#svnRepos")[0].style.display == '') {
         	url = "svn:" + url;
        } else if($("#gitRepos")[0].style.display == '') {
        	url = $('input[name=vcPath]').get(1).value;
        	url = "git:" + url;
        } else {
        	url = "none:war";
        }
        $("#hintbuildmsg").html(url);
        var msg = "服务器列表：" +  ids.join(",") +"<br/>";
        msg = msg + " " + "账号：" +  $('select[name=loginAccount] :selected').text()+"<br/>";
        if (!!$('select[name=dependenceId]').get(0).value) {
        	msg = msg + " 依赖版本： " +  $('select[name=dependenceId] :selected').text()+"<br/>";
        }
        if (!!$('select[name=xms] :selected').text()) {
	        msg = msg + " xms： " +  $('select[name=xms] :selected').text()+"<br/>";
	        msg = msg + " xmx： " +  $('select[name=xmx] :selected').text()+"<br/>";
	        msg = msg + " maxPerm： " +  $('select[name=maxPerm] :selected').text()+"<br/>";
        }
        $("#hintdeploymsg").html(msg);
    }
    else if (createEnvStep == 'createEnvStep3'){
        $("#createEnvStep1").hide();
        $("#createEnvStep2").hide();
        $("#createEnvStep4").show();
        var selVal = $('[name="envType"]:checked').val();
        $("#hintenvmsg").html($("#envName"+selVal).val()+" <br/>报警POPO:" + $("#directorAccount").val());
        var url = $('input[name=vcPath]').get(0).value;
         if($("#svnRepos")[0].style.display == '') {
         	url = "svn:" + url;
        } else if($("#gitRepos")[0].style.display == '') {
        	url = $('input[name=vcPath]').get(1).value;
        	url = "git:" + url;
        } else {
        	url = "none:war";
        }
        $("#hintbuildmsg").html(url);
        var msg = "服务器列表：" +  ids.join(",") +"<br/>";
        msg = msg + " " + "账号：" +  $('select[name=loginAccount] :selected').text()+"<br/>";
        if (!!$('select[name=dependenceId]').get(0).value) {
        	msg = msg + " 依赖版本： " +  $('select[name=dependenceId] :selected').text()+"<br/>";
        }
        if (!!$('select[name=xms] :selected').text()) {
	        msg = msg + " xms： " +  $('select[name=xms] :selected').text()+"<br/>";
	        msg = msg + " xmx： " +  $('select[name=xmx] :selected').text()+"<br/>";
	        msg = msg + " maxPerm： " +  $('select[name=maxPerm] :selected').text()+"<br/>";
        }
        $("#hintdeploymsg").html(msg);
    }
}

//选择应用后同步刷新页面
function changeByModule(moduleId){
	if (isNaN(moduleId)) return;
    var productId = $("#productSel").val();
    var value = "productId="+productId+"&"+"moduleId="+moduleId;
    window.location = "/env/envManager?"+value;
}

//环境页面刷新按钮
function flashEnvManager(){
    var productId = $("#productSel").val();
    var moduleId = $("#moduleSelect").val();
    var value = "productId="+productId+"&"+"moduleId="+moduleId;
    window.location = "/env/envManager?"+value;
}

/*//通过应用类型，选择应用配置显示web还是java工程
function changeModuleTypeById(url, s){
    var params = {"moduleId":s};
    ajaxGet(url, params, function(data) {
        var moduleType = data.params.moduleType;
        if (moduleType == 1){
            $("#javaWeb").show();
            $("#javaApp").hide();
        }
        else if (moduleType == 2){
            $("#javaApp").show();
            $("#javaWeb").hide();
        }
    });
}*/
 
//上传文件，获取绝对路径，兼容ie,ff等
function uploadFile() {
    var x = document.getElementById("upload");
    file=document.getElementById("upload").files[0];
    filename=file.name;
    filepath=x.value;
    //filepath=getPath(x)
    alert(filepath)
}
//点击tr元素中的一行，选中该行的复选框
function selectEnvCheckBox(event,obj,index,envId){
	  if (event.target.tagName==='A') {
 		return true;
	  }
     var o = $(".chose-line");
     if (!!o[0]) {
        	o[0].style.background="#ffffff";
        if (!!o[0]) {
        	o.removeClass('chose-line');
        }
     }
    $(obj).addClass('chose-line');
    obj.style.background="#e3f0f4";
    var existEnvId = $("#showbox").val();
    if (existEnvId) {
     	$("#builddeployops"+existEnvId).addClass("u-btn-3-dis");
    }
    $("#showbox").val(envId);
    changeEnvClassName('modifyconfig', 'startops', 'stopops', 'restartops', 'deleteops', 'crontaskops', 'buildops','rollbackops','builddeployops'+envId, 'u-btn-1-dis');
    return;
}

//环境管理页面，点击不同按钮，显示不同的操作
function changeEnvClassName(modifyconfigid, startopsid, stopopsid, restartopsid, deleteopsid, crontaskopsid, buildopsid, rollbackopsid, builddeployopsid, className) {
    var optEnv = $("#showbox").val();
    //ajax获取环境详情
    getEnvDetail(optEnv);
    var envVersion = detailForEnvCurrentVersion.innerHTML;
        if(optEnv){
	        $("#envdetail").show();
	        $("#envlog").show();
	        $('.u-btn-1-dis').each(function(){
		    	$(this).removeClass('u-btn-1-dis');
		    });
	        $("#" + builddeployopsid).removeClass('u-btn-3-dis');
	        $('.en-btn-tr').each(function(){
	        	$(this).removeClass(className);
	        })
        }
        else{
	        $("#envdetail").hide();
	        $("#envlog").hide();
 	        $("#" + builddeployopsid).removeClass('u-btn-3-dis').addClass('u-btn-3-dis');
	        $('.en-btn-tr').each(function(){
	        	$(this).removeClass(className).addClass(className);
	        })
       }
}

//ajax异步获取环境详情
function getEnvDetail(envId){
    if (envId){    	        	
        var params = {"envId":envId};
        ajaxGet('/env/getEnvDetail', params, function(data) {
        	showTabInfo('tabinfo1');
            detailForEnvId.innerHTML = typeof data.params.envId=='undefined'?"":data.params.envId;
            detailForEnvName.innerHTML = typeof data.params.envName=='undefined'?"":data.params.envName;
            detailForEnvDesc.innerHTML = typeof data.params.envDesc=='undefined'?"":data.params.envDesc;
            detailForEnvVcType.innerHTML = typeof data.params.envVcType=='undefined'?"":data.params.envVcType;
            detailForEnvVcPath.innerHTML = typeof data.params.envVcPath=='undefined'?"":data.params.envVcPath;
            detailForEnvCurrentVersion.innerHTML = typeof data.params.currentVersion=='undefined'?"":data.params.version;
            detailForEnvVcBranch.innerHTML = typeof data.params.envVcBranch=='undefined'?"":data.params.envVcBranch;
            detailForEnvAppName.innerHTML = typeof data.params.appName=='undefined'?"":data.params.appName;
            envBuildStartTime.innerHTML = typeof data.params.envStartTime=='undefined'?"":data.params.envStartTime;
            envBuildEndTime.innerHTML =  typeof data.params.envEndTime=='undefined'?"":data.params.envEndTime;
            envBuildPath.innerHTML = typeof data.params.envBuildPath=='undefined'?"":data.params.envBuildPath;
            envBuildVersion.innerHTML = typeof data.params.envBuildVersion=='undefined'?"":data.params.envBuildVersion;
            var log = typeof data.params.log=='undefined'?"":data.params.log;
            log = log.replace(new RegExp(/\n/g),"<br/>");
            envBuildLog.innerHTML = log;
            envBuildUUID.innerHTML = typeof data.params.uuid=='uuid'?"":data.params.uuid;
            $("select#instanceLogFiles option").remove();
            $('#instanceLogFiles').data(data.params.buildList);
            $.each(data.params.buildList,function(index,val){
				var text = val.start;
            	var value = val.recordId;
				$('#instanceLogFiles').append($('<option/>', {value:value, text:text}));
			});  
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
            
         });
    }
}

//获取应用下所有环境
function getEnvByModule(p,m){
	var value = "productId="+p+"&"+"moduleId="+m;
    if (isNaN(m)) return;
    window.location = "/env/envManager?"+value;
}

/*------------------下面是管理类操作-------------------------*/
//修改环境配置
function modifyEnvConfig(productId,moduleId) {
	var optEnv = $("#showbox").val();
    var value= "productId="+productId+"&"+"moduleId="+moduleId+"&"+"envId="+optEnv;
    if(!optEnv) { alert("请先选择环境"); return; }
    location.href="/env/modifyEnvConfig?"+value;
}

//选择环境删除
function deleteEnv(productId,moduleId) {
	var optEnv = $("#showbox").val();
	var purl = "productId="+productId+"&"+"moduleId="+moduleId;
    var value= purl+"&"+"envId="+optEnv;
	if(!optEnv) { alert("请先选择环境"); return; }
    if (confirm('确定删除环境?')){
    	$("#showbox").val("");
    	refreshStatus('/env/deleteEnv?' + value,"/env/getStatus?"+ purl,"list");
    	return;
		$.post("/env/deleteEnv",{productId:productId,moduleId:moduleId,envId:optEnv},function(res){
			var data = $.parseJSON(res);
			if (data.code>0) {
				location.href='/env/envManager?productId='+productId+"&moduleId="+moduleId;
			} else {
				alert(data.msg);
			}
		});
    }
}

//定时页面，点击显示按钮，显示不同操作
function changeCronClassName(deleteopsid, className) {
	if ($("[type=checkbox]:checked").length>=1) {
		$("#" + deleteopsid).removeClass(className);
	} else {
	    $("#" + deleteopsid).removeClass(className).addClass(className);
	}
}

function selectAllbox(obj){
	var checked = false;
	var className = "u-btn-1-dis";
	if ($(obj).prop("checked")) {
		checked = true;
		$("#deleteops").removeClass(className);
	} else {
		$("#deleteops").removeClass(className).addClass(className);
	}
	$("[name=showbox]").each(function(){
		$(this).prop("checked",checked);
	}); 
}

//定时任务
function crontaskEnv(productId,moduleId) {
	var optEnv = $("#showbox").val();
    var value= "productId="+productId+"&"+"moduleId="+moduleId+"&"+"envId="+optEnv;
    if(!optEnv) { alert("请先选择定时任务"); return; }
    location.href="/env/crontaskEnv?"+value;
}

//删除定时任务
function deleteCron(productId,moduleId,envId) {
	if ($("[name=showbox]:checked").length<=0){
	    alert("请先选择定时任务"); return; 
 	}
	var opts = $("[name=showbox]:checked").map(function(){return $(this).val()}).get().join(',');
    var w = confirm('确定删除?');
    if(w) {
        var value= "productId="+productId+"&"+"moduleId="+moduleId+"&"+"envId="+envId+"&"+"cronId="+opts;
        location.href="/env/deleteCron?"+value;
        return;
    }
}

/*------------------应用生命周期管理操作-------------------------*/
//构建环境
function buildEnv(productId,moduleId) {
    var optEnv = $("#showbox").val();
    var value= "productId="+productId+"&"+"moduleId="+moduleId+"&"+"envId="+optEnv;
    if(!optEnv) { alert("请先选择环境"); return; }
    refreshStatus('/env/buildEnv?' + value,"/env/getStatus?"+ value,"list");
    return;
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
 
var envStatus = function(refresh,div){
	var target = $("#"+div);
 	$.get(refresh, function(data) {
 		$("#"+div).html(data);
		deployTimerId = setTimeout(envStatus.bind(null,refresh,div),2000);
	});
}


var refreshStatus = function(req,refresh,div) {
   if (!!deployTimerId) {clearInterval(deployTimerId);}
   $.get(req, function(data) {
   		var jd = $.parseJSON(data);
   		if (jd.code>0){
   			refreshLog(jd.msg,0);
   			deployTimerId = setTimeout(envStatus.bind(null,refresh,div),2000);
   		} else {
   			if (!!jd.msg) {
   			 alert(jd.msg);
   			}
   		}
	}).done(function(responseText){}).fail(function(){clearInterval(deployTimerId);}).always(function(){});
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

//环境部署
function deployEnv() {
   	var productId = $("#productId").val();
   	var moduleId = $("#moduleId").val();
	var callBackUrl = "/env/envManager?productId="+productId+"&moduleId="+moduleId;
    var params = $("#deploy").serialize();
   	var optEnv = $("#showbox").val();
    if(!optEnv) { alert("请先选择环境"); return; }
    var value= "productId="+productId+"&"+"moduleId="+moduleId+"&"+"envId="+optEnv;
    refreshStatus('/env/deployEnv?' + value+"&" +params,"/env/getStatus?"+ value,"list");
    $("#buildVersionConfirm").hide();
    return;
    $.get('/env/deployEnv?' + value+"&" +params)
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
    .fail(function(data){
         alert('服务器连接失败!');
     });
    return false;
}

//选择环境，进行start操作
function startEnv(productId,moduleId) {
	var optEnv = $("#showbox").val();
	if(!optEnv) { alert("请先选择环境"); return; }
    var value= "productId="+productId+"&"+"moduleId="+moduleId+"&"+"envId="+optEnv;
    if(confirm('确定启动环境?')) {
        //location.href="/env/startEnv?"+value;
        refreshStatus('/env/startEnv?' + value,"/env/getStatus?"+ value,"list");
        return;
    }
}

//选择环境，进行stop操作
function stopEnv(productId,moduleId) {
	var optEnv = $("#showbox").val();
	if(!optEnv) { alert("请先选择环境"); return; }
    var value= "productId="+productId+"&"+"moduleId="+moduleId+"&"+"envId="+optEnv;
    //var w = confirm('确定停止环境?');
    if(confirm('确定停止环境?')) {
        //location.href="/env/stopEnv?"+value;
        refreshStatus('/env/stopEnv?' + value,"/env/getStatus?"+ value,"list");
        return;
    }
}

//选择环境，进行stop操作
function configStaticFile(productId,moduleId) {
	var optEnv = $("#showbox").val();
	if(!optEnv) { alert("请先选择环境"); return; }
    var value= "productId="+productId+"&"+"moduleId="+moduleId+"&"+"envId="+optEnv;
    location.href="/env/staticconfig?"+value;
}

//选择环境，进行restart操作
function restartEnv(productId,moduleId) {
	var optEnv = $("#showbox").val();
	if(!optEnv) { alert("请先选择环境"); return; }
	var value= "productId="+productId+"&"+"moduleId="+moduleId+"&"+"envId="+optEnv;
    var w = confirm('确定重启环境?');
    if(w) {
        //location.href="/env/restartEnv?"+value;
        refreshStatus('/env/restartEnv?' + value,"/env/getStatus?"+ value,"list");
        return;
    }
}

//环境回退到上一个版本
function rollbackEnv(productId,moduleId) {
	var optEnv = $("#showbox").val();
    var value= "productId="+productId+"&"+"moduleId="+moduleId+"&"+"envId="+optEnv;
    if(!optEnv)
    {
        alert("请先选择环境");
        return;
    }
    location.href="/env/rollbackEnv?"+value;
    return;
}

function showTabInfo(id) {
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

function buildVersionConfirm(envId,version){
    if($("#envTR"+envId).attr("class")) {
    	$("#buildVersion").val(version);
    	$("#buildAction").val(1);
        $("#buildVersionConfirm").show();
        $("#envbuildspan").text($("#envName"+envId).val());
        $("#closeMeBtn").click(function(){$("#buildVersionConfirm").hide()});
    }
    else{alert("请先选择环境");return;}
}

function buildVersionConfirm2(){
	var optEnv = $("#showbox").val();
	if(!optEnv) { alert("请先选择环境"); return; }
	var envId = optEnv;
 	if (parseInt($("#count"+envId).val())<=0) {
		alert("请添加实例后再操作");
		return;
	}
	$("#buildVersion").val();
	$("#buildAction").val(2);
	$("#envbuildspan").text($("#envName"+envId).val());
    $("#buildVersionConfirm").show();
    $("#closeMeBtn").click(function(){$("#buildVersionConfirm").hide()});
 }


function saveAs(productId,moduleId){
	var optEnv = $("#showbox").val();
	if(!optEnv) { alert("请先选择环境"); return; }
	 var params = {"envId":optEnv};
     ajaxGet('/env/getEnvDetail', params, function(data) {
		var envName = (typeof data.params.envName=='undefined'?"":data.params.envName);
		$("#saveAsEnvName").val(envName);
		var conf = (typeof data.params.conf=='undefined'?"":data.params.conf);
		$("#saveAsConf").val(conf);
		$("#saveAsProductId").val(productId);
		$("#saveAsModuleId").val(moduleId);
		$("#saveAsConfirm").show();
		$("#closeSaveAsBtn").click(function(){$("#saveAsConfirm").hide()});
    });    
}

function saveAsConfirm(){
	var optEnv = $("#showbox").val();
	if(!optEnv) { alert("请先选择环境"); return; }
	if (!english.test($.trim($("#saveAsEnvName").val()))){
    	$(".empty-saveAsEnvName").show().delay(3000).fadeOut();
        return false;
    }
    if ($.trim($("#saveAsConf").val())==""){
    	$(".empty-saveAsConf").show().delay(3000).fadeOut();
        return false;
    }
    var productId = $("#saveAsProductId").val();
    var moduleId = $("#saveAsModuleId").val();
	var params = {productId:productId,moduleId:moduleId,envId:optEnv,envName:$("#saveAsEnvName").val(),conf:$("#saveAsConf").val()};
	$.post("/env/saveAsEnv",params,function(res){
			var data = $.parseJSON(res);
			if (data.code>0) {
				$("#saveAsConfirm").hide();
				location.href='/env/envManager?productId='+productId+"&moduleId="+moduleId;
			} else {
				alert(data.msg);
			}
	});  
}

function changeTomcat(obj){
	 var val = $(obj).val();
	 var params = {"id":val};
     ajaxGet('/env/getDependId', params, function(data) {
		var type = data.params.result.type;
		if (type==1) {
		$("#tomcatdetailconfig").hide();
		$("#selfdetailconfig").show();
	} else {
		$("#tomcatdetailconfig").show();
		$("#selfdetailconfig").hide();
	}	
    });    
	return ;
	var val = $(obj).val();
	if (val.split("-")[1]=="1") {
		$("#tomcatdetailconfig").hide();
		$("#selfdetailconfig").show();
	} else {
		$("#tomcatdetailconfig").show();
		$("#selfdetailconfig").hide();
	}
	
}

