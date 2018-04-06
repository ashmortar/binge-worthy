
# _bingeWorthy_
![home-screen](https://github.com/ashmortar/binge-worthy/blob/master/screenshots/home_screen.png)
#### _A content suggestion system for lovers of books, movies, tv, music and more!_

#### By _**Aaron Ross (4/6/2018)**_

## Description

This android app uses the [TASTED!VE](https://tastedive.com/) api to drive user content discovery. The user can enter a genre, movie title, band, tv show and many other things to get relevent suggestions based upon their query.  The detailed information is displayed in its own view where the user can see a summary of the item, get a link to the wikipedia article, get a link to youtube and have the ability to save the item to their own list for future viewing.  The detail view can also be rotated horizontally and it will change into an embedded youtube player that will play a relevent youtube video.

## Setup/Installation
* Clone repository to your machine from GitHub
* Open the repository with Android Studio
* Set up a virtual android device (code was optimized for Nexus 6)
* Sign up for an API key from [tasteD!ve](https://tastedive.com/read/api)
* Register your android application following Step 2 of the [Android Quickstart Guide]{https://developers.google.com/youtube/v3/quickstart/android}
* Create a file in the root directory called ``gradle.properties``
* into ``gradle.properties`` add the following lines of code: 

   ```java
   TASTEDIVE_API_KEY="<YOUR_API_KEY>"
   YOUTUBE_API_KEY="<YOUR_API_KEY>"
   ```
  
* sync gradle
* Run the application
* If running the app on an emulated device you must have the YouTube app installed on the device in order for video playback to functions correctly.  This can either be accomplished by emulating on a Nexus 5 or Nexus 5x device running API v26 or above. After booting the device update your google play services and youtubee applications through the play store on the phone.


## Future Development

* use tmDB data and maybe another api for music data for different recommendation types

## Known Bugs

* no currently known bugs

## Support and contact details

If you have any issues or have questions, ideas or concerns please email ashmortar@gmail.com or contribute to the code

## Technologies Used

* Java
* Android Studio
* Youtube Android API


### License
Copyright (C) 2018 Aaron Ross

This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this program. If not, see https://www.gnu.org/licenses/.
