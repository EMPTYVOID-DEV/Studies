for files in $(ls); do
	if [[ -d $files ]]; then
		echo "$files"
	fi
done
