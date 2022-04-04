# Welcome to QR Hunt
QR Hunt is a mobile game based on the following description, for the CMPUT 301 Winter 2022 Group Project.  

# Original Project Description（Eclass）

[Link](https://eclass.srv.ualberta.ca/mod/page/view.php?id=5825439)

# Description

You are to design and implement a simple, attractive, and easy-to-use Android application to satisfy the following goals. Your design must be flexible enough to allow developers to extend or migrate it.

We want a mobile application that allows us to hunt for the coolest QR codes that score the most points. Players will run around scanning QR codes, barcodes, etc. trying to find barcodes and QR codes that give them the most points.

QR codes and barcodes (scannable codes) will be hashed and the hashes they produce will be analyzed and scored. A QR code that has certain properties like repeated nibbles or bytes (hex digits) will have a higher score than a QR code that does not. We have a proposed scoring system, but the implementers are free to use a different scoring system.

We want users to compete with each other for the highest scoring QR codes, the most QR codes, the highest sum of QR codes, or highest scoring QR codes in a region. 

When a player scans a QR code they will take a photo of what or where the QR code is and also record the geolocation of the QR code. 

Players can see on a map local QR codes that other players have scanned.

# Instructions

* Users: Users will be able to download the apk and install it on their devices from which they will be able to play.
* Developers: To work on the project, first clone the repository, then create a new branch and start working (it's that easy!). When you have completed your work, submit a pull request and we will review it.

# Development
QR Hunt was developed using Java and Android Studio. A representation of our storyboard, along with a detailed UML diagram and CRC cards can be found in the Wiki. User and code data is stored in firestore. Third party libraries were also used for the QR code scanning and the map view.


# Attribution of image assets
ConsoleActivity: </br>
<a href="https://www.flaticon.com/free-icons/camera" title="camera icons">Camera icons created by Freepik - Flaticon</a> </br>
QR code image - https://pngimg.com/image/71955 </br>
<a href="https://www.flaticon.com/free-icons/rank" title="rank icons">Rank icons created by surang - Flaticon</a> </br>
<a href="https://www.flaticon.com/free-icons/user" title="user icons">User icons created by Freepik - Flaticon</a> </br>
<a href="https://www.flaticon.com/free-icons/search" title="search icons">Search icons created by Freepik - Flaticon</a> </br>
<a href="https://www.flaticon.com/free-icons/map" title="map icons">Map icons created by Freepik - Flaticon</a> </br>

QRCodeActivity: </br>
<a href="https://www.flaticon.com/free-icons/rubbish" title="rubbish icons">Rubbish icons created by IYAHICON - Flaticon</a> </br>
<a href="https://www.flaticon.com/free-icons/customer" title="customer icons">Customer icons created by Freepik - Flaticon</a> </br>
<a href="https://www.flaticon.com/free-icons/comment" title="comment icons">Comment icons created by Freepik - Flaticon</a> </br>
