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
import edu.wit.scds.ds.lists.app.card_game.universal_base.pile.UniversalBasePile ;
import edu.wit.scds.ds.lists.app.card_game.universal_base.support.Orientation ;


/**
 * Representation of a pile of standard playing cards
 * <p>
 * NOTE: You probably won't modify this code
 * <p>
 * the top card is at position 0
 *
 * @author Dave Rosenberg
 *
 * @version 1.0 2025-06-26 extracted standard playing card-specific methods from {@code Pile}
 * @version 2.0 2025-07-11
 *     <ul>
 *     <li>track changes to previous {@code common} package, now {@code universal_base}
 *     <li>rename from {@code StandardPile} to {@code Pile}
 *     </ul>
 * 
 * @author Your Name
 * 
 * @version 3.0 2025-07-13 modifications for your implementation
 */
public abstract class Pile extends UniversalBasePile
    {

    /*
     * utility constants
     */
    /** by default, cards added to this pile will be turned face down */
    private final static Orientation DEFAULT_CARD_ORIENTATION = FACE_DOWN ;


    /*
     * data fields
     */
    // none


    /*
     * constructors
     */


    /**
     * Initialize the pile with cards placed face down by default
     *
     * @since 3.0
     */
    protected Pile()
        {

        this( DEFAULT_CARD_ORIENTATION ) ;

        }	// end no-arg constructor


    /**
     * Initialize the pile with cards placed face up/down as specified by default
     *
     * @param initialOrientation
     *     specify whether cards will be face up or down by default
     *
     * @since 3.0
     */
    protected Pile( final Orientation initialOrientation )
        {

        super( initialOrientation ) ;

        }	// end 1-arg constructor


    /*
     * public methods
     */


    @Override
    public Card getCardLike( final UniversalBaseCard likeCard )
        {

        return (Card) super.getCardLike( likeCard ) ;

        }  // end getCardLike() given a Card


    /**
     * Retrieve a specific card from the pile by card - the card is not removed from the pile
     *
     * @param rank
     *     the rank of the desired card
     * @param suit
     *     the suit of the desired card
     *
     * @return the specified card or {@code null} if the card isn't in the pile
     *
     * @since 1.0
     */
    public Card getCardLike( final Rank rank,
                             final Suit suit )
        {

        return getCardLike( new Card( rank, suit ) ) ;

        }  // end getCardLike() given rank and suit


    /**
     * Remove all instances of a specific card from the pile
     *
     * @param lookupCard
     *     the card to be removed
     *
     * @return a new pile containing the removed cards, if any
     *
     * @since 1.0
     */
    public Pile removeAllMatchingCards( final Card lookupCard )
        {

        class tempPile extends Pile
            { /* temporary collection type */ }

        final Pile removedCards = new tempPile() ;

        super.removeAllMatchingCards( lookupCard, removedCards ) ;

        return removedCards ;

        }  // end removeAllMatchingCards()


    @Override
    public Card removeCard( final UniversalBaseCard card )
        {

        return (Card) super.removeCard( card ) ;

        }  // end removeCard() given a card


    /**
     * Remove a specific card from the pile by card
     *
     * @param rank
     *     rank of the card to be removed
     * @param suit
     *     suit of the card to be removed
     *
     * @return the specified card or {@code null} if the card isn't in the pile
     *
     * @since 1.0
     */
    public Card removeCard( final Rank rank,
                            final Suit suit )
        {

        return removeCard( new Card( rank, suit ) ) ;

        }  // end removeCard() given a suit and rank


    @Override
    public Card removeCardAt( final int position )
        {

        return (Card) super.removeCardAt( position ) ;

        }  // end removeCardAt()


    @Override
    public Card removeTopCard()
        {

        return (Card) super.removeTopCard() ;

        }  // end removeTopCard()


    @Override
    public Pile setDefaultFaceDown()
        {

        super.setDefaultFaceDown() ;

        return this ;

        }   // end setFaceDown()


    @Override
    public Pile setDefaultFaceUp()
        {

        super.setDefaultFaceUp() ;

        return this ;

        }   // end setFaceUp()


    /*
     * utility methods
     */
    // none


    /*
     * utility classes
     */
    // none


    /*
     * testing/debugging
     */
    // none

    }	// end class Pile