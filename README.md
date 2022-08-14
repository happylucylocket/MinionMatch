# Minion Match
Ever wanted to match 18 different minions to its own clone? No? Well now you can with (with pals too) in Minion Match! 

# Setup
1. Download the project files and open the project on your preferred IDE. Make sure the "resources" folder is marked as a Resources Root. 
2. To change the number of players, in the `Server` class in line 23, change the number of threads in `Executors.newFixedThreadPool(4)` to the desired amount of players. 
For example, if there are two players, change the 4 to 2 so line 23 becomes `var pool = Executors.newFixedThreadPool(2);` 

### Server
1. One device must run the `Server` class before players run the game.
2. To get the IP address of the device running the server, open the Command Prompt and enter `ipconfig /all`. The IP address to use is at IPv4 Address under Wireless LAN adapter Wi-Fi.

### Players
1. Set the server IP in line 31 in the `matchingGame` class by changing the `SERVER_IP` variable to the IP address of the device running the server.
2. Once the server is running, run the game from the Main class.
3. Players can begin clicking on cards after all players have opened the game.

