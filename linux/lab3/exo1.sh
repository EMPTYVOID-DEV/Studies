### create dirctories of a path recurisively

if [[ $# -nq 1 ]]; then
	echo "wrong input"
	exit 1
fi

if [[ -d "$1" ]]
then
	exit 0
else
	"$0" $(dirname "$1")
	mkdir "$1"
fi
