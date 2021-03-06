﻿# OPC UA Simulator for Industrial Plants (OSIP)

<img src="./icon.png" width="100">

OSIP ermöglicht es, die Vorteile der Vernetzung von Maschinen mit OPC UA interaktiv
zu demonstrieren, um Industriekunden von den neuen Möglichkeiten der Industrie 4.0 zu überzeugen.

Dieses Projekt entstand während der Praxis der Softwareentwicklung 2016/17 am Karlsruher Institut für Technologie
zum Thema *Implementierung eines OPC UA Systemadapters für den Industrial Data Space*.
Durchgeführt am Fraunhofer IOSB.

## Screenshots

Hauptfenster der Simulation:  
<img src="./media/screenshot-simulation.png" width="500">

Hauptfenster der Überwachungskonsole:  
<img src="./media/screenshot-monitoring.png" width="500">

## Kompilieren

Das Projekt wird mit Maven verwaltet, der Build-Prozess ist somit weitestgehend automatisiert.
```bash
git clone https://github.com/ByteHamster/PSE.git
cd PSE/src
mvn package
```

Zusätzlich kann die Dokumentation mit generiert werden. Zu finden ist sie unter `src/target/site/apidocs`.

```bash
mvn package javadoc:aggregate
```

Mit folgender Befehlsfolge wird zusätzlich zur Dokumentation ein Maven-Report generiert, der unter `src/target/site` zu finden ist.

```
mvn install
mvn site
```

Folgendes Kommando erstellt unter `src/target/´ eine Zip-Datei mit den Quellcodedateien und den ausführbaren Jar-Dateien:

```
mvn package assembly:single
```

Ein häufiges Problem ist, dass die Abhängigkeit `org.eclipse.milo` nicht gefunden werden kann. Dies liegt daran, dass alte Versionen von Milo aus dem Repository genommen werden. Das Problem kann behoben werden, indem die verwendete Version angepasst wird:

```xml
src/pom.xml:

<properties>
    <milo-version>0.1.2-SNAPSHOT</milo-version>
```

Die jeweils aktuelle Version kann im Repository herausgefunden werden:
https://oss.sonatype.org/content/repositories/snapshots/org/eclipse/milo/sdk-client/

## Ausführen

Simulation:
```bash
java -jar src/osip-simulation-controller/target/osip-simulation-controller-1.0-with-dependencies.jar
```

Überwachungskonsole:
```bash
java -jar src/osip-monitoring-controller/target/osip-monitoring-controller-1.0-with-dependencies.jar
```

## Docker
Um OSIP mittels Docker zu starten, muss Docker und Docker Compose installiert sein. Dann muss im Hauptverzeichnis
```bash
docker-compose up
```
ausgeführt werden. Sollen die Docker Container neu erstellt werden, dann muss zuerst
```bash
docker-compose build
```
und danach wie gewohnt
```bash
docker-compose up
```
ausgeführt werden. **Achtung**: Die Docker Container werden mit den lokalen Quelldateien gebaut, d.h. wenn es lokale Änderungen an den Quelldateien gab, werden diese Änderungen erst nach dem erneuten Erstellen der Container übernommen.
Kommt beim Starten der Container die Fehlermeldung, dass der Container sich nicht mit Xorg verbinden kann (`Unable to open DISPLAY`), so sollte das mitgelieferte Skript
```bash
./startup.sh
```
ausgeführt werden.

## Beitragende

M. Armbruster ([@HansMartinA](https://github.com/HansMartinA))  
D. Kahles ([@Dav1dK](https://github.com/Dav1dK))  
H. Lehmann ([@ByteHamster](https://github.com/ByteHamster))  
M. Schwarzmann ([@marimbMax](https://github.com/marimbMax))  
N. Wilhelm ([@NikoWil](https://github.com/NikoWil))  
