pipeline {
    agent any

    environment {
        IMAGE_NAME = 'student-app'
        CONTAINER_NAME = 'student-container'
        PORT_1 = '8086'
        PORT_2 = '8087'
    }

    stages {
        stage('Build Docker Image') {
            steps {
                bat "docker build -t ${IMAGE_NAME} ."
            }
        }

        stage('Run Docker Container') {
            steps {
                script {
                    // Check if port 8086 is available, else use 8087
                    def portCheck = bat(script: "netstat -ano | findstr :${PORT_1}", returnStatus: true)

                    // Stop and remove existing container if it exists
                    bat "docker ps -a -q -f name=${CONTAINER_NAME} | ForEach-Object { docker stop $_; docker rm $_ }"

                    if (portCheck != 0) {
                        bat "docker run -d -p ${PORT_1}:${PORT_1} --name ${CONTAINER_NAME} ${IMAGE_NAME}"
                    } else {
                        bat "docker run -d -p ${PORT_2}:${PORT_2} --name ${CONTAINER_NAME} ${IMAGE_NAME}"
                    }
                }
            }
        }
    }
}
