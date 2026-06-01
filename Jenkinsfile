pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                echo 'Code checked out from GitHub'
            }
        }

        stage('Backend Build') {
            steps {
                dir('backend') {
                    sh 'chmod +x mvnw'
                    sh './mvnw clean package -DskipTests'
                }
            }
        }

        stage('Frontend Build') {
            steps {
                dir('frontend') {
                    sh 'npm install'
                    sh 'npm run build'
                }
            }
        }

        stage('Deploy to AWS EC2') {
            steps {
                echo 'Deploying application to AWS EC2...'

                sh '''
                echo "Stopping old backend if running..."
                pkill -f "tax-0.0.1-SNAPSHOT.jar" || true

                echo "Starting backend..."
                nohup java -jar backend/target/tax-0.0.1-SNAPSHOT.jar > backend-app.log 2>&1 &

                sleep 5
                echo "Application deployed successfully"
                '''
            }
        }
    }
}
