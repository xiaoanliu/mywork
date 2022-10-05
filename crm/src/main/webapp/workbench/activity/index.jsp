<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>">
    <meta charset="UTF-8">

    <link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet"/>
    <link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css"
          rel="stylesheet"/>

    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript"
            src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
    <link rel="stylesheet" type="text/css" href="jquery/bs_pagination/jquery.bs_pagination.min.css">
    <script type="text/javascript" src="jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
    <script type="text/javascript" src="jquery/bs_pagination/en.js"></script>

    <script type="text/javascript">

        $(function () {
            //为创建按钮绑定事件
            $("#addBtn").click(function () {
                $(".time").datetimepicker({
                    minView: "month",
                    language: 'zh-CN',
                    format: 'yyyy-mm-dd',
                    autoclose: true,
                    todayBtn: true,
                    pickerPosition: "bottom-left"
                });
                $.ajax({
                    url: "workbench/activity/getUserList.do",
                    type: "get",
                    dataType: "json",
                    success: function (data) {
                        var html = "<option></option>";
                        $.each(data, function (i, n) {
                            //遍历出来的每一个n，就是每一个user对象
                            html += "<option value='" + n.id + "'>" + n.name + "</option>";

                        })
                        //下拉框设置默认值
                        $("#create-owner").html(html);

                        var id = "${user.id}";
                        $("#create-owner").val(id);

                        $("#createActivityModal").modal("show");
                    }
                })
            })
            //为保存按钮绑定事件，执行添加操作
            $("#saveBtn").click(function () {
                $.ajax({
                    url: "workbench/activity/save.do",
                    data: {
                        "owner": $.trim($("#create-owner").val()),
                        "name": $.trim($("#create-name").val()),
                        "startDate": $.trim($("#create-startDate").val()),
                        "endDate": $.trim($("#create-endDate").val()),
                        "cost": $.trim($("#create-cost").val()),
                        "description": $.trim($("#create-description").val())
                    },
                    type: "post",
                    dataType: "json",
                    success: function (data) {
                        if (data.success) {
                            //添加成功刷新市场活动信息列表（局部刷新）
                            /**
                             *
                             * pageList(	$("#activityPage").bs_pagination('getOption', 'currentPage'),
                             * 	$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));

                             */
                            pageList(1,$("#activityPage").bs_pagination('getOption', 'currentPage'));

                            //清空窗口中的数据
                            $("#activityAddForm")[0].reset();


                            //关闭添加操作的窗口
                            $("#createActivityModal").modal("hide");

                        } else {
                            alert("添加失败");
                        }

                    }

                })
            })
            //页面加载时执行方法
            pageList(1, 2);
            //查询按钮执行方法
            $("#searchBtn").click(function () {
                //将所搜框中的内容保存到隐藏域中

                    $("#hidden-endDate").val($.trim($("#search-endDate").val()));
                    $("#hidden-name").val($.trim($("#search-name").val()));
                    $("#hidden-owner").val($.trim($("#search-owner").val()));
                    $("#hidden-startDate").val($.trim($("#search-startDate").val()));

                    //调用方法
                    pageList(1, 2);
            })
            //全选操作
            $("#qx").click(function (){
                $("input[name=xz]").prop("checked",this.checked);
            })

            //动态元素操作

            $("#activityBody").on("click",$("input[name=xz]"),function(){
               $("#qx").prop("checked",$("input[name=xz]").length==$("input[name=xz]:checked").length);
            })

            //为删除按钮绑定事件
            $("#deleteBtn").click(function (){
                //找到被选中的选项
                var $xz=$("input[name=xz]:checked");
                if($xz.length==0){
                    alert("选择删除对象");
                }else{

                    if (confirm("是否进行删除")){
                        //url  workbench/activity/delete.do
                        //拼接参数
                        var param="";
                        //将$xz中的每一个对象遍历出来并取id值
                        for(i=0;i<$xz.length;i++){
                            //$对象加下标变成dom对象，外层再添加$()变成js对象
                            param +="id="+$($xz[i]).val();
                            //添加元素间的分隔符
                            if(i<$xz.length-1){
                                param +="&";
                            }
                        }
                        //alert(param);
                        $.ajax({
                            url:"workbench/activity/delete.do",
                            data:param,
                            type:"post",
                            dataType:"json",
                            success: function (data){
                                if(data.success){
                                    pageList(1,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
                                }else{
                                    alert("删除失败")
                                }
                            }

                        })
                    }


                }
            })
            $("#editBtn").click(function (){
                //判断那些信息被选中
                var $xz =$("input[name=xz]:checked");
                if($xz.length==0){
                    alert("请选择修改记录");
                }else if($xz.length>1){
                    alert("只能选择一条记录修改");
                }else{
                    var id=$xz.val();
                    $.ajax({
                        url:"workbench/activity/getUserListAndActivity.do",
                        data:{
                            "id":id
                        },
                        type:"get",
                        dataType:"json",
                        success: function (data){

                            var html ="<option><option/>";
                            $.each(data.ulist,function (i,n){
                                html += "<option value='"+n.id+"'>"+n.name+"<option/>"
                            })

                            $("#edit-owner").html(html);

                            //处理单条activity
                            $("#edit-id").val(data.a.id);
                            $("#edit-name").val(data.a.name);
                            $("#edit-owner").val(data.a.owner);
                            $("#edit-startDate").val(data.a.startDate);
                            $("#edit-endDate").val(data.a.endDate);
                            $("#edit-cost").val(data.a.cost);
                            $("#edit-description").val(data.a.description);



                            //打开修改操作的窗口
                            $("#editActivityModal").modal("show");


                        }

                    })
                }
            })

            //更新按钮绑定事件
            $("#updateBtn").click(function (){
                $.ajax({
                    url: "workbench/activity/update.do",
                    data: {
                        "id": $.trim($("#edit-id").val()),
                        "owner": $.trim($("#edit-owner").val()),
                        "name": $.trim($("#edit-name").val()),
                        "startDate": $.trim($("#edit-startDate").val()),
                        "endDate": $.trim($("#edit-endDate").val()),
                        "cost": $.trim($("#edit-cost").val()),
                        "description": $.trim($("#edit-description").val())
                    },
                    type: "post",
                    dataType: "json",
                    success: function (data) {
                        if (data.success) {
                            //添加成功刷新市场活动信息列表（局部刷新）
                            pageList(
                                $("#activityPage").bs_pagination('getOption', 'currentPage'),
                            	$("#activityPage").bs_pagination('getOption', 'rowsPerPage')
                            );

                            //清空窗口中的数据
                            //$("#activityAddForm")[0].reset();


                            //关闭添加操作的窗口
                            $("#editActivityModal").modal("hide");

                        } else {
                            alert("修改失败");
                        }

                    }

                })
            })

        });

        //pageno:页码
        //pageSize:记录数


        function pageList(pageNo, pageSize) {

            $("#qx").prop("checked",false);

            $("#search-endDate").val($.trim($("#hidden-endDate").val()));
            $("#search-name").val($.trim($("#hidden-name").val()));
            $("#search-owner").val($.trim($("#hidden-owner").val()));
            $("#search-startDate").val($.trim($("#hidden-startDate").val()));
            $.ajax({
                url: "workbench/activity/pageList.do",

                data: {
                    "pageNo": pageNo,
                    "pageSize": pageSize,
                    "owner": $.trim($("#search-owner").val()),
                    "name": $.trim($("#search-name").val()),
                    "startDate": $.trim($("#search-startDate").val()),
                    "endDate": $.trim($("#search-endDate").val())
                },
                type: "get",
                dataType: "json",
                success: function (data) {

                    /*

                        data

                            我们需要的：市场活动信息列表
                            [{市场活动1},{2},{3}] List<Activity> aList
                            一会分页插件需要的：查询出来的总记录数
                            {"total":100} int total

                            {"total":100,"dataList":[{市场活动1},{2},{3}]}

                     */
                    var html = "";

                    //每一个n就是每一个对象
                    $.each(data.dataList, function (i, n) {
                        html += '<tr class="active">';
                        html += '<td><input type="checkbox" name="xz" value="' + n.id + '"/></td>';
                        html += '<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href=\'workbench/activity/detail.jsp\';">' + n.name + '</a></td>';
                        html += '<td>' + n.owner + '</td>';
                        html += '<td>' + n.startDate + '</td>';
                        html += '<td>' + n.endDate + '</td>';
                        html += '</tr>';
                    })
                    $("#activityBody").html(html);

                    var totalPages = data.total % pageSize == 0 ? data.total / pageSize : parseInt(data.total / pageSize) + 1;
                    //数据处理完毕展现数据分页信息
                    $("#activityPage").bs_pagination({
                        currentPage: pageNo, // 页码
                        rowsPerPage: pageSize, // 每页显示的记录条数
                        maxRowsPerPage: 20, // 每页最多显示的记录条数
                        totalPages: totalPages, // 总页数
                        totalRows: data.total, // 总记录条数

                        visiblePageLinks: 3, // 显示几个卡片

                        showGoToPage: true,
                        showRowsPerPage: true,
                        showRowsInfo: true,
                        showRowsDefaultInfo: true,

                        //该回调函数时在，点击分页组件的时候触发的
                        onChangePage: function (event, data) {
                            pageList(data.currentPage, data.rowsPerPage);
                        }
                    });
                }

            })
        }

    </script>
</head>
<body>


<input id="hidden-name" type="hidden"/>
<input id="hidden-owner" type="hidden"/>
<input id="hidden-startDate" type="hidden"/>
<input id="hidden-endDate" type="hidden"/>

<!-- 修改市场活动的模态窗口 -->
<div class="modal fade" id="editActivityModal" role="dialog">
    <div class="modal-dialog" role="document" style="width: 85%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
            </div>
            <div class="modal-body">

                <form class="form-horizontal" role="form">
                    <input type="hidden" id="edit-id"/>

                    <div class="form-group">
                        <label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <select class="form-control" id="edit-owner">

                            </select>
                        </div>
                        <label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="edit-name">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-startTime" class="col-sm-2 control-label">开始日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control time" id="edit-startDate">
                        </div>
                        <label for="edit-endTime" class="col-sm-2 control-label">结束日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control time" id="edit-endDate">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-cost" class="col-sm-2 control-label">成本</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="edit-cost">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-describe" class="col-sm-2 control-label">描述</label>
                        <div class="col-sm-10" style="width: 81%;">
                            <%--一定要以标签对形式呈现 并且要紧挨着

                            --%>
                            <textarea class="form-control" rows="3" id="edit-description"></textarea>


                        </div>
                    </div>

                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="updateBtn">更新</button>
            </div>
        </div>
    </div>
</div>

<!-- 创建市场活动的模态窗口 -->
<div class="modal fade" id="createActivityModal" role="dialog">
    <div class="modal-dialog" role="document" style="width: 85%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
            </div>
            <div class="modal-body">

                <form id="activityAddForm" class="form-horizontal" role="form">

                    <div class="form-group">
                        <label for="create-marketActivityOwner" class="col-sm-2 control-label">所有者<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <select class="form-control" id="create-owner">
                                <%--                                <option>zhangsan</option>--%>
                                <%--                                <option>lisi</option>--%>
                                <%--                                <option>wangwu</option>--%>
                            </select>
                        </div>
                        <label for="create-marketActivityName" class="col-sm-2 control-label">名称<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="create-name">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="create-startTime" class="col-sm-2 control-label">开始日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control time" id="create-startDate" readonly>
                        </div>
                        <label for="create-endTime" class="col-sm-2 control-label">结束日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control time" id="create-endDate" readonly>
                        </div>
                    </div>
                    <div class="form-group">

                        <label for="create-cost" class="col-sm-2 control-label">成本</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="create-cost">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="create-describe" class="col-sm-2 control-label">描述</label>
                        <div class="col-sm-10" style="width: 81%;">
                            <textarea class="form-control" rows="3" id="create-description"></textarea>
                        </div>
                    </div>

                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="saveBtn">保存</button>
            </div>
        </div>
    </div>
</div>




<div>
    <div style="position: relative; left: 10px; top: -10px;">
        <div class="page-header">
            <h3>市场活动列表123</h3>
        </div>
    </div>
</div>
<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
    <div style="width: 100%; position: absolute;top: 5px; left: 10px;">

        <div class="btn-toolbar" role="toolbar" style="height: 80px;">
            <form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">

                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">名称</div>
                        <input class="form-control" type="text" id="search-name">
                    </div>
                </div>

                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">所有者</div>
                        <input class="form-control" type="text" id="search-owner">
                    </div>
                </div>


                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">开始日期</div>
                        <input class="form-control" type="text" id="search-startDate"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">结束日期</div>
                        <input class="form-control" type="text" id="search-endDate">
                    </div>
                </div>

                <button type="button" id="searchBtn" class="btn btn-default">查询</button>

            </form>
        </div>
        <div class="btn-toolbar" role="toolbar"
             style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
            <div class="btn-group" style="position: relative; top: 18%;">
                <button type="button" class="btn btn-primary" id="addBtn"><span class="glyphicon glyphicon-plus"></span>
                    创建
                </button>
                <button type="button" class="btn btn-default" id="editBtn"><span
                        class="glyphicon glyphicon-pencil"></span> 修改
                </button>
                <button type="button" id="deleteBtn" class="btn btn-danger"><span class="glyphicon glyphicon-minus"></span> 删除</button>
            </div>

        </div>
        <div style="position: relative;top: 10px;">
            <table class="table table-hover">
                <thead>
                <tr style="color: #B3B3B3;">
                    <td><input type="checkbox" id="qx"/></td>
                    <td>名称</td>
                    <td>所有者</td>
                    <td>开始日期</td>
                    <td>结束日期</td>
                </tr>
                </thead>
                <tbody id="activityBody">

                </tbody>
            </table>
        </div>

        <div style="height: 50px; position: relative;top: 30px;">
            <div id="activityPage"></div>


        </div>

    </div>

</div>
</body>
</html>