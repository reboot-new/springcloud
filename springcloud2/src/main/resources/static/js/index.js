$(function(){
    $('#btnHello').on('click',function () {
        $.ajax({
            url: 'hello/index',
            datatype: 'json',
            type: "get",
            contentType: "application/json",
            success: function (data) {
                $('#content').html(data)
            },
            error: function () {
                alert("An error during data saving. Please, try again later");
            }
        });
    });
});