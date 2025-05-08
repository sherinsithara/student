pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'student-app'
        DOCKER_TAG = 'latest'
        CONTAINER_NAME = 'student-container'
        MAVEN_HOME = 'C:\\Users\\apache-maven-3.9.9'  // Set Maven path here
        PATH = "${MAVEN_HOME}\\bin;${env.PATH}"    // Add Maven bin folder to PATH
        JAR_FILE = 'target/student-application-0.0.1-SNAPSHOT.jar'  // Make sure this matches your build path
        TRIVY_IMAGE = 'aquasec/trivy'  // Trivy Docker image
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
                // Build the JAR file using Maven
                bat 'mvn clean package -DskipTests'
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

        stage('Trivy Vulnerability Scan') {
            steps {
                script {
                    // Run Trivy vulnerability scan on the built Docker image
                    echo 'Running Trivy vulnerability scan...'
                    try {
                        bat "docker run --rm ${TRIVY_IMAGE} --docker ${DOCKER_IMAGE}:${DOCKER_TAG} --format json --output trivy-report.json"
                    } catch (Exception e) {
                        echo "Trivy scan failed: ${e}"
                        currentBuild.result = 'FAILURE'
                    }
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
