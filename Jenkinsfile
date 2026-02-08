#!/usr/bin/env groovy
pipeline {
    agent any

    tools {
        jdk "1.17"
        maven "3.9.6"
  }

    stages {
            stage('Checkout Code') {
                steps {
                    // Get project from GitHub repository
                 git url: 'git@github.com:IrynaIG-hub/SimpleAPI.git',
                    branch: 'main'
                }
            }
            stage('Build and Run tests') {
                steps {
                    // Run Maven on Unix agent
                    sh "mvn clean install"
                }
            post {
                 always {
                      allure([
                          reportBuildPolicy: 'ALWAYS',
                          results: [[path: 'target/allure-results']]
                      ])
                 }
            }

     }

}


