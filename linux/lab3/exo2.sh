if [[ $# = 0 ]]; then
	echo "no arguments has been passed"
	exit 1
fi

while [[ $# != 0 ]]; do
	echo "$1"
	shift
done
