# :penguin: Introduction :penguin:
The purpose of this project is to gather hardware and usage information of a Linux system and store them in an RDBMS database. This is built for resource planning purposes, for the teams who wish to manage their resources for further changes to their systems. Technologies used in this project are Google Cloud, Git, bash, Docker, and PSQL.

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------

# Quick Start
**1. Initialize and start a psql instance using psql_docker.sh:**
  - `./scripts/psql_docker.sh create [db_username] [db_password]`
  - `./scripts/psql_docker.sh start`

**2. Create tables using ddl.sql:**
  - `psql -h localhost -U postgres -d host_agent -f sql/ddl.sql`

**3. Insert hardware specs data into the DB using host_info.sh:**
  - `./scripts/host_info.sh [hostname] [portnumber] [dbname] [psqluser] [psqlpassword]`
  - e.g. `./scripts/host_info.sh localhost 5432 host_agent postgres password`

**4. Insert hardware usage data into the DB using host_usage.sh:**
  - `./scripts/host_usage.sh [hostname] [portnumber] [dbname] [psqluser] [psqlpassword]`
  - e.g. `./scripts/host_info.sh localhost 5432 host_agent postgres password`

**5. Crontab setup (automate the execution of host_usage.sh):**
  - `crontab -e`
  - Copy and paste this line into it `* * * * * bash ~/linux_sql/host_agent/scripts/host_usage.sh localhost 5432 host_agent postgres password > /tmp/host_usage.log`

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------

# Implementation
The implementation of the project is done by first writing the proper CLI arguments to gather the necessary data (usage/hardware). We then set up our psql_docker.sh file to automate the process of creating/starting/stopping our psql instance. In the next step, we implement our ddl.sql and queries.sql. ddl.sql creates a table with the proper rows and columns, so that we can later store our data in the table. Also, what queries.sql does is that it helps us group hosts by hardware info, calculate the average memory usage, and also detect any host failures. The next and final step is to implement our host_info.sh and host_usage.sh files, so that we can insert the data we have gathered using CLI into the tables we have previously created. 

## Architecture


## Scripts

**1. psql_docker.sh**
  - This script is for the automation of creating/starting/stopping a docker container.
  - Usage: 
    - `./scripts/psql_docker.sh create db_username db_password`
    - `./scripts/psql_docker.sh start`
    - `./scripts/psql_docker.sh stop`

**2. host_info.sh**
  - Gathers and inserts host's hardware info into a table
  - Usage:
    - `./scripts/host_info.sh psql_host psql_port db_name psql_user psql_password`

**3. host_usage.sh**
  - Gathers and inserts host's usage info into a table
  - Usage:
    - `./scripts/host_usage.sh psql_host psql_port db_name psql_user psql_password`

**4. crontab**
  - Automates the process of gathering usage info for the host_usage.sh file. It runs the script every minute.
  - Usage:
    - `* * * * * bash /home/centos/dev/jarvis_data_eng_Barbod/linux_sql/scripts/host_usage.sh localhost 5432 host_agent postgres password &> /tmp/host_usage.log`

## Database Modeling

**host_info**

|   Name   | Data Type |  Contraints  | Description |
| ---	     |	---	 |	---   |	  ---	    |
| id	     | SERIAL    | PRIMARY KEY| psql db auto-increment |
| hostname   | VARCHAR   | NOT NULL UNIQUE | Hostname |
| cpu_number | INTEGER   | NOT NULL   | number of host's CPUs |
| cpu_architecture | VARCHAR |NOT NULL| architecture of host's CPU |
| cpu_model  | VARCHAR   | NOT NULL   | CPU model of the host |
| cpu_mhz    | FLOAT     | NOT NULL   | CPU mhz of the host |
| L2_cache   | INTEGER   | NOT NULL   | L2 cache in KB |
| total_mem  | INTEGER   | NOT NULL   | Total memory in KB |
| timestamp  | TIMESTAMP | NOT NULL   | Current time in UTC |

**host_usage**

|   Name   | Data Type |  Contraints  | Description |
| ---	     |	---	 |	---   |	  ---	   |
| timestamp   | TIMESTAMP | NOT NULL   | Current time in UTC |
| host_id     | SERIAL    | FOREIGN KEY   | Host id from `hosts` table |
| memory_free | INTEGER   | NOT NULL   | Free memory in MB |
| cpu_idle    | INTEGER   | NOT NULL | Percentage of idle CPU |
| cpu_kernel  | INTEGER   | NOT NULL    | Percentage of idle Kernel |
| disk_io     | INTEGER   | NOT NULL    | Number of disk I/O |
| disk_available   | INTEGER   | NOT NULL   | Root directory available disk in MB |

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------

# Test
The testing of the bash scripts was done using the command line in Linux. The results were compared to the previous outputs of the scripts to track any changes. SQL queries were tested based on the data that was inputted into the tables.

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------

# Deployment
Using crontab, the usage data of the user was repeatedly inserted into the host_usage table. Source codes of the whole project were also pushed into this GitHub repo using Git commands.

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------

# Improvements
The architecture of the project could be added to README to improve the clarity of the project's goal.
