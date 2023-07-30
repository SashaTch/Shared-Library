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

def aws_credentials(id, shell_commands) {
    withCredentials([[
        $class: 'AmazonWebServicesCredentialsBinding',
        credentialsId: "${id}",
        accessKeyVariable: 'AWS_ACCESS_KEY_ID',
        secretKeyVariable: 'AWS_SECRET_ACCESS_KEY'
        ]]) {
        // Split the shell commands into separate commands
        def commands = shell_commands.split('\n')
        // Iterate over each command
        for (command in commands) {
            // Trim and execute each command
            command = command.trim()
            if (command) { 
                echo "\033[33m< excuting :${command} >\033[0m"
                sh "${command}"
            }
        }
    }
}

def user_root(id, shell_commands) {
    withCredentials([usernamePassword(credentialsId: id, usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]){
          // Split the shell commands into separate commands
          def commands = shell_commands.split('\n')
          // Iterate over each command
          for (command in commands) {
              // Trim and execute each command
              command = command.trim()
              if (command) { 
                  echo "\033[33m< executing :${command} >\033[0m"
                  sh "${command}"
            }
        }
    }
}

def token(id, shell_commands){
     withCredentials([string(credentialsId: id, variable: 'TOKEN')]) {
        // Split the shell commands into separate commands
          def commands = shell_commands.split('\n')
          // Iterate over each command
          for (command in commands) {
              // Trim and execute each command
              command = command.trim()
              if (command) { 
                  echo "\033[33m< executing :${command} >\033[0m"
                  sh "${command}"
              }
          }
     }
}


//docker rm
def docker_rm(id) {
     IMAGE_IDS=$(docker images | grep id | awk 'NR>3 {print $2}')
     if [ -n "$IMAGE_IDS" ]; then
          docker rmi $IMAGE_IDS
     else
          echo "No images to delete."
     fi
}
