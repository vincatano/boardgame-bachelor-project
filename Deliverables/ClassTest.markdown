**Data Tests**  
   **Tool Tests**
   * CardContainer Test
      * - [x] testPrivatePullout
      * - [x] testPublicPullout
      * - [x] testPublicRules
      * - [x] testPatternPullout
      * - [x] testTooManyPlayers
      
   * Cell Test
     * - [x] testGetDice
     * - [x] testGetPosition
     * - [x] testGetProperty
     * - [x] testisOccupied
   
   * DiceBagTest
      * - [x] testCreation
      * - [x] testEmptyBag
      * - [x] testExactNumber
      * - [x] testAddDice
      * - [x] testPullOut
      * - [x] testNumPullOut

   * DiceTest
      * - [x] testGetNumber
      * - [x] testGetColour
      * - [x] testEquals
      * - [x] testSetDice
      * - [x] testRollDice
      
   * DraftPoolTest
      * - [x] testRemoveDice
      * - [x] testNeverEmpty
      * - [x] testFindDice
      * - [x] testFindInPosition
      * - [x] testRemoveAndFindDice
      
   * ObjectiveCardTest
      * - [x] testCardOne
   
   * RoundTrackTest
      * - [x] testGet_And_Set
      * - [x] testFind

   * ToolCardTest
      * - [x] testIncreasingCost
      * - [x] testGetter
      
   * WindowPatternCardTest
      * - [x] testMatrixLength
      * - [x] testOccupied
      * - [x] testFull
      * - [x] testFirstCard
      * - [x] testSecondCard
      * - [x] testThirdCard
      * - [x] testFourthCard
      * - [x] testFifthCard
      * - [x] testSixthCard
      * - [x] testSeventhCard
      * - [x] testEightCard
      * - [x] testNinthCard
      * - [x] testTenthCard
      * - [x] testEleventhCard
      * - [x] testTwelthCard
      * - [x] testThirteenthCard
      * - [x] testFourteenthCard
      * - [x] testFiftheenCard
      * - [x] testSixteenthCard
      * - [x] testSeventeenthCard
      * - [x] testEighteenthCard
      * - [x] testNineteenthCard
      * - [x] testTwenteenthCard
      * - [x] testTwentyfirstCard
      * - [x] testTwentysecondCard
      * - [x] testTwentythirdCard
      * - [x] testTwentyfourthCard

   * PlayerTest
      * - [x] testFavorTokens
      
   * PropertyTest
      * - [x] testRollDice
      
   * RulesTest
      * - [x] testCouple
      * - [x] testFiveNumbers
      * - [x] testFiveColours
      * - [x] testOneColour
      * - [x] testRowNoColor
      * - [x] testColumnNoColor
      * - [x] testRowNoNumber
      * - [x] testColumnNoNmuber
      * - [x] testDiagonal
      * - [x] testAllWithEmptyCard
   
   * TableTest
      * - [x] testConstructorAndPublicObjects
      * - [x] testingGetters
      * - [x] testDraftPool
      * - [x] testRoundTrack
      * - [x] testSettingWindowAndResetSelection
      
      
**LogicTest**
   * InspectorContextTest
      * - [x] testCheckTools
      * - [x] testCheckPlaceDice
      
   * InspectorContextToolTest
      * - [x] testColour_And_Number
      
   * InspectorPlaceTest
      * - [x] testCheckFirst
      * - [x] testCheckFirstRestrictionsColour
      * - [x] testCheckBoundaryCell
      * - [x] testCheckSecondPlacement
      * - [x] testCheckRightMove
      * - [x] testCheckOutOfBound
      * - [x] testCheckNearWithRestrictions
      * - [x] testCheckNearWrongRestrictionColour
      * - [x] testCheckNearWrongRestrictionNumber
      * - [x] testCheckRightNumberWrongColour
      
   * ModelModifierTest
      * - [x] testLaunchDice
      * - [x] testResetDraftPool
      * - [x] testSpinDice
      * - [x] testNewDice
      * - [x] testPositionDiceFromDraft
      * - [x] testPositionDiceFromWindow
      * - [x] testChangeDiceValue
      
   * PlayerContainerTest
      * - [x] testIterator1
      * - [x] testIterator2
      * - [x] testActivity1
      
      
**TurnTest**
   * ChooseDice1Test
      * - [x] testFirstTurn
      * - [x] testNotFirsRound
      * - [x] testNotOneWay
      
   * PositionDice1Test
      * - [x] testGoingEndTurn
      * - [x] testWrongMove
      * - [x] testWrongMoveNotFirstRound
      
   * StartTurn
      * - [x] testPlacingDice
      
   * ToolCard1Test
      * - [x] testingCardWrongMoves
      * - [x] testinCard
      
   * ToolCard2Test
      * - [x] testingAllowedMoves
      * - [x] testingRightMovesWithRestrictions
      * - [x] testingWrongSelection
      
   * ToolCard3Test
      * - [x] testingAllowedMoves
      * - [x] testingRightMovesWithRestrictions
      * - [x] testingWrongMove
      
   * ToolCard4Test
      * - [x] testingAlloweMoves
      * - [x] testingWrongFirstSelection
      * - [x] testinWrongPositioning
      * - [x] testingWrongSecondSelection
      * - [x] testingWrongSecondPlacement
      
   * ToolCard5Test
      * - [x] testingAllowedMoves
      * - [x] testingFirstSelectionWrong
      * - [x] testingAutomatedOperationListIsRight
      * - [x] testingNotAllowedInFirstRound
      
   * ToolCard6Test
      * - [x] testingAllowedMoves
      * - [x] testingPassingAfterCard
      
   * ToolCard7Test
      * - [x] testingAllowedMoves
      * - [x] testingNotInSecondTurn
      * - [x] testAfterSecondDice
    
   * ToolCard8Test
      * - [x] testAfterSecondDice
   
   * ToolCard9test
      * - [x] testAllowedMoves
      * - [x] testAllowedMovesWithRestrictions
      * - [x] testWrongRestriction
      * - [x] testWrongNumberRestriction
      
   * ToolCard10Test
      * - [x] testAllowedMoves
      
   * ToolCard11Test
      * - [x] testAllowedMoves
      
   * ToolCard12Test
      * - [x] testAllowedMoves
      * - [x] testAllowedMovesAfterPositioning_WithDiceJutsPlaced
      * - [x] testMovingOnlyOneDice
      * - [x] testInFirstRound
      
   * TurnTest
      * - [x] testStartTurn
      
**ViewTest**
   * VirtualViewParserTest
      * - [x] testParsingPlayers
      * - [x] testParsingRestrictions
      * - [x] testParsingDraftPool
      * - [x] testParsingWindowDices
