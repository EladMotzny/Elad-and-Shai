# Elad-and-Shai
Elad and Shai's repository for Object Oriented Programming class.

This project will take an assortment of WiggleWifi app files, collect the 10 best WIFI signals of each time period (along with the MAC address ,SSID and Frequency) and write it into a CSV file. It then takes the combined the CSV file and write it as a KML for us to view in a program like Google Earth along with the time stamp of each location. We can use several filters if we want to see any specific detail such as: seeing routers in a specific location, seeing from a specific phone ID and between a time period.

We can also add two different filters together with and/or. And will take the two filters and will write to the KML file only the points that have the two filters at the same time. Or will take the points that can have only one of the selected filters. Not will take every point BUT the ones who have the variables the user wrote.

We can then use one of our two different algorithems:
Algorithm number one takes a MAC address and calculates the aproximate location of a router 
Algorithm number two takes several MAC and their best signals and returns the aproximate location of the person who scanned the routers.

You can also insert log in credentials to a specific SQL database (IP,port,user name,password) and recive data from there to add to your files.

More information on the different classes and function, along with a How To Run document, can be found at the doc folder of our project.

API's that we used:
JAK: https://labs.micromata.de/projects/jak.html
WindowBuilder: https://www.eclipse.org/windowbuilder/download.php
ObjectAid: http://www.objectaid.com/download
Papyrus: https://www.eclipse.org/papyrus/download.html
