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


## Design
### Fault Tolerance
Er is
