
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<title>绑定游戏帐号</title>
<meta http-equiv="pragma" content="no-cache" />
<meta name="viewport" content="width=device-width"/>
<style>
    html,body{margin: 0;padding: 0;overflow: hidden;}
    form{margin: auto;width: 320px;}
    form *{box-sizing: border-box;}
    .hint, input[type=text], input[type=password]{font-size: 20px;border-radius: 5px;}
    div{margin: 30px 0;}
    input[type=text], input[type=password]{width: 100%;height: 48px;padding:11px 20px;border: 1px solid #ddd;}
    .hint{line-height: 46px;text-align: center;color: #fff;background-color: #ef968c;}
    input[type=button]{
        width: 100%;height: 66px;font-size: 26px;color: #fff;background-color: #00b58a;border: 2px solid #00b58a;
        border-radius: 10px;box-shadow: 0px 3px 0px 0px #008263;
    }
    @media screen and (min-width: 640px){
        form{width: 426px;}
        .hint, input[type=text], input[type=password]{font-size: 24px;}
        div{margin: 40px 0;}
        input[type=text], input[type=password]{height: 62px;padding:16px 24px;}
        .hint{line-height: 60px;}
        input[type=button]{height: 88px;font-size: 34px;}
    }
    @media screen and (max-width: 320px){
        form{width: 214px;}
        .hint, input[type=text], input[type=password]{font-size: 16px;}
        div{margin: 20px 0;}
        input[type=text], input[type=password]{height: 34px;padding:6px 16px;}
        .hint{line-height: 32px;}
        input[type=button]{height: 42px;font-size: 18px;}
    }
</style>
</head>
<body>
	<form action="${url}dt2/bind.wx" method="post">
    <div><input type="text" name='urs' placeholder="请输入帐号"></div>
    <div><input type='password'  name='passwd' placeholder="请输入密码"><input type='hidden' value='${st?default("")}' name="st"></div>
    <div class="hint">${error?default("")}</div>
    <div><input type="button" onclick="document.forms[0].submit();" value="确 定"></div>
    </form>
</div>
</body>
</html>