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


package edu.wit.scds.ds.lists.app.card_game.standard_cards.pile ;

import static edu.wit.scds.ds.lists.app.card_game.universal_base.support.Orientation.FACE_DOWN ;

import edu.wit.scds.ds.lists.app.card_game.standard_cards.card.Card ;
import edu.wit.scds.ds.lists.app.card_game.standard_cards.card.Rank ;
import edu.wit.scds.ds.lists.app.card_game.standard_cards.card.Suit ;
import edu.wit.scds.ds.lists.app.card_game.universal_base.card.UniversalBaseCard ;
import edu.wit.scds.ds.lists.app.card_game.universal_base.support.Orientation ;
import edu.wit.scds.ds.lists.app.card_game.universal_base.support.Persistence ;

/**
 * Representation of a standard deck of cards containing
 * <p>
 * NOTE: You will modify this code
 * <ul>
 * <li>4 suits: Clubs, Diamonds, Hearts, Spades
 * <li>13 ranks: Ace, 2..9, Jack, Queen, King
 * <li>2 Jokers
 * </ul>
 * <p>
 * this is the source of all cards available to the game
 *
 * @author Dave Rosenberg
 *
 * @version 1.0 2025-06-26 Initial implementation (extracted from {@code Deck})
 * @version 1.1 2025-07-13 make the number of playing cards per deck available
 * 
 * @author Your Name
 * 
 * @version 2.0 2025-07-13 modifications for your implementation
 */
public class Deck extends Pile
    {

    /*
     * utility constants
     */
    /** by default, cards added to this deck will be turned face down */
    protected final static Orientation DEFAULT_CARD_ORIENTATION = FACE_DOWN ;

    /** number of Jokers contained in each deck */
    private final static int DEFAULT_JOKER_COUNT = 2 ;
    
    /** number of playing cards in a deck */
    public final static int NUMBER_OF_PLAYING_CARDS_PER_DECK = 4 * // suits
                                                               13 ; // ranks

    
    /*
     * data fields
     */
    // none


    /*
     * constructors
     */


    /**
     * Initialize a deck of cards with default number of Jokers and default card orientation - the
     * cards are in the order as specified by {@link #createPlayingCards()}
     */
    public Deck()
        {

        // instantiate the deck with default card orientation and number of jokers
        this( DEFAULT_CARD_ORIENTATION, DEFAULT_JOKER_COUNT ) ;

        }	// end no-arg constructor


    /**
     * Initialize a deck of cards including a specified number of Jokers and default card
     * orientation<br>
     * the cards are in sorted order
     *
     * @param numberOfJokers
     *     the desired number of jokers to add to the deck
     */
    public Deck( final int numberOfJokers )
        {

        // instantiate the deck with default card orientation and specified number of jokers
        this( DEFAULT_CARD_ORIENTATION, numberOfJokers ) ;

        }  // end 1-arg constructor w/ # of jokers


    /**
     * Initialize a deck of cards including a default number of Jokers and specified card
     * orientation<br>
     * the cards are in sorted order
     *
     * @param initialOrientation
     *     indicate face up or face down
     */
    public Deck( final Orientation initialOrientation )
        {

        // instantiate the deck with specified card orientation and default number of jokers
        this( initialOrientation, DEFAULT_JOKER_COUNT ) ;

        }  // end 1-arg constructor w/ card orientation


    /**
     * Initialize a deck of cards including a specified number of Jokers and card orientation<br>
     * the cards are in sorted order
     *
     * @param initialOrientation
     *     indicate face up or face down
     * @param numberOfJokers
     *     the desired number of jokers to add to the deck
     */
    public Deck( final Orientation initialOrientation,
                 final int numberOfJokers )
        {

        // initialize the pile
        super() ;

        // populate it as a deck
        populateDeck( initialOrientation, numberOfJokers ) ;

        }  // end full/2-arg constructor


    /*
     * public methods
     */
    // none


    /*
     * private utility methods
     */


    /**
     * Add the specified number of Joker cards to the deck
     *
     * @param numberOfJokers
     *     the desired number of Jokers to add to the deck
     */
    private void createJokers( int numberOfJokers )
        {

        while ( numberOfJokers-- > 0 )
            {
            super.cards.add( new Card( Rank.JOKER ) ) ;
            }

        }  // end createJokers()


    /**
     * Instantiate all the playing cards in a new deck
     * <ul>
     * <li>cards are generated by rank within suit
     * <li>jokers are excluded
     * </ul>
     */
    private void createPlayingCards()
        {

        // generate all the cards in the deck
        for ( final Suit suit : Suit.values() )
            {

            // skip placeholder suit
            if ( suit == Suit.NA )
                {
                continue ;
                }

            for ( final Rank rank : Rank.values() )
                {

                // skip non-playing card(s) - Joker
                if ( rank == Rank.JOKER )
                    {
                    continue ;
                    }

                // build a card and save it
                super.cards.add( new Card( rank, suit ) ) ;
                }   // end inner for

            }   // end outer for

        }  // end createPlayingCards()


    /**
     * Initialize a deck of cards including a specified number of Jokers and card orientation<br>
     * the cards are in sorted order
     *
     * @param initialOrientation
     *     indicate face up or face down
     * @param numberOfJokers
     *     the desired number of jokers to add to the deck
     */
    private void populateDeck( final Orientation initialOrientation,
                               final int numberOfJokers )
        {

        // prepare to create the cards
        final Orientation savedOrientation = UniversalBaseCard.setDefaultOrientation( initialOrientation ) ;

        // these are the 'real' cards - mark them as permanent
        final Persistence savedPersistence = UniversalBaseCard.setDefaultPersistence( Persistence.PERMANENT ) ;

        // populate it with all the playing cards
        createPlayingCards() ;

        // add jokers, if any
        createJokers( numberOfJokers ) ;

        // set the default card state back to its prior state
        UniversalBaseCard.setDefaultPersistence( savedPersistence ) ;
        UniversalBaseCard.setDefaultOrientation( savedOrientation ) ;

        // put the cards in a predictable order
        super.sort() ;

        }  // end populateDeck()


    /*
     * for testing/debugging
     */


    /**
     * (optional) test driver
     *
     * @param args
     *     -unused-
     */
    public static void main( final String[] args )
        {
        // TODO Auto-generated method stub

        }	// end main()

    }	// end class Deck