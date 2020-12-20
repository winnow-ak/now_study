#!/bin/bash

# 卸载原来旧的docker
yum remove docker  docker-common docker-selinux docker-engine

# 安装依赖
yum install -y yum-utils device-mapper-persistent-data lvm2 epel-release python-pip git

# 添加docker源
yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo

# 安装docker
yum -y install docker-ce

# 安装compose（-I：忽略已经存在的包，强制重新安装）
# pip install -I docker-compose

# 国内加速
curl -sSL https://get.daocloud.io/daotools/set_mirror.sh | sh -s http://f1361db2.m.daocloud.io

# 启动 && 开机启动
systemctl enable docker
systemctl start docker

# 查看版本
docker --version