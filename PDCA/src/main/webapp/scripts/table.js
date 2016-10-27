$(function(){
	//获取到改用户的u_uid
	var uid=getCookie("uid");
	if(uid==null){
		window.location.href="log_in.html";
		return;
	}
	findUndoProject(uid);
	findFinshProject(uid);
	//未完成工作的按钮点击
	$("#do_panel_btn").click(function(){clickDoBtn(uid)});
	//将该项目改为已完成状态
	$("#finsh_btn").click(completeProject);
	//已完成工作的按钮点击
	$("#finsh_panel_btn").click(clickFinshBtn);
	//派发工作的按钮点击
	$("#send_panel_btn").click(clickSendBtn);
	//添加一行表格按钮
	$("#add_row_btn").click(clickAddRow);
	//输入框查询的事件
	$("#send_ipt").keyup(inputFindLike);
	//给查询结果的li添加事件
	$("#resultFind").on("click","li",addLiClick);
	//发送数据到数据库 发送表格
	$("#send_btn").click(function(){clickSendToDB(uid)});
	//发送未完成工作的内容显示
	//给页码的按钮添加事件 使得显示项目
	$("#pageNum_div").on("click","li",clickPageNumBtn);
		
		});
//点击未完成工作的li的功能
function clickDoBtn(uid){
	console.log(1);
	$("#finsh_panel").addClass("hide");
	$("#send_panel").addClass("hide");
	$("#do_panel").removeClass("hide");
	$("#pageNum_div").addClass("hide");
	
	$("#do_panel_btn").parent().addClass("active");
	$("#finsh_panel_btn").parent().removeClass("active");
	$("#send_panel_btn").parent().removeClass("active");
	findUndoProject(uid);
}
//点击已完成工作的li的功能
function clickFinshBtn(){
	$("#do_panel").addClass("hide");
	$("#send_panel").addClass("hide");
	$("#finsh_panel").removeClass("hide");
	$("#pageNum_div").removeClass("hide");
	
	$("#finsh_panel_btn").parent().addClass("active");
	$("#do_panel_btn").parent().removeClass("active");
	$("#send_panel_btn").parent().removeClass("active");
}
//点击派发li的功能
function clickSendBtn(){
	$("#do_panel").addClass("hide");
	$("#finsh_panel").addClass("hide");
	$("#send_panel").removeClass("hide");
	$("#pageNum_div").addClass("hide");
	
	$("#send_panel_btn").parent().addClass("active");
	$("#do_panel_btn").parent().removeClass("active");
	$("#finsh_panel_btn").parent().removeClass("active");
}
//将项目改为已完成状态
function completeProject(){
	var p_name=$("#do_panel h3").text();
	console.log(p_name);
	//发送请求将其状态码改变
	$.ajax({
		url:base_path+"/table/finshProject.do",
		data:{"u_uid":uid,"p_name":p_name},
		dataType:"json",
		type:"post",
		success:function(result){
			var status=result.status;
			if(status==0){
				alert(p_name+"项目已被完成");
			}else{
				alert("修改异常");
			}
		},
		error:function(){
			alert("警告！异常！");
		}
	})
}
//添加一行发送表格的按钮的功能
function clickAddRow(){
	$("#send_table tbody").append(
			"<tr>"+
			"<td>&nbsp;</td>"+
			"<td>&nbsp;</td>"+
			"<td>&nbsp;</td>"+
			"<td>&nbsp;</td>"+
			"<td>&nbsp;</td>"+
		"</tr>");
}
//输入框模糊查询事件查询
function inputFindLike(){
	var name=$("#send_ipt").val();
	if(name==""){
		return;
	}
	//指定控件添加样式
	var result_div=$("#resultFind");
	result_div.html("");
	//发送请求查询所有name开头的数据
	//模糊查询的ajax请求
		$.ajax({
			url:base_path+"/user/likeFindName.do",
			data:{"name":name},
			dataType:"json",
			type:"post",
			success:function(result){
				var status=result.status;
				if(status==0){
					//获取数据
					var data=result.data;
					for(var i=0;i<data.length;i++){
						var name=data[i].u_name;
						var str="";
						str+="<li class='list-group-item hover'>";
						str+=name;
						str+="</li>";
						//添加到div中
						result_div.append(str);
					}
					result_div.show();
				}
			}
		});
	}
//给所有的li添加点击事件
function addLiClick(){
	//输入框的值变为点击的值
	var name=$(this).text();
	$("#send_ipt").val(name);
	//将div显示取消
	$(this).parent().hide();
}
//发送数据到数据表的按钮的功能
function clickSendToDB(uid){
	var list="";
	console.log("11:"+uid);
	//获取到表格数据
	$("#send_table tbody tr").each(function(i){
	    var t_body=$(this).children().eq(0).text();
	    var t_plan=$(this).children().eq(1).text();
	    var t_standard =$(this).children().eq(2).text();
	    var t_time=$(this).children().eq(3).text();
	    var t_checkP=$(this).children().eq(4).text();
	    var task=t_body+","+t_plan+
	    		","+t_standard+","+t_time+","
	    		+t_checkP;
	    list=list+task+";";
	    if(!t_body||!t_plan||!t_standard||!t_time||!t_checkP){
	    	alert("请确认所有的表格都已填写！");
	    	console.log("==");
	    	return;
	    }
	    
	});
	//title
	 var t_title=$("#title").val();
	//发送到
	var accpect=$("#send_ipt").val();
	if(!t_title||!accpect){
		alert("请确认已输入标题和发送人");
		return;
	}
	//发送请求
	$.ajax({
		url:base_path+"/user/send_table.do",
		data:{"tableList":list,"accpect":accpect,"title":t_title},
		dataType:"json",
		type:"post",
		success:function(result){
			var status=result.status;
			if(status==0){
				alert(result.msg);
			}else{
				alert(result.msg);
			}
		},
		error:function(){
			alert("发送表格异常");
		}
	})
}
//查找已完成的项目的功能
function findFinshProject(uid){
	//发送请求查看已完成的工作
	$.ajax({
		url:base_path+"/table/findFinshProjectByUid.do",
		data:{"u_uid":uid},
		dataType:"json",
		type:"post",
		success:function(result){
			var status=result.status;
			if(status!=1){
				//将传送数据命名
				var p_data=result.data;
				//显示有几个项目
				var len=p_data.length;
				if(len==0) len=1;
				//显示页数
				var str="";
				str+=" <li><a href='#'>&laquo;</a></li>";
				for(var i=1;i<=len;i++){
					str+="<li><a href='#'>"+i+"</a></li>";
				}
				str+=" <li><a href='#'>&raquo;</a></li>";
				$("#pageNum_div ul").html(str);
				//将第一个显示为点击状态
				$("#pageNum_div ul li:eq(1)").addClass("active");
				if(status==2){
					return;
				}
				$("#finsh_table tbody").html("");
				//查看第一个项目中的任务
				for(var j=0;j<p_data.length;j++){
					//获取到项目里的数据
					var t_data=p_data[j].task_list;
					//获取到项目名
					var p_name=p_data[j].p_name;
					//遍历加载项目任务
					var str="";
					str+="<div class='table_"+(j+1)+"' style='display:none;'>";
					str+="<h3 class='text-center'>"+p_name+"</h3>";
					str+='<table id="finsh_table" class="table table-bordered table-hover" >'
					str+='<thead>';
					str+=	'<tr>';
					str+=		'<th>工作内容</th>';
					str+=		'<th>计划</th>';
					str+=		'<th>标准</th>';
					str+=		'<th>检查时间</th>';
					str+=		'<th>检查人</th>';
					str+=		'<th>完成情况</th>';
					str+=		'<th>改进</th>';
					str+=	'</tr>';
					str+='</thead>';
					str+='<tbody>';
					for(var i=0;i<t_data.length;i++){
						str+="<tr>";
						str+="<td>"+t_data[i].t_body+"</td>";
						str+="<td>"+t_data[i].t_plan+"</td>";
						str+="<td>"+t_data[i].t_standard;+"</td>";
						str+="<td>"+t_data[i].t_time+"</td>";
						str+="<td>"+t_data[i].t_checkP+"</td>";
						str+="<td>";
						str+="	<div class='progress'>"
						str+="		<div class='progress-bar progress-bar-success' role='progressbar'aria-valuenow='60' aria-valuemin='0' aria-valuemax='100' style='width: 80%;'>"
	     				str+="			<a style='color: #000'>80%</a>"
						str+="		</div>";
						str+="	</div>";
						str+="</td>";
						str+="<td>"+(t_data[i].t_better==null?"暂无":t_data[i].t_better)+"</td>";
						str+="</tr>";
					}
					str+='</tbody>';
					str+='</div>';
					var $str=$(str);
					$("#finsh_panel_body").append($str);
				}
				//将第一个项目显示
				$(".table_1").show();
				
			}else{
				alert(result.msg);
			}
		}
	});
}
//
function findUndoProject(uid){
	$.ajax({
		url:base_path+"/table/findUndoProjectByU_uid.do",
		data:{"u_uid":uid},
		dataType:"json",
		type:"post",
		success:function(result){
			var status=result.status;
			if(status==0){
				//清空do_table body的内容
				$("#do_table tbody").html("");
				var r_data=result.data;
				for(var i=0;i<r_data.length;i++){
					var str="";
					str+="<tr>";
					str+="<td>"+r_data[i].t_body+"</td>";
					str+="<td>"+r_data[i].t_plan+"</td>";
					str+="<td>"+r_data[i].t_standard;+"</td>";
					str+="<td>"+r_data[i].t_time+"</td>";
					str+="<td>"+r_data[i].t_checkP+"</td>";
					str+="<td>";
					str+="	<div class='progress'>"
					str+="		<div class='progress-bar progress-bar-success' role='progressbar'aria-valuenow='60' aria-valuemin='0' aria-valuemax='100' style='width: 80%;'>"
     				str+="			<a style='color: #000'>80%</a>"
					str+="		</div>";
					str+="	</div>";
					str+="</td>";
					str+="<td>"+(r_data[i].t_better==null?"暂无":r_data[i].t_better)+"</td>";
					str+="</tr>";
					var $str=$(str);
					$("#do_table tbody").append($str);
				}
				if(r_data.length>0){
					$("#do_panel h3 strong").text(result.msg);
				}
			}else{
				alert(result.msg);
			}
		}
	});
}
//底部页码点击显示每个项目的功能
function clickPageNumBtn(){
	//取消其他li的事件
	$(this).parent().children().removeClass("active");
	//将所有的表格都隐藏
	var len=$(this).parent().children().length;
	for(var i=1;i<=len-2;i++){
		var cls=".table_"+i;
		$(cls).hide();
	}
	//给该按钮添加事件  显示指定的表格
	$(this).addClass("active");
	var text=$(this).text();
	var cs=".table_"+text;
	$(cs).show();
}
