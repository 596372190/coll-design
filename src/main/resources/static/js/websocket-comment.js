var stompClient = null;
var wsCreateHandler = null;
var userId = null;

function connect() {
    var host = window.location.host; // 带有端口号
    userId = GetQueryString("userId");
    projectId = $("#projectId").val();
    console.log("userId:" + userId);
    var socket = new SockJS('/websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
            writeToScreen("connected: " + frame);
            stompClient.subscribe('/topic', function (response) {
                writeToScreen(response.body);
            });

            stompClient.subscribe("/user/queue/link", function (response) {
                writeToScreen(response.body);
            });

            stompClient.subscribe("/user/queue/sync/comment/aaa", function (response) {
                writeToScreen(response.body);
            });

            stompClient.subscribe('/sendToAll', function (response) {
                writeToScreen("sendToAll:" + response.body);
            });

        }, function (error) {
            wsCreateHandler && clearTimeout(wsCreateHandler);
            wsCreateHandler = setTimeout(function () {
                console.log("重连...");
                connect();
                console.log("重连完成");
            }, 1000);
        }
    )
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    writeToScreen("disconnected");
}

function writeToScreen(message) {
    console.log(message);
}

function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg); //获取url中"?"符后的字符串并正则匹配
    var context = "";
    if (r != null)
        context = r[2];
    reg = null;
    r = null;
    return context == null || context == "" || context == "undefined" ? "" : context;
}