export CLICOLOR=1
export LSCOLORS=gxBxhxDxfxhxhxhxhxcxcx

alias ls="ls --color"

alias ll="ls -la"
alias rm="rm -i"
alias cd..="cd .."
alias c="clear"

# history control !
# append history entries..
shopt -s histappend
# Force ignoredups and erase duplicates across the whole history which
# is good for desktop and bad for server
export HISTCONTROL=ignoredups:erasedups
# export HISTFILESIZE=9999 # Increace history file size
export HISTSIZE=5000
# Display TIMESTAMP in history, for auditing purpose
export HISTTIMEFORMAT='%F %T - '
# After each command, save and reload history
export PROMPT_COMMAND="${PROMPT_COMMAND:+$PROMPT_COMMAND$'\n'}history -a; history -c; history -r"

# helper
function h(){
history | grep --color -- "${@}"
}