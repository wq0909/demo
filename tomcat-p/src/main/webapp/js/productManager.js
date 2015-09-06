
/******处理产品管理异常******/
function checkProductSubmit(){
    //产品名称不能为空
    if ($('input[name=productName]').get(0).value == ''){
        $("p.u-tip.u-tip-2.u-tip-w5.u-itmtip.empty-productName").show();
        return false ;
    }
    if (!english.test($('input[name=productName]').get(0).value)){
    	$("p.u-tip.u-tip-2.u-tip-w5.u-itmtip.error-productName").show().delay(3000).fadeOut();
    	return false;
    }
    //检查产品名称的格式
    if ($('input[name=productName]').get(0).value){
        var regx = /^[a-zA-Z]/;
        var regx_b = /[a-zA-z]+[\s]+\w+$/;//名称内不允许有空格
        if (!regx.test($('input[name=productName]').get(0).value)) {
            $("p.u-tip.u-tip-2.u-tip-w5.u-itmtip.error-productName").show();
            return false;
        }
        if (regx_b.test($('input[name=productName]').get(0).value)) {
            $("p.u-tip.u-tip-2.u-tip-w5.u-itmtip.error-productName2").show();
            return false;
        }
    }
    //产品负责人不能为空
    if ($('input[name=directorAccount]').get(0).value == ''){
        $("p.u-tip.u-tip-2.u-tip-w5.u-itmtip.empty-directorAccount").show();
        return false ;
    }
}

//产品管理页面，点击显示不同按钮的操作显示
function changeProductClassName(modifyconfigid, deleteopsid, className, cbl) {
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
      //拿到当前产品权限后处理
      var params = {"productId":opts};
      ajaxGet('/product/getRoleByProductId', params, function(data) {
        var role = data.params.role;
        if (role == 'admin' || role =='env_mod_pro'){
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

//点击tr元素中的一行，选中该行的复选框
function selectProCheckBox(obj){
    if (event.target.tagName != 'INPUT'){
        var node = obj.firstElementChild.firstElementChild;
        node.checked=!node.checked;
    }
    changeProductClassName('modifyconfig', 'deleteops', 'u-btn-1-dis');
    return;
}

//全选操作
function selectProductAllCheckbox(ParentID, bool){
    var pid = document.getElementById(ParentID);
    var cb = pid.getElementsByTagName("input");
    var cbl = cb.length;
    for (var i = 0; i < cbl; i++ ){
        if ( cb[i].type == "checkbox" ){
            cb[i].checked = bool;
        }
        changeProductClassName('modifyconfig', 'deleteops', 'u-btn-1-dis' ,cbl);
    }
}

//修改产品
function modifyProductConfig() {
var opts = $("input[name=showbox]:checked").val();
    if(!opts) {
        alert("请先选择产品");
        return;
    }
    else if ($("#modifyconfig").attr("class") == 'u-btn u-btn-1 u-btn-1-dis' ){
        alert("您无操作权限！");
        return;
    }
    else if (selectNum != 1){
        alert("只能选一个产品操作");
        return;
    }
    
    var value = "productId="+opts;
    location.href="/product/modifyProductConfig?"+value;
    return;
}

//删除产品
function deleteProduct() {
    var opts = $("input[name=showbox]:checked").val();
    if(!opts){
        alert("请先选择产品");
        return;
    }
    if ($("#deleteops").attr("class") == 'u-btn u-btn-1 u-btn-1-dis' ){
        alert("您无操作权限！");
        return;
    }
    if (confirm('确定删除产品?')){
		$.post("/product/deleteProduct",{productId:opts},function(res){
			var data = $.parseJSON(res);
			if (!!data.code) {
				location.href='/product/productManager';
			} else {
				alert(data.msg);
			}
		}).fail(function(res){
	     	var data = jQuery.parseJSON(res.responseText);
 	     	if (!!data.msg) {
	     		alert(data.msg);
	     	} else {
	         	alert('服务器连接失败!');
	     	}
     	});
    }
}

