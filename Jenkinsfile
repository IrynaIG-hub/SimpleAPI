#!/usr/bin/env groovy
// execute this before anything else, including requesting any time on an agent
if (currentBuild.getBuildCauses().toString().contains('BranchIndexingCause')) {
    print "INFO: Build skipped due to trigger being Branch Indexing"
    currentBuild.result = 'ABORTED'
    return
}
List<String> CHOICES = [];
pipeline {
    agent { any }


    // Set log rotation, timeout and timestamps in the console
    options {
        buildDiscarder(logRotator(numToKeepStr: '20'))
        timestamps()
        timeout(time: 1200, unit: 'MINUTES')
    }
    stages {

        stage('Run ResNet FullText Flow Tests') {
            agent {
                docker {
                    image 'maven:3.6-jdk-11'
                    reuseNode true
                    args '-v /etc/passwd:/etc/passwd:rw,z -v /home/ec2-user/.ssh:/home/ec2-user/.ssh:rw,z'
                }
            }
            steps {
                withMaven(mavenSettingsConfig: 'entellect-maven', mavenOpts: '-Duser.home=.') {
                    withAWS(
                            role: 'jenkins-acceptance-tests-role',
                            roleAccount: '290244732740',
                            principalArn: 'arn:aws:iam::290244732740:role/jenkins-acceptance-tests-role',
                            roleSessionName: "jenkins-acceptance-tests-role-${env.BUILD_ID}",
                            duration: 43200,
                            region: 'eu-west-1'
                    ) {
                        script {
                            CHOICES = ["Volume Test","Service Test","E2E Test", "Content"];
                            env.TestType = input message: 'Which test type do you want to run: ' +
                            '\n Volume Test' +
                            '\n Service Test' +
                            '\n E2E Test' +
                            '\n Content' , ok: "Continue",
                                    parameters: [choice(choices: CHOICES, description: 'Select a test type for this test stage', name: 'TestType')]
                        }
                        script {
                            if(env.TestType == 'Volume Test'){
                            CHOICES = ["FullText150DocsVolumeTestRunner", "FullText500DocsVolumeTestRunner","FullText1000DocsVolumeTestRunner","FullText10000DocsVolumeTestRunner"];
                             env.Runner = input message: 'Which test stage do you want to run: ' +
                                                        '\n FullText150DocsVolumeTestRunner - Volume test for 100 documents' +
                                                        '\n FullText500DocsVolumeTestRunner- volume test for 500+ documents' +
                                                        '\n FullText1000DocsVolumeTestRunner - volume test for 1026 documents' +
                                                        '\n FullText10000DocsVolumeTestRunner - volume test for 10000 documents' , ok: "Run Tests",
                                parameters: [choice(choices: CHOICES, description: 'Select a runner for this test stage', name: 'Runner')]
                            }else if(env.TestType == 'Service Test'){
                            CHOICES = ["FullTextIndividualServiceTestRunner"];
                             env.Runner = input message: 'Which test stage do you want to run: ' +
                                                        '\n FullTextIndividualServiceTestRunner - Individual service regression tests',
                                                          ok: "Run Tests",
                                parameters: [choice(choices: CHOICES, description: 'Select a runner for this test stage', name: 'Runner')]
                            }else if(env.TestType == 'E2E Test'){
                            CHOICES = ["FullTextE2ETestRunner","FullTextE2E10TestRunner"];
                             env.Runner = input message: 'Which test stage do you want to run: ' +
                                                        '\n FullTextE2ETestRunner - E2E regression test for 1 document' +
                                                        '\n FullTextE2E10TestRunner - E2E regression test for 10 random document'  , ok: "Run Tests",
                                  parameters: [choice(choices: CHOICES, description: 'Select a runner for this test stage', name: 'Runner')]
                            }else{
                            CHOICES = ["ContentFullTextParallelTestNgRunner"];
                             env.Runner = input message: 'Which test stage do you want to run: ' +
                                                        '\n ContentFullTextParallelTestNgRunner - E2E content test for 5 random documents' , ok: "Run Tests"
                                  parameters: [choice(choices: CHOICES, description: 'Select a runner for this test stage', name: 'Runner')]
                            }
                        }
                        script {
                            CHOICES = ["dev01","int01", "qa01", "uat01"];
                            env.Env = input message: 'Select on which environment to execute the tests', ok: "Confirm",
                            parameters: [choice(choices: CHOICES, description: 'Select environment', name: 'Env')]

                                     if (env.Runner == 'ContentFullTextParallelTestNgRunner') {
                                      echo "Running tests from ${env.Runner} on ${env.Env} environment"
                                      sh "export PATH=$MVN_CMD_DIR:$PATH && mvn clean install && mvn exec:java \"-Dexec.mainClass=CucumberTestDataApp\" -pl bce/resnetfulltext"
                                      sh "export PATH=$MVN_CMD_DIR:$PATH && mvn test -Dtest=${env.Runner} -Denv=${env.Env} -pl bce/resnetfulltext"

                                     }else if (env.Runner == 'FullTextIndividualServiceTestRunner'){
                                     script {
                                     CHOICES = ["@150docs-field-extraction","@150docs-filtering", "@150docs-termite_rsft", "@150docs-xpath_union_rsft", "@150docs-sentence_rsft", "@150docs-bibliographic_enrichment", "@150docs-section_identification", "@150docs-resnet_pre_pro", "@150docs-bert_promoter_binding", "@150docs-bert_biomarker", "@150docs-bert_direct_regulation", "@150docs-bert_genetic_change", "@150docs-bert_quantitative_change", "@150docs-bert_binding_regulation", "@150docs-bert_state_change", "@150docs-bert_effect_extraction", "@150docs-resnet_post_pro", "@150docs-format_conversion", "@150docs-datalake_writer", "@150docs-event_generator", "@1000docs-field-extraction"];
                                     env.Tags = input message: 'Enter preferred service tag', ok: "Submit",
                                     parameters: [choice(choices: CHOICES, description: 'Select tag', name: 'Tags')]
                                     }
                                     echo "Running tests from ${env.Runner} on ${env.Env} environment"
                                     sh "export PATH=$MVN_CMD_DIR:$PATH && mvn clean install && mvn test -Dtest=${env.Runner} -Denv=${env.Env} -pl bce/resnetfulltext -D'cucumber.filter.tags'='${env.Tags}'"

                                     }else {
                                      echo "Running tests from ${env.Runner} on ${env.Env} environment"
                                      sh "export PATH=$MVN_CMD_DIR:$PATH && mvn clean install && mvn test -Dtest=${env.Runner} -Denv=${env.Env} -pl bce/resnetfulltext"
                                     }
                        }
                    }
                }
            }
        }
    }

    post {
        always {
            script {
                allure([
                        includeProperties: false,
                        jdk              : '',
                        properties       : [],
                        reportBuildPolicy: 'ALWAYS',
                        results          : [[path: 'bce/resnetfulltext/target/allure-results']]
                ])
                cleanWs()
            }
        }
    }
}
