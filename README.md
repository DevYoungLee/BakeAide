# BakeAide
BakeAide is an android application written in java that details recipe instructions for baked goods.<br >
<br >
The application follows an MVVM architecture <br />
A single activity pattern is used for flexible UI. <br />
Video is played using ExoPlayer  <br />
Data is fetched from Udacity's sample API in JSON format using Retrofit. <br />
Room is used for persistent data storage. <br />
RecyclerView is used to show lists. <br />
The application includes an interactive widget that displays the ingredients of the recipes. <br />
<br />
The main screen of the application shows available recipes: <br />
![alt text](screenshots/main_screen_phone.png "Main Screen") <br />
<br />
Clicking on an item shows its ingredients and the overview of the steps: <br />
![alt text](screenshots/detail_screen_top.png "Detail Screen Top") <br />
![alt text](screenshots/detail_screen_buttom.png "Detail Screen Bottom") <br />
<br />
Clicking on a recipe step shows detailed information about the step including a video: <br /> 
![alt text](screenshots/step_detail.png "Step detail") <br />
<br />
The widget of the application allows viewing of the ingredients: <br />
![alt text](screenshots/widget.png "Step detail") <br />
<br />
A single activity with multiple fragments allow for flexible UI for tablets: <br />
![alt text](screenshots/detail_tablet.png "Tablet UI") <br />
<br />
Any suggestions will be greatly appreciated!

