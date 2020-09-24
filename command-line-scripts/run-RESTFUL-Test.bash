#!/bin/bash
#
# How to test this script:
# ========================
#
#   Run this script:
#   bash run-RESTFUL-Test.bash
#
#   The script will download the graphwalker jar and and example model
#   to the current folder and execute that using the RESTFUL service.
#
#



# Check if dependecy command exists
command -v curl >/dev/null 2>&1 || { echo >&2 "Script requires curl but it's not installed.  Aborting."; exit 1; }
command -v java >/dev/null 2>&1 || { echo >&2 "Script requires java but it's not installed.  Aborting."; exit 1; }
command -v wget >/dev/null 2>&1 || { echo >&2 "Script requires wget but it's not installed.  Aborting."; exit 1; }
command -v jq >/dev/null 2>&1 || { echo >&2 "Script requires jq but it's not installed.  Aborting."; exit 1; }



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
VERSION=4.3.0
GW_JAR=graphwalker-cli-$VERSION.jar
if [ ! -f $GW_JAR ]
then
  wget https://github.com/GraphWalker/graphwalker-project/releases/download/$VERSION/$GW_JAR
fi



# Launch graphwalker, and redirect stderr and stdout to gw.log
java -jar $GW_JAR -d all online --service RESTFUL > gw.log 2>&1 &
PID=$!

# Wait until graphwalker has started
sh -c 'tail -n +0 --pid=$$ -f gw.log | { sed "/Press Control/ q" && kill $$ ;}'
echo "GraphWalker REST started"

# Get the model file
GW_MODEL=PetClinic.json
if [ ! -f $GW_MODEL ]
then
  wget https://raw.githubusercontent.com/GraphWalker/graphwalker-example/master/java-petclinic/src/main/resources/com/company/$GW_MODEL
fi

# Upload model file to service
RESULT=$(curl -s -H "Content-Type: text/plain;charset=UTF-8" -X POST -d @$GW_MODEL http://localhost:8887/graphwalker/load | jq -r .result)

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
