function logout() {
    if (confirm("是否退出?")) {
        alert("执行退出操作");
        // 给后端发送请求退出登录
        jQuery.ajax({
            url:"/user/logout",
            type:"Post",
            data:{},
            success:function(res) {
                if (res.code == 200 && res.data == 1) {
                    // 注销成功
                    location.href = "blog_list.html";
                } else {
                    alset("操作失败！！"+res.msg);
                }
            }
        });
    } 
}
