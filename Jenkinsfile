pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'student-app'
        DOCKER_TAG = 'latest'
        CONTAINER_NAME = 'student-container'
        JAR_FILE = 'target/student-application-0.0.1-SNAPSHOT.jar'
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/sherinsithara/student.git'
            }
        }

        stage('Build JAR') {
            steps {
                // Use the full path to Maven (if needed)
                bat '"C:\\Program Files\\Apache\\Maven\\bin\\mvn" clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("${DOCKER_IMAGE}:${DOCKER_TAG}")
                }
            }
        }

        stage('Trivy Scan') {
            steps {
                echo 'Running Trivy vulnerability scan...'
                script {
                    // Run Trivy scan on the Docker image
                    bat "trivy image ${DOCKER_IMAGE}:${DOCKER_TAG} --format table --exit-code 1 --no-progress"
                }
            }
        }

        stage('Run Docker Container') {
            steps {
                script {
                    bat "docker rm -f ${CONTAINER_NAME} || exit 0"
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
            bat "docker stop ${CONTAINER_NAME} || exit 0"
            bat "docker rm ${CONTAINER_NAME} || exit 0"
        }
    }
}
