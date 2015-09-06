/**
 * Created by IntelliJ IDEA.
 * User: hzzhanyr
 * Date: 14-1-8
 * Time: 下午4:36
 * To change this template use File | Settings | File Templates.
 */

function importKey(files) {
    if(typeof FileReader == 'undefined') {
        alert("抱歉，你的浏览器不支持 FileReader");
    }
    var file = files[0];
    var reader = new FileReader();
    reader.readAsText(file);
    reader.onload = function() {
        document.getElementById("privateKey").innerHTML = reader.result;
    }
}

function trclick(event,obj){
	if (event.target.type !== 'checkbox') {
		$(obj).find('[type="checkbox"]').click();
	}
}

//删除key
function deleteKey() {
    var f = document.getElementsByName("showbox");
    var opts = '';
    for(var i=0; i < f.length; i++) {
        if(f[i].checked) {
            opts = opts + f[i].value + ',';
        }
    }
    opts = opts.substring(0,opts.length-1);
    if(opts == null)
    {
        alert("请先选择密钥对");
        return;
    } else {
        var w = confirm('确定删除密钥对?');
        if(w) {
            var value= "keyId="+opts;
            location.href="/key/deleteKey?"+value;
            return;
        }
    }
}

//全选操作
function selectKeyAllCheckbox(ParentID, bool){
    var pid = document.getElementById(ParentID);
    var cb = pid.getElementsByTagName("input");
    var cbl = cb.length;
    for (var i = 0; i < cbl; i++ ){
        if ( cb[i].type == "checkbox" ){
            cb[i].checked = bool;
        }
        changeKeyClassName('deleteops', 'u-btn-1-dis');
    }
}

//点击显示不同按钮操作显示
function changeKeyClassName(deleteopsid, className) {
    var f = document.getElementsByName("showbox");
    var opts;
    var selectNum = 0;
    for(var i=0; i < f.length; i++) {
        if(f[i].checked) {
            opts = f[i].value;
            selectNum++;
        }
    }
    if(selectNum > 0) {
        $("#" + deleteopsid).removeClass(className);
    } else {
        $("#" + deleteopsid).removeClass(className).addClass(className);
    }
}