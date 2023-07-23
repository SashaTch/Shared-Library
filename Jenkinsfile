@Library('Logs_OPT')_
pipeline {
    agent any
    options {
        ansiColor('xterm')
    }
    stages {
        stage('Cleanup') {
            steps {
                script{
                    logs.echoStage("Cleanup")
                    logs.echoGreen('cleaning project\'s directory')
                    sh 'rm -rf ./*'
                    logs.echoYellow('cleaunp stage has been finished')
                }
            }
        }
        stage('Build') {
            steps {
                script{
                    logs.echoStage("Build the App")
                    logs.echoGreen('cloning repository')
                    sh 'git clone https://github.com/SashaTch/Flask_app.git >/dev/null 2>&1' //cloning to workspace
                    logs.echoYellow('cloned the repo to the workspace')
                    sh 'tar -zcvf app.tar.gz Flask_app/ >/dev/null 2>&1' //zipped the file
                    logs.echoYellow('zipped the app\'directory')
                    logs.echoGreen('The App is ready for uploading')
                    logs.echoYellow("Current directory: ${PWD}")
                    logs.echoYellow("List of files:")
                    sh 'ls'
                }
            }
        }
        stage('Upload') {
            steps {
                script{
                    logs.echoStage("Upload to Cloud")
                    withCredentials([[
                        $class: 'AmazonWebServicesCredentialsBinding',
                        credentialsId: "AWS",
                        accessKeyVariable: 'AWS_ACCESS_KEY_ID',
                        secretKeyVariable: 'AWS_SECRET_ACCESS_KEY'
                    ]]) {
                        sh 'aws s3 cp app.tar.gz s3://devopsbucketex'
                    }
                    logs.echoGreen('Uploaded to s3 succefully')
                }
            }
        }
        stage('Test') {
            steps {
                script{
                    logs.echoStage("Testing App")
                    logs.echoGreen('making ec2 instances online')
                    logs.echoYellow("testing+deployment server----->")
                    withCredentials([[
                        $class: 'AmazonWebServicesCredentialsBinding',
                        credentialsId: 'AWS',
                        accessKeyVariable: 'AWS_ACCESS_KEY_ID',
                        secretKeyVariable: 'AWS_SECRET_ACCESS_KEY'
                    ]]) {
                        sh 'bash /etc/ansible/scripts/state.sh state run environment flask /var/lib/jenkins/.ssh/aws_rsa'
                        logs.echoGreen("testing+deployment server----->ONLINE")
                        sh 'ansible-playbook /etc/ansible/packages.yml --private-key=/var/lib/jenkins/.ssh/aws_rsa'
                        withCredentials([usernamePassword(credentialsId: 'sasha', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                        sh "echo '${PASSWORD}' | su -l ${USERNAME} -c 'bash /etc/ansible/scripts/state.sh deploy run Name Test /home/${USERNAME}/.ssh/aws_rsa'"
                        sh "echo '${PASSWORD}' | su -l ${USERNAME} -c 'bash /etc/ansible/scripts/state.sh test run Name Test /home/${USERNAME}/.ssh/aws_rsa'"
                        sh 'bash /etc/ansible/scripts/state.sh state stop Name Test /var/lib/jenkins/.ssh/aws_rsa'
                        }
                        logs.echoGreen("all packages and app deployed/updated in the test server")
                        logs.echoYellow("shutting test server---.continuing to deployment serve")
                    }
                }
            }
        }
        stage('Deploy') {
            steps {
                script{
                    logs.echoStage("Deploying the App")
                    logs.echoYellow("checking app status:")
                    withCredentials([[
                        $class: 'AmazonWebServicesCredentialsBinding',
                        credentialsId: 'AWS',
                        accessKeyVariable: 'AWS_ACCESS_KEY_ID',
                        secretKeyVariable: 'AWS_SECRET_ACCESS_KEY'
                    ]]) {
                        withCredentials([usernamePassword(credentialsId: 'sasha', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                        sh "echo '${PASSWORD}' | su -l ${USERNAME} -c 'bash /etc/ansible/scripts/state.sh deploy run Name Deployment /home/${USERNAME}/.ssh/aws_rsa'"
                        sh "echo '${PASSWORD}' | su -l ${USERNAME} -c 'bash /etc/ansible/scripts/state.sh test run Name Deployment /home/${USERNAME}/.ssh/aws_rsa'"
                        }
                        logs.echoGreen("all packages and app deployed/updated in the deployment server")
                        logs.echoGreen("APP--->ONLINE AND WORKS")
                    }
                }
            }
        }
    }
}
