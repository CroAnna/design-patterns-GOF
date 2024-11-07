# Railway Transport Management System - design patters

This project is mostly focused on different types of design patterns by GOF (Gang of Four) in Java. 

## About the project

It is separated in 3 different subprojects + data used to start them. Every one of them has following document that explains project requirements for it, how it should be implemented, what commands does it have etc. The second one is built upon the first one, and third one upon the second one, even though every one has new funcionalities and used design patterns.

## 🔨 Project #1
This Java project manages railway transport for passengers and goods, involving railway infrastructure, stations, and transport compositions. The program operates in a command-line environment, executing various commands for data retrieval and loads data from given CSV files.

### Design patterns

<img src="https://github.com/user-attachments/assets/6b9794bf-bc4c-418b-a649-c732633b6ddb" />


### Commands

<ul>
<li>IP: Display railway lines, showing each line's code, starting and ending stations, and total distance</li>
<li>ISP <railwayLineCode> <order>: List stations on a specific railway line. Use N for normal order and O for reverse order. (example: `ISP M501 N`)</li>
<li>ISI2S <startStation> - <endStation>: Show all stations between two specified stations on the same or different lines. (example: `ISI2S Cerje Tužno - Lepoglava` )</li>
<li>IK <compositionCode>: Show transport vehicles in a specific composition, detailing each vehicle's role, type, and specifications. (example: IK 8001)</li>
<li>Q: Quit the program.</li>
</ul>

### Demo

![dz1](https://github.com/user-attachments/assets/50d7a921-1199-4ddf-b39e-0828ff9cb6e4)

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

```
java -jar /home/NWTiS_1/DZ_1/askarica20_zadaca_1/target/askarica20_zadaca_1-1.0.0.jar --zs DZ_1_stanice.csv --zps DZ_1_vozila.csv --zk DZ_1_kompozicije.csv
```
