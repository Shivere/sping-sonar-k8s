pipeline {
    agent any

    tools {
        maven 'Apache Maven 3.8.7' // Name of the Maven installation in Jenkins
        jdk 'JDK 17'        // Name of the JDK installation in Jenkins
    }

    environment {
        DOCKER_IMAGE = "shivere/demo-spring-sonar:latest"  // Docker image name
        DOCKER_REGISTRY_CREDENTIALS = 'dockerhub-credentials-id'        // Jenkins credential ID for Docker Hub
        SONAR_ORGANIZATION = 'shivere'             // SonarCloud organization key
        SONAR_PROJECT_KEY = 'demo-spring-sonar'               // SonarCloud project key
        SONAR_TOKEN = credentials('sonarcloud-token-id')                // SonarCloud token stored in Jenkins credentials
        ROSA_KUBECONFIG = credentials('rosa-kubeconfig')                // OpenShift Kubeconfig file stored in Jenkins credentials
        ECR_REPOSITORY = '648045682632.dkr.ecr.eu-north-1.amazonaws.com/demo-spring-sonar'
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/Shivere/demo-spring-sonar.git'
            }
        }

        stage('SonarCloud Analysis') {
            steps {
                script {
                    // Run SonarCloud analysis
                    withSonarQubeEnv('SonarCloud') {
                        sh """
                        mvn sonar:sonar \
                        -Dsonar.projectKey=${SONAR_PROJECT_KEY} \
                        -Dsonar.organization=${SONAR_ORGANIZATION} \
                        -Dsonar.host.url=https://sonarcloud.io \
                        -Dsonar.login=${SONAR_TOKEN}
                        """
                    }
                }
            }
        }

        stage('Build and Test') {
            steps {
                sh 'mvn clean package'
            }
            post {
                success {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Docker Build') {
            steps {
                script {
                    docker.build(DOCKER_IMAGE)
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    docker.withRegistry('', DOCKER_REGISTRY_CREDENTIALS) {
                        docker.image(DOCKER_IMAGE).push()
                    }
                }
            }
        }

        stage('Deploy to ROSA') {
            steps {
                script {
                    withCredentials([file(credentialsId: 'ROSA_KUBECONFIG', variable: 'KUBECONFIG')]) {
                        sh '''
                        oc login --token=$(cat $KUBECONFIG) --server=https://api.your-rosa-cluster.region.rosa.amazonaws.com:6443

                        # Update the deployment with the new image
                        oc set image deployment/springboot-app springboot-app=${ECR_REPOSITORY}:latest

                        # Wait for the deployment to roll out
                        oc rollout status deployment/springboot-app
                        '''
                    }
                }
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}
