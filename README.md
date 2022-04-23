# CO327-Mini-Project---Vital-Monitor-System
This repository contains the code base for a vital monitor system.

# Aim: 

This project aims to get hands-on experience in a real-world scenario. You will require the knowledge of networking, operating systems, and programming to complete this project.

# Background: 

This project is based on a real-life scenario. In a hospital, there will be a number of patients whose vitals (things like heart rate, blood pressure, etc.) should be monitored. Each patient would be connected to a vital monitor and these monitors will transmit the vital information over a network to a central location. That way nursing staff will be able to monitor many patients and do it remotely – which is useful when the patients are contagious. 

# Specifications: 

There will be a set of vital monitors running. Each of these vital monitors will have an IP address. These vital monitors will be running on a server. Vital monitors will broadcast their identity to a specific UDP port in the following format:
> <ip_addr, port, monitor_id>.
