#!/bin/bash

# Проверяем наличие папки для логов HAProxy, создаем ее при необходимости
log_dir="./haproxy/logs"
mkdir -p "$log_dir"

# Находим ID контейнера HAProxy
container_id=$(docker ps --filter "ancestor=haproxy:latest" -q)

if [ -z "$container_id" ]; then
  echo "Не удалось найти запущенный контейнер HAProxy."
  exit 1
fi

echo "Найден контейнер HAProxy с ID: $container_id"

# Просматриваем логи HAProxy в реальном времени и перенаправляем их в файл haproxy.log
docker logs -f $container_id > "$log_dir/haproxy.log" &
tail -f "$log_dir/haproxy.log"