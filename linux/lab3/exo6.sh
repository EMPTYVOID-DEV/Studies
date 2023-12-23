if [[ $# != 2 ]]; then
	echo "you need to enter two arguments"
	exit 1
fi

change() {
	for file in $(ls *"$2"); do
		echo "$file"
		chmod g$1 $file
	done
}

case "$1" in
-r)
	change "-r" $2
	;;
+r)
	change "+r" $2
	;;
+w)
	change "+w" $2
	;;
-w)
	change "-w" $2
	;;
*)
	echo "wrong output"
	;;
esac
