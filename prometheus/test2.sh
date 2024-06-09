#!/bin/bash

count=1  # Начальное количество запусков

while true; do
    echo "Iteration $count"
    ab -n 6000 -c 10 -t 600 http://localhost/auth/health
    ((count++))  # Увеличение счетчика на 1
done