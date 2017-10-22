$(document).ready(function(){

	$.fn.datetimepicker.dates['zh'] = {  
	        days:       ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六","星期日"],  
	        daysShort:  ["日", "一", "二", "三", "四", "五", "六","日"],  
	        daysMin:    ["日", "一", "二", "三", "四", "五", "六","日"],  
	        months:     ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月","十二月"],  
	        monthsShort:  ["一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"],  
	        meridiem:    ["上午", "下午"],  
	        //suffix:      ["st", "nd", "rd", "th"],  
	        today:       "今天"  
	};  
	$('.datepicker').datetimepicker({
	    language:  'zh',
	    format: 'yyyy-mm-dd HH:mm:ss',
	    weekStart: 1,
	    todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		minView: 0,
		maxView: 1,
		forceParse: 0
	});

	$("input[name='leaveDay']").keyup(function(){       
	    var tmptxt=$(this).val();       
	    $(this).val(tmptxt.replace(/\D|^0/g,''));       
	}).bind("paste",function(){       
	    var tmptxt=$(this).val();       
	    $(this).val(tmptxt.replace(/\D|^0/g,''));       
	}).css("ime-mode", "disabled"); 

});

//表单验证
function onSub() {

	var arrayName = new Array("leaveDay","department","remark","applicationDate","assignee","createDate");
	var arrayTtile = new Array("请假天数","所属部门","请假理由","申请时间","请假人","创建时间");
	
	for (var i = 0; i < arrayName.length; i++) {
//		console.log((!isNull($('#'+arrayName[i]),arrayTtile[i])) +",arrayName:"+arrayName[i]+",arrayTtile:"+arrayTtile[i]);
		if (!isNull($('#'+arrayName[i]),arrayTtile[i])) { 
			return false;
		}
	}  
	return true;
} 

//判断是否为空
function isNull(comp,title) { 
	console.log(comp); 
	if (comp.val() == "" || comp.val() == undefined){ 
		
//		var dlg = BootstrapDialog.show({message: 'Hi Apple!'});SetTimeOut(function(){dlg.close();},1000);
		
		//调用model框
		$('#modal-body').html("<br><b>"+title+" 不能为空</b>");
		$('#mainModal').modal({
			keyboard: true
		});
		
		//调用结束执行
		$('#mainModal').on('hidden.bs.modal', function () {
			comp.focus(); 
		}); 
		return false
    } 
	return true;
}