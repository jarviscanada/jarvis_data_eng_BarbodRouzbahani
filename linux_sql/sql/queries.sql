SELECT
	cpu_number,
	id AS host_id,
	total_mem
FROM host_info
GROUP BY cpu_number, id
ORDER BY total_mem DESC;

CREATE FUNCTION round5(ts timestamp) RETURNS timestamp AS
$$
BEGIN
    RETURN date_trunc('hour', ts) + date_part('minute', ts):: int / 5 * interval '5 min';
END;
$$
    LANGUAGE PLPGSQL;

SELECT host_id, host_name, round5(host_usage.timestamp) AS rt, round ( ( ((AVG(( total_mem/1000 ) - memory_free ))/( total_mem/1000 ))*100 ), 2 ) AS avg_usage
FROM host_usage
	JOIN host_info ON host_usage.host_id = host_info.id
GROUP BY host_id, hostname, rt, total_mem, memory_free
ORDER BY rt ASC;

SELECT host_id, round5(host_usage.timestamp) AS rt, COUNT(*) AS num_data
FROM host_usage
GROUP BY rt, host_id
HAVING COUNT(*) < 3;
