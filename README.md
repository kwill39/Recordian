# Kyles-Hour-Tracker

<h2>Introduction</h2>
This README is not even remotely finished. I plan to update it to make it more conventional in the future. For now, it just gives some basic information about the app.

<h2>Purpose</h2>
This app was created for my father to help him keep track of the hours he has worked after he goes home.

<h2>Database</h2>
This app uses an SQLite database for some of its features while using a flat file database for other features.




My first intuition was to solely use a simple flat file database such as a text file for the entire application. I knew I wanted to keep this app simple so I could build it quickly. However, I later realized that I would want a database that was a little more versatile when I took into consideration the fact that this app will keep track of the locations where my father worked, the companies which he worked for, and the supervisors whom he worked under. The app would need to: store various locations/companies/supervisors, access any one of them on command, add new ones, update existing ones, remove old ones, and possibly keep track of a default choice for each one - which could change upon request. I knew I needed the database to be serverless while still carrying the benefits that a relational database has. So, upon further research, I settled on using an SQLite database. I still chose to use a flat file database (text file) for recording the daily entries so that any entry could be easily edited by my father if he accidently submits incorrect information.<p>The use of a flat file database may be eliminated in future versions if I find the time to expand upon this project.
