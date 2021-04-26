#!/bin/bash

DATEFMT="+%m/%d/%Y"
if [ $# -ne 3 ]; then
  echo "Invalid arguments: create START_DATE END_DATE"
  echo "Date format: MM/DD/YYYY"
  exit 1
fi

CMD=$1
START_DATE=$2
END_DATE=$3

DATE=${START_DATE}

while [ ${DATE} != ${END_DATE} ]
do
  echo "Processing date ${DATE}"
  java -Djava.util.logging.config.file=./app.logging.properties -jar ./class-manager-0.0.2-SNAPSHOT.jar ${CMD} ${DATE}
  DATE=$(date ${DATEFMT} -d "$DATE + 1 day")
done

