#!/bin/sh

psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

if [ $# -gt 5 ] || [ $# -lt 1 ]; then
	echo "Command line error!"
	exit 1
fi

hostname=$(hostname -f)

cpu_number=cpu_number=$(echo "$lscpu_out"  | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)

cpu_arch=$(echo "$lscpu_out" | egrep "Architecture" | awk '{print $2}' | xargs)

cpu_model=$(echo "$lscpu_out" | egrep "Model name:" | awk '{print}' | xargs | cut -d " " -f 3-)

cpu_mhz=$(echo "$lscpu_out" | egrep "MHz:" | awk '{print $3}' | xargs)

l2_cache=$(echo "$lscpu_out" | egrep "L2 cache:" | awk '{print $3}' | xargs)

#cut -d ":" -f 2  --->    this tells the system to consider ':' as the delimiter and only print out the second field of the line
total_mem=$(cat /proc/meminfo | egrep "MemTotal" | awk "{print $2}" | cut -d ":" -f 2 | xargs)

#current timestamp in `2019-11-26 14:40:19` format
timestamp_val=`vmstat -t`
timestamp=$(echo "$timestamp_val" | grep "2022" | awk "{print}" | xargs | cut -d " " -f 18-19)


exit 0
