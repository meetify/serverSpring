#!/bin/sh
#echo "$OSTYPE"
if [ "$1" != "" ]; then
  arg=$1
else
  arg="-t"
fi
usage="Meetify server launch utility\nUsage:\n -d drops all tables in database\n -t switches branch to testing (default)\n -m switches branch to master \n -h prints this"
len=${#1}
beg=${arg:0:1}
created=0
if [ "$beg" != "-" ]; then
  printf "params should begin from dash (-) $beg\n$usage"
  exit 1
fi
if [ ! -d "serverSpring" ]; then #if there is no serverSpring dir, we should clone some
  mkdir serverSpring #trying to create dir
  if [ ! -d "serverSpring" ]; then #if dir was not created => we haven't permission here
    cd ~ #going to home dir
    if [ ! -d "serverSpring" ]; then #if dir is present, repo already here
      git clone https://github.com/meetify/serverSpring
	  created=1
    fi
  else #if dir was created, so we have permission and just clone repo
    rm serverSpring -rf
    git clone https://github.com/meetify/serverSpring
	created=1
  fi
fi
cd ./serverSpring
git checkout testing
if [ ${created} -eq 1 ]; then
	cd ./src/main
	if [ "$OSTYPE" == "msys" ]; then
		export MSYS=winsymlinks:nativestrict
	fi
	ln -s ./kotlin ./java
	cd ../..
fi
for (( index=1; index <= $len; index++ ))  do
  char=${arg:index:1}
  case ${char} in
    "d")
      printf "dropping all tables in database meetify with user postgres\n"
psql meetify postgres << EOF
DROP SCHEMA public CASCADE;
EOF
psql meetify kr3v << EOF
DROP SCHEMA public CASCADE;
CREATE SCHEMA public;
EOF
    ;;
    "m")
      git checkout master
    ;;
    "t")
      git checkout testing
    ;;
    "h")
      printf "$usage"
      exit 0
    ;;
  esac
done
git fetch
git pull
if [ "$OSTYPE" == "msys" ]; then
 /c/Maven/bin/mvn spring-boot:run
else
  mvn spring-boot:run
fi