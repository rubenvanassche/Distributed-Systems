# Gedistribueerde Systemen

## Runnen
Er zijn 2 manieren om het project te runnen: gebruik maken van de al reeds gecompileerde app.jar of het project openen in eclipse en Core/CLI.java runnen(in de map lauchers zijn standaard run configurations opgeslagen). Voor alle devices in het domotica netwerk wordt gebruik gemaakt van eenzelfde executable. Hieronder staan de verschillende mogelijkheden om devices aan te maken. Een belangrijke opmerking: voor het runnen van replication devices(Fridges en Users) zijn steeds twee opeenvolgende pporten nodig! Dit wil zeggen dat als een Fridge wordt gecreerd op poort 1000, alsook poort 1001 beschikbaar moet zijn!

### Controller
```
java -jar app.jar -type controller -id 1 -ip 192.168.1.1 -port 1000 -a 5
[id]   ID van het apparaat
[ip]   Ip addres van het apparaat waar het op draait
[port] Poort waarop het apparaat draait
[a]    Aantal temperatuur waarnemingen die worden bijgehouden
```

### Fridge
```
java -jar app.jar -type fridge -id 1 -ip 192.168.1.1 -port 1000 -cip 192.168.1.1 -cport 2000
[id]    ID van het apparaat
[ip]    Ip addres van het apparaat waar het op draait
[port]  Poort waarop het apparaat draait
[cip]   Ip adres van de controller waar het apparaat mee werkt
[cport] Ip adres van de controller waar het apparaat mee werkt
```

### Light
```
java -jar app.jar -type light -id 1 -ip 192.168.1.1 -port 1000 -cip 192.168.1.1 -cport 2000
[id]    ID van het apparaat
[ip]    Ip addres van het apparaat waar het op draait
[port]  Poort waarop het apparaat draait
[cip]   Ip adres van de controller waar het apparaat mee werkt
[cport] Ip adres van de controller waar het apparaat mee werkt
```

### Sensor
```
java -jar app.jar -type sensor -id 1 -ip 192.168.1.1 -port 1000 -cip 192.168.1.1 -cport 2000 -s 20.0 -d 0.1 -f 5
[id]    ID van het apparaat
[ip]    Ip addres van het apparaat waar het op draait
[port]  Poort waarop het apparaat draait
[cip]   Ip adres van de controller waar het apparaat mee werkt
[cport] Ip adres van de controller waar het apparaat mee werkt
[s]     Temperatuur waarop de sensor start met waarnemen [Double]
[d]     Drifting time waarmee de clock wordt aangepast [Double]
[f]     De update frequentie in seconden waarmee de sensor zijn temperatuur naar de controller stuur [Int]
```

### User
```
java -jar app.jar -type user -id 1 -ip 192.168.1.1 -port 1000 -cip 192.168.1.1 -cport 2000
[id]    ID van het apparaat
[ip]    Ip addres van het apparaat waar het op draait
[port]  Poort waarop het apparaat draait
[cip]   Ip adres van de controller waar het apparaat mee werkt
[cport] Ip adres van de controller waar het apparaat mee werkt
```

## Gebruik
Elk device bevat een cliche command shell, deze is interactief en d.m.v. het commando ?l kan men alle commandos bekomen voor een bepaalde shell. Meer informatie over een commando kan opgevraagd worden d.m.v. ?h [COMMANDONAAM].

## Design
Alle vereisten uit de opgave zijn geimplementeerd! Hieronder een woordje uitleg bij belangrijke facetten van het netwerk.
### Fault Tolerance
Er is rekening gehouden met vanalles wat kan fout gaan, hieronder een lijst van mogelijkheden(welke niet volledig is):
#### Controller
- Kan uitvallen maar zal haar data repliceren naar fridges en users
- Indien een gebruiker alle online devices opvraagt zal een correcte lijst worden weergeven

#### Fridge
- Indien geopend zal het niet mogelijk zijn dat een andere user deze opent
- Indien dubbele items worden toegevoegd zullen deze worden toegevoegd met een nummer achteraan(vb. item, item1, item2)
- Als een gebruiker een fridge wilt openen die niet bestaat is of offline is, dan wordt een melding weergeven
- Indien een andere user de fridge wil sluiten zal er een melding worden weergeven
- Indien een operatie wordt uitgevoerd op een fridge die niet bestaat of offline is, dan wordt er een melding weergeven
- Indien een user een fridge al geopend heeft en andere wilt openen wordt gevraagd om eerst de andere fridge te sluiten
- Indien ene user iets probeert te plaatsen of verwijderen in een fridge die hij niet heeft geopend wordt een melding weergeven
- Indien een item wordt verwijderd uit een fridge dat niet aanwezig is wordt een melding weergeven

#### Light
- Indien een gebruiker de status wil aanpassen van een licht of deze wilt lezen van een licht dat niet bestaat of offline is, wordt er een melding weergeven

#### Sensor
- Indien een user de temperatuur van een niet bestaande sensor wil lezen, zal deze een melding krijgen
- Indien een user de temperatuur van een offline sensor wil lezen, zal de laatste temperatuur die werd verstuurd toen de sensor online was worden getoond
- Indien een controller offline gaat zal de sensor gegevens blijven versturen tot de controller terug online komt of deze info krijgt van een backup controller naar waar het gegevens kan sturen

#### User
- Indien een user het huis uit is zal deze geen mogelijkheid hebben tot bepaalde operaties die enkel binnenhuis mogelijk zijn
- Indien een gebruiker al thuis is en nogmaals probeert thuis te komen, wordt een melding weergeven

### Replication
Om ervoor te kunnen zorgen dat de controller kan uitvallen en dat een fridge of user kan overnemen is er een replication protocol gebouwd. Dit protocol houdt zich bezig met het repliceren van data van controller(client) naar fridges & users(server). En het selecteren d.m.v. een Chang-Roberts ring van een nieuwe controller mocht de oude uitvallen.

Wanneer een nieuwe fridge of user opstart, start deze naast een standaard server die commando's van de controller verwerkt ook een replication server. Dit is de reden dat users en fridges 2 poorten nodig hebben om te kunnen draaien.

De controller stuurt intitieel alle data door die het heeft voor replication naar nieuwe fridges en users. En bij elke update aan de interne structuur van de controller wordt ook een update gestuurd.

### Time Sync
Om de klokken van de sensoren te synchroniseren wordt gebruik gemaakt can Christians Algorithm. De controller heeft een centrale klok die juist staat en hierop worden alle andere klokken gesynchroniseerd.

### Architectuur
Het project maakt gebruik van een MVC structuur waarbij de controllers gesymboliseerd worden door classes met de naam manager.X . Deze bevat zowel een client en een server. In het geval van een gebruiker bevat deze dus een client voor connectie met de controller en UserServer.

Elke Manager van een device wordt afgeleid van een Manager class. Elk device dat controlled is (fridges, users, lights en sensors) wordt afgeleid van een ControlledManager class welke functies voorziet om connecties te maken met een controller. Elk replicating device(users en fridges) wordt afgeleid van een ReplicatedManager welke wordt afgeleid van een ControlledManager. Deze ReplicatedManager voorziet alle functionaliteit om om te gaan met kopieen van de controller en eventueel een backup controller te verkiezen en starten.

De data structuur wordt gesymboliseerd door de structure.X . Elke user heeft dus een speciale Structure.User. Elke structure van een device wordt afgeleid van een structure.Device welke informatie bevat over hoe te connecteren met een controller. Deze structure.Device wordt dan weer afgeleid van een structure.Entity welke informatie(ip addres, port, id, type) bevat over het device om connecties aan te gaan.

Replication wordt uitgevoerd met een ReplicationClient voor de controller en ReplicationServers voor users en fridges. Wanneer types van structure.X moeten omgezet worden naar Avro types wordt er gebruik gemaakt van ReplicationConverter.

De Avro protocols bevinden zich allemaal in de protocols.X packages. Hierin zitten ook de JSON files om deze protocols d.m.v. Avro te genereren.

Er zijn 2 factories: de devicefactory maakt structures.X aan voor verschillende apparaten en de managerfactory combineert deze structure met een client en een server om zo aan RPC te doen. Election.ElectionProcessor is een class die in elke ReplicatedManager wordt voorzien en een eventuele verkiezing van een nieuwe controller afhandeld.

De GUI class wordt gebruikt om een apart thread op te starten met daarin een Cliche shell voor het uitvoeren van commando's. Als laatste is er de CLI class, hierin wordt een device opgestart. D.m.v. Commons.Cli wordt de command line input gelezen en verwerkt.

## Externe Code
- Cliche (http://cliche.sourceforge.net)
- Apache Commons Cli (https://commons.apache.org/proper/commons-cli/)
