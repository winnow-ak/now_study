### Mac切换node版本
1.首先安装n模块：sudo npm install -g n

2.升级node.js到最新稳定版 
sudo n stable

3.升级到最新版
sudo n latest

4.n后面也可以跟随版本号,升级到任意版本
sudo n v0.10.26或sudo n 0.10.26

5.切换使用版本
sudo n 7.10.0

6.删除制定版本
sudo n rm 7.10.0

7.用制定的版本执行脚本
n use 7.10.0 some.js

8.sudo n
可以查看所有已安装的node版本，可以根据上下和回车选择要使用的版本
