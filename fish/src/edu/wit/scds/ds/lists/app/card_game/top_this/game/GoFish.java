/* @formatter:off
 *
 * © David M Rosenberg
 * Summer 2025
 *
 * COMP 2000 ~ Data Structures
 * Demonstration: List App ~ Card Game
 *
 * Usage restrictions:
 *
 * You may use this code for exploration, experimentation, and furthering your
 * learning for this course. You may not use this code for any other
 * assignments, in my course or elsewhere, without explicit permission, in
 * advance, from myself (and the instructor of any other course).
 *
 * Further, you may not post (including in a public repository such as on github)
 * nor otherwise share this code with anyone other than current students in my
 * sections of this course
 *
 * Violation of these usage restrictions will be considered a violation of
 * Wentworth Institute of Technology's Academic Honesty Policy.  Unauthorized posting
 * or use of this code may also be considered copyright infringement and may subject
 * the poster and/or the owners/operators of said websites to legal and/or financial
 * penalties.  Students are permitted to store this code in a private repository
 * or other private cloud-based storage.
 *
 * Do not modify or remove this notice.
 *
 * @formatter:on
 */


package edu.wit.scds.ds.lists.app.card_game.top_this.game ;

import static edu.wit.scds.ds.lists.app.card_game.standard_cards.card.Rank.JOKER ;

import edu.wit.scds.ds.lists.app.card_game.standard_cards.card.Card ;
import edu.wit.scds.ds.lists.app.card_game.standard_cards.card.Rank ;
import edu.wit.scds.ds.lists.app.card_game.standard_cards.card.Suit ;
import edu.wit.scds.ds.lists.app.card_game.standard_cards.pile.Deck ;
import edu.wit.scds.ds.lists.app.card_game.standard_cards.pile.Pile ;
import edu.wit.scds.ds.lists.app.card_game.top_this.pile.DiscardPile ;
import edu.wit.scds.ds.lists.app.card_game.top_this.pile.Meld ;
import edu.wit.scds.ds.lists.app.card_game.top_this.pile.Stock ;

import java.util.ArrayList ;
import java.util.LinkedList ;
import java.util.List ;
import java.util.ListIterator ;
import java.util.Scanner ;


/**
 * NOTE: You will modify this code
 * <p>
 * NOTE This is a sample, fictitious card game
 * <p>
 * This is the main driver for the game of Top This. It supports 3 or more players. Players take
 * turns using a simple character cell console interface.
 * <p>
 * Goal: to collect the most melds
 * <p>
 * Rules:
 * <ul>
 * <li>use 1 or more decks of standard playing cards (52 cards per deck, 4 suits, 13 ranks, no
 * jokers) as specified by the players
 * <li>3 or more players are each dealt the same number of cards (as specified by the players), one
 * at a time, in rotation
 * <li>the players specify the number of rounds
 * <li>the first player dealt a card is the first to play in the first round
 * <li>in subsequent rounds, the first player is the second player from the previous round
 * <li>a round is
 * <ul>
 * <li>each player selects one card from their hand and in turn places it face down on the table
 * <li>once all players have played a card, the played cards are turned face up
 * <li>if there is a single highest card, whichever player played it takes all the cards and saves
 * them as a meld
 * <li>if two or more players played the highest card, there is no winner for that round and the
 * cards are placed face down on the discard pile
 * </ul>
 * <li>
 * <li>a player wins the round when their card is the highest played in that round
 * <li>if there is no winner in a round (2 or more players have the highest card), the cards are
 * discarded
 * <li>the cards won during a round are given to the player with the highest card and saved as a
 * meld
 * <li>the game ends when the specified number of rounds have been played
 * <li>whichever player has the most melds at the end of the game wins
 * </ul>
 * <p>
 * NOTE You must rename this class to whatever your game is called. If the name of the game begins
 * with a number, spell out the number (can't start a class name with a digit). Replace the comments
 * above describing my game with appropriate comments for yours.
 *
 * @author Dave Rosenberg
 *
 * @version 1.0 2025-03-27 Initial implementation
 * @version 2.0 2025-06-28 track changes to other classes
 * 
 * @author Your Name
 * 
 * @version 3.0 2025-07-13 modifications for your implementation
 */
public class GoFish
    {
    /*
     * constants
     */

    /** can't play with fewer than this many decks at an absolute minimum */
    private final static int MINIMUM_NUMBER_OF_DECKS = 1 ;

    /** can't play with fewer than this many players at an absolute minimum */
    private final static int MINIMUM_PLAYER_COUNT = 2 ;
    

    /*
     * data fields
     */

    private final List<Player> players ;
    private int numberOfPlayers ;
    private int totalNumPairs ;
    
    private int roundNumber ;

    private final Scanner scanner ;

    private Deck deck;
    private  Stock stock ;
    private final List<DiscardPile> pointPiles ;

    private boolean running = false ;


    /*
     * constructors
     */


    /**
     * set up the game instance
     *
     * @param input
     *     used for player interactions
     *
     * @since 1.0
     */
    private GoFish( final Scanner input )
        {

        this.running = false ;

        this.players = new ArrayList<>() ;  // indexing is O(1)
        this.numberOfPlayers = -1 ;

        this.roundNumber = 0 ;
        this.totalNumPairs = 0;

        this.scanner = input ;
        
        this.stock = new Stock() ;

        this.pointPiles = new ArrayList<>() ;

        this.deck = new Deck() ;   // indexing is O(1)

        }   // end constructor


    /*
     * game driver
     */


    /**
     * This is the top-level driver for the game of Top This.
     *
     * @param args
     *     -unused-
     *
     * @since 1.0
     */
    public static void main( final String[] args )
        {
        
        try ( final Scanner input = new Scanner( System.in ) ; )
            {
                final GoFish goFish = new GoFish( input );
                
                goFish.setup();
                goFish.doTurns( input );
                goFish.displayWinners();
                
            }   // end try (input)
            
            
        }   // end main()
    
    /**
     * 
     *
     * @since 1.0
     */
    private void displayWinners()
        {

        ArrayList<Player> winners = new ArrayList<>();
        winners.add( this.players.getFirst() );
 
        for(Player player: this.players) {
            if( player.getNumPairs() > winners.get( 0 ).getNumPairs() ) {
                winners.clear();
                winners.add( player );
            } else if ( player.getNumPairs() == winners.get( 0 ).getNumPairs() ) {
                winners.add( player );
            } // end if-else

        } // end for
        StringBuilder wins = new StringBuilder("Game over, winner(s): ") ;
        for(Player winner: winners) {
            wins.append( winner.name );
        }
        System.out.println(wins);
        

        }


    private void doTurns(Scanner input) {
        do {
        for(Player player: this.players) {
            takeTurn( player, input ) ;
        }
        
        this.roundNumber++ ;
        
    } while( this.totalNumPairs < 26 );
        
    } // end doTurns
    // Setup game
    private void setup() {
        // we're not set up yet but we're on our way
        this.running = true ;   // input methods will set this false based upon user input
        
        
        // configure the game components
        
        configurePlayers() ;
    
        if ( !this.running )
        {
        return ;
        }
        
        configureCards() ;

        if ( !this.running )
            {
            return ;
            }
        
        dealHands();
    }

    /**
     * deal hands to all players
     * 
     * @since 2.0
     */
    private void dealHands()
        {

        // deal one card to each player in turn
        for ( int i = 1 ; i <= 5 ; i++ )
            {

            for ( final Player aPlayer : this.players )
                {
                final Card dealt = this.stock.drawTopCard().hide() ;
                aPlayer.dealtACard( dealt ) ;
                }

            }

        }   // end dealHands()
    
    /**
     * determine the number of decks, create them, and populate the stock from them
     *
     * @since 2.0
     */
    private void configureCards()
        {
        
        // open the appropriate number of decks (no jokers) and put the cards into the stock
        getCardsFromDeck() ;
        
        // shuffle the cards
        //this.stock.shuffle() ;

        }   // end configureCards()
    
    /**
     * populate stock from all playing cards (excludes jokers) from one or more decks
     *
     * @since 1.0
     */
    private void getCardsFromDeck()
        {

        // populate the stock from the deck

        // NOTE we're determining the maximum # of cards per player based on the # of cards in all
        // the decks
        // NOTE alternative: determine minimum # of decks based upon the # of hands and # of
        // cards/hand

        final Card joker = new Card( JOKER ) ;    // for lookup

        // 'open' a 'box' of cards
        final Deck newDeck = new Deck() ;

        // pull out the jokers
        final Pile jokers = newDeck.removeAllMatchingCards( joker ) ;

        // add the playing cards to the stock
        this.stock.moveCardsToBottom( newDeck ) ;

        // put the jokers back in the 'box'
        newDeck.moveCardsToBottom( jokers ) ;

        // turn the jokers face up
        newDeck.revealAll() ;

        // keep the 'box'
        this.deck = newDeck ;

        // assertion: each deck in this.decks have all cards that won't be used during game play

        // assertion: this.stock contains all cards to be used during game play

        }   // end getCardsFromDecks()
    
    /**
     * determine the number of players and set up for play
     * 
     * @since 2.0
     */
    private void configurePlayers()
        {

        // find out how many players

        do
            {
            this.numberOfPlayers = promptForInt( "How many players (minimum %,d)?",
                                                 MINIMUM_PLAYER_COUNT ) ;

            if ( !this.running )
                {
                return ;
                }

            }
        while ( this.numberOfPlayers < MINIMUM_PLAYER_COUNT ) ;

        
        // create the players

        for ( int i = 1 ; i <= this.numberOfPlayers ; i++ )
            {
            String playerName = promptForLine( String.format( "%nWhat is the name of player %,d?",
                                                              i ) ) ;

            if ( !this.running )
                {
                return ;
                }

            this.players.add( new Player( playerName ) ) ;
            }
        
        }   // end configurePlayers()
    
    /**
     * displays a formatted prompt
     *
     * @param prompt
     *     the prompt with optional formatting specifiers
     * @param arguments
     *     argument(s) used by the formatting specifiers
     *
     * @since 1.0
     */
    private static void displayPrompt( final String prompt,
                                       final Object... arguments )
        {

        System.out.printf( "%s ", String.format( prompt, arguments ) ) ;

        }   // end displayPrompt()

    /**
     * prompts the user for a positive integer value greater than 0
     *
     * @param prompt
     *     the prompt with optional formatting specifiers
     * @param arguments
     *     argument(s) used by the formatting specifiers
     *
     * @return the integer value as specified by the user or -1 if no more input is available
     *
     * @since 1.0
     */
    private int promptForInt( final String prompt,
                              final Object... arguments )
        {

        do
            {
            displayPrompt( prompt, arguments ) ;

            if ( this.scanner.hasNextInt() )    // have an int?
                {
                final int inputValue = this.scanner.nextInt() ;
                
                if ( inputValue > 0 )   // have an int, make sure it's positive
                    {
                    // clear out anything left in the scanner's buffer on the current line
                    this.scanner.nextLine() ;
                    
                    return inputValue ;
                    }
                }

            if ( !this.scanner.hasNext() )  // no more input available?
                {
                this.running = false ;

                return -1 ;
                }

            // assertion: there's more input available but the next token isn't an int
            
            if ( ".".equals( this.scanner.next() ) )    // skip the noise
                {
                this.running = false ;
                
                return -1 ;
                }
            
            }
        while ( true ) ;    // try again

        }   // end promptForInt()


    /**
     * prompts the user for a line of text
     *
     * @param prompt
     *     the prompt with optional formatting specifiers
     * @param arguments
     *     argument(s) used by the formatting specifiers
     *
     * @return the non-empty line of text as specified by the user with leading and trailing
     *     whitespace removed or null if no more input available
     *
     * @since 1.0
     */
    private String promptForLine( final String prompt,
                                  final Object... arguments )
        {

        String response = "" ;
        String compressedResponse = "" ;

        do
            {
            displayPrompt( prompt, arguments ) ;

            if ( !this.scanner.hasNextLine() )  // no more input available?
                {
                this.running = false ;

                return null ;
                }
            

            // get the line
            response = this.scanner.nextLine().trim() ;

            // make sure we got something other than whitespace
            compressedResponse = response.replace( " ", "" )
                                         .replace( "\t", "" ) ;
            
            // quit?
            if ( ".".equals( compressedResponse ) )
                {
                this.running = false ;

                return null ;
                }
            
            }
        while ( "".equals( compressedResponse ) ) ;

        // assertion: we have the user's trimmed input (no leading or trailing whitespace
        
        return response ;

        }   // end promptForLine()
    
    private void takeTurn(Player player, Scanner input) {
    
        if(player.getHandSize() > 0) {
            while( player.hasPairs() != null) {
                player.makePair(player.hasPairs());
                this.totalNumPairs++ ;
            } // end while
            
            System.out.println( player.revealHand() );
            
            System.out.printf("Select player to target (1-" + this.numberOfPlayers + "): ");
            int target = input.nextInt() - 1;
            
            if(target < 0 || target > this.numberOfPlayers) {
                System.out.println("Error, target selection outside range.");
                takeTurn(player, input);
            } // end if
            
            System.out.printf("Select rank (that you have) to seek (1 - 13): ");
            int rank = input.nextInt();
            
            if(rank < 1 || rank > 13) {
                System.out.println("Error, rank selection outside range.");
                takeTurn(player, input);
            } else if ( ! player.hasCardOfRank(rank)) {
                System.out.println("Error, card not in your hand.");
                takeTurn(player, input);
            } // end if-else
            
            if( this.players.get( target ).hasCardOfRank( rank ) ) {
            
                player.dealtACard( this.players.get( target ).popFirstOfRank( rank ) );
                System.out.println("Card aquired from target");
                takeTurn(player, input);
                
            } else if ( ! this.stock.isEmpty() ){
            
                System.out.println("Target did not have card, drawing a new one.");
                final Card dealt = this.stock.drawTopCard().hide() ;
                player.dealtACard( dealt ) ;
                
                if( Player.getUniCardRank( dealt ) == rank ) {
                    System.out.println("Card aquired from deck");
                    takeTurn(player, input);
                } // end if
                
            } else {
                System.out.println("No remaining cards to draw.");
            } // end if-else
            
        } else {
            System.out.println("No cards left in your hand.");
        } // end if-else
        
        while( player.hasPairs() != null) {
            player.makePair(player.hasPairs());
            this.totalNumPairs++ ;
        } // end while
        
        System.out.println("Turn over.");
        
    } // end takeTurn()
    
    }   // end class TopThis