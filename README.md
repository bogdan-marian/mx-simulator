# mx-simulator

This is a examples that shows from outside of mx-sdk repo how you can use the simulator. 
Sometimes devnet or test net they slow you down. This is fast (the docker-run task, use this to debug while working on client apps)
Make tasks they clone and build the simulator and then build a docker image with simulator waiting for
stuff to happen

# make flow

## make run-repo-examples 
this one clones the mx-chain-simulator-go and runs all the run-examples in the respectiv repo

## make run-local-adder-example
local folder adder-example is a copy of adder-example of skd simulator repo but it adds also the folowing files that have also bean collect and copied over to the local folder so we have complete view over what is needed
  - wallet.pem
  - script.sh modified to only start adder.py
  - adder.py modified to only use fiels in the current directory

## make run-java-broadcast-test
This will run the java broadcast test. Run this on a machine that has jdk 17 o higher configured since the scripts to not configure it for you 

## make docker-run
This is useful for debugging. 
You can also run the emulator and keep it running so you can connect to with with any 
language you want. I use java but you can use any other. 

## make docker-stop
shuts down multiversx/chainsimulator

