<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<title>管理配置</title>
<style>
    .hint, input[type=text], input[type=password]{font-size: 20px;border-radius: 2px;}
    div{margin: 5px;}
    input[type=text], input[type=password]{width: 478px;height: 12px;padding:11px 20px;border: 1px solid #ddd;margin-left:20px;}
    .hint{width: 320px;line-height: 46px;text-align: center;color: #fff;background-color: #ef968c;}
    input[type=button]{
        width: 420px;font-size: 26px;color: #fff;background-color: #00b58a;
        border-radius: 10px;box-shadow: 0px 3px 0px 0px #008263;text-shadow: 0px -3px 0px #008263;
    }
    @media screen and (min-width: 640px){
        .hint, input[type=text], input[type=password]{font-size: 24px;}
         input[type=text], input[type=password]{width: 65%;}
        .hint{width: 426px;line-height: 60px;}
        input[type=button]{width: 426px;}
    }
    @media screen and (max-width: 320px){
        .hint, input[type=text], input[type=password]{font-size: 16px;}
        div{margin: 20px;}
        input[type=text], input[type=password]{width: 180px;height: 20px;padding:6px 16px;}
        .hint{width: 214px;line-height: 32px;}
    }
</style>
</head>
<body>
	<form action="add" method="post">
    <div>请输入帐号<input type="text" name='pid' value="${config.pid?default("")}" placeholder="请输入pid"></div>
    <div>游戏名称<input type="text" name='account' value="${config.account?default("")}" placeholder="精灵标识代号"></div>
    <div>人工评价代号<input type="text" name='gameId' value="${config.gameId?default("")}" placeholder="人工评价代号"></div>
    <div>精灵标识代号<input type="text" name='passwd' value="${config.passwd?default("")}" placeholder="精灵标识代号"></div>
    <div>精灵游戏代号<input type="text" name='aiGameId' value="${config.aiGameId?default("")}" placeholder="精灵游戏代"></div>
    <div>精灵问答地址<input type="text" name='junAsk'  value="${config.junAsk?default("")}" placeholder="精灵问答地址"></div>
    <div>精灵评分地址<input type="text" name='junFeed' value="${config.junFeed?default("")}" placeholder="精灵评分地址"></div>
    <div>人工问答地址<input type="text" name='mmAsk' value="${config.mmAsk?default("")}" placeholder="人工问答地址"></div>
    <div>人工评分地址<input type="text" name='mmFeed' value="${config.mmFeed?default("")}" placeholder="人工评分地址"></div>
    <div>TOKEN申请地址<input type="text" name='token' value="${config.token?default("")}" placeholder="TOKEN申请地址"></div>
    <div>菜单数据<textarea style="height:200px;width:80%" name='tokenVaild'  placeholder="菜单数据">${config.tokenVaild?default("")}</textarea></div>
    <div class="hint">${error?default("")}</div>
    <div><input type="button" onclick="document.forms[0].submit();" value="确 定"></div>
    </form>
    <script>

    </script>
</div>
</body>
</html>