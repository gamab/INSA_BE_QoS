#!/bin/bash
ifconfig eth0 192.168.1.100/24
rmiregistry -J-Djava.rmi.server.useCodebaseOnly=false&
cd /opt/beqos/openjsip/conf
nano users.properties

