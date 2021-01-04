seajs.use(['jdf/1.0.0/ui/dialog/1.0.0/dialog'], function (dialog) {
    window.submitOrderAudit=function(submitBtn){
        var orderId = $(submitBtn).attr('data-orderId');
        if(orderId== undefined || orderId == null || orderId =='') {
            console.log('未获取到订单ID');
            return;
        }
        //收货仓库照片 czcs-shck-add
        var message = '';
        var shck = [];
        $('#czcs-shck-add').parent().find('.czcs-main-img,.czcs-main-video').each(function (index, elem) {
            shck.push($(elem).attr('src'));
        });
        console.log(shck)
        if (shck.length == 0) message = '收货仓库照片、';
        //货物照片 czcs-hwzp-add
        var hwzp = [];
        $('#czcs-hwzp-add').parent().find('.czcs-main-img,.czcs-main-video').each(function (index, elem) {
            hwzp.push($(elem).attr('src'));
        });
        if (hwzp.length == 0) message += '货物照片、';
        //签收单照片 czcs-qsd-add
        var qsd = [];
        $('#czcs-qsd-add').parent().find('.czcs-main-img,.czcs-main-video').each(function (index, elem) {
            qsd.push($(elem).attr('src'));
        });
        if (qsd.length == 0) message += '签收单照片、';

        if (message.length != 0) {
            message = "【" + message.substring(0, message.length - 1) + "】 至少上传一个文件！";
            showDialog('警告', message, function () { }, 400, 50);
            return;
        }

        showDialog('发起收货审核', '确认提交审核资料至京东审核？', function () {
            var submitData = {
                "orderId": orderId,
                "shck":shck,
                "hwzp":hwzp,
                "qsd":qsd
            };

            $.ajax({
                type: 'POST',
                url: '/order/submitOrderAudit',
                contentType: 'application/json;charset=UTF-8',
                async:false,
                data: JSON.stringify(submitData),
                success: function (result) {
                    console.log(JSON.stringify(result));
                }
            })
        });
    }

    $('.czcsConfirmBtn').click(function () {
        var id = $(this).parent().parent().attr("id");
        if(id && id.length>0){
            id = id.substring(3);
            showDialog('温馨提示', '此为厂直厂商直送订单，需要提交审核材料，至京东审核后，方可完成确认收货', function () {
                window.open("/order/detail?orderId="+id);
            },360,50);
        }else{
            $.customAlert({
                content: "参数有误",
                alertType: 'warning'
            })
        }
    });

    function showDialog(title, message, submitFun, width, height) {
        if (height == undefined || height == null) height = 30;
        if (width == undefined || width == null) width = 360;
        var a = $('body').dialog({
            title: title,
            width: width,
            height: height,
            type: 'text',
            hasButton: true,
            submitButton: '确定',
            cancelButton: '',
            onSubmit: function () {
                if(submitFun != undefined && submitFun != null) submitFun();
                a.close();
                a = null;
            },
            source: '<div class="m flexbox">'
                + '<div class="mc">'
                + '<div class="tip-box icon-box">'
                // + '<span class="erro-icon m-icon"></span>'
                + '<div class="item-fore" style="margin-left: 0px">'
                + '<h3><span class="ftx04">' + message + '</span>'
                + '</h3>'
                + '</div></div></div></div>'
        });
    }
    window.removeFile = function(removeButton) {
        $(removeButton).parent().parent().children(".czcs-add").show();
        $(removeButton).parent().remove();
    }
    window.uploadFile = function(fileButton) {
        var file = fileButton.files[0];
        if (file == null) return;
        var btnID = $(fileButton).parent().attr('id')
        fileButton.outerHTML = fileButton.outerHTML;
        //检查文件类型及大小
        if (checkFileVedio(file)) {
            if (file.size > 1024 * 1024 * 50) {
                showDialog("警告","单个视频大小不超过50MB!");
                return;
            }
        } else if (checkFileImg(file)) {
            if (file.size > 1024 * 1024 * 5) {
                showDialog("警告","单个照片大小不超过5MB");
                return;
            }
        } else {
            showDialog("警告","请选择jpg,jpeg,png,mp4,avi格式的媒体文件！",function(){},400,50);
            return;
        }

        //上传图片 改写 上传 按钮样式，变成加载中，并禁用按钮
        var formData = new FormData();
        formData.append('file', file);
        var btnID = $(fileButton).parent().attr('id')
        formData.append('buttonId', btnID);
        $.ajax({
            url: "/order/uploadMedia",
            type: "post",
            data: formData,
            contentType: false,
            processData: false,
            beforeSend: function () {
                showLoading(btnID);
            },
            success: function (result) {
                if (result.success == "1") {
                    var buttonId = result.data.buttonId;
                    //创建对象添加
                    var newObj = "";
                    if (result.data.fileType == 'img') {
                        newObj = "<div class='czcs-upload-img'><img class='czcs-main-img' src='" + result.data.fileUrl + "' /><div class='czcs-remove-img' onclick='removeFile(this)'></div></div>";
                    } else {
                        newObj = "<div class='czcs-upload-img'><video class='czcs-main-video' src='" + result.data.fileUrl + "' controls><p class='vjs-no-js'> 您的浏览器不支持 HTML 5 Video 标签，请升级浏览器。</p></video><div class='czcs-remove-img' onclick='removeFile(this)'></div></div>";
                    }
                    $('#' + buttonId).before(newObj);

                } else {
                    showDialog("错误",result.message,function () {},400,50);
                }
            },
            error: function (data) {
                showDialog("错误",'上传失败');
            },
            complete: function (XMLHttpRequest) {
                var result = JSON.parse(XMLHttpRequest.responseText);
                var btnID = result.data.buttonId;
                hideLoading(btnID);
            }
        });
    }
    function showLoading(addBtnId) {
        $('#' + addBtnId).hide();
        $('#' + addBtnId).parent().children('.loading').show();
    }

    function hideLoading(addBtnId) {
        $('#' + addBtnId).parent().children('.loading').hide();
        var imgCount = $('#' + addBtnId).parent().children('.czcs-upload-img').length;
        if (imgCount >= 5) {
            $('#' + buttonId).hide();
        } else {
            $('#' + addBtnId).show();
        }
    }

    function checkFileVedio(file) {
        var fileName = file.name;
        var fileExtension = fileName.split('.').pop().toLowerCase();
        if (!(fileExtension && /^(mp4|avi)$/i.test(fileExtension))) {
            return false;
        }
        return true;
    }

    function checkFileImg(file) {
        var fileName = file.name;
        var fileExtension = fileName.split('.').pop().toLowerCase();
        if (!(fileExtension && /^(jpg|png|jpeg)$/i.test(fileExtension))) {
            return false;
        }
        return true;
    }
})