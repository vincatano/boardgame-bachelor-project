# Vincenzo Catano
## Digital version of the board game "Sagrada" - Bachelor of Science project thesis
This project was developed by a team of three as part of my thesis for the Bachelor of Science in Computer Science Engineering @ Politecnico di Milano. **This repository is a copy of the original repository that cannot be shown publicly due to copyright issues.** The project aimed at developing the digital version of an existing board game called ["Sagrada"](http://www.craniocreations.it/prodotto/sagrada/). The digital version allows you to play it through the GUI or the Command Line, allowing also to play with two different network options (Socket and RMI).

### Describe your role in the project
My main focus during the project was on the model of the game, particularly the [gamedata](https://github.com/vincatano/boardgame-bachelor-project/tree/main/src/main/java/it/polimi/ingsw/model/gamedata), the [tests](https://github.com/vincatano/boardgame-bachelor-project/tree/main/src/test/java/it/polimi/ingsw) and the [Command Line visualization](https://github.com/vincatano/boardgame-bachelor-project/tree/main/src/main/java/it/polimi/ingsw/view/cli) of the game. I also formalized the rules of the cards so that we wouldn't need to hard-code them.

### What you personally learned from it
Thanks to this project I learned a lot. First, I learned how much organizing tasks is important: without a proper plan, it is really difficult to do everything on time and reach a target. Secondly, I worked in a team for the first time, which gave me the chance to understand how to divide the work among teammates and to trust others to do their job as planned. I also learned how to be independent and autonomous while doing my job and to manage the time because we had to do also exams during the development of the game. On the technical side, I learned a lot about Java, XML and Version Control (Git) and how to use them to develop a big project. Also, when formalizing the rules of the cards in XML, I learned that writing a good and efficient code may take time at the beginning of the activities, but it will save much more time afterwards

### Why you think this particular code sample demonstrates the highlight of your skillset
The code that I wrote during this project is, in my opinion, simple and efficient. As stated before, the use of formalized rules helped us a lot during the project in developing faster different cards and made them more reusable in case some other cards (DLC for example) with different consequences but equal rules wanted to be added. 

### Additional information
Below you can find some of the Project Choices that we made during the development of the code:

1) We adopted the [MVC](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller) pattern.
2) We decided to have a centralized architecture to avoid duplicated data.
3) We chose to represent ObjectiveCards ([Public Cards](https://github.com/vincatano/boardgame-bachelor-project/blob/main/src/main/resources/public_cards_formalization.xml) and [Private Cards](https://github.com/vincatano/boardgame-bachelor-project/blob/main/src/main/resources/private_cards_formalization.xml)), [ToolCards](https://github.com/vincatano/boardgame-bachelor-project/blob/main/src/main/resources/tool_cards_formalization.xml) and [WindowPatternCards](https://github.com/vincatano/boardgame-bachelor-project/blob/main/src/main/resources/window_pattern_cards_formalization.xml) in an XML format, this way we avoided hard coding information inside Classes.
4) To implement game turns and to control the player's moves flow we decided to use the state pattern, creating a [finite state machine](https://github.com/vincatano/boardgame-bachelor-project/blob/main/Deliverables/Documentazione%20Supplementare/FSM.pdf).
5) ToolCards have different effects on a player's move but most of the time they have few similarities. We decided to decompose these effects in minor moves (ex: selecting a dice that belongs to the RoundTrack) and formalize them in the ToolCard XML file. Using JavaReflection we analyzed each move dynamically, which allowed us to avoid writing 12 toolcard classes with common or similar effects.  
6) We decided to implement a VirtualView to process events coming from the model and forward them to the client through the network.
7) [All messages](https://github.com/vincatano/boardgame-bachelor-project/blob/main/Deliverables/Documentazione%20Supplementare/Struttura%20Messaggi%20client-server.pdf) that passed through the network were strings. This made the client application language independent just by knowing the adopted protocol.

Here instead are some of the commands useful to run the game.

COMMAND LINE PARAMETERS FOR THE CLIENT
1) "-a serverAddress" (Default: localhost).
2) "-gui" (Default).
3) "-cli".

COMMAND LINE PARAMETERS FOR THE SERVER
1) "-timerRoom" waitingRoom timer (Default : timerRoom=30).
2) "-timerCard" timer for the patternCard choice (Default : timerCard=10).
3) "-timerMove" player move's timer (Default : timerCard=30.


Point of attention: if you run the game you might find that some GUI images are different from the ones uploaded in the resources. This is because, to make this code public, I had to replace the original images with new ones. I also decided to leave the original ones in the game since they fit better in the overall graphics of the game.
