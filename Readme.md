# Gedistribueerde Systemen

## Runnen
Er zijn 2 manieren om het project te runnen: gebruik maken van de al reeds gecompileerde app.jar of het project openen in eclipse en Core/CLI.java runnen. Voor alle devices in het domotica netwerk wordt gebruik gemaakt van eenzelfde executable. Hieronder staan de verschillende mogelijkheden om devices aan te maken. Een belangrijke opmerking: voor het runnen van replication devices(Fridges en Users) zijn steeds twee opeenvolgende pporten nodig! Dit wil zeggen dat als een Fridge wordt gecreerd op poort 1000, alsook poort 1001 beschikbaar moet zijn!

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

### Time Sync

### Architectuur

## Gebruikte Code
