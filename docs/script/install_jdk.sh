#!/bin/bash


source /etc/profile
java -version



if [ $? -eq 0 ]
then
    echo "JDK已经存在，退出安装程序。。。"
else
    # ========================================================
    yum install -y git

    grep "JAVA_HOME=/usr/local/java" /etc/profile

    if [ $? -eq 0 ]
    then
        echo "/etc/profile中已经存在JDK的环境变量，退出安装程序。。。"
    else
        # JDK不存在，执行如下安装操作。。。
        echo "================================"
        echo "正在从云端拉取JDK安装包，大小为170MB左右，请安心等待3~5分钟即可。。。"
        echo "================================"
        git clone https://gitee.com/tay3223/git_bao_JDK.git ~/git_bao_JDK

        tar zxvf ~/git_bao_JDK/bao/jdk-8u91-linux-x64.tar.gz -C /usr/local/

        ln -s /usr/local/jdk1.8.0_91 /usr/local/java

        echo "export JAVA_HOME=/usr/local/java" >> /etc/profile
        echo "export PATH=\$PATH:\$JAVA_HOME/bin" >> /etc/profile

        source /etc/profile
        java -version
    fi
    # ========================================================
fi