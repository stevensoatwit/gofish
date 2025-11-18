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
import edu.wit.scds.ds.lists.app.card_game.top_this.pile.Hand ;
import edu.wit.scds.ds.lists.app.card_game.top_this.pile.Meld ;
import edu.wit.scds.ds.lists.app.card_game.top_this.pile.Stock ;
import edu.wit.scds.ds.lists.app.card_game.universal_base.card.UniversalBaseCard ;
import edu.wit.scds.ds.lists.app.card_game.universal_base.support.NoCardsException ;

import java.io.File ;
import java.io.FileNotFoundException ;
import java.util.ArrayList ;
import java.util.List ;
import java.util.Random ;
import java.util.Scanner ;

/**
 * Representation of a player
 * <p>
 * NOTE: You will modify this code
 *
 * @author Dave Rosenberg
 *
 * @version 0.1 2025-07-13 skeleton code taken from my full solution
 * 
 * @author Your Name
 * 
 * @version 1.0 2025-07-13 Initial implementation
 */
public class Player
    {

    /*
     * data fields
     */


    /** the cards that are in-play */
    private final Hand hand ;

    /** groups of cards collected during play */
    private final List<Meld> melds ;

    /** player's name */
    public final String name ;

    private ArrayList<Integer> pairs ;

    /*
     * constructor(s)
     */


    /**
     * initialize a player
     *
     * @param playerName
     *     the player's name
     */
    public Player( final String playerName )
        {

        this.name = playerName ;

        this.hand = new Hand() ;

        this.melds = new ArrayList<>() ;

        this.pairs = new ArrayList<>();
        
        }	// end constructor

    /*
     * public methods
     */

    /**
     * 
     * @return the number of cards in this hand
     *
     * @since 1.0
     */
    public int getHandSize() {
        return this.hand.cardCount();
    }
    
    /**
     * 
     * @return number of pairs the player has made
     *
     * @since 1.0
     */
    public int getNumPairs() {
        return this.pairs.size();
    }
    
    /**
     * 
     * @param rank rank you are seeking
     * @return whether or not your hand has it
     *
     * @since 1.0
     */
    public boolean hasCardOfRank( int rank ) {
        
        for(UniversalBaseCard card: this.hand) {
            if( Player.getUniCardRank( card ) == rank ) {
                return true;
            } // end if
        } // end for
        
        return false;
    }
    
    /**
     * Checks for pairs, returns the first one or null
     * @return null if no pairs, the first found paired card if there is one
     */
    public UniversalBaseCard hasPairs() {
        // Setup a catalogue of card values
        ArrayList<Integer> catalogue = new ArrayList<>();
        // Iterate over cards
        for(UniversalBaseCard card : this.hand ) {
            System.out.println(card.getFaceUpText());
            // Get this cards value
            int thisCardVal = getUniCardRank(card);
            // Check if its already in catalogue
            if(catalogue.contains(thisCardVal)) {
                return card; // This hand has pairs!
            }
            // Add this card to catalogue
            catalogue.add(thisCardVal);
        }
        return null;
    }
    
    /**
     * 
     * @param card card to remove
     *
     * @since 1.0
     */
    public void makePair(UniversalBaseCard card) {
        int rank = getUniCardRank(card);
        this.pairs.add( rank );
        this.popFirstOfRank( rank );
        this.popFirstOfRank( rank );
        System.out.println("Made pair on rank " + rank);
        
    }
    
    /**
     * Removes and returns the first card of a rank, null if no matches
     * @param uniRank rank of the card
     * @return null if no cards of rank, the first found card if there is one or more
     */
    public UniversalBaseCard popFirstOfRank(int uniRank) {
        // Iterate over cards
        for(UniversalBaseCard card : hand ) {
            System.out.println(card.getFaceUpText());
            // Get this cards value
            int thisCardVal = getUniCardRank(card);
            // Check if it matches
            if(thisCardVal == uniRank) {
                return hand.removeCard(card); // This hand has pairs!
            }
        }
        return null;
    }
    
    /**
     * UniversalBaseCards dont have rank, this interprets a numeric value from the faceuptext
     * @param card 
     * @return Card rank value
     */
    public static int getUniCardRank(UniversalBaseCard card) {
        int value = 0;
        String faceText = card.getFaceUpText();
        faceText = faceText.substring(0, faceText.length() - 1);
        for(char c : faceText.toCharArray()) {
            value += (int) c;
        }
        
        return value;
    }


    /**
     * Add a dealt card to our hand
     *
     * @param dealt
     *     the card we're dealt
     */
    public void dealtACard( final Card dealt )
        {

        this.hand.addToBottom( dealt ) ;
        this.hand.sort() ;

        }  // end dealtACard()
    
    /**
     * Add a dealt card to our hand
     *
     * @param dealt
     *     the card we're dealt
     */
    public void dealtACard( final UniversalBaseCard dealt )
        {

        this.hand.addToBottom( dealt ) ;
        this.hand.sort() ;

        }  // end dealtACard()


    /**
     * retrieve the number of melds
     *
     * @return the number of melds
     *
     * @since 2.0
     */
    public int getMeldCount()
        {

        return this.melds.size() ;

        }   // end getMeldCount()


    /**
     * Remove an unspecified card from our hand
     *
     * @return any card currently in the hand
     *
     * @throws NoCardsException
     *     if the hand is empty
     *
     * @since 1.0
     */
    public Card playACard() throws NoCardsException
        {

        return this.hand.removeCardAt( new Random().nextInt( 0,
                                                             this.hand.cardCount() ) ) ;

        }  // end playACard()


    /**
     * Remove a specified card from our hand
     *
     * @param cardToThrow
     *     the card to remove
     *
     * @return the specified card or null if not in the hand
     *
     * @since 2.0
     */
    public Card playACard( final Card cardToThrow )
        {

        return this.hand.removeCard( cardToThrow ) ;

        }  // end playACard()


    /**
     * Remove a specified card from our hand
     *
     * @param rank
     *     the rank of the card to remove
     * @param suit
     *     the suit of the card to remove
     *
     * @return the specified card or null if not in the hand
     *
     * @since 1.0
     */
    public Card playACard( final Rank rank,
                           final Suit suit )
        {

        return playACard( new Card( rank, suit ) ) ;

        }  // end playACard()


    /**
     * text describing the contents of the player's hand
     * <p>
     * note that cards' orientation is unchanged
     *
     * @return a string containing the cards in the player's hand
     *
     * @since 1.0
     */
    public String revealHand()
        {

        if ( this.hand.cardCount() == 0 )
            {
            return "empty" ;
            }

        return this.hand.revealAll().toString() ;

        }   // end revealHand()


    /**
     * text describing the contents of the player's melds
     * <p>
     * note that cards' orientation is unchanged
     *
     * @return a string containing the cards in the player's melds
     *
     * @since 2.0
     */
    public String revealMelds()
        {

        if ( this.melds.size() == 0 )
            {
            return "none" ;
            }

        final ArrayList<String> meldsText = new ArrayList<>( this.melds.size() ) ;

        for ( final Meld aMeld : this.melds )
            {
            meldsText.add( aMeld.revealAll().toString() ) ;
            }

        return meldsText.toString() ;

        }   // end revealMelds()


    /**
     * Remove all cards from our hand and our collected cards
     *
     * @return a pile with all the cards we have - order and orientation may be inconsistent
     *
     * @since 2.0
     */
    public Pile turnInAllCards()
        {

        // local temporary class (pile) to hold our cards
        class AllCards extends Pile
            { /* temporary collection */ }

        final AllCards allCards = new AllCards() ;

        // we may have cards in our hand and we may have 1 or more melds

        allCards.moveCardsToBottom( this.hand ) ;

        for ( final Pile aMeld : this.melds )
            {
            allCards.moveCardsToBottom( aMeld ) ;
            }

        this.melds.clear() ;

        // assertion: we have no cards, any we had will be returned via allCards

        return allCards ;

        }  // end turnInAllCards()


    /**
     * save the cards won as a meld
     *
     * @param cardsWon
     *     the cards this player won
     *
     * @since 2.0
     */
    public void wonRound( final Pile cardsWon )
        {

        // make sure we're given a Meld
        if ( ! ( cardsWon instanceof final Meld meldWon ) )
            {
            throw new IllegalStateException( String.format( "require argument of type Meld but is %s",
                                                            cardsWon.getClass()
                                                                    .getSimpleName() ) ) ;
            }

        cardsWon.revealAll() ;

        this.melds.add( meldWon ) ;

        }   // end cardsWon()


    /*
     * utility methods
     */


    @Override
    public String toString()
        {

//        final String meldsText = String.format( "%s", this.melds ) ;
        return String.format( "%nPlayer: %s%n\thand: %s%n\tmelds: %s",
                              this.name,
                              revealHand(),
                              revealMelds().replace( ", [", "[" )
                                           .replace( "[[", "[" )
                                           .replace( "]]", "]" )
                                           .replace( "[", "\n\t\t[" ) ) ;

        }   // end toString()


    /*
     * testing/debugging
     */


    /**
     * (optional) test driver
     *
     * @param args
     *     -unused-
     */
    public static void main( final String[] args )
        {

        // we'll sort by rank only and treat ace as highest value card
        Card.setCompareSuit( false ) ;
        Rank.setUseAltOrder( true ) ;

        final Deck testDeck = new Deck() ;

        // create the stock initially populated with all the cards from the deck
        final Stock testStock = new Stock( testDeck ) ;
        testStock.shuffle() ;

        // put any jokers back in the deck
        final Card lookupJoker = new Card( JOKER ) ;
        Card foundJoker ;

        while ( null != ( foundJoker = testStock.removeCard( lookupJoker ) ) )
            {
            testDeck.addToBottom( foundJoker ) ;
            }

        testStock.revealAll() ;
        System.out.printf( "Stock: %s%n%n", testStock ) ;
        testStock.hideAll() ;

        testDeck.revealAll() ;
        System.out.printf( "Deck: %s%n%n", testDeck ) ;
        testDeck.hideAll() ;


        final Player testPlayer = new Player( "tester" ) ;

        System.out.printf( "start: %s%n", testPlayer ) ;

        for ( int i = 1 ; i <= 5 ; i++ )
            {
            final Card dealt = testStock.drawTopCard().reveal() ;

            testPlayer.dealtACard( dealt ) ;
            }

        System.out.printf( "%ndealt: %s%n", testPlayer ) ;

        for ( int i = 1 ; i <= 3 ; i++ )
            {
            final Pile aMeld = new Meld().setDefaultFaceUp() ;

            for ( int j = 1 ; j <= 5 ; j++ )
                {
                aMeld.addToTop( testStock.drawTopCard() ) ;
                }

            testPlayer.wonRound( aMeld ) ;
            }

        System.out.printf( "%nwith some melds: %s%n", testPlayer ) ;


        // the following is the correct way to access a file in the data folder
        System.out.printf( "%n%naccessing a file in the data folder:%n%n" ) ;

        try ( Scanner input = new Scanner( new File( "./data/readme.txt" ) ) ; )
            {

            while ( input.hasNextLine() )
                {
                System.out.printf( "%s%n", input.nextLine() ) ;
                }

            }
        catch ( final FileNotFoundException e )
            {
            System.err.printf( "failed to open readme.txt:%n%s%n", e ) ;
            }

        }	// end main()

    }	// end class Player