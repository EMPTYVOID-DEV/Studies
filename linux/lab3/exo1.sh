trap 'echo -e "\n";exit' SIGINT

IFS="," read -l <<<"$i"
