#!/bin/bash
#
# How to test this script:
# ========================
#
# 1)  The script will download the graphwalker jar and and example model
#     to the current folder.
#
# 2)  Run this script into the graphwalker-project folder and run:
#     bash run-RESTFUL-Test.bash 



# Check if dependecy command exists
command -v jq >/dev/null 2>&1 || { echo >&2 "I require jq but it's not installed.  Aborting."; exit 1; }



# Run if control-c
control_c()
{
  echo -en "\n*** Caught ctrl+c! Exiting ***\n"
  kill $PID
  exit $?
}
 
# trap keyboard interrupt (control-c)
trap control_c SIGINT


# Get the latest graphwalker jar
GW3_JAR=graphwalker-cli-3.4.0-SNAPSHOT.jar
if [ ! -f $GW3_JAR ]
then
  wget http://graphwalker.org/archive/graphwalker-cli-3.4.0-SNAPSHOT.jar
fi



# Launch graphwalker, and redirect stderr and stdout to gw3.log
java -jar graphwalker-cli-3.4.0-SNAPSHOT.jar -d all online --service RESTFUL > gw3.log 2>&1 &
PID=$!

# Wait until graphwalker has started
sh -c 'tail -n +0 --pid=$$ -f gw3.log | { sed "/Press Control/ q" && kill $$ ;}'
echo "GraphWalker REST started"

# Get the model file
GW3_FILE=petClinic.gw3
if [ ! -f $GW3_FILE ]
then
  wget https://raw.githubusercontent.com/GraphWalker/graphwalker-project/master/graphwalker-io/src/test/resources/gw3/petClinic.gw3
fi

# Upload model file to service
RESULT=$(curl -s -H "Content-Type: text/plain;charset=UTF-8" -X POST -d @$GW3_FILE http://localhost:8887/graphwalker/load | jq -r .result)

# Check result
if [[ $RESULT != "ok" ]]
then
  kill $PID
  exit
fi

# While the model is not exhausted
while [[ $(curl -s http://localhost:8887/graphwalker/hasNext | jq -r .hasNext) == "true" ]]
do
  # Get the next element to execute
  GETNEXT=$(curl -s http://localhost:8887/graphwalker/getNext | jq -r .currentElementName)
  echo "Will exectute: $GETNEXT"

  # Get the data in the model,if any
  DATA=$(curl -s http://localhost:8887/graphwalker/getData | jq -r .data)
  echo "Current state of data: $DATA"
done

kill $PID
