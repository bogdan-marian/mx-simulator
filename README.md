# mx-simulator

I'm trying to build clear examples that show from outside on mx-sdk repo how you can use the simulator

this make targets work
- run-repo-examples: this one clones the mx-chain-simulator-go and runs all the run-examples in the respectiv repo
- run-local-adder-example: local folder adder-example is a copy of adder-example of skd simulator repo but it adds also the folowing files that have also bean collecte and copied over to the local folder so we have complete view over what is needed
  - wallet.pem
  - script.sh modified to only start adder.py
  - adder.py modified to only use fiels in the current directory
