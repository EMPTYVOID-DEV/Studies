for file in $(ls *$1); do
	base=$(echo $file | awk -F "." '{print $1}')
	echo "$base"
	mv $file $base$2
done
