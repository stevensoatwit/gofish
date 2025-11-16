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
    private final static int MINIMUM_PLAYER_COUNT = 3 ;
    

    /*
     * data fields
     */

    private final List<Player> players ;
    private int numberOfPlayers ;

    private int roundNumber ;

    private final Scanner scanner ;

    private final Deck deck;
    private final Stock stock ;
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

        this.scanner = input ;

        this.stock = new Stock() ;

        this.pointPiles = new ArrayList<DiscardPile>() ;

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
            
            }   // end try (input)

        }   // end main()
    
    // Setup game
    private void setup() {
        
    }

    }   // end class TopThis