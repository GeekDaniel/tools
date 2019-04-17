#!/bin/bash

TARGET_DIR=./classes
JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_161.jdk/Contents/Home

# 删除目录
if [ -d ${TARGET_DIR} ]; then
    rm -rf ${TARGET_DIR}
fi

# 创建目录
mkdir ${TARGET_DIR}

# tools.jar的路径
TOOLS_PATH=${JAVA_HOME}/lib/tools.jar

# 编译Builder注解以及注解处理器
javac -cp ${TOOLS_PATH} $(find ../java -name "*.java")  -d ${TARGET_DIR}/

# 统计文件 `META-INF/services/javax.annotation.processing.Processor` 的行数
LINE_NUM=$(cat META-INF/services/javax.annotation.processing.Processor | wc -l)
LINE_NUM=$((LINE_NUM+1))

# 将文件 `META-INF/services/javax.annotation.processing.Processor` 中的内容合并成串，以','分隔
PROCESSORS=$(cat META-INF/services/javax.annotation.processing.Processor | awk '{ { printf $0 } if(NR < "'"${LINE_NUM}"'") { printf "," } }')

echo "1"
echo  ${PROCESSORS}
# 编译UserDTO.java，通过-process参数指定注解处理器
javac -cp ${TARGET_DIR} -d ${TARGET_DIR} -processor ${PROCESSORS} User.java
echo "2"
# 运行UserDTO
java -cp ${TARGET_DIR} User
echo "3"
# 删除目录
#rm -rf classes
