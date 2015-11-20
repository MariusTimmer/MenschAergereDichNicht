## Networking
**Wie das Netzwerk-Interface funktioniert**


Zuständige Entwickler: Marius Timmer
Noch niemand



## PRINZIP

MADN soll eine Server- und eine ClientKlasse bieten. Wird ein Spiel als Server gestartet, werden die anderen Instanzen dementsprechend als Clients gestartet und verbinden sich. Der Server informiert die Clients darüber wer zu Beginn wo steht und hält sie bei jeder Veränderung auf dem aktuellen Stand. Er verrät auch, welcher Spieler/Client gerade an der Reihe ist und wartet dann auf eine Aktion ( *=das Setzen einer Figur* ) des Clients. Beim Starten des Spiels wird ein kleines Hauptmenü erscheinen in dem der Nutzer wählen kann zwischen "offline-Spiel" ( *Standard* ), oder Netzwerk als Server/Client. In diesem Menü werden dann auch die Konfigurationen des Spiels vorgenommen ( *Spiel-Datei, Anzahl der Spieler, etc.*) oder im Netzwerk-Spiel die ServerNamen und -Ports ( *evtl. auch Spielernamen* ) angegeben.

Mehr Details über die Kommunikation untereinander sind noch nicht bekannt, da zuvor ein gemeinsames Protokoll entworfen werden muss.



## NETZWERK-PROTOKOLL

Die Netzwerk-Schnittstelle soll gewährleisten, dass die Befehle für das Programm auch von einer anderen Instanz des Spiels gegeben werden können. Um allerdings verschiedene Logiken gegeneinander antreten zu lassen, ist es zwingend erforderlich, dass sich die verschiedenen Programme mit ihren eigenen Logiken an einen bestimmten Befehlssatz einigen um untereinander zu kommunizieren.

Um das zu schaffen, werden wir uns einmal mit den anderen Gruppen zusammen setzen und uns auf ein einheitliches Protokoll einigen. Die anderen Gruppen werden dabei schon teilweise nahezu fertige Schnittstellen bieten, für die wir unser Projekt kompatibel schreiben. Einige minimale Anpassungen im Protokoll könnten erforderlich sein, da die verschiedenen Programme von Grund auf andere Funktionsweisen aufzeigen und besonders angesprochen werden müssten.
