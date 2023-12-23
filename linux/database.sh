trap "echo -e '\n';exit" SIGINT

PS3="Please select an option : "
timeOutDuration=5
databasePath="$HOME/database"
red_color="\e[38;2;255;0;0m"
green_color="\e[38;2;0;255;0m"
yellow_color="\e[38;2;255;255;0m"
nc="\e[0m"

init() {
	if [[ ! -d $databasePath ]]; then
		mkdir "$databasePath"
		dirCreated="Creating the database dirctory. $(date)"

	fi
	if [[ ! -f "$databasePath/database.csv" ]]; then
		touch "$databasePath/database.csv"
		fileCreated="Creating the database file. $(date)"

	fi
	if [[ ! -f "$databasePath/database.log" ]]; then
		touch "$databasePath/database.log"
	fi
	if [[ -n "$dirCreated" ]]; then
		echo "$dirCreated" >>"$databasePath/database.log"
	fi
	if [[ -n "$fileCreated" ]]; then
		echo "$fileCreated" >>"$databasePath/database.log"
	fi
}

menu() {

	local options
	options[0]="Add new entry"
	empty=$(isEmpty)
	if [[ $empty == 1 ]]; then
		options[1]="Search->Edit/delete"
	fi
	options[2]="Exit"
	numberOfOptions="${#options[@]}"
	select option in "${options[@]}"; do
		if [[ $numberOfOptions == 3 ]]; then
			case "$REPLY" in
			1)
				addEntry
				;;
			2)
				Search
				;;
			3)
				exit
				;;
			*)
				echo -e "${red_color}Selecting non valid option exiting... $nc"
				exit
				;;
			esac
		else
			case "$REPLY" in
			1)
				addEntry
				;;
			2)
				exit
				;;
			*)
				echo -e "${red_color}Selecting non valid option exiting... $nc"
				exit
				;;
			esac
		fi
	done
}

addEntry() {
	rePrint
	echo -e "${green_color}Add new entry to the database. $nc"
	info
	name=$(readName)
	email=$(readEmail)
	tel=$(readTel)
	mobile=$(readMobile)
	place=$(readPlace)
	msg=$(readMessage)
	continue=$(yesOrNo "Do you really need to add that component (y/n) ? : ")
	if [[ $continue == y ]]; then
		newVal="$name,$email,$tel,$mobile,$place,$msg"
		echo "$newVal" >>"$databasePath/database.csv"
		echo "New entry added at $(date) with the value $newVal." >>"$databasePath/database.log"
	fi
	rePrint
	menu
}

Search() {
	rePrint
	echo -e "${green_color}Search for an entry using a query. $nc"
	echo -e "${green_color}Example of query :name=aymen keskas&mobile=213645120299&telephone=666&place=setif&message=hello $nc"
	echo -e "${yellow_color}Warning: query accept only & operator. $nc"
	info
	validQuery=0
	while [[ $validQuery == 0 ]]; do
		unset conditionsMap
		declare -A conditionsMap
		read -p "Enter a search query : " query
		IFS="&" read -ra conditions <<<"$query"
		for condition in "${conditions[@]}"; do
			IFS="=" read -ra parts <<<"$condition"
			property="${parts[0]}"
			value="${parts[1]}"
			conditionsMap["$property"]="$value"
		done
		validateQuery conditionsMap
		validQuery="$?"
	done
	echo "Search query for ${conditionsMap[@]} at $(date)" >>"$databasePath/database.log"
	idx=$(getEntryIndex conditionsMap)
	if [[ -z $idx ]]; then
		rePrint
		echo -e "${yellow_color}No entry matches your query. $nc"
		select option in "Search again" "Exit"; do
			case "$REPLY" in
			1)
				Search
				;;
			2)
				rePrint
				menu
				;;
			*)
				echo -e "${red_color}Selecting non valid option exiting... $nc"
				exit
				;;
			esac
		done
	fi
	entry=$(getEntry "$idx")
	IFS="," read -ra fields <<<"$entry"
	rePrint
	echo -e "${green_color}The search result .$nc"
	echo "name      : ${fields[0]}"
	echo "email     : ${fields[1]}"
	echo "telephone : ${fields[2]}"
	echo "mobile    : ${fields[3]}"
	echo "place     : ${fields[4]}"
	echo "message   : ${fields[5]}"
	select option in "Delete" "Edit" "Exit"; do
		case "$REPLY" in
		1)
			delete "$idx"
			;;
		2)
			edit "${fields[@]}" "$idx"
			;;
		3)
			rePrint
			menu
			;;
		*)
			echo -e "${red_color}Selecting non valid option exiting... $nc"
			exit
			;;
		esac
	done
}

getEntry() {
	idx=$1
	entry=$(awk -v i="$idx" "NR==i {print ;exit}" "$databasePath/database.csv")
	echo "$entry"
}

getEntryIndex() {
	local -n queryParts=$1
	awkScript=""
	for key in "${!queryParts[@]}"; do
		case "$key" in
		name)
			sentenceName=$(toSentenceCase "${queryParts[$key]}")
			awkScript+="\$1==\"$sentenceName\" && "
			;;
		email)
			awkScript+="\$2==\"${queryParts[$key]}\" && "
			;;
		telephone)
			awkScript+="\$3==\"${queryParts[$key]}\" && "
			;;
		mobile)
			mobile="${queryParts[$key]}"
			countryNumber="${mobile:0:3}"
			actualNumber="${mobile:3:11}"
			mobileParsed="+$countryNumber-$actualNumber"
			awkScript+="\$4==\"$mobileParsed\" && "
			;;
		place)
			sentencePlace=$(toSentenceCase "${queryParts[$key]}")
			awkScript+="\$5==\"$sentencePlace\" && "
			;;
		message)
			awkScript+="\$6==\"${queryParts[$key]}\" && "
			;;
		esac
	done
	awkScript="${awkScript%&& } {print NR ;exit}"
	lineNumber=$(awk -F "," "$awkScript" "$databasePath/database.csv")
	echo "$lineNumber"
}

edit() {
	rePrint
	name="$1"
	email="$2"
	telephone="$3"
	mobile="$4"
	place="$5"
	message="$6"
	echo -e "${green_color}Edit one of these properties $nc"
	while true; do
		local options
		options[0]="name      : $name"
		options[1]="email     : $email"
		options[2]="telephone : $telephone"
		options[3]="mobile    : $mobile"
		options[4]="place     : $place"
		options[5]="message   : $message"
		select option in "${options[@]}" "Save" "Exit"; do
			case "$REPLY" in
			1)
				name=$(readName)
				break
				;;
			2)
				email=$(readEmail)
				break
				;;
			3)
				telephone=$(readTel)
				break
				;;
			4)
				mobile=$(readMobile)
				break
				;;
			5)
				place=$(readPlace)
				break
				;;
			6)
				message=$(readMessage)
				break
				;;
			7)
				newVal="$name,$email,$telephone,$mobile,$place,$message"
				awk -i inplace -v new="$newVal" -v i="$7" 'NR==i {print new ; next } 1' "$databasePath/database.csv"
				echo "Updating line number $7 at $(date) with this value $newVal." >>"$databasePath/database.log"
				rePrint
				menu
				;;
			8)
				rePrint
				menu
				;;
			*)
				echo -e "${red_color}Selecting non valid option exiting... $nc"
				exit
				;;
			esac
		done
	done
}

delete() {
	continue=$(yesOrNo "Do you really want to delete this entry (y/n) ? : ")
	idx=$1
	if [[ $continue == y ]]; then
		sed "${idx}d" "$databasePath/database.csv" >temp && mv temp "$databasePath/database.csv"
		echo "The entry in the line $idx with the value $(getEntry "$idx") deleted at $(date)." >>"$databasePath/database.log"
	fi
	rePrint
	menu
}

validateQuery() {
	local -n queryParts=$1
	for key in "${!queryParts[@]}"; do
		case "$key" in
		name | place)
			if [[ ! "${queryParts[$key]}" =~ ^[a-zA-Z\ ]+$ ]]; then
				return 0
			fi
			;;
		mobile)
			if [[ ! "${queryParts[$key]}" =~ ^[0-9]{12}$ ]]; then
				return 0
			fi
			;;
		telephone)
			if [[ ! "${queryParts[$key]}" =~ ^[0-9]+$ ]]; then
				return 0
			fi
			;;
		message)
			if [[ ! "${queryParts[$key]}" =~ ^.+$ ]]; then

				return 0
			fi
			;;
		email)
			if [[ ! "${queryParts[$key]}" =~ ^[a-zA-Z0-9._]+@[a-zA-Z0-9]+\.[a-zA-Z]{2,}$ ]]; then
				return 0
			fi
			;;
		*)
			return 0
			;;
		esac
	done
	return 1
}

yesOrNo() {
	read -p "$1"
	while [[ ! $REPLY =~ [yn] ]]; do
		read -p "$1"
	done
	echo "$REPLY"
}

readName() {
	name=$(globalRead "Enter a name : " "^[a-zA-Z ]+$")
	modName=$(toSentenceCase "$name")
	echo "$modName"
}

readEmail() {
	email=$(globalRead "Enter an email : " "^[a-zA-Z0-9._]+@[a-zA-Z0-9]+\.[a-zA-Z]{2,}$")
	echo "$email"
}

readTel() {
	tel=$(globalRead "Enter a telephone number : " "^[0-9]+$")
	echo "$tel"
}

readMobile() {
	mobile=$(globalRead "Enter a mobile number : " "^[0-9]{12}$")
	countryNumber="${mobile:0:3}"
	actualNumber="${mobile:3:11}"
	echo "+$countryNumber-$actualNumber"
}

readPlace() {
	place=$(globalRead "Enter a place : " "^[a-zA-Z ]+$")
	modPlace=$(toSentenceCase "$place")
	echo "$modPlace"
}

readMessage() {
	msg=$(globalRead "Enter a message : " "^.+$")
	echo "$msg"
}

toSentenceCase() {
	parsedVal=$(echo "$1" | tr [:upper:] [:lower:])
	parsedVal="${parsedVal^}"
	echo "$parsedVal"
}

info() {
	echo -e "${yellow_color}Note: Valid input format. $nc"
	echo " name               Must respect (Alphabets and spaces only, sentence case will be applied)"
	echo " email              Must respect (Symbols like '.', '_', alphabets, and numbers only, '@' and '.' required)"
	echo " telephone          Must respect (Numbers only)"
	echo " mobile             Must respect (12 digits, numbers only,with country number)"
	echo " place              Must respect (Alphabets and spaces only, sentence case will be applied)"
	echo " message            Must respect (Any character allowed)"
	echo
	echo -e "${green_color}Example: $nc"
	echo " John Doe,john.doe@example.com,1234567890,+911234567890,new york,'Hello, World!'"
	echo
}

globalRead() {
	read -p "$1"
	while [[ ! $REPLY =~ $2 ]]; do
		read -p "$1"
	done
	echo "$REPLY"
}

rePrint() {
	clear
	#echo -e "$yellow_color warning:There is a $timeOutDuration seconds timeout. $nc"
	echo -e "$green_color **********************  Database management script ********************** $nc"
}

isEmpty() {
	content=$(cat "$databasePath/database.csv")
	if [[ -n $content ]]; then
		echo "1"
	else
		echo "" "0"
	fi
}

## instructions

rePrint

init

menu
