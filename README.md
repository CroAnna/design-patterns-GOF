# Railway Transport Management System - design patters

This project is mostly focused on different types of design patterns by GOF (Gang of Four) in Java. 

## About the project

It is separated in 3 different subprojects + data used to start them. Every one of them has following document that explains project requirements for it, how it should be implemented, what commands does it have etc. The second one is built upon the first one, and third one upon the second one, even though every one has new funcionalities and used design patterns.

## 🔨 Project #1
This Java project manages railway transport for passengers and goods, involving railway infrastructure, stations, and transport compositions. The program operates in a command-line environment, executing various commands for data retrieval and loads data from given CSV files.

### Design patterns

<b>Singleton:</b>
<ul>
  <li>Used for the ZeljeznickiSustav (Railway System) class because:
    <ul>
      <li>There must be exactly 1 instance of the system class</li>
      <li>Global access point is required since it contains data used throughout the entire system (e.g., lists of loaded data)</li>
      <li>Centralizes critical system data:
        <ul>
          <li>Lists of loaded vehicles, stations, and compositions</li>
          <li>Total error count in the system</li>
        </ul>
      </li>
      <li>Ensures consistent access to system state from any component</li>
    </ul>
  </li>
</ul>

<b>Factory method:</b>
<ul>
  <li>Used for CSV reader classes because:
    <ul>
      <li>Different CSV files require different reading processes based on the file provided through arguments</li>
      <li>Each CSV file has:
        <ul>
          <li>Different data structures and columns</li>
          <li>Unique object creation processes</li>
          <li>Distinct parsing requirements</li>
        </ul>
      </li>
      <li>Makes it easier to add new CSV readers later (since each CSV document has different data and reading process)</li>
    </ul>
  </li>
</ul>

<b>Builder:</b>
<ul>
  <li>Used for vehicle creation because:
    <ul>
      <li>Different types of railway vehicles exist:
        <ul>
          <li>Locomotives</li>
          <li>Passenger wagons</li>
          <li>Car transport wagons</li>
          <li>Container freight wagons</li>
        </ul>
      </li>
      <li>According to the file, while all vehicle data is mandatory, vehicles of certain categories have some data (columns) filled with value 0</li>
      <li>This means that data isn't relevant for that vehicle group</li>
      <li>Allows multiple different construction processes for vehicles, each providing a different representation</li>
    </ul>
  </li>
</ul>

<img src="https://github.com/user-attachments/assets/6b9794bf-bc4c-418b-a649-c732633b6ddb" />


### Commands

<ul>
<li><b>IP</b>: Display railway lines, showing each line's code, starting and ending stations, and total distance</li>
<li><b>ISP railwayLineCode order</b>: List stations on a specific railway line. Use N for normal order and O for reverse order. (example: ISP M501 N)</li>
<li><b>ISI2S startStation - endStation</b>: Show all stations between two specified stations on the same or different lines. (example: ISI2S Donji Kraljevec - Čakovec)</li>
<li><b>IK compositionCode</b>: Show transport vehicles in a specific composition, detailing each vehicle's role, type, and specifications. (example: IK 8001)</li>
<li><b>Q</b>: Quit the program.</li>
</ul>

### Demo

![dz1](https://github.com/user-attachments/assets/50d7a921-1199-4ddf-b39e-0828ff9cb6e4)


## 🔨 Project #2

Project #2 is an upgrade to the project #1. 
Two more CSV files were added (day marks and train schedule), as well as new commands and new problems. Now different types of trains exist (train is not equal to composition or vehicle) - normal, accelerated and fast (new columns were added to the train stations CSV file from project #1).

### Design patterns

Old patterns changes:

<b>Singleton:</b>
<ul> <li>Added several new lists, methods for them and command management</li> </ul>

<b>Factory Method:</b>
<ul>  <li>Added new classes for new files: <ul> <li>CSV day markings </li> <li>CSV timetable</li> </ul> </li> </ul>

New patterns:

<b>Composite:</b>
<ul> <li>Timetable consists of multiple trains, trains have one or more stages with stations which can be implemented as tree structure</li> <li>Defines class hierarchy: <ul> <li>VozniRedComponent (Timetable Component)</li> <li>EtapaLeaf (Stage Leaf)</li> <li>VlakComposite (Train Composite)</li> <li>VozniRedComposite (Timetable Composite)</li> </ul> </li> </ul>

<b>Visitor</b>
<ul> <li>Builds upon previously described Composite pattern to avoid overcrowding classes</li> <li>Allows defining new operations without changing element classes, which is needed for tree structure with multiple required behaviors</li> <li>Special print structure and different logic in composing that print for each command</li> <li>Just need to add new concrete visitor class if new command appears in future homework</li> </ul>

<b>Observer</b>
<ul> <li>Used for functionality where subscribed users need to be notified about station/train they are following</li> <li>When one object changes state, all dependents are notified and updated</li> </ul>

<b>Mediator</b>
<ul> <li>Used for lost & found office functionality</li> <li>Instead of user who found/lost something questioning every user if they lost/found that item, mediator was introduced to handle this</li> <li>Users can communicate through mediator</li> </ul>

![dijagram1 drawio](https://github.com/user-attachments/assets/99fc7008-3f65-4999-a53c-834029bf27fb)


### Commands

<ul>
<li><b>IV</b>: Display all trains showing train number, starting station, ending station, departure time, arrival time, and total distance</li>
<li><b>IEV trainNumber</b>: Display stages for a specific train, showing train number, railway line, stage's starting and ending stations, departure and arrival times, stage distance, and days of operation. (example: IEV 3609)</li>
<li><b>IEVD days</b>: Show trains and their stages that operate on specified days of week. Days are specified using abbreviations: Po (Monday), U (Tuesday), Sr (Wednesday), Č (Thursday), Pe (Friday), Su (Saturday), N (Sunday). (example: IEVD PoSrPeN)</li>
<li><b>IVRV trainNumber</b>: Display complete train timetable showing train number, railway line code, station names, departure times, and distance from starting station. (example: IVRV 3609)</li>
<li><b>IVI2S startStation - endStation - day - fromTime - toTime - format</b>: Show trains (timetable) that can be used for travel between two stations on a specific day within given time period. Format parameter controls column display:
  <ul>
    <li>S: Station name</li>
    <li>P: Railway line</li>
    <li>K: Distance in kilometers</li>
    <li>V: Train departure time</li>
  </ul>
  Example: IVI2S Donji Kraljevec - Čakovec - N - 0:00 - 23:59 - SPKV</li>
<li><b>DK firstName lastName</b>: Register a new user. (example: DK Pero Kos)</li>
<li><b>PK</b>: List all registered users</li>
<li><b>DPK firstName lastName - trainNumber [- station]</b>: Add user subscription for train tracking:
  <ul>
    <li>Track entire train journey: DPK Pero Kos - 3301</li> 
    <li>Track specific station arrival: DPK Mato Medved - 3309 - Donji Kraljevec</li>
  </ul>
</li>
<li><b>SVV trainNumber - day - coefficient</b>: Simulate train journey for specified day with time acceleration coefficient (1 simulation minute = 1/coefficient real seconds). Simulation runs until train reaches destination or user enters 'X'. Updates subscribed users about train progress. (example: SVV 3609 - Po - 60)</li>
<li><b>NAD firstName lastName item description</b>: Report a found item to the lost & found office (example: NAD Marko Maric mobitel Samsung)</li>
<li><b>IZG firstName lastName item description</b>: Report a lost item to the lost & found office (example: IZG Ana Anic mobitel Samsung)</li>
</ul>

### Demo

![2024-12-2120-10-22-ezgif com-video-to-gif-converter](https://github.com/user-attachments/assets/1b95904a-7ae9-4b1a-983d-48c3c8ab8701)


## 🔨 Project #3

### Design patterns




## How to start

### Prerequisites
<ul>
<li>Maven installed</li>
<li>Java 21 installed</li>
</ul>

To set up and run the Railway Transport Management System, follow these steps:

On Linux, open the terminal and navigate to the selected project

```
cd askarica20_zadaca_1
```

Run the following command to compile and package the application:

```
mvn clean package
```

After building, start the application using the following command:

Project #1:

```
java -jar /home/NWTiS_1/DZ_1/askarica20_zadaca_1/target/askarica20_zadaca_1-1.0.0.jar --zs DZ_1_stanice.csv --zps DZ_1_vozila.csv --zk DZ_1_kompozicije.csv
```

Project #2:
```
java -jar /home/NWTiS_1/DZ_1/askarica20_zadaca_2/target/askarica20_zadaca_2-1.0.0.jar --zs DZ_2_stanice.csv --zps DZ_2_vozila.csv --zk DZ_2_kompozicije.csv --zvr DZ_2_vozni_red.csv --zod DZ_2_oznake_dana.csv
```
