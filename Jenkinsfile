pipeline {
    agent any

    environment {
        DOCKERHUB_CREDENTIALS_ID = 'dockerhub-credentials'
        DOCKERHUB_USERNAME = 'deathlymade' // <--- CHANGE THIS
        BACKEND_IMAGE_NAME = "deathlymade/spe-backend"
        FRONTEND_IMAGE_NAME = "deathlymade/spe-frontend"
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Pulling latest code from repository...'
                git branch: 'main', url: 'https://github.com/DeathlyMade/SPE_Mini_Project.git'
            }
        }

        stage('Build & Test Backend') {
            steps {
                echo 'Building and testing the Java backend...'
                sh 'mvn clean install'
            }
        }

        stage('Build & Tag Docker Images') {
            steps {
                echo 'Building Docker images...'
                sh "docker build -t ${env.BACKEND_IMAGE_NAME}:latest -f backend.Dockerfile ."
                sh "docker build -t ${env.FRONTEND_IMAGE_NAME}:latest -f frontend.Dockerfile ."
            }
        }

        stage('Push Docker Images to Docker Hub') {
            steps {
                echo "Logging in to Docker Hub and pushing images..."
                withCredentials([usernamePassword(credentialsId: env.DOCKERHUB_CREDENTIALS_ID, usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                    sh "echo ${DOCKER_PASSWORD} | docker login -u ${DOCKER_USERNAME} --password-stdin"
                    sh "docker push ${env.BACKEND_IMAGE_NAME}:latest"
                    sh "docker push ${env.FRONTEND_IMAGE_NAME}:latest"
                }
            }
        }

        stage('Deploy with Ansible') {
            steps {
                echo 'Deploying application using Ansible native Docker modules...'
                // ansiblePlaybook(
                //     // Call the new playbook that doesn't use docker-compose
                //     playbook: 'deploy-ansible.yml',
                //     inventory: 'inventory',
                //     // The variables passed to Ansible remain the same
                //     extras: "-e 'backend_image=${env.BACKEND_IMAGE_NAME}:latest' -e 'frontend_image=${env.FRONTEND_IMAGE_NAME}:latest' -e 'ansible_python_interpreter=/usr/bin/python3'"
                // )
                sh "ansible-playbook -i inventory -vvv deploy-ansible.yml"
            }
        }
    }

    post {
        success {
            emailext(
                subject: "SUCCESS: Pipeline '${currentBuild.fullDisplayName}'",
                body: """<p>Build and Deployment Succeeded!</p>
                       <p>Project: ${env.JOB_NAME}, Build: ${env.BUILD_NUMBER}</p>
                       <p>Check the console output at: <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>""",
                to: 'divyamsareen@gmail.com' // <--- CHANGE THIS
            )
        }
        failure {
            emailext(
                subject: "FAILURE: Pipeline '${currentBuild.fullDisplayName}'",
                body: """<p>Build Failed!</p>
                       <p>Project: ${env.JOB_NAME}, Build: ${env.BUILD_NUMBER}</p>
                       <p>Check the console output at: <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>""",
                to: 'divyamsareen@gmail.com' // <--- CHANGE THIS
            )
        }
        always {
            echo 'Pipeline finished.'
            cleanWs()
        }
    }
}