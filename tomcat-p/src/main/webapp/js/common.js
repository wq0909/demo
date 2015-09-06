function replaceAll(find, replace, str) {
  return str.replace(new RegExp(find, 'g'), replace);
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

function appendConfHtml(){
	var html = "";
	var target = $('#selfdetaillist');
    target.html("");
    $("select#SelFileContent option").each(function(index,val){
    	var file = $(this).text();
    	var conf = $(this).val();
    	var id= new Date().getTime();
		var fileHtml = $('<input  style="display:" name="files" id=file'+id+'  type="text" />').val(file);
		var confHtml = $('<textarea style="display:"  id=conf'+id+' name="confs" ></textarea>').val(conf);
   		target.append(fileHtml,confHtml);
   		$("#file"+id).val(file);
   		$("#conf"+id).val(conf);
	});
	return;
}

function saveConfClick(){
	var fileName=$.trim($("#InpFileName").val());
	var content=$.trim($("#TexContent").val());
	if (fileName=="" || content=="") {
		alert("请输入文件名或者配置内容");
	} else {
		var target = $("select#SelFileContent option");
		var exist = false;
		for (var i = 0;i<target.length;i++){
			var option = target[i];
			if (fileName==$(option).text()){
				$(option).val(content);
				exist = true;
				break;
			} 
		}
		if (!exist) {
			var target = $('#selfdetaillist');
			var id= fileName;
			var fileHtml = $('<input  style="display:" name="files" id=file'+id+'  type="text" />').val(fileName);
			var confHtml = $('<textarea style="display:"  id=conf'+id+' name="confs" ></textarea>').val(content);
			console.log(fileHtml.val());
			console.log(confHtml.val())
	   		target.append(fileHtml,confHtml);
			($('#SelFileContent').append($('<option/>', {value:fileName, text:fileName})));
			$($('#SelFileContent option')[target.length]).prop('selected', 'selected');
		} else {
			var target = document.getElementById("conf"+fileName);
			if (!!target) {
				$(target).val(content);
			}
		}
		$("#InpFileName").val("");
		$("#TexContent").val("");
		return ;
		$("select#SelFileContent option").each(function(index,val){
			console.log("val " + $(val).text());
			if (fileName==$(val).text()){
				$(val).val(content);
			} else {
 				$('#SelFileContent').append($('<option/>', {value:content, text:fileName}));
			}
		});
		return ;
	}
}

function delConfClick(){
	var target = $("select#SelFileContent").find('option:selected');
 	var confTarget = document.getElementById("conf"+target.text());
 	var fileTarget = document.getElementById("file"+target.text());
 	$(confTarget).remove();
 	$(fileTarget).remove();
 	target.remove();
	return;
}

function selectConfClick(){
	var file=$("#SelFileContent option:selected").text();
	$("#InpFileName").val(file);
	var target = document.getElementById("conf"+file);
	if (!!target) {
		$("#TexContent").val($(target).val());
	}
};