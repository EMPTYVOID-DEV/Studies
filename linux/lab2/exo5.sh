#! /bin/bash

if [ $# -eq 1 ]; then
	mkdir ../backup   
	cp -r $1 ../backup
else 
     echo "pass the path of directory"
fi