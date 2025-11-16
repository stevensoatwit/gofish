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


package edu.wit.scds.ds.lists.app.card_game.standard_cards.card ;

import static edu.wit.scds.ds.lists.app.card_game.standard_cards.card.Rank.FOUR ;
import static edu.wit.scds.ds.lists.app.card_game.standard_cards.card.Rank.JOKER ;
import static edu.wit.scds.ds.lists.app.card_game.standard_cards.card.Rank.KING ;
import static edu.wit.scds.ds.lists.app.card_game.standard_cards.card.Rank.QUEEN ;
import static edu.wit.scds.ds.lists.app.card_game.standard_cards.card.Suit.DIAMONDS ;
import static edu.wit.scds.ds.lists.app.card_game.standard_cards.card.Suit.HEARTS ;
import static edu.wit.scds.ds.lists.app.card_game.standard_cards.card.Suit.NA ;
import static edu.wit.scds.ds.lists.app.card_game.standard_cards.card.Suit.SPADES ;
import static edu.wit.scds.ds.lists.app.card_game.universal_base.support.Orientation.FACE_DOWN ;
import static edu.wit.scds.ds.lists.app.card_game.universal_base.support.Orientation.FACE_UP ;
import static edu.wit.scds.ds.lists.app.card_game.universal_base.support.Persistence.TEMPORARY ;

import edu.wit.scds.ds.lists.app.card_game.universal_base.card.UniversalBaseCard ;
import edu.wit.scds.ds.lists.app.card_game.universal_base.support.Persistence ;

import java.util.ArrayList ;
import java.util.Collections ;
import java.util.List ;
import java.util.Objects ;

/**
 * Representation of a playing card with a suit and rank - Ace (lowest) to King (highest)
 * <p>
 * The suit and rank are immutable.
 * <p>
 * Note: we will override all superclass methods that our game uses which return a
 * {@link UniversalBaseCard} reference to reduce/eliminate the need to cast to {@code Card},
 * particularly for fluent methods
 *
 * @author Dave Rosenberg
 *
 * @version 1.0 2020-11-19 initial version
 * @version 2.0 2021-12-08
 *     <ul>
 *     <li>add support for face up/down
 *     <li>add {@code matches()}
 *     </ul>
 * @version 2.1 2022-11-06 support dynamic switching to compare cards based on suit and rank or rank
 *     alone
 * @version 2.2 2024-03-26
 *     <ul>
 *     <li>minor cosmetic changes
 *     <li>revise {@code toString()} for greater formatting flexibility and control
 *     </ul>
 * @version 3.0 2025-03-25 track changes to {@code Suit} and addition of {@code Color}
 * @version 4.0 2025-03-30 switch comparison from suit then rank to rank then suit
 * @version 5.0 2025-06-26
 *     <ul>
 *     <li>make subclass of {@code Card} to support different kinds of cards
 *     <li>move general constants, fields, and methods to {@code Card}
 *     <li>track changes to other {@code Card}-related classes
 *     </ul>
 * @version 6.0 2025-07-11
 *     <ul>
 *     <li>track changes to {@code UniversalBaseCard} fka {@code Card}
 *     <li>rename this class from {@code StandardCard} to {@code Card}
 *     <li>eliminate our {@code toString()} because {@code UniversalBaseCard}'s was re-tooled to do
 *     the work
 *     <li>add {@code null}-argument checking
 *     <li>override fluent methods used in our game
 *     </ul>
 * @version 7.0 2025-07-13 remove all inner class {@code Comparator}s
 */
public class Card extends UniversalBaseCard
    {

    /*
     * utility constants
     */

    /** indicate suit should be considered when comparing cards */
    public final static boolean COMPARE_SUIT_AND_RANK = true ;
    /** indicate suit should not be considered when comparing cards */
    public final static boolean COMPARE_RANK_ONLY = false ;


    /*
     * static data
     */


    /** controls whether {@code equals()} and {@code compareTo()} consider suit in calculations */
    private static boolean compareSuit = COMPARE_SUIT_AND_RANK ;


    /*
     * data fields
     */


    /** The card's suit */
    public final Suit suit ;

    /** The card's rank within its suit */
    public final Rank rank ;


    /*
     * constructors
     */


    /**
     * Initialize a card with no suit (e.g., a joker)
     *
     * @param theRank
     *     this card's rank
     */
    public Card( final Rank theRank )
        {

        this( theRank, Suit.NA ) ;

        }   // end 1-arg constructor


    /**
     * Initialize a card with a specified suit and rank
     *
     * @param theRank
     *     this card's rank
     * @param theSuit
     *     this card's suit
     */
    public Card( final Rank theRank, final Suit theSuit )
        {

        Objects.requireNonNull( theSuit, "theSuit" ) ;
        Objects.requireNonNull( theRank, "theRank" ) ;

        this.suit = theSuit ;
        this.rank = theRank ;

        super.setFaceUpText( String.format( "%s%s", this.rank, this.suit ) ) ;

        }   // end 2-arg constructor


    /**
     * create a temporary clone of a card
     *
     * @param sourceCard
     *     the card to copy
     */
    public Card( final Card sourceCard )
        {

        super( TEMPORARY ) ;

        Objects.requireNonNull( sourceCard, "sourceCard" ) ;

        this.suit = sourceCard.suit ;
        this.rank = sourceCard.rank ;

        super.setFaceUpText( String.format( "%s%s", this.rank, this.suit ) ) ;

        }   // end 1-arg 'cloning' constructor


    /*
     * methods to affect face up/down state and display of an instance
     */


    /**
     * Retrieves the current behavior of {@code equals()} and {@code compareTo()} wrt
     * {@code this.suit}
     *
     * @return {@code true} if {@code equals()} and {@code compareTo()} include {@code this.suit};
     *     {@code false} otherwise
     */
    public static boolean getCompareSuit()
        {

        return Card.compareSuit ;

        }  // end getCompareSuit()


    /**
     * Sets the behavior of {@code equals()} and {@code compareTo()} wrt {@code this.suit}
     *
     * @param newCompareRankSuit
     *     the new evaluation behavior wrt {@code this.suit} where {@code true} causes
     *     {@code equals()} and {@code compareTo()} to include {@code this.suit} in their
     *     evaluations; {@code false} won't consider it
     *
     * @return the previous state (consider/ignore)
     */
    public static boolean setCompareSuit( final boolean newCompareRankSuit )
        {

        Objects.requireNonNull( newCompareRankSuit, "newCompareRankSuit" ) ;

        final boolean wasCompareSuit = Card.compareSuit ;

        Card.compareSuit = newCompareRankSuit ;

        return wasCompareSuit ;

        }  // end setCompareSuit()


    @Override
    public Card setFaceDown()
        {

        setOrientation( FACE_DOWN ) ;

        return this ;

        }   // end setFaceDown()


    @Override
    public Card setFaceUp()
        {

        setOrientation( FACE_UP ) ;

        return this ;

        }   // end setFaceUp()


    /*
     * overridden superclass methods
     */


    @Override
    public Card flip()
        {

        return (Card) super.flip() ;

        }   // end flip()


    @Override
    public Card hide()
        {

        return (Card) super.hide() ;

        }   // end hide()


    @Override
    public Card reveal()
        {

        return (Card) super.reveal() ;

        }   // end reveal()


    /*
     * general methods
     */


    @Override
    public int compareTo( final UniversalBaseCard otherCard )
        {

        // if other card is a standard playing card
        // compare rank then suit

        if ( otherCard instanceof final Card otherStandardCard )
            {
            // other card is one of ours

            int cardComparison = this.rank.getAltOrder() -
                                 otherStandardCard.rank.getAltOrder() ;

            // check suit, if necessary
            if ( ( 0 == cardComparison ) &&
                 ( COMPARE_SUIT_AND_RANK == compareSuit ) )
                {
                cardComparison = this.suit.getAltPriority() -
                                 otherStandardCard.suit.getAltPriority() ;
                }

            return cardComparison ;
            }

        // other card is not one of ours or is null (will cause NullPointerException)

        throw new IllegalArgumentException( String.format( "other card must be a %s but is a %s",
                                                           this.getClass()
                                                               .getSimpleName(),
                                                           otherCard.getClass()
                                                                    .getSimpleName() ) ) ;

        }	// end compareTo()


    @Override
    public boolean equals( final Object otherObject )
        {

        // same object?
        if ( this == otherObject )
            {
            return true ;
            }

        // another StandardCard? false if otherObject is null
        if ( otherObject instanceof final Card otherCard )
            {
            return compareTo( otherCard ) == 0 ;
            }

        // not one of ours so can't match
        return false ;

        }	// end equals()


    @Override
    public int hashCode()
        {

        return Objects.hash( this.rank,
                             COMPARE_SUIT_AND_RANK == compareSuit
                                 ? this.suit
                                 : 0 ) ;

        }   // end hashCode()


    /*
     * inner classes
     */
    // none


    /*
     * for testing/debugging
     */


    /**
     * Sample demo program
     *
     * @param args
     *     -unused-
     */
    public static void main( final String[] args )
        {

        final Suit[] suits = Suit.values() ;
        final Rank[] ranks = Rank.values() ;

        final List<Card> cards = new ArrayList<>( suits.length *
                                                  ranks.length ) ;

        System.out.printf( "Key: ranks are Ace (1) .. King (13)%n%n" ) ;

        // generate a deck of cards
        System.out.printf( "New cards:%n" ) ;

        // make them permanent
        setDefaultPersistence( Persistence.PERMANENT ) ;

        for ( final Suit suit : suits )
            {

            // skip placeholder suit
            if ( NA == suit )
                {
                continue ;
                }

            for ( final Rank rank : ranks )
                {

                // skip non-playing card(s) - Joker
                if ( JOKER == rank )
                    {
                    continue ;
                    }

                // build a card
                final Card newCard = new Card( rank, suit ) ;
                System.out.printf( " %s", newCard ) ;

                // keep track of it
                cards.add( newCard ) ;
                }

            }

        // rest card permanence
        resetDefaultPersistence() ;

        // turn top card over
        cards.getFirst().flip() ;

        // display all the cards
        System.out.printf( "%n%nAll cards:%n%s%n%n", cards.toString() ) ;

        // turn all cards face up
        for ( final Card aCard : cards )
            {
            aCard.reveal() ;
            }

        // display all the cards
        System.out.printf( "%n%nAll cards:%n%s%n%n", cards.toString() ) ;

        // shuffled
        Collections.shuffle( cards ) ;
        System.out.printf( "%nShuffled:%n%s%n%n", cards.toString() ) ;

        // sorted
        Collections.sort( cards ) ;
        System.out.printf( "%nSorted (rank and suit):%n%s%n%n",
                           cards.toString() ) ;

        // sort only on rank
        setCompareSuit( COMPARE_RANK_ONLY ) ;

        // shuffled
        Collections.shuffle( cards ) ;
        System.out.printf( "%nShuffled:%n%s%n%n", cards.toString() ) ;

        // sorted
        Collections.sort( cards ) ;
        System.out.printf( "%nSorted (rank only):%n%s%n%n", cards.toString() ) ;

        // sorted
        Collections.sort( cards ) ;
        System.out.printf( "%nSorted (rank only):%n%s%n%n", cards.toString() ) ;

        // sort on rank and suit
        setCompareSuit( COMPARE_SUIT_AND_RANK ) ;

        // sorted
        Collections.sort( cards ) ;
        System.out.printf( "%nSorted (rank and suit):%n%s%n%n",
                           cards.toString() ) ;


        // compare some cards against each other
        Card card1 = cards.get( 2 ) ;
        Card card2 = cards.get( 3 ) ;
        System.out.printf( "%s.compareTo(%s) = %+,d (rank and suit)%n",
                           card1,
                           card2,
                           card1.compareTo( card2 ) ) ;
        System.out.printf( "%s.compareTo(%s) = %+,d (rank and suit)%n",
                           card2,
                           card1,
                           card2.compareTo( card1 ) ) ;

        card1 = cards.get( 15 ) ;
        card2 = cards.get( 43 ) ;
        System.out.printf( "%s.compareTo(%s) = %+,d (rank and suit)%n",
                           card1,
                           card2,
                           card1.compareTo( card2 ) ) ;


        card2 = cards.get( 4 ) ;
        System.out.printf( "%s.compareTo(%s) = %+,d (rank and suit)%n",
                           card1,
                           card2,
                           card1.compareTo( card2 ) ) ;

        card2 = cards.get( 20 ) ;
        System.out.printf( "%s.compareTo(%s) = %+,d (rank and suit)%n",
                           card1,
                           card2,
                           card1.compareTo( card2 ) ) ;


        System.out.printf( "%n" ) ;
        card1 = cards.get( 2 ) ;
        card2 = cards.get( 3 ) ;
        System.out.printf( "%s.equals(%s) = %b (rank and suit)%n",
                           card1,
                           card2,
                           card1.equals( card2 ) ) ;
        System.out.printf( "%s.equals(%s) = %b (rank and suit)%n",
                           card1,
                           card1,
                           card1.equals( card1 ) ) ;
        System.out.printf( "%s.equals(%s) = %b (rank and suit)%n",
                           card2,
                           card1,
                           card2.equals( card1 ) ) ;
        System.out.printf( "%s == %s = %b (rank and suit)%n",
                           card1,
                           card2,
                           card1 == card2 ) ;

        System.out.printf( "%ncreating temporary cards%n" ) ;
        UniversalBaseCard.setDefaultOrientation( FACE_UP ) ;
        card1 = new Card( FOUR, DIAMONDS ) ;
        card2 = new Card( FOUR, HEARTS ) ;
        System.out.printf( "%s.equals(%s) = %b (rank and suit)%n",
                           card1,
                           card2,
                           card1.equals( card2 ) ) ;


        System.out.printf( "%n" ) ;
        System.out.printf( "%s.matches(%s) = %b (rank and suit)%n",
                           card1,
                           card1,
                           card1.matches( card1 ) ) ;
        System.out.printf( "%s.matches(%s) = %b (rank and suit)%n",
                           card1,
                           card2,
                           card1.matches( card2 ) ) ;
        System.out.printf( "%s == %s = %b (rank and suit)%n",
                           card1,
                           card2,
                           card1 == card2 ) ;

        UniversalBaseCard.setDefaultOrientation( FACE_UP ) ;
        card1 = new Card( FOUR, DIAMONDS ) ;
        card2 = new Card( FOUR, HEARTS ) ;
        System.out.printf( "%s.matches(%s) = %b (rank and suit)%n",
                           card1,
                           card2,
                           card1.matches( card2 ) ) ;


        // repeat comparisons without considering suit
        setCompareSuit( COMPARE_RANK_ONLY ) ;

        System.out.printf( "%n" ) ;

        // compare some cards against each other
        card1 = cards.get( 15 ) ;
        card2 = cards.get( 43 ) ;
        System.out.printf( "%s.compareTo(%s) = %+,d (rank only)%n",
                           card1,
                           card2,
                           card1.compareTo( card2 ) ) ;

        card2 = cards.get( 4 ) ;
        System.out.printf( "%s.compareTo(%s) = %+,d (rank only)%n",
                           card1,
                           card2,
                           card1.compareTo( card2 ) ) ;

        card2 = cards.get( 20 ) ;
        System.out.printf( "%s.compareTo(%s) = %+,d (rank only)%n",
                           card1,
                           card2,
                           card1.compareTo( card2 ) ) ;


        System.out.printf( "%n" ) ;
        System.out.printf( "%s.equals(%s) = %b (rank only)%n",
                           card1,
                           card1,
                           card1.equals( card1 ) ) ;
        System.out.printf( "%s.equals(%s) = %b (rank only)%n",
                           card1,
                           card2,
                           card1.equals( card2 ) ) ;


        setCompareSuit( COMPARE_SUIT_AND_RANK ) ;

        UniversalBaseCard.setDefaultOrientation( FACE_UP ) ;
        card1 = new Card( FOUR, DIAMONDS ) ;
        card2 = new Card( FOUR, HEARTS ) ;
        System.out.printf( "%s.equals(%s) = %b (rank and suit only)%n",
                           card1,
                           card2,
                           card1.equals( card2 ) ) ;


        UniversalBaseCard.setDefaultOrientation( FACE_UP ) ;
        card1 = new Card( QUEEN, DIAMONDS ) ;
        card2 = new Card( QUEEN, HEARTS ) ;
        System.out.printf( "%s.equals(%s) = %b (rank and suit only)%n",
                           card1,
                           card2,
                           card1.equals( card2 ) ) ;
        System.out.printf( "%s.equals(%s) = %b (rank and suit only)%n",
                           card2,
                           card1,
                           card2.equals( card1 ) ) ;


        UniversalBaseCard.setDefaultOrientation( FACE_UP ) ;
        card1 = new Card( KING, DIAMONDS ) ;
        card2 = new Card( QUEEN, SPADES ) ;
        System.out.printf( "%s.equals(%s) = %b (rank and suit only)%n",
                           card1,
                           card2,
                           card1.equals( card2 ) ) ;
        System.out.printf( "%s.equals(%s) = %b (rank and suit only)%n",
                           card2,
                           card1,
                           card2.equals( card1 ) ) ;


        setCompareSuit( COMPARE_RANK_ONLY ) ;

        System.out.printf( "%n" ) ;
        System.out.printf( "%s.matches(%s) = %b (rank only)%n",
                           card1,
                           card1,
                           card1.matches( card1 ) ) ;
        System.out.printf( "%s.matches(%s) = %b (rank only)%n",
                           card1,
                           card2,
                           card1.matches( card2 ) ) ;

        UniversalBaseCard.setDefaultOrientation( FACE_UP ) ;
        card1 = new Card( FOUR, DIAMONDS ) ;
        card2 = new Card( FOUR, HEARTS ) ;
        System.out.printf( "%s.matches(%s) = %b (rank only)%n",
                           card1,
                           card2,
                           card1.matches( card2 ) ) ;

        }	// end main()

    }	// end class Card