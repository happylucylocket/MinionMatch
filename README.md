# Minion Match
Ever wanted to match 18 different minions to its own clone? No? Well now you can with (with pals too) in Minion Match! 

# Setup
**Note:** We used IntelliJ IDEA to create our project.
1. Download the project files and open the project on your preferred IDE. Right click on the `resources` folder and mark it as a **Resources Root**.
2. To change the number of players, in the `Server` class in line 23, change the number of threads in `Executors.newFixedThreadPool(4)` to the desired amount of players. 
For example, if there are two players, change the 4 to 2 so line 23 becomes `var pool = Executors.newFixedThreadPool(2);` 

---

### Server
1. One device must run the `Server` class before players run the game. If they are also running a client, set `SERVER_IP` in line 26 of the `matchingGame` class to localhost ip `127.0.0.1`. 
2. The server must share its IP address with other clients on remote machines.
3. To get the IP address of the device running the server, open the Command Prompt and enter `ipconfig /all`. The IP address to use is at IPv4 Address under Wireless LAN adapter Wi-Fi.

### Players
1. Set the server IP in line 26 in the `matchingGame` class by changing the `SERVER_IP` variable to the IP address of the device running the server.
2. Once the server is running, run the game from the Main class.
3. Players can begin clicking on cards after all players have opened the game.

