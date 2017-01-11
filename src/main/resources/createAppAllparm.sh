#!/bin/sh

echo "设置,JAVA与MAVEN环境"
JAVA_HOME="/Library/Java/JavaVirtualMachines/jdk1.7.0_80.jdk/Contents/Home/jre"
M2_HOME="/usr/local/maven3.3.9"
echo "设置完成!"

echo "进人工作目录,如果不存在则创建"
cd $2
echo $2

echo "执行maven命令,查看maven版本"
mvn -version > tttt.txt

echo "执行maven 构建工程"
echo "设置maven脚本参数内容如下"
atype="generate"
aCatalog="local"
agroupId="com.nswt"
aid="8888888"
avsion="1.0-SNAPSHOT"
atgid="com.nswt"
ataid="nswt-web-archetype"
atv="1.0"
arul="http://192.168.2.86:9091/nexus/content/groups/public/archetype-catalog.xml"
apkg="tooop"
atm="false"
echo "JAVA_HOME="$JAVA_HOME
echo "M2_HOME="$M2_HOME
echo "command:mvn"
echo "archetype:"$atype
echo "archetypeCatalog="$aCatalog
echo "groupId="$agroupId
echo "artifactId="$aid
echo "version="$avsion
echo "archetypeGroupId="$atgid
echo "archetypeArtifactId="$ataid
echo "archetypeVersion="$atv
echo "archetypeRepository="$arul
echo "package="$apkg
echo "interactiveMode="$atm
echo "开始执行命令"
#mvn -X archetype:$archetype -DarchetypeCatalog=$aCatalog -DgroupId={$agroupId} -DartifactId=$aid -Dversion=$avsion -DarchetypeGroupId={$atgid} -DarchetypeArtifactId={$ataid} -DarchetypeVersion=$atv -DarchetypeRepository={$arul} -Dpackage=$apkg -DinteractiveMode=$atm
mvn -X archetype:generate -DarchetypeCatalog=local -DgroupId=com.nswt -Dversion=1.0-SNAPSHOT -DarchetypeGroupId=com.nswt -DarchetypeArtifactId=nswt-web-archetype -DarchetypeVersion=1.0 -DarchetypeRepository=http://192.168.2.86:9091/nexus/content/groups/public/archetype-catalog.xml -Dpackage=tooop -DinteractiveMode=false -DartifactId=$1
echo "执行完毕"