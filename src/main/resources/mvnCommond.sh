#!/bin/sh

echo "����,JAVA��MAVEN����"
JAVA_HOME="/Library/Java/JavaVirtualMachines/jdk1.7.0_80.jdk/Contents/Home/jre"
M2_HOME="/usr/local/maven3.3.9"
echo "�������!"

echo "���˹���Ŀ¼,����������򴴽�"
cd $1
echo $1

echo "ִ��maven����,�鿴maven�汾"
mvn $2
echo "ִ�����"