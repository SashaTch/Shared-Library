def echoGreen(msgs) {
    echo "\\e[92m${msgs}\\e[0m\n"
}


def echoStage(msgs) {
    echo """
\\e[35m==================================\\e[0m
\\e[35m${msgs.center(46)}\\e[0m
\\e[35m==================================\\e[0m
"""
}
