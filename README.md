# Kyles-Hour-Tracker

## Introduction
This app was created for my father to help him keep track of the hours he's worked each day after he goes home. I discovered my father's need for an app like this after he expressed the problems he was having with keeping a written logbook. He wouldn't always remember to write down his hours, and occasionally, he would misplace his logbook entirely. Once I noticed that he frequently logged onto his computer after going home from work, I knew that I could help him find a better solution. I decided to build a personalized hour tracker for him that would launch each time his computer started. Through the app, he would be able to keep track of his hours, as well as a few other pieces of information pertaining to the work day.
<br>
<br>
Since the invention of this application, my father has been successful in routinely keeping track of his hours each day, and because it's highly unlikely that he'll ever misplace his desktop computer, I can say with confidence that he won't be losing his "logbook" anymore.    

## Getting Started

#### Prerequisites
You must have [Java](https://java.com/en/download/) installed on your computer.

#### Download
The latest release can be found [here](https://github.com/kwilliams3/Kyles-Hour-Tracker/releases).

#### Install
Once the download has finished, move the JAR file to a more permanent location such as your Documents folder or even your Desktop.

#### Usage
If you are using Windows, you should be able to double-click the JAR file to launch the program. Alternatively, you can launch the program by opening a terminal, navigating to the directory where the JAR file is stored, and entering the command `java -jar Kyles-Hour-Tracker.jar`
<br>
<br>
The program should launch, and a new folder will be created called *Hour_Tracker_Files*. This folder should not be moved or edited. It is where the database and defaults are stored.

## The Database
This app uses an SQLite database for some of its features while using a flat file database for other features.

My first intuition was to solely use a simple flat file database such as a text file for the entire application. I knew I wanted to keep this app simple so I could build it quickly. However, I later realized that I would want a database that was a little more versatile when I took into consideration the fact that this app will keep track of the locations where my father worked, the companies which he worked for, and the supervisors whom he worked under. The app would need to: store various locations/companies/supervisors, access any one of them on command, add new ones, update existing ones, remove old ones, and possibly keep track of a default choice for each one - which could change upon request. I knew I needed the database to be serverless while still carrying the benefits that a relational database has. So, upon further research, I settled on using an SQLite database. I still chose to use a flat file database (text file) for recording the daily entries so that any entry could be easily edited by my father if he accidently submits incorrect information.<p>The use of a flat file database may be eliminated in future versions if I find the time to expand upon this project.
