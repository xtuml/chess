# Getting Started with the Lichess Bot

This project provides a framework for creating bots to play chess on
[lichess.org](https://lichess.org). The project provides bindings to the
Lichess public API as well as management of challenges and games and a utility
for listing legal moves. The project is set up to run in BridgePoint Model
Verifier.

## Prerequisites

### Download BridgePoint

Download the latest version of BridgePoint from
[here](https://s3.amazonaws.com/xtuml-releases/nightly-build/buildfiles.html).
Due to ongoing development related to this project, an engineering branch build
is required to run the project.

### Create a Lichess bot account

To get started, you must create an account on lichess.org. In accordance with
Lichess terms of service, an account must _not_ have played any games before
being upgraded. Note that you will not be able to use a bot account to play
normally on the site after it has been upgraded. We recommend that you create
two accounts -- one for the bot and one to play as a human player.

1. Create a Lichess account for your bot [here](https://lichess.org/signup).
2. Once logged in, follow [this
   link](https://lichess.org/account/oauth/token/create?scopes[]=bot:play) to
   generate an API token for your bot. Save the token in a secure location. Do not
   commit to the repository.

Your account will be automatically upgraded to a bot account the first time you
launch the application.


## Running the application

1. Open BridgePoint on a fresh workspace. Import the `lichess_bot` project.
2. Switch to the xtUML Debugging perspective.
3. Select "Run" > "Debug Configurations..." and click on "LichessBot" under the
   "xtUML eXecute Application" heading.
4. Click "Debug" to launch the application.
5. Log in to your human account on lichess.com.
6. Use the search bar to navigate to the user page for your bot. Challenge your
   bot to a match.


## Adding bot intelligence

The template project provides the minimum amount of intelligence to play a
game. On its turn, the bot will select a random move from the list of legal
moves and play that move. It is the task of the modeler to improve this
behavior to create a winning strategy.

### Interfaces

The `chess` interface provides a modeled hook into the lichess.org public API.
For more information on this API, visit the Lichess documentation page
[here](https://lichess.org/api). Messages and types were modeled to closely
follow this reference.

### Utilities

The `ChessLib` utility has been provided with the `legalMoves` bridge. This
bridge takes as input an array of moves that have occurred in the game since
the start position. It returns an array of legal moves.

### Move representation

Each move is are represented by a 4 character string in [Long Algebraic
Notation](https://en.wikipedia.org/wiki/Algebraic_notation_(chess)#Long_algebraic_notation).
In standard chess vocabulary, a _move_ represents a move by white and a
corresponding response by black. In the model, however, each move by black _or_
white is represented as its own element in the array of moves representing the
state of the game. A natural consequence of this fact is that if the length of
the move array is divisible by 2, it is white's turn to play. If the array is
not divisible by 2, it is black's turn to play.

### Extending the application

The application consists of two components -- `Engine` and `Lichess`. The
`Lichess` component is a realized component and should not be edited by the
modeler.

The modeler has freedom to extend the application as desired. A natural
starting place is the "our turn" state within the `Game` instance state machine
in the `Engine` component. Here the bot must select the next move and then
generate the "play move" event.

### Chess rules and strategy

lichess.org requires bots to play according to standard chess rules. The moves
returned by the `ChessLib` EE will also observe the rules and only return legal
moves. Any move selected by a bot to play next should be one of these legal
moves.

An excellent resource for learning the rules and some basic bot strategies is
the Chess Programming Wiki found
[here](https://www.chessprogramming.org/Main_Page). The official rules of chess
can be found [here](https://www.fide.com/FIDE/handbook/LawsOfChess.pdf).


## Issues

If you are having an issue with BridgePoint, Verifier, or the project itself,
please check the [issue tracker](https://support.onefact.net) to see if the
behavior matches an existing issue. [This custom
query](https://support.onefact.net/projects/bridgepoint/issues?query_id=169)
has been created to track issues specifically relating to this modeling
challenge. If you don't find the issue you are looking for, please raise a new
issue [here](https://support.onefact.net/projects/bridgepoint/issues/new)
