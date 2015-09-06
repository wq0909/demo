/******处理权限页面异常******/
function checkAddPermSubmit(){
    //用户名称不能为空
    if ($.trim($('textarea[name=permAccount]').get(0).value) == ''){
        $("p.u-tip.u-tip-2.u-tip-w5.u-itmtip.empty-permAccount").show().delay(3000).fadeOut();
        return false ;
    }
    //名称的格式
 }


//选择授权类型
function selectPermType(value,var1){
    if(value == 2) {
        var1.style.display = "";
        var target = $("#moduleSelect");
        if (target.val() == 0){
        	target.find('option:selected').text("请选择应用")
         }
    } else {
        var1.style.display = "none";
    }
}


//选择产品后异步刷新应用列表
function changeModuleByProduct(url, s){
    var params = {"productId":s};
    ajaxGet(url, params, function(data) {
        //调用page.js中的创建option
       var type = $("[name='permType']:checked").val()
        if (data.params.moduleList.length>0 && type ==1){
        	setSelectOption("moduleSelect", data.params.moduleList, '全选应用', 'selected',"moduleName","moduleId",0);
        } else {
        	setSelectOption("moduleSelect", data.params.moduleList, '请选择应用', 'selected',"moduleName","moduleId",-1);
        }
    });
}

//权限管理页面，点击显示按钮，显示不同操作
function changePermClassName(deleteopsid, className) {
    var f = document.getElementsByName("showbox");
    var opts="";
    var selectNum = 0;
    for(var i=0; i < f.length; i++) {
        if(f[i].checked) {
            opts += f[i].value+",";
            selectNum++;
        }
    }
    if (selectNum == 0 || selectNum >1){
        $("#" + deleteopsid).removeClass(className).addClass(className);
    }
    else{
        $("#" + deleteopsid).removeClass(className);
    }
}
//选择应用后异步刷新环境列表
function changeEnvByModule(url, s){
    var params = {"moduleId":s};
    ajaxGet(url, params, function(data) {
        //调用page.js中的创建option
        setSelectOption("envSelect", data.params.envList, '请选择环境', 'selected',"envName","envId",-1);
    });
}


//删除权限
function deletePerm() {
    var f = document.getElementsByName("showbox");
    var opts="";
    var num = 0;
    for(var i=0; i < f.length; i++) {
        if(f[i].checked) {
            opts += f[i].value+",";
            num++;
        }
    }
     if(!opts){
        alert("请先选择要删除的权限记录");
        return;
    }
    var w = confirm('确定删除?');
    if(w) {
        var value= "authId="+opts;
        location.href="/permission/DeletePerm?"+value;
        return;
    }
}

