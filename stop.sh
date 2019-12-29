netstat -nlp |grep :8080 |grep -v grep|awk '{print $7}' |awk -F '/' '{print $1}' |xargs kill -9
