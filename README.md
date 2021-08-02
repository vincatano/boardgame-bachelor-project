# Vincenzo Catano
## Digital version of the board game "Sagrada" - Bachelor of Science project thesis
This project was developed by a team of three as a part of the thesis for the Bachelor of Science in Computer Science Engineering @ Politecnico di Milano. The project aimed at developing the digital version of an existing board game called ["Sagrada"](http://www.craniocreations.it/prodotto/sagrada/). The digital version allows you to play it through the GUI or the Command Line, allowing also to play with two different network options (Socket and RMI).

### Describe your role in the project
My main focus during the project was on the model of the game, particularly the [gamedata](https://github.com/vincatano/boardgame-bachelor-project/tree/main/src/main/java/it/polimi/ingsw/model/gamedata), the [tests](https://github.com/vincatano/boardgame-bachelor-project/tree/main/src/test/java/it/polimi/ingsw) and the [Command Line visualization](https://github.com/vincatano/boardgame-bachelor-project/tree/main/src/main/java/it/polimi/ingsw/view/cli) of the game. I also formalized the rules of the cards so that we wouldn't need to hard-code them. We as a team decided to don't have a single project manager but we decided that we were all on the same level of decision making.

### What you personally learned from it
Thanks to this project I learned a lot. First of all, I learned how to organize the work to arrive at a goal, how to work in a team, dividing the work and trusting others to do their job. I also learned how to be independent and autonomous while doing my job and to manage the time because we had to do also exams during the development of the game. On the technical part, I learned a lot about Java and how to use it to create a big project. Also, noticing that the idea to formalize in XML the rules of the cards of the game, I understood that good and efficient code might take more time to write but it will save you even more time in the end.

### Why you think this particular code sample demonstrates the highlight of your skillset
The code that I wrote during this project is, in my opinion, simple and efficient. As stated before, the use of formalized rules helped us a lot during the project in developing faster different cards and made them more reusable in case some other cards (DLC for example) with different consequences but equal rules wanted to be added. 

### Additional information
Below you can find some of the Project Choices that we made during the development of the code:

1) We adopted the [MVC](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller) pattern.
2) We decided to have a centralized architecture to avoid duplicated data.
3) We chose to represent ObjectiveCards ([Public Cards](https://github.com/vincatano/boardgame-bachelor-project/blob/main/src/main/resources/public_cards_formalization.xml) and [Private Cards](https://github.com/vincatano/boardgame-bachelor-project/blob/main/src/main/resources/private_cards_formalization.xml), [ToolCards](https://github.com/vincatano/boardgame-bachelor-project/blob/main/src/main/resources/tool_cards_formalization.xml) and [WindowPatternCards](https://github.com/vincatano/boardgame-bachelor-project/blob/main/src/main/resources/window_pattern_cards_formalization.xml) in an XML format, this way we avoid hard coding information inside Classes.
4) To implement game turns and to control the player's moves flow we decided to use the state pattern, creating a [finite state machine](https://github.com/vincatano/boardgame-bachelor-project/blob/main/Deliverables/Documentazione%20Supplementare/FSM.pdf).
5) ToolCards have different effects on a player's move but most of the time they have few similarities. We decided to decompose these effects in minor moves (ex: selecting a dice that belongs to the RoundTrack) and formalize them in the ToolCard XML file. Using JavaReflection we analyze each move dynamically, avoiding writing 12 toolcard classes with effects in common.  
6) We decided to implement a VirtualView to process events coming from the model and forward them to the client through the network.
7) [All messages](https://github.com/vincatano/boardgame-bachelor-project/blob/main/Deliverables/Documentazione%20Supplementare/Struttura%20Messaggi%20client-server.pdf) that pass through the network are strings, this way knowing the protocol adopted, the client application is language independent.

Here instead are some of the commands useful to run the game.

COMMAND LINE PARAMETERS FOR THE CLIENT
1) "-a serverAddress" (Default: localhost).
2) "-gui" (Default).
3) "-cli".

COMMAND LINE PARAMETERS FOR THE SERVER
1) "-timerRoom" waitingRoom timer (Default : timerRoom=30).
2) "-timerCard" timer for the patternCard choice (Default : timerCard=10).
3) "-timerMove" player move's timer (Default : timerCard=30.


Point of attention: if you run the game you might find different images for the GUI from the ones that are uploaded in the resources. This is because, to make this code public, I had to replace the original images with new ones. I also decided to leave the original ones in the game since they fit better in the overall graphics of the game.
