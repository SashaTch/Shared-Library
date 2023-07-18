def echoGreen(msgs) {
    ansiColor('xterm') {
        echo "\u001B[32m${msgs}\u001B[0m\n"
    }
}

}
def echoStage(msgs) {
    echo """
\e[35m==================================\e[0m
\e[35m${msgs.center(46)}\e[0m
\e[35m==================================\e[0m
"""
}
