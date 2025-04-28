pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'student-app'   // Docker image name
        CONTAINER_NAME = 'my-student-container'  // New container name
    }

    stages {
        stage('Build with Maven') {
            steps {
                script {
                    // Use Maven Wrapper if 'mvn' is not available globally
                    bat './mvnw clean install'  // Use Maven wrapper (mvnw)
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    // Build the Docker image for your app
                    bat 'docker build -t ${DOCKER_IMAGE} .'
                }
            }
        }

        stage('Run Docker Container') {
            steps {
                script {
                    // Clean up any existing container with the same name if it exists
                    bat """
                        FOR /F "tokens=*" %%i IN ('docker ps -aq --filter "name=${CONTAINER_NAME}"') DO (
                            echo Stopping container %%i
                            docker stop %%i
                            echo Removing container %%i
                            docker rm %%i
                        )
                    """

                    // Declare variable to hold host port
                    def HOST_PORT = '8086'  // Default host port
                    def portFound = false
                    def triedPorts = ['8086', '8087', '8088', '8089', '8090']

                    // Try ports 8086, 8087, 8088, etc. to find an available port
                    for (port in triedPorts) {
                        echo "Checking port ${port}..."
                        def result = bat(script: "netstat -ano | findstr :${port}", returnStatus: true)
                        if (result != 0) {
                            echo "Port ${port} is available."
                            HOST_PORT = port
                            portFound = true
                            break
                        } else {
                            echo "Port ${port} is in use."
                        }
                    }

                    if (!portFound) {
                        error "No available ports found."
                    }

                    // Run the Docker container on the selected port
                    bat "docker run -d -p ${HOST_PORT}:8086 --name ${CONTAINER_NAME} ${DOCKER_IMAGE}"
                    echo "Container is running on port ${HOST_PORT}."
                }
            }
        }
    }

    post {
        always {
            // Clean up: Remove Docker containers after the pipeline
            bat """
                FOR /F "tokens=*" %%i IN ('docker ps -q --filter "ancestor=${DOCKER_IMAGE}"') DO (
                    echo Stopping container %%i
                    docker stop %%i
                )
                FOR /F "tokens=*" %%i IN ('docker ps -aq --filter "ancestor=${DOCKER_IMAGE}"') DO (
                    echo Removing container %%i
                    docker rm %%i
                )
            """
        }
    }
}
