#!/bin/bash
#
# How to test this script:
# ========================
#
# 1)  Either do step 2) or step 3)
#
# 2)  Download the graphwalker jar file:
#     wget http://graphwalker.org/archive/graphwalker-cli-3.4.0-SNAPSHOT.jar
#
#     Continue to step 4)
#
# 3)  Checkout the GraphWalker project from github
#     git clone https://github.com/GraphWalker/graphwalker-project.git
#
#     Build the standalone graphwalker jar file
#     cd graphwalker-project
#     mvn package -pl graphwalker-cli -am
#     cp graphwalker-cli/target/graphwalker-cli-*.jar .
#
# 4)  Run this script into the graphwalker-project folder and run:
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



# Launch graphwalker, and redirect stderr and stdout to gw3.log
java -jar graphwalker-cli-3.4.0-SNAPSHOT.jar -d all online --service RESTFUL > gw3.log 2>&1 &
PID=$!

# Wait until graphwalker has started
sh -c 'tail -n +0 --pid=$$ -f gw3.log | { sed "/Press Control/ q" && kill $$ ;}'
echo "GraphWalker REST started"

# Upload models to service
GW3_FILE=graphwalker-io/src/test/resources/gw3/petClinic.gw3
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
