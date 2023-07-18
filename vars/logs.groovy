def echoGreen(msgs) {
     echo "\033[m${msgs}\033[0m"
}


def echoStage(msgs) {
    echo """
\\e[35m==================================\\e[0m\n
\\e[35m${msgs.center(46)}\\e[0m\n
\\e[35m==================================\\e[0m\n
"""
}
