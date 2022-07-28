#!/bin/sh

psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

if [ $# -ne 5 ]; then
	echo -e "\n- Command line arguements must be = 5. Exiting ...\n"
	exit 1
fi

hostname=$(hostname -f)
lscpu_out=$(lscpu)

cpu_number=$(echo "$lscpu_out"  | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)

cpu_arch=$(echo "$lscpu_out" | egrep "Architecture" | awk '{print $2}' | xargs)

cpu_model=$(echo "$lscpu_out" | egrep "Model name:" | awk '{print}' | xargs | cut -d " " -f 3-)

cpu_mhz=$(echo "$lscpu_out" | egrep "MHz:" | awk '{print $3}' | xargs)

L2_cache=$(echo "$lscpu_out" | egrep "L2 cache:" | awk '{print $3}' | xargs | sed 's/K//g' )

total_mem=$(cat /proc/meminfo | egrep "MemTotal" | awk "{print $2}" | cut -d ":" -f 2 | xargs)

timestamp=$(echo "$(vmstat -t)" | grep "2022" | awk "{print}" | xargs | cut -d " " -f 18-19)

insert_stmt="INSERT INTO host_info(hostname, cpu_number, cpu_architecture, cpu_model, cpu_mhz, L2_cache, total_mem, timestamp) VALUES('$hostname', '$cpu_number', '$cpu_architecture', '$cpu_model', '$cpu_mhz', '$L2_cache', '$total_mem', '$timestamp');"

export PGPASSWORD=$psql_password

psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$insert_stmt"


exit $?
