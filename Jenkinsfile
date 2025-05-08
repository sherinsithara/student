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
                script {
                    // Build Docker image using standard bat command
                    bat "docker build -t %IMAGE_NAME% ."
                }
            }
        }

        stage('Run Docker Container') {
            steps {
                script {
                    // Check if PORT_1 is in use (Windows command)
                    def portInUse = bat(
                        script: "netstat -ano | findstr :%PORT_1%",
                        returnStatus: true
                    )

                    // Stop and remove container if it exists
                    bat "docker stop %CONTAINER_NAME% || exit 0"
                    bat "docker rm %CONTAINER_NAME% || exit 0"

                    // Choose port based on availability
                    if (portInUse != 0) {
                        bat "docker run -d -p %PORT_1%:%PORT_1% --name %CONTAINER_NAME% %IMAGE_NAME%"
                    } else {
                        bat "docker run -d -p %PORT_2%:%PORT_2% --name %CONTAINER_NAME% %IMAGE_NAME%"
                    }
                }
            }
        }
    }
}
