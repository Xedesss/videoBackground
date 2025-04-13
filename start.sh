#!/bin/bash

# 定义颜色
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[0;33m'
NC='\033[0m' # 无颜色

# 定义项目目录和日志文件
PROJECT_DIR=$(pwd)
LOG_FILE="${PROJECT_DIR}/app.log"
PID_FILE="${PROJECT_DIR}/app.pid"

# 打印带颜色的消息
print_message() {
  echo -e "${2}$(date '+%Y-%m-%d %H:%M:%S') - $1${NC}"
}

# 检查Java是否安装
check_java() {
  if ! command -v java &> /dev/null; then
    print_message "Java未安装，请先安装Java" "${RED}"
    exit 1
  fi
  
  java_version=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}')
  print_message "Java版本: ${java_version}" "${GREEN}"
}

# 检查Maven是否安装
check_maven() {
  if ! command -v mvn &> /dev/null; then
    print_message "Maven未安装，请先安装Maven" "${RED}"
    exit 1
  fi
  
  maven_version=$(mvn --version | head -n 1)
  print_message "Maven版本: ${maven_version}" "${GREEN}"
}

# 创建必要的目录
create_directories() {
  print_message "创建上传目录..." "${YELLOW}"
  
  mkdir -p uploads/videos
  mkdir -p uploads/images
  mkdir -p uploads/covers
  mkdir -p uploads/qrcodes
  mkdir -p uploads/documents
  
  print_message "上传目录创建完成" "${GREEN}"
}

# 停止正在运行的应用
stop_app() {
  if [ -f "$PID_FILE" ]; then
    PID=$(cat "$PID_FILE")
    if kill -0 "$PID" &> /dev/null; then
      print_message "停止应用 (PID: $PID)..." "${YELLOW}"
      kill "$PID"
      sleep 5
      
      # 检查是否仍在运行
      if kill -0 "$PID" &> /dev/null; then
        print_message "应用未能优雅停止，强制终止..." "${RED}"
        kill -9 "$PID"
      fi
      
      print_message "应用已停止" "${GREEN}"
    else
      print_message "应用不在运行状态" "${YELLOW}"
    fi
    rm -f "$PID_FILE"
  else
    print_message "PID文件不存在，应用可能未在运行" "${YELLOW}"
  fi
}

# 编译项目
build_app() {
  print_message "清理并编译项目..." "${YELLOW}"
  
  if mvn clean package -DskipTests; then
    print_message "项目编译成功" "${GREEN}"
    return 0
  else
    print_message "项目编译失败" "${RED}"
    return 1
  fi
}

# 启动应用
start_app() {
  if [ ! -f "target/health-video-0.0.1-SNAPSHOT.jar" ]; then
    print_message "找不到JAR文件，请先编译项目" "${RED}"
    return 1
  fi
  
  print_message "启动应用..." "${YELLOW}"
  
  # 使用nohup在后台运行应用
  nohup java -jar target/health-video-0.0.1-SNAPSHOT.jar > "$LOG_FILE" 2>&1 &
  
  # 保存PID
  echo $! > "$PID_FILE"
  
  # 等待应用启动
  sleep 5
  
  # 检查应用是否成功启动
  if [ -f "$PID_FILE" ] && kill -0 $(cat "$PID_FILE") &> /dev/null; then
    print_message "应用成功启动 (PID: $(cat "$PID_FILE"))" "${GREEN}"
    print_message "日志文件: $LOG_FILE" "${GREEN}"
    return 0
  else
    print_message "应用启动失败，请检查日志" "${RED}"
    return 1
  fi
}

# 显示日志
tail_log() {
  if [ -f "$LOG_FILE" ]; then
    print_message "显示最新日志..." "${YELLOW}"
    tail -n 50 "$LOG_FILE"
  else
    print_message "日志文件不存在" "${RED}"
  fi
}

# 主函数
main() {
  # 检查Java和Maven
  check_java
  check_maven
  
  # 停止正在运行的应用
  stop_app
  
  # 创建目录
  create_directories
  
  # 编译项目
  if build_app; then
    # 启动应用
    if start_app; then
      # 显示日志
      tail_log
    fi
  fi
}

# 执行主函数
main 