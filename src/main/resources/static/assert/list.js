$(function () {

    M.manage.queryData();
    M.operate.createAccountbindclick();
    M.operate.updateAccountbindclick();
    //M.operate.searchNamebindchange();
    M.operate.delAccountbindclick();
    M.operate.managePermissionbindclick();

});

$(window).keydown(function(event){
    if (event.keyCode==27){
        event.preventDefault();
        layer.closeAll();
    }
    if (event.keyCode==13){
        event.preventDefault();
    }
});

var M = managePermission = {
    submitStatus : false,
    manage : {
        queryData:function () {
            $("#manageUserPermission").tableQuery({
                loadData:function (f) {
                    $.ajax({
                        type: "get",
                        url: basePath + "/account/queryAll",
                        beforeSend: function () {
                            M.submitStatus = true;
                        },
                        complete: function () {
                            M.submitStatus = false;
                        },
                        statusCode: {
                            200: function (data) {
                                f(data);
                                //M.draw.showTbody(data);
                            },
                            500: function () {
                                M.message.error('出错了,请联系管理员...');
                            }
                        }

                    });
                },
                drawRow:function (one) {
                    var str ="";
                    if(one.menuIds != null && one.menuIds != "null" && one.menuIds.indexOf(1000) !=-1){
                        str+='<a href="'+basePath+'/account/courserelevance/list/'+one.userId+'">关联课程</a>\n' ;
                    }
                    return `<tr>
                                <td><input type="hidden" value="${one.userId}"/><div class="txtEllipsis col0">`+M.common._string_ifNull(one.account)+`</div></td>
                                <td><div class="txtEllipsis col1 userName" title="`+M.common._string_ifNull(one.name)+`">`+M.common._string_ifNull(one.name)+`</div></td>
                                <td><div class="txtEllipsis col2" title="`+M.common._string_ifNull(one.remark)+`">`+M.common._string_ifNull(one.remark)+`</div></td>
                                <td><div class="txtEllipsis col3">`+M.common._date_toString(one.createTime)+`</div></td>
                                 <td><div class="txtEllipsis col4" title="`+M.common.list2String2(one.menuIds)+`">`+M.common.list2String(one.menuIds)+`</div></td>
                                <td class="rightAlign">
                                    ${str}
                                    <a href="javascript:void(0);" name="managePermission">分配权限</a>
                                    <a href="javascript:void(0);" name="updateAccount">编辑</a>
                                    <a class="danger-link" href="javascript:void(0);" name="delAccount">删除</a>
                                </td>
                            </tr>`;
                },
                emptyContent:function () {
                    return '<tr class="td-line no-data"><td colspan="6">没有数据</td></tr>';
                },
                onPostKeyup:function ($ele) {
                    //C.css.initCheckBox();
                    //form.render('checkbox'); //刷新select选择框渲染
                },
                filters:[{
                    ele:"#searchName",
                    prop:"name",
                    mode:"*",//* =
                    addClear:{
                        paddingRight:"20px",
                        right : "8px",
                    }
                }]
            });
        },
        addAccount:function (moblie,userName,remark) {
            $.ajax({
                type: "POST",
                url: basePath + "/account/addAccount",
                data:{"moblie":moblie,"userName":userName,"remark":remark},
                beforeSend: function () {
                    M.submitStatus = true;
                },
                complete: function () {
                    M.submitStatus = false;
                },
                statusCode: {
                    200: function () {
                        M.manage.queryData();
                        layer.closeAll();
                    },
                    201:function () {
                        M.message.error('该用户已存在...');
                    },
                    500: function () {
                        M.message.error('出错了,请联系管理员...');
                    }
                }

            });
        },
        removeAccount:function (userId,$tr) {
            $.ajax({
                type: "POST",
                url: basePath + "/account/removeAccount",
                data:{"userId":userId},
                beforeSend: function () {
                    M.submitStatus = true;
                },
                complete: function () {
                    M.submitStatus = false;
                },
                statusCode: {
                    200: function () {
                        $tr.detach();
                        layer.closeAll();
                    },
                    500: function () {
                        M.message.error('出错了,请联系管理员...');
                    }
                }
            });
        },
        updateAccount:function (userId,moblie,userName,remark,$tr) {
            $.ajax({
                type: "POST",
                url: basePath + "/account/updateAccount",
                data:{"moblie":moblie,"userName":userName,"remark":remark,"userId":userId},
                beforeSend: function () {
                    M.submitStatus = true;
                },
                complete: function () {
                    M.submitStatus = false;
                },
                statusCode: {
                    200: function () {
                        //M.manage.queryData();
                        $tr.children("td").eq(0).children("div").eq(0).text(M.common.phone_encrypt(moblie));
                        $tr.children("td").eq(1).children("div").eq(0).text(userName);
                        $tr.children("td").eq(1).children("div").eq(0).attr("title",userName);
                        $tr.children("td").eq(2).children("div").eq(0).text(remark);
                        $tr.children("td").eq(2).children("div").eq(0).attr("title",remark);
                        layer.closeAll();
                    },
                    201:function () {
                        M.message.error('该手机号已存在...');
                    },
                    500: function () {
                        M.message.error('出错了,请联系管理员...');
                    }
                }

            });

        },
        AssignPermissions:function (menulists) {
            $.ajax({
                type : "POST",
                url : basePath + "/account/AssignPermissions",
                dataType: 'json',
                contentType: "application/json",
                data : JSON.stringify(menulists),
                beforeSend: function () {
                    M.submitStatus = true;
                },
                complete: function () {
                    M.submitStatus = false;
                },
                statusCode: {
                    200: function () {
                        M.manage.queryData();
                        /*$tr.children("td").eq(0).children("div").eq(0).text(M.common.phone_encrypt(moblie));
                        $tr.children("td").eq(1).children("div").eq(0).text(userName);*/
                        layer.closeAll();
                    },
                    500: function () {
                        M.message.error('出错了,请联系管理员...');
                    }
                }
            });



        },
        queryAccountByUserId:function (userId,$tr) {
            $.ajax({
                type: "get",
                url: basePath + "/account/queryAccountByUserId",
                data:{"userId":userId},
                beforeSend: function () {
                    M.submitStatus = true;
                },
                complete: function () {
                    M.submitStatus = false;
                },
                statusCode: {
                    200: function (data) {
                        M.message.updateAccountOpen(data,$tr);
                    },
                    500: function () {
                        M.message.error('出错了,请联系管理员...');
                    }
                }

            });
        },
        queryPermissionByUserId:function (userId) {
            $.ajax({
                type: "get",
                url: basePath + "/account/queryPermissionByUserId",
                data:{"userId":userId},
                beforeSend: function () {
                    M.submitStatus = true;
                },
                complete: function () {
                    M.submitStatus = false;
                },
                statusCode: {
                    200: function (data) {

                        M.message.managePermissionOpen(data,userId);
                    },
                    500: function () {
                        M.message.error('出错了,请联系管理员...');
                    }
                }

            });
        }

    },
    data:{

    },
    common:{
        _number_ifNull : function(number){
            if(isNaN(number)){
                throw "这个不是数字";
            }else{
                if(number == null || number=="null" || number==0){
                    return "0";
                }else{
                    return number;
                }
            }
        },
        //如果参数为空 则转为""
        _string_ifNull : function (string){
            if(string == null || string == 'null'){
                return '';
            }else{
                return string;
            }
        },
        //时间转换（date --> string）
        _date_toString : function(date){
            if(date == null || date == 'null'){
                return '';
            }else{
                var d = new Date(date);
                var year=d.getFullYear();
                var month=d.getMonth()+1;
                if(month < 10){
                    month = "0"+month;
                }
                var date=d.getDate();
                if(date < 10){
                    date = "0"+date;
                }
                var hours=d.getHours();
                if(hours < 10){
                    hours = "0"+hours;
                }
                var minutes=d.getMinutes();
                if(minutes < 10){
                    minutes = "0"+minutes;
                }
                return year+"-"+month+"-"+date+" "+hours+":"+minutes;
            }
        },
        //时间转换（string --> date）
        _string_toDate : function(str){
            var arr = str.split("-");
            return new Date((arr));
        },
        //权限列表
        list2String : function (array) {
            if(array == null || array=="null"){
                return "";
            }else{
                var str = "";
                for(var i = 0;i<array.length;i++){
                    if(array[i]==1000){
                        str+="课程管理，"
                    }
                    if(array[i]==2000){
                        str+="企业管理，"
                    }
                    if(array[i]==3000){
                        str+="运营管理，"
                    }
                }
                return str.substring(0,str.length-1);
            }
        },
        //权限列表
        list2String2 : function (array) {
            if(array == null || array=="null"){
                return "";
            }else{
                var str = "";
                var secondStr = "(";
                for(var i = 0;i<array.length;i++){
                    if(array[i]==1000){
                        str+="课程管理、"
                    }
                    if(array[i]==2000){
                        str+="企业管理、"
                    }
                    if(array[i]==3000){
                        str+="运营管理、"
                    }
                    if(array[i]==3010){
                        secondStr+="App广告、"
                    }
                    if(array[i]==3020){
                        secondStr+="发现频道管理、"
                    }
                    if(array[i]==3040){
                        secondStr+="开班管理、"
                    }
                    if(array[i]==3030){
                        secondStr+="课程上下架管理、"
                    }
                }
                str = str.substring(0,str.length-1);
                secondStr = secondStr.substring(0,secondStr.length-1);
                if(secondStr.length >0){
                    secondStr+=")";
                    return str+secondStr;
                }else{
                    return str;
                }
            }
        },
        //将手机号转换
        phone_encrypt : function (mobile) {
            if(mobile.trim() != "" &&  mobile !=null && mobile != "null"){
                if(mobile.length>=7){
                    mobile = mobile.substring(0,3)+"****"+mobile.substring(7);
                }else if(mobile.length<7 && mobile.length>=3){
                    var temp ="";
                    for (var i=0;i<mobile.length-3;i++){
                        temp +="*";
                    }
                    mobile = mobile.substring(0,3)+temp;
                }
            }
            return mobile;
        },

    },
    draw:{
        showTbody : function (data) {
            $("#manageUserPermission").empty();
            var tbody = "";
            for(var i=0;i<data.length;i++){
                tbody+='<tr>\n' +
                    '                    <td><input type="hidden" value="'+data[i].userId+'"/><div class="txtEllipsis col0">'+M.common._string_ifNull(data[i].account)+'</div></td>\n' +
                    '                    <td><div class="txtEllipsis col1 userName" title="'+M.common._string_ifNull(data[i].name)+'">'+M.common._string_ifNull(data[i].name)+'</div></td>\n' +
                    '                    <td><div class="txtEllipsis col2" title="'+M.common._string_ifNull(data[i].remark)+'">'+M.common._string_ifNull(data[i].remark)+'</div></td>\n' +
                    '                    <td><div class="txtEllipsis col3">'+M.common._date_toString(data[i].createTime)+'</div></td>\n' +
                    '                    <td><div class="txtEllipsis col4" title="'+M.common.list2String2(data[i].menuIds)+'">'+M.common.list2String(data[i].menuIds)+'</div></td>\n' +
                    '                    <td class="rightAlign">\n' ;
                                    if(data[i].menuIds != null && data[i].menuIds != "null" && data[i].menuIds.indexOf(1000) !=-1){
                                        tbody+='<a href="'+basePath+'/account/courserelevance/list/'+data[i].userId+'">关联课程</a>\n' ;
                                    }
                tbody+='                        <a href="javascript:void(0);" name="managePermission">分配权限</a>\n' +
                    '                        <a href="javascript:void(0);" name="updateAccount">编辑</a>\n' +
                                            '<a class="danger-link" href="javascript:void(0);" name="delAccount">删除</a>\n' +
                    '                    </td>\n' +
                    '                </tr>'
            }
            $("#manageUserPermission").append(tbody);
        }
    },
    operate:{
        createAccountbindclick:function () {
            $("#createAccountBtn").unbind("click");
            $("#createAccountBtn").bind("click",function () {
                M.message.createAccountOpen();
            })
        },
        updateAccountbindclick:function () {
            $("a[name='updateAccount']").unbind("click");
            $("a[name='updateAccount']").live("click",function () {
                var userId = $(this).parent("td").parent("tr").children("td").eq(0).children("input").eq(0).val();
                var $tr = $(this).parent("td").parent("tr");
                M.manage.queryAccountByUserId(userId,$tr);
            });
        },
        /*searchNamebindchange:function () {
            $("#searchName").unbind("input propertychange");
            $("#searchName").bind("input propertychange",function () {
                var inputVal = $(this).val().trim();
                $(".userName").each(function () {
                    if($(this).text().search(inputVal) != -1){
                        $(this).parents("tr").show();
                    }else{
                        $(this).parents("tr").hide();
                    }
                })
            });
        },*/
        delAccountbindclick:function () {
            $("a[name='delAccount']").unbind("click");
            $("a[name='delAccount']").live("click",function () {
                var userId = $(this).parent("td").parent("tr").children("td").eq(0).children("input").eq(0).val();
                var userName = $(this).parent("td").parent("tr").children("td").eq(1).children("div").eq(0).text();
                var $tr = $(this).parent("td").parent("tr");
                M.message.delAccountConfirm(userId,userName,$tr);
            });
        },
        managePermissionbindclick:function () {
            $("a[name='managePermission']").unbind("click");
            $("a[name='managePermission']").live("click",function () {
                var userId = $(this).parent("td").parent("tr").children("td").eq(0).children("input").eq(0).val();
                M.manage.queryPermissionByUserId(userId);
            });
        }
    },
    message:{
        delAccountConfirm : function (userId,userName,$tr) {
            layui.use('layer', function() {
                layer = layui.layer;
                layer.alert('您确定删除“'+userName+'”的账号吗？',{
                    title: "提示",
                    icon:3,
                    btn:["确定","取消"],
                    btnAlign: "c",
                    move:false,
                    yes:function () {
                        M.manage.removeAccount(userId,$tr);
                    }
                })
            });
        },
        createAccountOpen:function () {
            //创建账号
            layui.use('layer', function() {
                layer = layui.layer;
                layer.open({
                    title: ['创建账号', 'font-weight:bold'],
                    type: 1,
                    content: $(".creat-account-pop"),
                    area: ["500px"],
                    fixed:false,
                    btn: ["确认", "取消"],
                    btnAlign: "c",
                    success: function(layero, index) {
                        JPlaceHolder.init();
                        M.css.initOperationConfirm();
                    },
                    yes:function (layero, index) {
                        var moblie = $("#phoneMobile").val().trim();
                        if(!M.check.checkMoblie(moblie)){
                            return false;
                        }
                        var userName = $("#userName").val().trim();
                        if(!M.check.checkUserName(userName)){
                            return false;
                        }
                        var remark = $("#remark").val().trim();
                        if(!M.check.checkRemark(remark)){
                            return false;
                        }
                        M.manage.addAccount(moblie,userName,remark);
                    }
                })
            });
        },
        updateAccountOpen:function (data,$tr) {
            layui.use('layer', function() {
                layer = layui.layer;
                layer.open({
                    title: ['编辑账号', 'font-weight:bold'],
                    type: 1,
                    content: $(".creat-account-pop"),
                    fixed:false,
                    area: ["500px"],
                    btn: ["确认", "取消"],
                    btnAlign: "c",
                    success: function(layero, index) {
                        JPlaceHolder.init();
                        M.css.initOperationConfirm();
                        $("#phoneMobile").val(data.mobile);
                        $("#userName").val(data.name);
                        $("#remark").val(data.remark);
                    },
                    yes:function (layero, index) {
                        var moblie = $("#phoneMobile").val().trim();
                        if(!M.check.checkMoblie(moblie)){
                            return false;
                        }
                        var userName = $("#userName").val().trim();
                        if(!M.check.checkUserName(userName)){
                            return false;
                        }
                        var remark = $("#remark").val().trim();
                        if(!M.check.checkRemark(remark)){
                            return false;
                        }
                        M.manage.updateAccount(data.userId,moblie,userName,remark,$tr);
                    }
                })
            });
        },
        managePermissionOpen:function (data,userId) {
            layui.use('layer', function() {
                layer = layui.layer;
                layer.open({
                    title: ['权限分配', 'font-weight:bold'],
                    type: 1,
                    fixed:false,
                    content: $(".permissions-pop"),
                    area: ["400px"],
                    btn: ["确认", "取消"],
                    btnAlign: "c",
                    success:function () {
                        M.css.initCheckBox();
                        $("input[type='checkbox']").each(function (index,ele) {
                            var $this = $(this);
                            for(var i =0;i<data.length;i++){
                                if(data[i].menuId == $this.attr("data")){
                                    $this.attr('checked','checked');
                                    $this.attr('data-id',data[i].id);
                                }
                            }
                        })
                        form.render('checkbox'); //刷新select选择框渲染
                    },
                    yes:function () {
                        if($("#operationManage").attr("checked") == "checked"){
                            var flag = false;
                            $(".permissions-child-wrap input[type='checkbox']").each(function () {
                               if($(this).attr("checked")=="checked"){
                                   flag = true;
                               }
                            })
                            if(!flag){
                                M.message.error("至少选中一个二级权限。。。。");
                                return false;
                            }
                        }
                        var menulist=[];
                        $("input[type='checkbox']").each(function (index,ele) {
                            if($(this).attr("checked") == "checked"){
                                var menu = {};
                                menu.id = $(this).attr("data-id");
                                /*console.log($(this).attr("data"));
                                console.log($(this).attr("data-id"));*/
                                menu.menuId = $(this).attr("data");
                                menulist.push(menu);
                            }
                        });
                        var menulists={};
                        menulists.menulist=menulist;
                        menulists.userId=userId;
                        M.manage.AssignPermissions(menulists);
                        /*console.log(menulist);
                        console.log(userId);*/
                    }
                })
            });
        },
        error:function (msg,callback) {
            layer.msg(msg, {
                icon: 2,//1对勾  2叉  3 ？  4 锁  5  悲伤   6 开心  7 感叹号
                time: 2000, //2秒关闭（如果不配置，默认是3秒）
            }, function(){
                if (callback) {
                    callback();
                }
            });
        },
    },
    check:{
        checkMoblie:function (moblle) {
            if(moblle == ""){
                M.css.showErrorCss($("#phoneMobile"),"手机号不能为空！");
                return false;
            }else if(isNaN(moblle) || moblle.length!=11){
                M.css.showErrorCss($("#phoneMobile"),"手机号格式不正确！");
                return false;
            }else{
                M.css.hiddenErrorCss($("#phoneMobile"));
                return true;
            }
        },
        checkUserName:function (userName) {
            if(userName == ""){
                M.css.showErrorCss($("#userName"),"姓名不能为空！");
                return false;
            }else if(userName.length>20){
                M.css.showErrorCss($("#userName"),"姓名不能超过20！");
                return false;
            } else{
                M.css.hiddenErrorCss($("#userName"));
                return true;
            }
        },
        checkRemark:function (remark) {
            if(remark.length>50){
                M.css.showErrorCss($("#remark"),"备注不能超过50字！");
                return false;
            }else{
                M.css.hiddenErrorCss($("#remark"));
                return true;
            }
        }
    },
    css:{
        initOperationConfirm:function () {
            $("#phoneMobile").val("");
            $("#userName").val("");
            $("#remark").val("");

            M.css.hiddenErrorCss($("#phoneMobile"));
            M.css.hiddenErrorCss($("#userName"));
            M.css.hiddenErrorCss($("#remark"));
        },
        showErrorCss:function ($o,message) {
            if(!$o.hasClass("input-danger")){
                $o.addClass("input-danger");
            }
            $o.next("span").eq(0).text(message);
            $o.next("span").eq(0).attr("style","");
        },
        hiddenErrorCss:function ($o) {
            if($o.hasClass("input-danger")){
                $o.removeClass("input-danger");
            }
            $o.next("span").eq(0).text("");
            $o.next("span").eq(0).attr("style","display:none");
        },
        initCheckBox:function () {
            $("input[type='checkbox']").each(function () {
                $(this).attr("checked",false);
                $(this).attr("data-id","");
            })
        }
    }

}