#!/bin/bash

var=`ps aux | grep nginx | grep -v grep | grep /home/www-data/nginx.conf | wc -l`;

if [ "$var" = 1 ]; then
        echo "The nginx is running under right config";
else
        echo "The nginx is not run with right config, kill them and restart it correctly";
        kill -9 `pidof nginx`;
        /usr/sbin/nginx -c /home/www-data/nginx.conf;
fi