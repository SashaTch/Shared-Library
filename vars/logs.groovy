//used for sup stages
def echoGreen(msgs) {
     echo "\033[32m===========<  ${msgs}  >===========\033[0m"
}

//used for comments
def echoYellow(msgs) {
     echo "\033[33m<${msgs}>\033[0m"
}

//used for stages
def echoStage(msgs) {
    echo """
\033[35m|==========================================|\033[0m\n
\033[35m|                                          |\033[0m\n
\033[35m${msgs.center(46)}\033[0m\n               \033[0m\n
\033[35m|                                          |\033[0m\n
\033[35m|==========================================|\033[0m\n
"""
}

def aws_credentials(shell_command) {
     withCredentials([[
                        $class: 'AmazonWebServicesCredentialsBinding',
                        credentialsId: "AWS",
                        accessKeyVariable: 'AWS_ACCESS_KEY_ID',
                        secretKeyVariable: 'AWS_SECRET_ACCESS_KEY'
                    ]]) {
          sh '${shell_command}'
}
