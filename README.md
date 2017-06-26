# Kyles-Hour-Tracker


<h2>Purpose</h2>
This app was created for my father to help him keep track of the hours he worked after he goes home.


<h2>Platform</h2>
This app is designed for desktops and optimized for the Windows OS.


<h2>Database</h2>
This app uses an SQLite database for some of its features while using a flat file database for other features.




My first intuition was to solely use a simple flat file database such as a text file for the entire application. I knew I wanted to keep this app simple so that I could build it quickly. However, I later realized that I would want a database that was a little more versatile when I took into consideration the fact that this app will keep track of the location where my father worked each day. The app would need to store a list of various locations, access them on command, add new ones, remove old ones, and possibly keep track of a default location - which could change upon request. I knew I needed the database to be serverless while still having the accessibility that a relational database shares. So, upon further research, I settled on using an SQLite database for keeping track of the locations. I still chose to use a flat file database (text file) to keep track of the hours and the date on which the hours were worked so that the information could be easily edited by my father while still allowing the application to maintain its simplicity. The use of a flat file database may be eliminated in future versions if I find the time to expand upon this project.
