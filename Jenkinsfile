// create a new pipeline with stages to checkout the code build & test,
// build a Docker container and then deploy to Kubernetes

pipeline {
    agent any
    stages {
        stage ('checkout scm') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh ''
            }
        }

        stage('Docker build') {
            steps {
                sh '''

                '''
            }
        }

        stage('Deploy dev to EKS') {
            steps {
                sh '''

                '''
            }
        }
    }
}
