# ProgettoIngSw
Team components:

Alessandrelli Luca 10533904

Caldarola Giovanni 10520479

Catano Vincenzo 10520959


Description of the project: the project is based on the realization of the digital version of the board game "Sagrada".
Implemented requirements: Complete Rules + CLI(it's not fully tested) + GUI + RMI + Socket + MultiMatch

COMMAND LINE PARAMETERS FOR THE CLIENT
1) "-a serverAddress" (Default: localhost).
2) "-gui" (Default).
3) "-cli".

COMMAND LINE PARAMETERS FOR THE SERVER
1) "-timerRoom" waitingRoom timer (Default : timerRoom=30).
2) "-timerCard" timer for the patternCard choice (Default : timerCard=10).
3) "-timerMove" player move's timer (Default : timerCard=30.

PROJECT CHOICES

1) We adopted the pattern MVC.
2) We decided to have a centralized architecture to avoid duplicated data.
3) We chose to represent ObjectiveCards, ToolCards and WindowPatternCards in a XML format, this way we avoid hard coding information inside Classes.
4) In order to implement game turns and to control the player's moves flow we decided to use the state pattern, creating a finite state machine.
5) ToolCards have different effects on a player's move but most of the time they have little similarities. We decided to decompose these effects in minor moves (ex: selecting a dice that belongs to the RoundTrack) and formalize them in the ToolCard xml file. Using JavaReflection we analyze each move dynamically, avoiding to write 12 toolcard classes with effects in common.  
6) We decided to implement a VirtualView in order to process events coming from the model and forward them to the client through the network.
7) All messages that pass through the network are strings, this way knowing the protocol adopted, the client application is language independent.
