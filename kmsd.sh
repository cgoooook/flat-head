#!/bin/bash

DESC="Key Management System"
NAME=kmsd
PIDFILE=/var/run/$NAME.pid
SCRIPTNAME=/etc/init.d/$NAME
SYSTEMCTL_SKIP_REDIRECT=1
USER=root
OPATH=$PATH

###############
# SysV Init Information
# chkconfig: 2345 20 80
# description: Description of the <service>
### BEGIN INIT INFO
# Provides: $NAME
# Required-Start:  $local_fs $network $named $time $syslog
# Required-Stop:  $local_fs $network $named $time $syslog
# Default-Start: 2 3 4 5
# Default-Stop: 0 1 6
# Should-Start: $named
# Should-Stop: $named
# Short-Description: start and stop <service>
# Description: <service> daemon
### END INIT INFO

# Get function from functions library
if [ -f /etc/init.d/functions ]; then
  . /etc/init.d/functions
fi

# Start the service
start() {
  echo -n "Starting $NAME service..."
  if [ -f $PIDFILE ]; then
    PID=$(cat $PIDFILE)
    if [ -x failure ]; then
    failure && echo
    fi
    echo $"$PIDFILE exists (pid $PID), service $NAME is already running or crashed"
    return 0
  else
    cd /srv/$NAME
    su -c "./kmsd.sh daemon &" #USER
    RETVAL=0
    echo
    return $RETVAL
  fi
}

# Stop the service
stop() {
  echo -n "Stopping $NAME service... "
  if [ ! -f $PIDFILE ]; then
    if [ -x failure ]; then
    failure && echo
    fi
    echo "$PIDFILE does not exist, process is not running"
    return 0
  else
    killproc -p $PIDFILE $NAME
    RETVAL=$?
    killall -9 $NAME
    echo
    return $RETVAL
  fi
}

# Reload the service
reload() {
  echo -n "Reloading $NAME service..."
  killproc -p $PIDFILE $NAME -HUP
  RETVAL=$?
  echo
  return $RETVAL
}

# run the service in frontground
run() {
  java -jar kms-services-executable.jar
}

daemon() {
  echo "Pid is" $$
  echo $$ >$PIDFILE
  while true; do
    java -jar kms-services-executable.jar >/dev/null 2>&1
    echo "Relaunching..."
    sleep 1
  done
  rm $PIDFILE
}

### main logic ###
case "$1" in
start)
  start
  ;;
stop)
  stop
  ;;
status)
  if [ -x status ]; then
  status -p $PIDFILE $NAME
  fi
  ps -ef | grep -v grep | grep -v status | grep $NAME
  ;;
restart)
  stop
  start
  ;;
reload)
  reload
  ;;
run)
  run
  ;;
daemon)
  daemon
  ;;
*)
  echo $"Usage: $0 {start|stop|restart|reload|status|run}"
  exit 1
  ;;
esac
exit $?
