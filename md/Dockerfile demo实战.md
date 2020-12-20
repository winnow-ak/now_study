mac dockerfile  demo实践
1.编写Dockerfile文件
```shell script
vi Dockerfile
#-------------------war 编写方式
FROM tomcat:8.5
RUN rm -rf /usr/local/tomcat/webapps/*
#Dockerfile 同级目录war包
ADD *.war /usr/local/tomcat/webapps/app.war
#8080 是tomcat 默认对外端口
EXPOSE 8080
CMD ["/usr/local/tomcat/bin/catalina.sh","run"]
#--------------------jar 编写方式
FROM  java:8
COPY *.jar  /app.jar
EXPOSE 8080
CMD ["java","-jar","/app.jar"]
```
2.构建镜像
```shell script
docker build -t imageName .
#镜像名称 .当前目录Dockerfile
```

3.启动镜像
```shell script
docker run -d -p 8080:8080 --name demo imageName
```

4.查看启动日志
```shell script
docker logs demo
```

5.访问 http://localhost:8080/app/ok.html

"wget https://mirrors.cnnic.cn/apache/tomcat/tomcat-8/v8.5.37/bin/apache-tomcat-8.5.37.tar.gz \t
&& tar -xzf apache-tomcat-8.5.37.tar.gz        
 && mkdir -p apache-tomcat-8.5.37/conf/Catalina/localhost     
     && rm -rf apache-tomcat-8.5.37/webapps/ROOT        
      && sed -i '$i\\\t<CookieProcessor className=\\\"org.apache.tomcat.util.http.LegacyCookieProcessor\\\" />' apache-tomcat-8.5.37/conf/context.xml"