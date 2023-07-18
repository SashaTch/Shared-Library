def echoGreen(msgs) {
    ansicolor('xterm'){
        echo "\\e[92m${msgs}\\e[0m\n"

    }
}


def echoStage(msgs) {
    echo """
\\e[35m==================================\\e[0m\n
\\e[35m${msgs.center(46)}\\e[0m\n
\\e[35m==================================\\e[0m\n
"""
}
