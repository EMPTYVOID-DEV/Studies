read -p "give me the number for the fibonnaci calculation : " iterations
declare -i temp
temp=0
a=0
b=1

for ((i = o; i < iterations; i++)); do
	temp=a+b
	echo -e "$temp\n"
	a=$b
	b=$temp
done

