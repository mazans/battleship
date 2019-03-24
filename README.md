# Battleship
This is simple Battleship game which uses sockets to communicate through network. Application consists of three parts: commons, server and client.

Commons part contains classes responsible for representing whole game model, that is ships models, representation of playing board and player. Moreover, commons part contains many classes following Strategy/Command Design Pattern, which enable communiaction betweeen server and client.

Server part is responsible for creating games, maintaining communication betweeen players and controlling game rules.

Client part provides interface for end user and create communicaton with server.

This application helped me learn about network communication, using threads and gave me opportunity to use tools such as JUnit or Maven.
## Technologies
* Java 8
* JUnit 5+
* Maven 3+
## Launch
1. Go to 'commons' directory.
2. Call command:
```
mvn install
```
3. Go to 'server' directory.
4. Call command:
```
mvn package
```
5. Go to 'client directory.
6. Call command:
```
mvn package
```

To run server, go to 'target' directory under 'server' one and call command: 
```
java -jar server-0.0.1-SNAPSHOT-jar-with-dependencies.jar
```

To stop server, type in 'CLOSE' in command line.

To run client, go to 'target' directory under 'client' one and call command:
```
java -jar client-0.0.1-SNAPSHOT-jar-with-dependencies.jar
```
