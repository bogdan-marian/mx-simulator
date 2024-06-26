#!Makefile

CHAIN_SIMULATOR_IMAGE_NAME=chainsimulator
CHAIN_SIMULATOR_IMAGE_TAG=latest
DOCKER_FILE=Dockerfile
IMAGE_NAME=simulator_image
SIMULATOR_REPO_URL=https://github.com/multiversx/mx-chain-simulator-go
SIMULATOR_REPO_BRANCH=main
CONTAINER_NAME=chainsimulator_container
REPO_TEMP_DIR=$(HOME)/.temp_simulator_repo
PIP_ENV_DIR=$(HOME)/.simulatorPipEnv

docker-build:
	docker build \
		--build-arg SIMULATOR_REPO_URL=${SIMULATOR_REPO_URL} \
		--build-arg SIMULATOR_REPO_BRANCH=${SIMULATOR_REPO_BRANCH} \
		-t ${CHAIN_SIMULATOR_IMAGE_NAME}:${CHAIN_SIMULATOR_IMAGE_TAG} \
		-f ${DOCKER_FILE} \
		.


setup-env:
	rm -rf ${REPO_TEMP_DIR}
	git clone --branch ${SIMULATOR_REPO_BRANCH} ${SIMULATOR_REPO_URL} ${REPO_TEMP_DIR}
	python3 -m venv ${PIP_ENV_DIR}
	. ${PIP_ENV_DIR}/bin/activate && pip install -r ${REPO_TEMP_DIR}/examples/requirements.txt

docker-run: setup-env
	docker stop "${IMAGE_NAME}" || true
	docker rm "${IMAGE_NAME}" || true

	docker run -d --name "${IMAGE_NAME}" -p 8085:8085 ${CHAIN_SIMULATOR_IMAGE_NAME}:${CHAIN_SIMULATOR_IMAGE_TAG}

docker-stop:
	docker stop "${IMAGE_NAME}" || true

docker-rm:
	docker rm "${IMAGE_NAME}" || true



run-repo-examples: setup-env
	$(MAKE) docker-build
	docker stop "${IMAGE_NAME}" || true
	docker rm "${IMAGE_NAME}" || true
	docker run -d --name "${IMAGE_NAME}" -p 8085:8085 ${CHAIN_SIMULATOR_IMAGE_NAME}:${CHAIN_SIMULATOR_IMAGE_TAG}
	echo "Running script in ${REPO_TEMP_DIR}/scripts/run-examples"
	if [ -f ${REPO_TEMP_DIR}/scripts/run-examples/script.sh ]; then \
		cd ${REPO_TEMP_DIR}/scripts/run-examples && . ${PIP_ENV_DIR}/bin/activate && /bin/bash script.sh; \
	else \
		echo "script.sh not found in ${REPO_TEMP_DIR}/scripts/run-examples"; \
	fi
	docker stop "${IMAGE_NAME}"
	docker rm ${IMAGE_NAME}

run-local-adder-example: setup-env
	$(MAKE) docker-build
	# sometimes you need to run this before
	# chmod -R u+w /home/bogdan/.simulatorPipEnv
	docker stop "${IMAGE_NAME}" || true
	docker rm "${IMAGE_NAME}" || true
	docker run -d --name "${IMAGE_NAME}" -p 8085:8085 ${CHAIN_SIMULATOR_IMAGE_NAME}:${CHAIN_SIMULATOR_IMAGE_TAG}
	echo "Running adder example script"
	. ${PIP_ENV_DIR}/bin/activate && pip install -r ${REPO_TEMP_DIR}/examples/requirements.txt && cd $(CURDIR)/adder-example && /bin/bash script.sh
	docker stop "${IMAGE_NAME}"
	docker rm "${IMAGE_NAME}"

run-java-broadcast-test: setup-env
	$(MAKE) docker-build
	# sometimes you need to run this before
	# chmod -R u+w /home/bogdan/.simulatorPipEnv
	docker stop "${IMAGE_NAME}" || true
	docker rm "${IMAGE_NAME}" || true
	docker run -d --name "${IMAGE_NAME}" -p 8085:8085 ${CHAIN_SIMULATOR_IMAGE_NAME}:${CHAIN_SIMULATOR_IMAGE_TAG}
	echo "Waiting for simulator to start"
	cd $(CURDIR)/adder-example-java && ./waitSimulatorToStart.sh
	echo "Running Java BroadcastTest"
	cd $(CURDIR)/adder-example-java && ./gradlew test --tests "adder.example.BroadcastTest.sendTransactions"
	docker stop "${IMAGE_NAME}"
	docker rm "${IMAGE_NAME}"