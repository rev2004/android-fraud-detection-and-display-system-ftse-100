#!/bin/bash

running="false"
log="FinanceLog_`date +"%y_%m_%d_%H_%M_%S"`"

echo "`date` Started" >> "$log"

while [ true ]
do
        hour=`date +"%H"`
        day=`date +"%w"`
        if [[ 10#$hour -le 17 && 10#$hour -ge 6 && 10#$day -ne 6 && 10#$day -ne 0 ]]; then
                if [ $running = "false" ]; then
                        year=`date +"%D" | sed 's/\//_/g'`
                        year="FinanceData${year}"
                        nc localhost 9745 >> $year &
                        pid=$!
                        running="true"
                        echo "`date` Running" >> "$log"
                fi
        else
                if [ $running = "true" ]; then
                        kill -9 $pid
                        running="false"
                        echo "`date` Sleeping 4h" >> "$log"
                        sleep 4h
                fi

                echo "`date` Sleeping 50m" >> "$log"
                sleep 50m
        fi
        sleep 2s
done