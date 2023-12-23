read -p "enter the file name : " filename
read -p "do you want to delete the file (y/n) : " confirmation

case "$confirmation" in
y)
	rm $filename
	;;
n)
	echo "verywall not deleting the file"
	;;
*)
	echo "wrong input"
	$0
	;;
esa:c
