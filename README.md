# Elad-and-Shai
**Elad and Shai's repository for Object Oriented Programming class.**

In this project, the user will give the address to a folder with an assortment of WiggleWifi app files, collect the 10 best WIFI signals of each time period (along with the MAC address ,SSID and Frequency) and write it into a CSV file. It then takes the combined CSV file and write it as a KML for us to view in a program like Google Earth along with the time stamp of each location. We can use several filters if we want to see any specific detail such as: seeing routers in a specific location, seeing from a specific phone ID and between a time period.

After the user inputs the folder path, a thread will watch over that folder to see if there are any changes to the data and will update the data base accordingly.

We can also add two different filters together with and/or. And filter will take the two filters and will write to the KML or CSV file only the points that have the two filters at the same time. Or filter will take the points that can have only one of the selected filters. Not will take every point BUT the ones who have the variables the user wrote.

If the user want to save the filters that he used, he can click on the "save filters" button to export the current setting into a bin file for later use. The user can now use the saved filter using the "use saved filters" button.

We can then use one of our two different algorithems:
Algorithm number one will take a MAC address and calculates the aproximate location of a router based on the current data.
Algorithm number two will take several MAC and their best signals and returns the aproximate location of the person who scanned the routersbased on the current data.

You can also insert log in credentials to a specific SQL database (IP,port,user name,password) and recive data from the database to add to your files.

Additional information on the different classes and function, along with a How To Run document, can be found at the doc folder of our project.

**API's that we used:**

JAK: https://labs.micromata.de/projects/jak.html

WindowBuilder: https://www.eclipse.org/windowbuilder/download.php

ObjectAid: http://www.objectaid.com/download

Papyrus: https://www.eclipse.org/papyrus/download.html
