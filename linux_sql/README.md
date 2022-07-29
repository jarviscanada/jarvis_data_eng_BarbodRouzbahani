# :penguin: Introduction :penguin:
The purpose of this project is to gather hardware and usage information of a linux system and store them in an RDBMS database. This is built for recource planning purposes, for the teams who wish to manage their recources for further changes to their systems. Technologies used in this project are Google Cloud, Git, bash, Docker, and PSQL.

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------

# Quick Start
1. Initialize and start a psql instance using psql_docker.sh:
- `./scripts/psql_docker.sh create [db_username] [db_password]`
- `./scripts/psql_docker.sh start`

2. Create tables using ddl.sql:
- `psql -h localhost -U postgres -d host_agent -f sql/ddl.sql`

3. Insert hardware specs data into the DB using host_info.sh:
- `./scripts/host_info.sh [hostname] [portnumber] [dbname] [psqluser] [psqlpassword]`
- e.g. `./scripts/host_info.sh localhost 5432 host_agent postgres password`

4. Insert hardware usage data into the DB using host_usage.sh:
- `./scripts/host_usage.sh [hostname] [portnumber] [dbname] [psqluser] [psqlpassword]`
- e.g. `./scripts/host_info.sh localhost 5432 host_agent postgres password`

5. Crontab setup (automate the execution of host_usage.sh):
- `crontab -e`
- Copy and paste this line into it `* * * * * bash ~/linux_sql/host_agent/scripts/host_usage.sh localhost 5432 host_agent postgres password > /tmp/host_usage.log`

-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------

# Implemenation


## Architecture


## Scripts


## Database Modeling


-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------

# Test


-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------

# Deployment


-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------

# Improvements

