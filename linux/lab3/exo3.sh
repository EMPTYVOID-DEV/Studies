read -p "give me the path of the file : " filename

if [[ ! -f $filename ]]; then
	echo "the file does not exist"
	exit 1
fi

IFS="\n"
lineNumber=0

for line in $(cat $filename); do
	lineNumber=$((lineNumber + 1))
	echo "$lineNumber"
	echo "$line"
done
