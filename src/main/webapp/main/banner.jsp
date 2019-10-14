<%@page contentType="text/html; utf-8" pageEncoding="UTF-8" isELIgnored="false" %>
<script>
    $(function () {
        $("#bannerList").jqGrid({
            url:"${pageContext.request.contextPath}/banner/findAll",
            editurl:"${pageContext.request.contextPath}/banner/edit",
            datatype:"json",
            colNames:["ID","标题","简介","创建时间","图片","状态"],
            colModel:[
                {name:"id"},
                {name:"title",editable:true},
                {name:"dese",editable:true},
                {name:"create_time",editable:true},
                {name:"img",editable:true,edittype:"file",
                    formatter:function (a, b, c) {
                        return "<img style='width:100px;height:50px' src='${pageContext.request.contextPath}/img/"+a+"' />"
                    }
                },
                {name:"status",editable:true,edittype:'select',editoptions: {value:'待定:待定;正常:正常;冻结:冻结'},}
            ],
            styleUI:"Bootstrap",
            autowidth:true,
            height:"60%",
            pager:"#bannerPager",
            page:1,
            rowNum:2,
            rowList:[2,4,6],
            viewrecords:true,
            multiselect:true
        }).jqGrid("navGrid","#bannerPager",
            {//处理页面几个按钮的样式
                search:false
            },
            {//在编辑之前或者之后进行额外的操作
                //修改完成之后关闭窗口
                    beforeShowForm:function (obj) {
                    obj.find("#title").attr("readonly",true);
                    obj.find("#img").attr("disabled",true);
                    obj.find("#dese").attr("readonly",true);
                    obj.find("#create_time").attr("readonly",true);
                },
                closeAfterEdit:true



            },
            {//在添加数据 之前或者之后进行额外的操作
                //添加完成后关闭窗口
                closeAfterAdd:true,
                afterSubmit:function (response) {
                    var bannerId = response.responseText;
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/banner/upload",
                        fileElementId:"img",
                        data:{bannerId:bannerId},
                        success:function (data) {
                            $("#bannerList").trigger("reloadGrid");
                        }
                    })
                    return response;
                }
            },
            {//在删除数据之前或者之后进行额外的操作
                // beforeShowForm:function () {
                //     alert("3")
                // }
            }
        )
    })
    function outBanner() {
        location.href="${pageContext.request.contextPath}/banner/importOut"
    }
</script>
<div>

    <!-- Nav tabs -->
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">轮播图列表</a></li>
        <li role="presentation"><a href="#profile" onclick="outBanner()" aria-controls="profile" role="tab" data-toggle="tab">导出轮播图信息</a></li>
    </ul>

</div>
<table id="bannerList"></table>
<div id="bannerPager"></div>