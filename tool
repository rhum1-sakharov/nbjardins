#!/usr/bin/env bash

command -v readlink &> /dev/null
if [ "$?" -eq "0" ] ; then
	#shellcheck disable=SC2155
	export curdir="$( cd "$( dirname "$(readlink "${BASH_SOURCE[0]}" || echo "${BASH_SOURCE[0]}" )" )" && pwd )"
else
	#shellcheck disable=SC2155
	export curdir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
fi

shift $((OPTIND-1))
[ "$1" = "--" ] && shift

# sinon option par défaut
export _F_FORCE=${_F_FORCE-0}

case "$1" in

	bs) #  generer le livrable du back spring
	    cd "${curdir}"
	    mvn clean install
		cd "${curdir}/application/springapp"
		mvn clean package spring-boot:repackage
		;;

	fa) # generer le livrable du front angular
	    cd "${curdir}/front"
	    rm -rf node_modules
	    npm ci
	    ng build --prod
		;;

	*)
		echo "Utilisation : $0"
		echo "	bs : generer le livrable du back spring"
		echo "	fa : generer le livrable du front angular"
		;;
esac