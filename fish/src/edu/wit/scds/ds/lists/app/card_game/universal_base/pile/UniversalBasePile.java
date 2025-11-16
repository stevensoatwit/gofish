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


package edu.wit.scds.ds.lists.app.card_game.universal_base.pile ;

import static edu.wit.scds.ds.lists.app.card_game.universal_base.support.Orientation.AS_IS ;
import static edu.wit.scds.ds.lists.app.card_game.universal_base.support.Persistence.PERMANENT ;
import static edu.wit.scds.ds.lists.app.card_game.universal_base.support.Persistence.TEMPORARY ;
import static edu.wit.scds.ds.lists.app.card_game.universal_base.support.Persistence.UNRESTRICTED ;

import edu.wit.scds.ds.lists.app.card_game.universal_base.card.UniversalBaseCard ;
import edu.wit.scds.ds.lists.app.card_game.universal_base.support.NoCardsException ;
import edu.wit.scds.ds.lists.app.card_game.universal_base.support.Orientation ;
import edu.wit.scds.ds.lists.app.card_game.universal_base.support.Persistence ;

import java.util.Collections ;
import java.util.Iterator ;
import java.util.LinkedList ;
import java.util.List ;
import java.util.ListIterator ;
import java.util.Objects ;


/**
 * Representation of a pile of cards
 * <p>
 * NOTE: You probably won't modify this code
 * <p>
 * the top card is at position 0
 *
 * @author Dave Rosenberg
 *
 * @version 1.0 2021-12-08 Initial implementation
 * @version 2.0 2025-03-26
 *     <ul>
 *     <li>change to {@code abstract} class so games must be built with appropriate subclasses of
 *     {@code Pile}
 *     <li>remove no-arg constructor
 *     </ul>
 * @version 3.0 2025-03-30
 *     <ul>
 *     <li>switch from {@code ArrayList} to {@code LinkedList} to improve efficiency to add/remove
 *     at both ends of the list (top and bottom) with O(1) efficiency
 *     <li>remove initial capacity argument from constructor - no longer needed/relevant
 *     <li>major cleanup and restructuring
 *     <li>move {@code shuffle()} here from {@code Deck}
 *     </ul>
 * @version 4.0 2025-06-26
 *     <ul>
 *     <li>track changes to other classes to support different types of card decks
 *     <li>move standard playing deck-specific methods to {@code Pile}
 *     <li>add various constructors and methods
 *     <li>make most instance methods fluent
 *     </ul>
 * @version 5.0 2025-07-12
 *     <ul>
 *     <li>rename from {@code Pile} to {@code UniversalBasePile} for consistency with {@code Card}
 *     <li>add {@code null}-argument checking
 *     <li>add methods to manipulate the default orientation
 *     <li>support {@code AS-IS} default orientation
 *     <li>rename some methods to improve consistency
 *     <li>add tooling to optionally restrict cards based upon their persistence
 *     </ul>
 *
 * @author Your Name
 * 
 * @version 8.0 2025-07-13 only modify this tag if you modified the code
 */
public abstract class UniversalBasePile implements Iterable<UniversalBaseCard>
    {

    /*
     * constants
     */


    /** by default, leave the orientation for {@code Card}s added to us unchanged */
    protected final static Orientation DEFAULT_ORIENTATION = AS_IS ;

    /** by default, piles will only accept permanent cards */
    protected final static Persistence DEFAULT_ACCEPTABLE_CARD_PERSISTENCE = PERMANENT ;


    /*
     * data fields
     */


    /** the list of cards where the top is in position 0 and the bottom is the highest position */
    protected List<UniversalBaseCard> cards ;

    /** indicate whether added cards should be face up or down or left as-is by default */
    protected Orientation defaultOrientation ;

    /** restrict to permanent or temporary, or accept both - can only be set during instantiation */
    private Persistence acceptablePersistence ;


    /*
     * constructors
     */


    /**
     * Initialize the pile with default configuration
     *
     * @since 3.0
     */
    protected UniversalBasePile()
        {

        this.cards = new LinkedList<>() ;

        resetDefaultOrientation() ;

        setAcceptablePersistence( DEFAULT_ACCEPTABLE_CARD_PERSISTENCE ) ;

        }	// end no-arg constructor


    /**
     * Initialize the pile with added cards placed face up/down/as-is by default
     *
     * @param initialOrientation
     *     specify the default orientation for newly added cards
     *
     * @since 3.0
     */
    protected UniversalBasePile( final Orientation initialOrientation )
        {

        this() ;

        setDefaultOrientation( initialOrientation ) ;

        }	// end 1-arg constructor w/ orientation


    /**
     * initialize this pile with added cards required to be compatible with the specified
     * persistence
     *
     * @param persistenceSetting
     *     specify whether this pile will only accept permanent, temporary, or any cards
     *
     * @since 3.0
     */
    protected UniversalBasePile( final Persistence persistenceSetting )
        {

        this() ;

        setAcceptablePersistence( persistenceSetting ) ;

        }	// end 1-arg constructor w/ persistence


    /**
     * initialize the pile and populate it with provide {@code Card}s
     *
     * @param sourcePile
     *     if non-{@code null}, any {@code Card}s it contains will be moved to this {@code Pile}
     *
     * @since 4.0
     */
    protected UniversalBasePile( final UniversalBasePile sourcePile )
        {

        this() ;

        if ( sourcePile != null )
            {
            moveCardsToBottom( sourcePile ) ;
            }

        }   // end 1-arg constructor w/ source pile


    /**
     * initialize the pile and populate it with provided {@code Card}s
     *
     * @param sourcePile
     *     if non-{@code null}, any {@code Card}s it contains will be moved to this {@code Pile}
     * @param initialOrientation
     *     specify whether cards will be face up or down by default
     *
     * @since 4.0
     */
    protected UniversalBasePile( final UniversalBasePile sourcePile,
                                 final Orientation initialOrientation )
        {

        this( initialOrientation ) ;    // checks for null argument

        if ( sourcePile != null )
            {
            moveCardsToBottom( sourcePile ) ;
            }

        }   // end 2-arg constructor w/ source pile and orientation


    /*
     * public methods
     */


    /**
     * Add a single card at a specified position
     *
     * @param newCard
     *     the card to add - will be turned face up/down if necessary
     * @param position
     *     the 0-based position for the card where 0 is the top
     *
     * @return this pile (fluent)
     *
     * @throws IndexOutOfBoundsException
     *     if the position is invalid given the current card count
     *
     * @since 3.0
     */
    public UniversalBasePile addAtPosition( final UniversalBaseCard newCard,
                                            final int position )
        throws IndexOutOfBoundsException
        {

        prepareCardToAdd( newCard ) ;   // checks for null argument

        this.cards.add( position, newCard ) ;

        return this ;

        }  // end addAt()


    /**
     * Add a single card to the bottom of the pile
     *
     * @param newCard
     *     the card to add - will be turned face up/down if necessary
     *
     * @return this pile (fluent)
     *
     * @since 3.0
     */
    public UniversalBasePile addToBottom( final UniversalBaseCard newCard )
        {

        prepareCardToAdd( newCard ) ;   // checks for null argument

        this.cards.addLast( newCard ) ;

        return this ;

        }  // end addToBottom()


    /**
     * Add a single card to the top of the pile
     *
     * @param newCard
     *     the card to add - will be turned face up/down if necessary
     *
     * @return this pile (fluent)
     *
     * @since 3.0
     */
    public UniversalBasePile addToTop( final UniversalBaseCard newCard )
        {

        prepareCardToAdd( newCard ) ;   // checks for null argument

        this.cards.addFirst( newCard ) ;

        return this ;

        }  // end addToTop()


    /**
     * Retrieve the current number of cards in the pile
     *
     * @return the current number of cards in the pile
     *
     * @since 1.0
     */
    public int cardCount()
        {

        return this.cards.size() ;

        }  // end cardCount()


    /**
     * remove all cards from the pile
     *
     * @return this pile (fluent)
     *
     * @since 3.0
     */
    public UniversalBasePile clear()
        {

        this.cards.clear() ;

        return this ;

        }   // end clear()


    /**
     * flip all cards in the pile - if a card was face down, turn it face up, and vice versa
     *
     * @return this pile (fluent)
     *
     * @since 1.0
     */
    public UniversalBasePile flipAll()
        {

        this.cards.forEach( UniversalBaseCard::flip ) ;

        return this ;

        }   // end flipAll()


    /**
     * retrieve the persistence restrictions for this pile
     *
     * @return the acceptable card persistence setting
     *
     * @since 5.0
     */
    public Persistence getAcceptablePersistence()
        {

        return this.acceptablePersistence ;

        }   // end getAcceptablePersistence()


    /**
     * retrieve the default orientation for cards added to this pile
     *
     * @return the default orientation
     *
     * @since 4.1
     */
    public Orientation getDefaultOrientation()
        {

        return this.defaultOrientation ;

        }   // end getDefaultOrientation()


    /**
     * Retrieve the bottom card from the pile - the card is not removed from the pile
     * <p>
     * Caution: the returned card should only be in one pile at any time
     *
     * @return the bottom card
     *
     * @throws NoCardsException
     *     if the pile is empty
     *
     * @since 3.0
     */
    public UniversalBaseCard getBottomCard() throws NoCardsException
        {

        if ( isEmpty() )
            {
            throw new NoCardsException() ;
            }

        // assertion: there is at least one card in the pile

        return this.cards.getLast() ;

        }  // end getTopCard()


    /**
     * Retrieve a specific card from the pile by position - the card is not removed from the pile
     * <p>
     * Caution: the returned card should only be in one pile at any time
     *
     * @param position
     *     the 0-based position of the card in the pile where 0 is the top of the pile
     *
     * @return the specified card
     *
     * @throws NoCardsException
     *     if the pile is empty
     *
     * @since 3.0
     */
    public UniversalBaseCard getCardAt( final int position )
        throws NoCardsException
        {

        if ( isEmpty() )
            {
            throw new NoCardsException() ;
            }

        // assertion: there is at least one card in the pile

        return this.cards.get( position ) ;

        }  // end getCardAt()


    /**
     * Retrieve a specific card from the pile by card - the card is not removed from the pile
     * <p>
     * Caution: the returned card should only be in one pile at any time
     *
     * @param likeCard
     *     a placeholder - usually is a throw-away instance used for lookup only, not game play
     *
     * @return the specified card or {@code null} if the card isn't in the pile
     *
     * @since 3.0
     */
    public UniversalBaseCard getCardLike( final UniversalBaseCard likeCard )
        {

        Objects.requireNonNull( likeCard, "likeCard" ) ;

        final int positionOfCard = this.cards.indexOf( likeCard ) ;

        if ( positionOfCard == -1 )
            {
            return null ;
            }

        return this.cards.get( positionOfCard ) ;

        }  // end getCardLike() given a Card


    /**
     * Retrieve the top card from the pile - the card is not removed from the pile
     * <p>
     * Caution: the returned card should only be in one pile at any time
     * <p>
     * Note: there is no corresponding convenience method to retrieve the bottom card since that's
     * usually cheating and we can't condone that 8~)
     *
     * @return the top card
     *
     * @throws NoCardsException
     *     if the pile is empty
     *
     * @since 3.0
     */
    public UniversalBaseCard getTopCard() throws NoCardsException
        {

        if ( isEmpty() )
            {
            throw new NoCardsException() ;
            }

        // assertion: there is at least one card in the pile

        return this.cards.getFirst() ;

        }  // end getTopCard()


    /**
     * turn all cards in the pile face down
     *
     * @return this pile (fluent)
     *
     * @since 1.0
     */
    public UniversalBasePile hideAll()
        {

        this.cards.forEach( UniversalBaseCard::hide ) ;

        return this ;

        }   // end hideAll()


    /**
     * Report if the pile is empty (contains no cards)
     *
     * @return true if there are no cards in the pile; false otherwise
     *
     * @since 1.0
     */
    public boolean isEmpty()
        {

        return this.cards.isEmpty() ;

        }   // end isEmpty()


    @Override
    public Iterator<UniversalBaseCard> iterator()
        {

        return new CardIterator() ;

        }   // end iterator()


    /**
     * creates a list iterator for this pile
     *
     * @return a new {@code ListIterator}
     *
     * @since 3.0
     */
    public ListIterator<UniversalBaseCard> listIterator()
        {

        return new CardIterator() ;

        }   // end listIterator()


    /**
     * Counts the number of cards in the pile which match the target card
     * <p>
     * This count may be different than would be determined by testing cards for equality, for
     * instance, if matching only rank or suit.
     *
     * @param targetCard
     *     the card to look for
     *
     * @return the number of cards which match the target card
     *
     * @since 1.0
     */
    public int matchCount( final UniversalBaseCard targetCard )
        {

        Objects.requireNonNull( targetCard, "targetCard" ) ;

        int matches = 0 ;

        // count the number of times the target card appears in the pile
        for ( final UniversalBaseCard aCard : this.cards )
            {

            if ( aCard.matches( targetCard ) )
                {
                matches++ ;
                }

            }   // end for

        return matches ;

        }   // end matchCount()


    /**
     * Move all cards from {@code otherCards} to the bottom of this pile
     *
     * @param otherCards
     *     another pile of cards
     *     <p>
     *     post-condition: {@code otherCards} will be empty
     *
     * @return this pile (fluent)
     *
     * @since 1.0
     */
    public UniversalBasePile moveCardsToBottom(
                                                final UniversalBasePile otherCards )
        {

        Objects.requireNonNull( otherCards, "otherCards" ) ;

        // add the cards to the bottom of our pile
        this.cards.addAll( nextBottomPosition(), otherCards.cards ) ;

        // remove all cards from the other pile
        otherCards.clear() ;

        return this ;

        }  // end moveCardsToBottom()


    /**
     * Move all cards from {@code otherCards} to the top of this pile
     * <p>
     * otherCards are placed in their original order on top of the current top of this pile
     *
     * @param otherCards
     *     another pile of cards
     *     <p>
     *     post-condition: {@code otherCards} will be empty
     *
     * @return this pile (fluent)
     *
     * @since 1.0
     */
    public UniversalBasePile moveCardsToTop(
                                             final UniversalBasePile otherCards )
        {

        Objects.requireNonNull( otherCards, "otherCards" ) ;

        // add the cards to the top of our pile
        this.cards.addAll( nextTopPosition(), otherCards.cards ) ;

        // remove all cards from the other pile
        otherCards.clear() ;

        return this ;

        }  // end moveCardsToTop()


    /**
     * Remove a specific card from the pile by card
     *
     * @param card
     *     the card to be removed
     *
     * @return the specified card or {@code null} if the card isn't in the pile
     *
     * @since 3.0
     */
    public UniversalBaseCard removeCard( final UniversalBaseCard card )
        {

        Objects.requireNonNull( card, "card" ) ;

        final int positionOfCard = this.cards.indexOf( card ) ;

        if ( positionOfCard == -1 )
            {
            return null ;
            }

        return this.cards.remove( positionOfCard ) ;

        }  // end removeCard()


    /**
     * Remove all instances of a specific card from the pile
     *
     * @param lookupCard
     *     the card to be removed
     * @param removedCards
     *     an existing {@code Pile} to receive the removed cards
     *
     * @return this pile (fluent)
     *
     * @since 4.0
     */
    public UniversalBasePile removeAllMatchingCards(
                                                     final UniversalBaseCard lookupCard,
                                                     final UniversalBasePile removedCards )
        {

        Objects.requireNonNull( lookupCard, "lookupCard" ) ;
        Objects.requireNonNull( removedCards, "removedCards" ) ;

        // pull any matching cards from the pile
        UniversalBaseCard foundCard ;

        while ( null != ( foundCard = removeCard( lookupCard ) ) )
            {
            removedCards.addToBottom( foundCard ) ;
            }

        return this ;

        }  // end removeAllMatchingCards()


    /**
     * Remove a specific card from the pile by position
     *
     * @param position
     *     the 0-based position of the card in the pile where 0 is the bottom of the pile
     *
     * @return the specified card
     *
     * @throws NoCardsException
     *     if the pile is empty
     * @throws IndexOutOfBoundsException
     *     if the specified position is negative or greater than or equal to the number of cards in
     *     the pile
     *
     * @since 1.0
     */
    public UniversalBaseCard removeCardAt( final int position )
        throws NoCardsException, IndexOutOfBoundsException
        {

        if ( isEmpty() )
            {
            throw new NoCardsException() ;
            }

        // assertion: there is at least one card in the pile

        return this.cards.remove( position ) ;

        }  // end removeCardAt()


    /**
     * Remove the top card from the pile
     * <p>
     * Note: there is no corresponding convenience method to remove the bottom card since that's
     * usually cheating and we can't condone that 8~)
     *
     * @return the top card
     *
     * @throws NoCardsException
     *     if the pile is empty
     *
     * @since 1.0
     */
    public UniversalBaseCard removeTopCard() throws NoCardsException
        {

        if ( isEmpty() )
            {
            throw new NoCardsException() ;
            }

        // assertion: there is at least one card in the pile

        return this.cards.removeFirst() ;

        }  // end removeTopCard()


    /**
     * turn all cards in the pile face up
     *
     * @return this pile (fluent)
     *
     * @since 1.0
     */
    public UniversalBasePile revealAll()
        {

        this.cards.forEach( UniversalBaseCard::reveal ) ;

        return this ;

        }   // end revealAll()


    /**
     * reset the default orientation for cards added to this pile
     *
     * @return this pile (fluent)
     *
     * @since 4.1
     */
    public UniversalBasePile resetDefaultOrientation()
        {

        this.defaultOrientation = DEFAULT_ORIENTATION ;

        return this ;

        }   // end resetDefaultOrientation()


    /**
     * set the default orientation for cards added to this pile to face down
     *
     * @return this pile (fluent)
     *
     * @since 5.0
     */
    public UniversalBasePile setDefaultFaceDown()
        {

        setDefaultOrientation( Orientation.FACE_DOWN ) ;

        return this ;

        }   // end setDefaultFaceDown()


    /**
     * set the default orientation for cards added to this pile to face up
     *
     * @return this pile (fluent)
     *
     * @since 5.0
     */
    public UniversalBasePile setDefaultFaceUp()
        {

        setDefaultOrientation( Orientation.FACE_UP ) ;

        return this ;

        }   // end setDefaultFaceUp()


    /**
     * set the default orientation for cards added to this pile
     *
     * @param newOrientation
     *     the orientation to be used by default for all cards subsequently added to this pile
     *
     * @return this pile (fluent)
     *
     * @since 4.1
     */
    public UniversalBasePile setDefaultOrientation(
                                                    final Orientation newOrientation )
        {

        Objects.requireNonNull( newOrientation, "newOrientation" ) ;

        this.defaultOrientation = newOrientation ;

        return this ;

        }   // end setDefaultOrientation()


    /**
     * Randomize (shuffle) the cards in the deck
     *
     * @return this pile (fluent)
     *
     * @since 3.0
     */
    public UniversalBasePile shuffle()
        {

        Collections.shuffle( this.cards ) ;

        return this ;

        }   // end shuffle()


    /**
     * Reorder (sort) the cards in the deck
     *
     * @return this pile (fluent)
     *
     * @since 1.0
     */
    public UniversalBasePile sort()
        {

        Collections.sort( this.cards ) ;

        return this ;

        }   // end sort()


    @Override
    public String toString()
        {

        return this.cards.toString() ;

        }	// end toString()


    /*
     * protected utility methods
     */


    /**
     * determine the position of the card currently on the bottom of the pile
     *
     * @return the position of the current bottom card
     *
     * @since 3.0
     */
    protected int currentBottomPosition()
        {

        return ( this.cards.size() - 1 ) ;

        }   // end currentBottomPosition()


    /**
     * determine the position of the card currently on top of the pile
     *
     * @return the position of the current top card
     *
     * @since 3.0
     */
    @SuppressWarnings( "static-method" )
    protected int currentTopPosition()
        {

        return 0 ;

        }   // end currentTopPosition()


    /**
     * determine the position of a card to be added to the bottom of the pile
     *
     * @return the position of the next bottom card
     *
     * @since 3.0
     */
    protected int nextBottomPosition()
        {

        return this.cards.size() ;

        }   // end nextBottomPosition()


    /**
     * determine the position of a card to be added to the top of the pile
     *
     * @return the position of the next top card
     *
     * @since 3.0
     */
    @SuppressWarnings( "static-method" )
    protected int nextTopPosition()
        {

        return 0 ;

        }   // end nextTopPosition()


    /*
     * private utility methods
     */


    /**
     * prepare a card to be added to this pile:
     * <ul>
     * <li>must be provided (non-{@code null})
     * <li>must have an acceptable persistence
     * <li>adjust orientation if necessary
     * </ul>
     *
     * @param newCard
     *     the card to be added
     *
     * @since 5.0
     */
    private void prepareCardToAdd( final UniversalBaseCard newCard )
        {

        // new card is required
        Objects.requireNonNull( newCard, "newCard" ) ;

        // check persistence
        if ( UNRESTRICTED != this.acceptablePersistence )
            {

            // must be permanent?
            if ( ( PERMANENT == this.acceptablePersistence ) &&
                 ( PERMANENT != newCard.getPersistence() ) )
                {
                throw new IllegalArgumentException( "pile only accepting permanent cards given a non-permanent card" ) ;
                }

            // must be temporary?
            if ( ( TEMPORARY == this.acceptablePersistence ) &&
                 ( TEMPORARY != newCard.getPersistence() ) )
                {
                throw new IllegalArgumentException( "pile only accepting temporary cards given a non-temporary card" ) ;
                }

            }

        // set orientation if necessary
        if ( AS_IS != this.defaultOrientation )
            {
            newCard.setOrientation( this.defaultOrientation ) ;
            }

        }   // end prepareCardToAdd()


    /**
     * set the persistence requirements for this pile (permanent, temporary, unrestricted)
     *
     * @param persistenceSetting
     *     a non-{@code null} specification
     *
     * @since 5.0
     */
    private void setAcceptablePersistence(
                                           final Persistence persistenceSetting )
        {

        Objects.requireNonNull( persistenceSetting, "persistenceSetting" ) ;

        this.acceptablePersistence = persistenceSetting ;

        }   // end setPersistence()


    /*
     * testing/debugging
     */
    // none


    /*
     * utility classes
     */


    /**
     * enable iteration over all cards in the pile
     *
     * @author David M Rosenberg
     *
     * @version 1.0 2025-03-29 Initial implementation
     *
     * @since 1.0
     */
    private class CardIterator implements ListIterator<UniversalBaseCard>
        {

        /** the actual iterator is that of the list of cards */
        private final ListIterator<UniversalBaseCard> cardIterator ;


        /**
         * configure the instance state
         * 
         * @since 1.0
         */
        private CardIterator()
            {

            this.cardIterator = UniversalBasePile.this.cards.listIterator() ;

            }   // end constructor


        @Override
        public boolean hasNext()
            {

            return this.cardIterator.hasNext() ;

            }   // end hasNext()


        @Override
        public UniversalBaseCard next()
            {

            return this.cardIterator.next() ;

            }   // end next()


        @Override
        public boolean hasPrevious()
            {

            return this.cardIterator.hasPrevious() ;

            }   // end hasPrevious()


        @Override
        public UniversalBaseCard previous()
            {

            return this.cardIterator.previous() ;

            }   // end previous()


        @Override
        public int nextIndex()
            {

            return this.cardIterator.nextIndex() ;

            }   // end nextIndex()


        @Override
        public int previousIndex()
            {

            return this.cardIterator.previousIndex() ;

            }   // end previousIndex()


        @Override
        public void remove()
            {

            this.cardIterator.remove() ;

            }   // end remove()


        @Override
        public void set( final UniversalBaseCard replacementCard )
            {

            this.cardIterator.set( replacementCard ) ;

            }   // end set()


        @Override
        public void add( final UniversalBaseCard newCard )
            {

            this.cardIterator.add( newCard ) ;

            }   // end add()

        }   // end inner class CardIterator

    }	// end class UniversalBasePile