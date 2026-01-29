## Coding exercise for Sportradar:
### Implementation of the Football World Cup Score Board as a simple library.

Includes Scoreboard interface and ScoreboardService implementing the interface.
Scoreboard is tested on operations listed in coding exercise.
The scoreboard has a few extra operations and is designed to be easily 
extended to perform more operations. The extra operations are not tested,
only operations listed in the exercise are tested in ScoreboardTest.

I have one assumption regarding point 4: Get a summary of games by total score.
Those games with the same total score will be returned ordered by the most 
recently added to our system. 
Although filtering games by the total score feels illogical to me,
I assumed that this means getting games matching a given total score,
returning only the games matching the given score exactly. 
There is further functionality for getting all active games in order and
getting a subset of games based on other request criteria.