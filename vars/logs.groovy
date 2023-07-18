def echoGreen(msgs) {
     echo "\033[92m${msgs}\033[0m"
}


def echoStage(msgs) {
    echo """
\033[35m==================================\033[0m\n
\033[35m${msgs.center(46)}\033[0m\n
\033[35m==================================\033[0m\n
"""
}
