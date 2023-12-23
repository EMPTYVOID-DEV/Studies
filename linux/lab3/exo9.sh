##v1

#if [[ $# != 1 ]]; then
#echo "you need to pass the directory as argument "
#exit 1
#fi

#echo "Here is the files of this directory $1"

#ls $1

##v2

if [[ $# != 1 ]]; then
	echo "you need to pass the directory as argument "
	exit 1
fi

echo -e "Here is the files of this directory $(readlink -f $1)"

ls $1
