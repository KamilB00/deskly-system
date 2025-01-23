pipeline {
	agent {
		label 'jenkins-agent'
    }
    tools {
		maven 'Maven_3.9.9'
    }
    environment {
		SPRING_PROFILES_ACTIVE = 'dev'
		SPRING_DATASOURECE_URL = 'jdbc:postgresql://terraform-20250116140847713600000003.cwriekum629g.us-east-1.rds.amazonaws.com:5432/desklydb'
		SPRING_DATASOURECE_USERNAME = 'kamil'
		SPRING_DATASOURECE_PASSWORD = 'password123'
		AWS_BUCKET_NAME = 'deskly-bucket-2'
		AWS_SQS_URL = 'https://sqs.us-east-1.amazonaws.com/619508104249/deskly-sqs'
		AWS_DEFAULT_REGION = 'us-east-1'
		AWS_SECRET_ACCESS_KEY = 'AWS_SECRET_ACCESS_KEY'
		AWS_ACCESS_KEY_ID = 'AWS_ACCESS_KEY_ID'
		AWS_SESSION_TOKEN = 'AWS_SESSION_TOKEN'
        DOCKER_REPOSITORY_LOCATION = 'kamilbonkowski/deskly-location'
        DOCKER_REPOSITORY_CORE = 'kamilbonkowski/deskly-core'
        DOCKERHUB_CREDENTIALS = 'docker-credentials-id'
        CLUSTER_NAME = 'staging-eks_deskly'
    }
    stages {
		stage('Environment Setup') {
			steps {
				echo "SPRING_PROFILES_ACTIVE is set to: ${env.SPRING_PROFILES_ACTIVE}"
				echo "SPRING_DATASOURECE_URL is set to: ${env.SPRING_DATASOURECE_URL}"
				echo "SPRING_DATASOURECE_USERNAME is set to: ${env.SPRING_DATASOURECE_USERNAME}"
				echo "SPRING_DATASOURECE_PASSWORD is set to: ${env.SPRING_DATASOURECE_PASSWORD}"
				echo "AWS_BUCKET_NAME is set to: ${env.AWS_BUCKET_NAME}"
				echo "AWS_SQS_URL is set to: ${env.AWS_SQS_URL}"
				echo "AWS_SECRET_ACCESS_KEY is set to: ${env.AWS_SECRET_ACCESS_KEY}"
				echo "AWS_ACCESS_KEY_ID is set to: ${env.AWS_ACCESS_KEY_ID}"
				echo "DOCKER_REPOSITORY_LOCATION is set to: ${env.DOCKER_REPOSITORY_LOCATION}"
				echo "DOCKER_REPOSITORY_CORE is set to: ${env.DOCKER_REPOSITORY_CORE}"
				echo "DOCKERHUB_CREDENTIALS is set to: ${env.DOCKERHUB_CREDENTIALS}"
				echo "CLUSTER_NAME is set to: ${env.CLUSTER_NAME}"
            }
        }
		stage('Clean Workspace') {
			steps {
				echo "Cleaning workspace..."
                deleteDir()
            }
        }
        stage('Checkout Code') {
			steps {
				checkout scm
            }
        }
        stage('Check for Changes') {
			steps {
				script {
					def changes = sh(script: "git diff --name-only HEAD~1 HEAD", returnStdout: true).trim()
                    echo "Changed files:\n${changes}"

                    env.CHANGED_CORE = changes.contains('deskly-core/')
                    env.CHANGED_LOCATION = changes.contains('deskly-location/')
                }
            }
        }
        stage('Build and Test') {
			parallel {
				stage ('Deskly Core'){
					when {
						expression { env.CHANGED_CORE == 'true' }
            		}
					steps {
						script {
					sh '''
						cd deskly-core
                        echo "Building the Java application using Maven... "
                        mvn clean install
                        mvn test
                    '''
                		}
            		}
				}
				stage ('Deskly Location'){
					when {
						expression { env.CHANGED_LOCATION == 'true' }
            		}
					steps {
						script {
					sh '''
						cd deskly-location
                        echo "Building the Java application using Maven... "
                        mvn clean install
                        mvn test
                    '''
                		}
            		}
				}
			}
        }
		stage('Deploy image to DockerHub') {
			parallel {
				stage ('Deskly Core') {
					when {
						expression { env.CHANGED_CORE == 'true' }
            		}
					steps {
						script {
							def tag = "${env.BUILD_NUMBER}"
							withCredentials([usernamePassword(credentialsId: "${env.DOCKERHUB_CREDENTIALS}", usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
								sh """
								cd deskly-core
								echo "Building Docker image..."
								echo "Build number: ${tag}"
								docker build -t $DOCKER_REPOSITORY_CORE:${tag} .
								echo "Logging in to Docker Hub... "
                        		echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin
                        		echo "Pushing Docker image to Docker Hub...."
                        		docker push $DOCKER_REPOSITORY_CORE:${tag}
							"""
							}
        				}
        			}
           		}
				stage ('Deskly Location') {
					when {
						expression { env.CHANGED_LOCATION == 'true' }
            		}
					steps {
						script {
							def tag = "${env.BUILD_NUMBER}"
							withCredentials([usernamePassword(credentialsId: "${env.DOCKERHUB_CREDENTIALS}", usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
								sh """
									cd deskly-location
									echo "Building Docker image..."
									echo "Build number: ${tag}"
									docker build -t $DOCKER_REPOSITORY_LOCATION:${tag} .
									echo "Logging in to Docker Hub... "
                        			echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin
                        			echo "Pushing Docker image to Docker Hub...."
                        			docker push $DOCKER_REPOSITORY_LOCATION:${tag}
								"""
							}
						}
           			}
				}
			}
		}
    }
}
