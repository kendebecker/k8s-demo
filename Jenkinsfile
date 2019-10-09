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
                sh 'mvn clean install'
            }
        }

        stage('Docker build') {
            steps {
                sh '''
                docker build -t 163091829738.dkr.ecr.eu-west-1.amazonaws.com/k8s-demo-kdb:$BUILD_NUMBER-$GIT_COMMIT .

                docker push 163091829738.dkr.ecr.eu-west-1.amazonaws.com/k8s-demo-kdb:$BUILD_NUMBER-$GIT_COMMIT
                '''
            }
        }

        stage('Deploy dev to EKS') {
            steps {
                sh '''
                export KUBECONFIG=/opt/kubecfg/config
                envsubst < k8s/demo-deployment.yaml | kubectl apply -f -
                '''
            }
        }
    }
}
