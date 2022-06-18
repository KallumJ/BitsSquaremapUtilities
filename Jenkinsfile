pipeline {
    agent any

    tools {
        jdk 'jdk_17'
    }

    stages {
        stage('Build') {
            steps {
                sh "sed -i 's/%VERSION%/${BRANCH_NAME}-${BUILD_NUMBER}/' gradle.properties"
                sh './gradlew clean build'
            }
        }
        /* Squaremap is not available on 1.19 yet. This test will fail. stage('Test') {
            steps {
                sh 'rm -rf test/'
                sh 'git clone https://hogwarts.bits.team/git/Bits/TestServer.git test/'
                sh 'chmod +x test/production_server_test.sh'
                sh 'rm -rf prod-server/ && mkdir -p prod-server/config'
                sh 'cp $CONFIG_FILE prod-server/config/bits-vanilla.cfg'
                sh """test/production_server_test.sh \
                        --java-path '${JAVA_HOME}' \
                        --mod-jar 'bits-vanilla-fabric-${BRANCH_NAME}-${BUILD_NUMBER}.jar' \
                        --mc-version '1.19' \
                        --loader-version '0.14.6' \
                        --install-mod 'fabric-api' '1.19'
                   """
            }
        }*/
        stage('Archive') {
            steps {
                archiveArtifacts artifacts: 'build/libs/*.jar', fingerprint: true
            }
        }
        stage("Publish") {
            steps {
                script {
                    artifactPath = 'build/libs/bits-squaremap-utilities-${BRANCH_NAME}-${BUILD_NUMBER}.jar';
                    sourcesPath = 'build/libs/bits-squaremap-utilities-${BRANCH_NAME}-${BUILD_NUMBER}-sources.jar';

                    if (env.BRANCH_NAME == "master") {
                        groupId = "team.bits"
                        artifactId = "squaremap-utilities"
                    } else {
                        groupId = "team.bits.squaremap-utilities.dev"
                        artifactId = "squaremap-utilities-${BRANCH_NAME}"
                    }

                    nexusArtifactUploader(
                        nexusVersion: NEXUS_VERSION,
                        protocol: NEXUS_PROTOCOL,
                        nexusUrl: NEXUS_URL,
                        groupId: groupId,
                        version: "${BUILD_NUMBER}",
                        repository: NEXUS_REPOSITORY,
                        credentialsId: NEXUS_CREDENTIAL_ID,
                        artifacts: [
                            [artifactId: artifactId,
                            classifier: '',
                            file: artifactPath,
                            type: "jar"],
                            [artifactId: artifactId,
                            classifier: 'sources',
                            file: sourcesPath,
                            type: "jar"]
                        ]
                    );
                }
            }
        }
    }
}