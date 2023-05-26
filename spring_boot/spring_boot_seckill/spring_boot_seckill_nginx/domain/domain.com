server {
    listen 7081;
    location /sayHello {
        default_type text/application;
        content_by_lua_block {
            ngx.say("hello world!!!")
        }
    }
}