#!/bin/bash

wget -i -c http://dev.mysql.com/get/mysql57-community-release-el7-10.noarch.rpm

yum -y install mysql57-community-release-el7-10.noarch.rpm


yum -y install mysql-community-server

#Centos 7 开启防火墙
#systemctl start firewalld


#开发端口
#firewall-cmd --zone=public --add-port=3306/tcp --permanent # 开放3306端
#firewall-cmd --zone=public --remove-port=3306/tcp --permanent #关闭3306端口

#防火墙配置立即生效
#firewall-cmd --reload

grant all privileges on *.* to 'root'@'%' identified by 'a123456' with grant option;
