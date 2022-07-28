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

memory_freeVal=$(cat /proc/meminfo | egrep "MemFree" | cut -d ":" -f 2 | awk '{$1=$1/1000; print $1;}')
memory_free=${memory_freeVal%.*}

cpu_idle=$(echo "$(vmstat 1 2 | tail -1 | awk '{print $15}')")

cpu_kernel=$(echo "$(vmstat --unit M | awk '{print $14}' | xargs | awk '{print $2}')")

disk_io=$(echo $[$(vmstat --unit M | tail -1 | awk '{print $9}')+$(vmstat --unit M | tail -1 | awk '{print $10}')])

disk_available=$(echo $(df -BM | tail -7 | awk '{print $4}' | paste -sd+ - | sed 's/M//g' | bc))

timestamp=$(echo "$(vmstat -t)" | grep "2022" | awk "{print}" | xargs | cut -d " " -f 18-19)

host_id="(SELECT id FROM host_info WHERE hostname='$hostname')";

insert_stmt="INSERT INTO host_usage(timestamp, host_id, memory_free, cpu_idle, cpu_kernel,disk_io, disk_available) VALUES('$timestamp', "$host_id", '$memory_free', '$cpu_idle', '$cpu_kernel', '$disk_io', '$disk_available');"

export PGPASSWORD=$psql_password

psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$insert_stmt"

exit $?
