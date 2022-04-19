javac VitalMonitor.java
startingPort=5000
for i in $(seq 5)
do
	j=$((i + startingPort));
	echo "Starting Vital Monitor: CICU_$i at port: $j"
	java VitalMonitor "CICU_$i" "$j" &
done
