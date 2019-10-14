<%@page contentType="text/html; utf-8" pageEncoding="UTF-8" isELIgnored="false" %>
<script>
    $(function () {
        $("#albumList").jqGrid({
            url:"${pageContext.request.contextPath}/album/findAll",
            editurl:"${pageContext.request.contextPath}/album/edit",
            datatype:"json",
            colNames:["ID","标题","封面","作者","播音员","集数","发行时间","能容","分数","状态"],
            colModel:[
                {name:"id"},
                {name:"title",editable:true},
                {name:"cover",editable:true,edittype:"file",
                    formatter:function (a, b, c) {
                        return "<img style='width:100px;height:50px' src='${pageContext.request.contextPath}/img/"+a+"' />"
                    }
                },
                {name:"author",editable:true},
                {name:"beam",editable:true},
                {name:"counts",editable:true},
                {name:"publish_date"},
                {name:"content",editable:true},
                {name:"score",editable:true},
                {name:"status",editable:true,edittype:'select',editoptions: {value:'待定:待定;正常:正常;冻结:冻结'},},

            ],
            styleUI:"Bootstrap",
            autowidth:true,
            height:"60%",
            pager:"#albumPager",
            page:1,
            rowNum:2,
            multiselect:true,
            rowList:[2,4,6],
            viewrecords:true,
            subGrid : true,
            subGridRowExpanded:function (subgrid_id, albumId) {
                addSubGrid(subgrid_id,albumId);
            }
     }).jqGrid("navGrid","#albumPager",
            {//处理页面几个按钮的样式
                search:false
            },
            {//在编辑之前或者之后进行额外的操作
                //修改完成之后关闭窗口
                beforeShowForm:function (obj) {
                    obj.find("#cover").attr("disabled",true);
                },
                closeAfterEdit:true
            },
            {//在添加数据 之前或者之后进行额外的操作
                //添加完成后关闭窗口
                closeAfterAdd:true,
                afterSubmit:function (response) {
                    var albumId = response.responseText;
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/album/upload",
                        fileElementId:"cover",
                        data:{albumId:albumId},
                        success:function (data) {
                            $("#albumList").trigger("reloadGrid");
                        }
                    })
                    return response;
                }
            },
            {//在删除数据之前或者之后进行额外的操作
                /*beforeShowForm:function () {
                    alert("3")
                }*/
            }

        )
    });

    function addSubGrid(subgrid_id,albumId) {
        var tableId=subgrid_id+"table";
        var divId=subgrid_id+"div";
        $("#"+subgrid_id).html(
            "<table id='"+tableId+"' ></table>"+"<div id='"+divId+"' ></div>"
        );
        $("#"+tableId).jqGrid({
            url:"${pageContext.request.contextPath}/chapter/findAll?albumid="+albumId,
            editurl:"${pageContext.request.contextPath}/chapter/edit?albumid="+albumId,
            datatype:"json",
            colNames:["ID","标题","大小","时长","名字","状态","操作"],
            colModel:[
                {name:"id"},
                {name:"title",editable:true},
                {name:"size"},
                {name:"times"},
                {name:"file_path",editable:true,edittype:"file"},
                {name:"status",editable:true,edittype:'select',editoptions: {value:'待定:待定;正常:正常;冻结:冻结'},},
                {name:"file_path",
                    formatter:function (cellValue, options, rowObject) {
                        return "<a onclick=\"playAudio('"+cellValue+"')\" href='#'><span class='glyphicon glyphicon-play-circle'></span></a>"+"                       "+
                            "<a onclick=\"downloadAudio('"+cellValue+"')\" href='#'><span class='glyphicon glyphicon-download'></span></a>"
                    }
                }
            ],
            styleUI:"Bootstrap",
            autowidth:true,
            height:"60%",
            pager:"#"+divId,
            page:1,
            rowNum:2,
            multiselect:true,
            rowList:[2,4,6],
            viewrecords:true
        }).jqGrid("navGrid","#"+divId,
            {//处理页面几个按钮的样式
                search:false
            },
            {//在编辑之前或者之后进行额外的操作
                //修改完成之后关闭窗口
                beforeShowForm:function (obj) {
                    obj.find("#cover").attr("disabled",true);
                },
                closeAfterEdit:true
            },
            {//在添加数据 之前或者之后进行额外的操作
                //添加完成后关闭窗口
                closeAfterAdd:true,
                afterSubmit:function (response) {
                    var albumId = response.responseText;
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/chapter/upload?album=\"+albumId",
                        fileElementId:"file_path",
                        data:{albumId:albumId},
                        success:function (data) {
                            $("#"+tableId).trigger("reloadGrid");
                            $("#albumList").trigger("reloadGrid");
                        }
                    })
                    return response;
                }

            },
            {//在删除数据之前或者之后进行额外的操作
                afterSubmit:function () {
                    $("#"+tableId).trigger("reloadGrid");
                    $("#albumList").trigger("reloadGrid");
                    return "adf";
                }
            })

    }
    function playAudio(d) {
        $("#dialogId").modal("show");
        $("#audioId").attr("src","${pageContext.request.contextPath}/mp3/"+d);
    }
    function downloadAudio(a) {
        location.href="${pageContext.request.contextPath}/chapter/download?audioName="+a;
    }
</script>


<div id="dialogId" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <audio id="audioId" controls src=""> </audio>
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<table id="albumList"></table>
<div id="albumPager"></div>