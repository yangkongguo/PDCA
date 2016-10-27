/**login.js 封装登录和注册处理**/
//log_in.html主处理
$(function(){//页面载入完毕
	var str=createCode(4).toLowerCase();
	//给注册按钮绑定单击处理
	$("#regist_button").click(registUser);
	$("#code_span").click(function(){str=createCode(4)});
	//给登录按钮绑定单击处理
	$("#login").click(function(){checkLogin(str)});
	console.log(str)
});
//注册处理
function registUser(){
	//获取请求参数
	var name = 
	  $("#regist_username").val().trim();
	var phone = $("#phone").val().trim();
	var password = 
		$("#regist_password").val().trim();
	var f_password = 
		$("#final_password").val().trim();
	//格式检查
	//清空消息
	$("#warning_1").html("");
	$("#warning_2").html("");
	$("#warning_3").html("");
	$("#warning_4").html("");
	console.log(phone.length);
	var ok = true;//是否通过验证
	if(name==""){
		ok = false;
		$("#warning_1").text("用户名不能为空");
	}
	if(phone==""){
		ok = false;
		$("#warning_2").text("手机号不能为空");
	}else if(phone.length<11){
		ok = false;
		$("#warning_2").text("手机号格式不正确");
	}else if(phone.length>11){
		ok = false;
		$("#warning_2").text("手机号格式不正确");
	}
	if(password==""){
		ok = false;
		$("#warning_3").text("密码不能为空");
	}else if(password.length<6){
		ok = false;
		$("#warning_3").text("密码不能短于六位");
	}
	if(f_password==""){
		ok = false;
		$("#warning_4").text("重复密码不能为空");
	}else if(f_password!=password){
		ok = false;
		$("#warning_4").text("两次密码不相同");
	}
	//发送Ajax请求
	if(ok){
		$.ajax({
			url:base_path+"/user/add.do",
			type:"post",
			data:{"name":name,
				"password":password,"phone":phone},
			dataType:"json",
			success:function(result){
				if(result.status==0){//成功
					alert(result.msg);//提示成功
					$("#back").click();//转向登录界面
				}else {//用户名被占
					console.log(1);
					alert(result.msg);
				}
			},
			error:function(){
				alert("注册异常");
			}
		});
	}
	
};
//登录处理
function checkLogin(str){
	//获取请求参数
	var name = $("#count").val().trim();
	var password = $("#password").val().trim();
	var code=$("#code").val().trim();
	code=code.toLowerCase();
	console.log("test:"+code);
	//检测参数格式
	//清空以前提示信息
	$("#count_span").html("");
	$("#password_span").html("");
	$("#code_span").html("");
	var ok = true;//是否通过检测
	if(name==""){
		ok = false;
		$("#count_span").text("账号不能为空");
	}
	if(password==""){
		ok = false;
		$("#pasword_span").text("密码不能为空");
	}
	if(code==""){
		ok=false;
		$("#check_code_span").text("验证码不能为空");
	}
	if(code!=str){
		ok=false;
		$("#check_code_span").text("验证码不正确");
	}
	console.log("test");
	$("#code_span").click();
	//发送Ajax请求
	if(ok){
		$.ajax({
			url:base_path+"/user/login.do",
			type:"post",
			data:{"name":name,"password":password},
			dataType:"json",
			success:function(result){
				//result就是服务器返回的JSON结果
				if(result.status==0){//成功
					var user = result.data;//获取返回的user信息
					//写入Cookie
					addCookie("uid",user.u_uid,2);
					addCookie("uname",user.u_name,2);
					window.location.href="table2.html";
				}else if(result.status==1){//用户名错
					$("#count_span").html(result.msg);
				}else if(result.status==2){//密码错
					$("#password_span").html(result.msg);
				}
			},
			error:function(){
				alert("登录异常");
			}
		});
	}
};
//创建验证码
function createCode(len){ 
    var seed = new Array(  
                    'abcdefghijklmnopqrstuvwxyz',    
                    'ABCDEFGHIJKLMNOPQRSTUVWXYZ',    
                    '0123456789'  
    )//创建需要的数据数组  
    var idx,i;  
    var result = '';  
    for(i=0;i<len;i++){  
        idx = Math.floor(Math.random()*3);//随机获取一个3以内的整数  
        result += seed[idx].substr(Math.floor(Math.random()*(seed[idx].length)),1); //从seed[idx]中随机选中一个数据并接在result后面  
    }  
    $("#code_span").html(result);
    return result;
}  
//验证 验证码
function checkCode(){  
    var inputdata = document.getElementById("v_code").value.toLowerCase();//toLowerCase()将字符串转换成小写  
    var autodata = document.getElementById("autoCode").innerHTML.toLowerCase();  
  
    if(inputdata == autodata)  
    {  
        alert("验证通过！");  
    }  
    else   
    {  
        alert("验证码输入错误！");  
    }     
}  