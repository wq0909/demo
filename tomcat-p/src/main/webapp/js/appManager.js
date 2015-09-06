
/******处理应用管理异常******/
function checkModuleSubmit(){
    //应用名称不能为空
    if ($('input[name=moduleName]').get(0).value == ''){
        $("p.u-tip.u-tip-2.u-tip-w5.u-itmtip.empty-moduleName").show();
        return false ;
    }
    if (!english.test($('input[name=moduleName]').get(0).value)){
    	 $("p.u-tip.u-tip-2.u-tip-w5.u-itmtip.error-moduleName").show().delay(3000).fadeOut();
         return false;
    }
    //检查应用名称的格式
    if ($('input[name=moduleName]').get(0).value){
        var regx = /^[a-zA-Z]/;
        var regx_b = /[a-zA-z]+[\s]+\w+$/;//名称内不允许有空格
        if (!regx.test($('input[name=moduleName]').get(0).value)) {
            $("p.u-tip.u-tip-2.u-tip-w5.u-itmtip.error-moduleName").show();
            return false;
        }
        if (regx_b.test($('input[name=moduleName]').get(0).value)) {
            $("p.u-tip.u-tip-2.u-tip-w5.u-itmtip.error-moduleName2").show();
            return false;
        }
    }
    //应用负责人不能为空
    if ($('input[name=directorAccount]').get(0).value == ''){
        $("p.u-tip.u-tip-2.u-tip-w5.u-itmtip.empty-directorAccount").show();
        return false ;
    }
}
//刷新操作
function flashModuleManager(productId) {
    location.href="/app/appManager?productId="+productId;
    return;
}

function changeByProduct(productId){
	location.href="/app/appManager?productId="+productId;
}

function trclick(event,obj){
	if (event.target.tagName==='A') {
 		return true;
	}
	if (event.target.type !== 'checkbox') {
		$(obj).find('[type="checkbox"]').click();
	}
}

//应用管理页面，点击显示按钮，显示不同操作
function changeModuleClassName(productId,modifyconfigid, deleteopsid, className, cbl) {
    var f = document.getElementsByName("showbox");
    var opts;
    var selectNum = 0;
    for(var i=0; i < f.length; i++) {
        if(f[i].checked) {
            opts = f[i].value;
            selectNum++;
        }
    }
    if(opts == null || selectNum >1 || cbl >1){
        $("#" + modifyconfigid).removeClass(className).addClass(className);
        $("#" + deleteopsid).removeClass(className).addClass(className);
        return;
    }
    else{
      var params = {"productId":productId, "moduleId":opts};
      ajaxGet('/app/getRoleByModuleId', params, function(data) {
        var role = data.params.role;
        if (role == 'admin' || role =='env_mod_pro' || role == 'env_mod'){
            if (selectNum == 0 || selectNum >1){
                $("#" + modifyconfigid).removeClass(className).addClass(className);
            }
            if (selectNum == 1){
                $("#" + modifyconfigid).removeClass(className);
            }
            if(selectNum > 0) {
                $("#" + deleteopsid).removeClass(className);
            } else {
                $("#" + deleteopsid).removeClass(className).addClass(className);
            }
        }
        });
    }
}
//全选操作
function selectModuleAllCheckbox(ParentID, bool){
    var pid = document.getElementById(ParentID);
    var cb = pid.getElementsByTagName("input");
    var cbl = cb.length;
    for (var i = 0; i < cbl; i++ ){
        if ( cb[i].type == "checkbox" ){
            cb[i].checked = bool;
        }
        changeModuleClassName('modifyconfig', 'deleteops', 'u-btn-1-dis',cbl);
    }
}

//修改应用
function modifyModuleConfig(productId) {
    var target = $("[name=showbox]:checked");
    var selectNum = target.length;
    if (selectNum != 1){
        alert("请选择一个应用操作");
        return;
    }
    var opts = target[0].value;
    var value= "productId="+productId+"&"+"moduleId="+opts;
    location.href="/app/modifyAppConfig?"+value;
    return;
}

//删除应用
function deleteModule(productId) {
    var target = $("[name=showbox]:checked");
    var selectNum = target.length;
    if (selectNum != 1){
        alert("请选择一个应用操作");
        return;
    }
    var opts = target[0].value;
    if (confirm('确定删除应用?')){
		$.post("/app/deleteApp",{productId:productId,moduleId:opts},function(res){
			var data = $.parseJSON(res);
			if (data.code>0) {
				location.href='/app/moduleApp?productId='+productId;
			} else {
				alert(data.msg);
			}
		});
    }
}

