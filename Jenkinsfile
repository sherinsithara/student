pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'student-app'
        DOCKER_TAG = 'latest'
        CONTAINER_NAME = 'student-container'
        JAR_FILE = 'target/student-application-0.0.1-SNAPSHOT.jar'  // Ensure this matches your build path
    }

    stages {
        stage('Checkout') {
            steps {
                // Checkout the code from your Git repository
                git 'https://github.com/sherinsithara/student.git'  // Replace with your repo URL
            }
        }

        stage('Build JAR') {
            steps {
                script {
                    // Use the Maven tool configured in Jenkins
                    def mvnHome = tool name: 'Maven 3', type: 'Maven'  // This should match the name you provided in the configuration
                    bat "\"${mvnHome}/bin/mvn\" clean package -DskipTests"
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    // Build the Docker image
                    docker.build("${DOCKER_IMAGE}:${DOCKER_TAG}")
                }
            }
        }

        stage('Run Docker Container') {
            steps {
                script {
                    // Remove the previous container if it exists
                    bat "docker rm -f ${CONTAINER_NAME} || true"
                }

                // Run the Docker container in detached mode
                script {
                    bat "docker run -d --name ${CONTAINER_NAME} -p 8086:8086 ${DOCKER_IMAGE}:${DOCKER_TAG}"
                }
            }
        }

        stage('Post-Build Actions') {
            steps {
                echo 'Build and deployment complete!'
            }
        }
    }

    post {
        always {
            // Stop and remove the container after the build
            bat "docker stop ${CONTAINER_NAME}"
            bat "docker rm ${CONTAINER_NAME}"
        }
    }
}
