# mx-simulator

This is a examples that shows from outside of mx-sdk repo how you can use the simulator. 
Sometimes devnet or test net they slow you down. This is fast. 
Make tasks they clone and build the simulator and then build a docker image with simulator waiting for
stuff to happen

# make flow

## docker-build
Run this once before any other task

## run-repo-examples 
this one clones the mx-chain-simulator-go and runs all the run-examples in the respectiv repo

## run-local-adder-example
local folder adder-example is a copy of adder-example of skd simulator repo but it adds also the folowing files that have also bean collect and copied over to the local folder so we have complete view over what is needed
  - wallet.pem
  - script.sh modified to only start adder.py
  - adder.py modified to only use fiels in the current directory

## run-java-broadcast-test
This will run the java broadcast test. 

# debug java code
You can also run the emulator and keep it running so you can connect to with with any 
language you want. I use java but you can use any other. 

