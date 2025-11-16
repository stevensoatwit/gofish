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


package edu.wit.scds.ds.lists.app.card_game.universal_base.card ;

import static edu.wit.scds.ds.lists.app.card_game.universal_base.support.Orientation.FACE_DOWN ;
import static edu.wit.scds.ds.lists.app.card_game.universal_base.support.Orientation.FACE_UP ;
import static edu.wit.scds.ds.lists.app.card_game.universal_base.support.Persistence.PERMANENT ;
import static edu.wit.scds.ds.lists.app.card_game.universal_base.support.Persistence.TEMPORARY ;

import edu.wit.scds.ds.lists.app.card_game.universal_base.support.Orientation ;
import edu.wit.scds.ds.lists.app.card_game.universal_base.support.Persistence ;

import java.util.Arrays ;
import java.util.Objects ;

/**
 * representation of any kind of playing card providing 'universal' functionality
 * <p>
 * NOTE: You probably won't modify this code
 *
 * @author David M Rosenberg
 *
 * @version 1.0 2025-06-26 Initial implementation
 * @version 2.0 2025-07-11
 *     <ul>
 *     <li>rename from {@code Card} to {@code UniversalBaseCard}
 *     <li>add persistence
 *     <li>make most instance methods fluent
 *     <li>eliminate all constructors except no-arg and 1-arg supplying face up text
 *     <li>add {@code null}-argument checking
 *     <li>move {@code Persistence} to its own source file
 *     </ul>
 *
 * @author Your Name
 * 
 * @version 3.0 2025-07-13 only modify this tag if you modified the code
 */
public abstract class UniversalBaseCard implements Comparable<UniversalBaseCard>
    {

    /*
     * constants
     */


    /** default text to display when a card is face up */
    protected static final String DEFAULT_FACEUP_TEXT = "??" ;

    /** default text to display when a card is face down */
    protected static final String DEFAULT_FACEDOWN_TEXT = "--" ;

    /** default orientation */
    protected static final Orientation DEFAULT_ORIENTATION = FACE_DOWN ;


    /** default persistence */
    protected static final Persistence DEFAULT_PERSISTENCE = TEMPORARY ;

    /**
     * default decoration for permanent cards where [0] is applied immediately before the face text
     * and [1] immediately following
     */
    protected static final String[] DEFAULT_PERMANENT_DECORATION = { "", "" } ;

    /**
     * default decoration for temporary cards where [0] is applied immediately before the face text
     * and [1] immediately following
     */
    protected static final String[] DEFAULT_TEMPORARY_DECORATION = { "«",
                                                                     "»" } ;


    /*
     * static fields
     */


    /** the text to display when the card is face up - if not specified, use current default */
    protected static String defaultFaceUpText = DEFAULT_FACEUP_TEXT ;

    /** the text to display when the card is face down - if not specified, use current default */
    protected static String defaultFaceDownText = DEFAULT_FACEDOWN_TEXT ;


    /** default state of a card when instantiated - face up or down */
    protected static Orientation defaultOrientation = DEFAULT_ORIENTATION ;

    /**
     * default state for decoration for permanent cards where [0] is applied immediately before the
     * face text and [1] immediately following
     */
    protected static String[] defaultPermanentCardDecoration = DEFAULT_PERMANENT_DECORATION ;

    /**
     * default state for decoration for temporary cards where [0] is applied immediately before the
     * face text and [1] immediately following
     */
    protected static String[] defaultTemporaryCardDecoration = DEFAULT_TEMPORARY_DECORATION ;


    /** control instantiated cards' persistence */
    private static Persistence defaultPersistence = DEFAULT_PERSISTENCE ;


    /*
     * data fields
     */


    /** text to display when the card is face up */
    private String faceUpText ;

    /** lock for faceUpText - it's supposed to be immutable */
    private boolean faceUpTextLocked ;

    /** text to display when the card is face down */
    private String faceDownText ;

    /** control display - face up/down */
    private Orientation orientation ;

    /** permanent or temporary (used for lookup/matching) */
    private final Persistence persistence ;


    /*
     * constructors
     */


    /**
     * set initial state to default values
     *
     * @since 1.0
     */
    protected UniversalBaseCard()
        {

        this( defaultPersistence ) ;

        }   // end no-arg constructor


    /**
     * set initial state to default values with specified persistence
     * <p>
     * Note: this constructor is typically used for cloning a card which should be temporary
     *
     * @param cardPersistence
     *     the persistence specifically for this card
     *
     * @since 2.0
     */
    protected UniversalBaseCard( final Persistence cardPersistence )
        {

        validatePersistence( cardPersistence ) ;

        this.faceUpText = defaultFaceUpText ;
        this.faceUpTextLocked = false ;

        this.faceDownText = defaultFaceDownText ;

        this.orientation = defaultOrientation ;

        this.persistence = cardPersistence ;

        }   // end 1-arg constructor w/ persistence


    /**
     * set initial state to specified face up text and default orientation
     *
     * @param initialFaceUpText
     *     text to display when the card is face up
     *
     * @since 2.0
     */
    protected UniversalBaseCard( final String initialFaceUpText )
        {

        this() ;

        setFaceUpText( initialFaceUpText ) ;

        }   // end 1-arg constructor w/ face up text


    /*
     * public API methods
     */


    /**
     * create a temporary copy of this card
     *
     * @return the new, temporary card
     *
     * @since 2.0
     */
    @SuppressWarnings( "static-method" )
    public UniversalBaseCard copyOf()
        {

        throw new UnsupportedOperationException() ;

        }   // end copyOf()


    /**
     * Flip a card over
     *
     * @return this card (fluent)
     *
     * @since 1.0
     */
    public UniversalBaseCard flip()
        {

        this.orientation = this.orientation.flip() ;

        return this ;

        }  // end flip()


    /**
     * Retrieve the default text to display when the card is face down
     *
     * @return the text to display when the card is face down - {@code null} indicates to use the
     *     default
     *
     * @since 1.0
     */
    public static String getDefaultFaceDownText()
        {

        return defaultFaceDownText ;

        }  // end getDefaultFaceDownText()


    /**
     * Retrieve the default orientation (face up/down) for new cards
     *
     * @return the current default state (face up/down)
     *
     * @since 1.0
     */
    public static Orientation getDefaultOrientation()
        {

        return defaultOrientation ;

        }  // end getDefaultOrientation()


    /**
     * retrieve the whether new cards will be permanent or temporary
     *
     * @return the current default persistence
     *
     * @since 2.0
     */
    public static Persistence getDefaultPersistence()
        {

        return defaultPersistence ;

        }   // end getDefaultPersistence()


    /**
     * Retrieve the text to display when the card is face up
     *
     * @return the text to display when the card is face up
     *
     * @since 2.0
     */
    public String getFaceUpText()
        {

        return this.faceUpText ;

        }  // end getFaceUpText()


    /**
     * Retrieve the text to display when the card is face down
     *
     * @return the text to display when the card is face down - {@code null} indicates to use the
     *     default
     *
     * @since 1.0
     */
    public String getFaceDownText()
        {

        return this.faceDownText ;

        }  // end getFaceDownText()


    /**
     * Retrieve a card's orientation (face up/down)
     *
     * @return the card's state (face up/down)
     *
     * @since 1.0
     */
    public Orientation getOrientation()
        {

        return this.orientation ;

        }  // end getOrientation()


    /**
     * retrieve the decoration text for permanent cards
     *
     * @return 2-element array where [0] is the left decoration and [1] is the right
     *
     * @since 2.0
     */
    public static String[] getPermanentDecoration()
        {

        return Arrays.copyOf( defaultPermanentCardDecoration,
                              defaultPermanentCardDecoration.length ) ;

        }   // end getPermanentDecoration()


    /**
     * retrieve the decoration text for temporary cards
     *
     * @return 2-element array where [0] is the left decoration and [1] is the right
     *
     * @since 2.0
     */
    public static String[] getTemporaryDecoration()
        {

        return Arrays.copyOf( defaultTemporaryCardDecoration,
                              defaultTemporaryCardDecoration.length ) ;

        }   // end getTemporaryDecoration()


    /**
     * retrieve the whether this card is permanent or temporary
     *
     * @return the card's persistence
     *
     * @since 2.0
     */
    public Persistence getPersistence()
        {

        return this.persistence ;

        }   // end getPersistence()


    /**
     * Turn a card face down
     *
     * @return this card (fluent)
     *
     * @since 1.0
     */
    public UniversalBaseCard hide()
        {

        this.orientation = FACE_DOWN ;

        return this ;

        }  // end hide()


    /**
     * Compare two cards to see if they match, which may be different from them being
     * {@code equal()}
     * <p>
     * The game or {@code Pile} class may require this method to behave differently from
     * {@code equals()} or {@code compareTo()}.
     * <p>
     * Note: this implementation uses {@code equals()} to perform the match functionality which may
     * be overridden in a subclass
     *
     * @param otherCard
     *     the card to compare against this card
     *
     * @return {@code true} if the cards match, {@code false} otherwise
     */
    public boolean matches( final UniversalBaseCard otherCard )
        {

        // delegate to equals() - game should override if necessary
        return equals( otherCard ) ;

        }  // end matches()


    /**
     * reset the default text to display when the card is face down to the system default
     *
     * @return the previous text to display when the card is face down
     *
     * @since 1.0
     */
    public static String resetDefaultFaceDownText()
        {

        final String savedFaceDownText = defaultFaceDownText ;

        defaultFaceDownText = DEFAULT_FACEDOWN_TEXT ;

        return savedFaceDownText ;

        }  // end resetDefaultFaceDownText()


    /**
     * reset the default orientation for new cards to the system default
     * <p>
     * Note that this will only be effective for cards instantiated
     *
     * @return the previous state (face up/down)
     *
     * @since 1.0
     */
    public static Orientation resetDefaultOrientation()
        {

        final Orientation savedDefaultOrientation = defaultOrientation ;

        UniversalBaseCard.defaultOrientation = DEFAULT_ORIENTATION ;

        return savedDefaultOrientation ;

        }  // end resetDefaultOrientation()


    /**
     * reset the default persistence for new cards to the system default
     * <p>
     * Note that this will only be effective for cards instantiated
     *
     * @return the previous state (permanent/temporary)
     *
     * @since 2.0
     */
    public static Persistence resetDefaultPersistence()
        {

        final Persistence savedDefaultPersistence = defaultPersistence ;

        UniversalBaseCard.defaultPersistence = DEFAULT_PERSISTENCE ;

        return savedDefaultPersistence ;

        }  // end resetDefaultPersistence()


    /**
     * reset the text to display when the card is face down to the current default
     *
     * @return this card (fluent)
     *
     * @since 1.0
     */
    public UniversalBaseCard resetFaceDownText()
        {

        this.faceDownText = defaultFaceDownText ;

        return this ;

        }  // end resetFaceDownText()


    /**
     * Reset a card's orientation (face up/down) to the current default
     *
     * @return this card (fluent)
     *
     * @since 1.0
     */
    public UniversalBaseCard resetOrientation()
        {

        this.orientation = defaultOrientation ;

        return this ;

        }  // end resetOrientation()


    /**
     * Turn a card face up
     *
     * @return this card (fluent)
     *
     * @since 1.0
     */
    public UniversalBaseCard reveal()
        {

        this.orientation = FACE_UP ;

        return this ;

        }  // end reveal()


    /**
     * Set the default text to display when the card is face down
     *
     * @param newFaceDownText
     *     the new default text to display when the card is face down
     *
     * @return the previous text to display when the card is face down
     *
     * @since 1.0
     */
    public static String setDefaultFaceDownText( final String newFaceDownText )
        {

        final String savedFaceDownText = defaultFaceDownText ;

        defaultFaceDownText = newFaceDownText ;

        return savedFaceDownText ;

        }  // end setDefaultFaceDownText()


    /**
     * Set the default orientation for new cards to be face up/down
     * <p>
     * Note that this will only be effective for cards subsequently instantiated
     *
     * @param newOrientation
     *     the new default orientation (face up/down state)
     *
     * @return the previous state (face up/down)
     *
     * @throws IllegalArgumentException
     *     if the specified orientation is other than {@code FACE_UP} or {@code FACE_DOWN}
     *
     * @since 1.0
     */
    public static Orientation setDefaultOrientation(
                                                     final Orientation newOrientation )
        throws IllegalArgumentException
        {

        Objects.requireNonNull( newOrientation, "newOrientation" ) ;

        if ( ( FACE_DOWN != newOrientation ) && ( FACE_UP != newOrientation ) )
            {
            throw new IllegalArgumentException() ;
            }

        final Orientation savedDefaultOrientation = defaultOrientation ;

        UniversalBaseCard.defaultOrientation = newOrientation ;

        return savedDefaultOrientation ;

        }  // end setDefaultOrientation()


    /**
     * set persistence for new cards
     * <p>
     * Note: decks (the classes which instantiate the cards for game play) are the only classes
     *     expected to change the persistence
     *
     * @param newPersistence
     *     the new persistence setting
     *
     * @return the prior persistence
     *
     * @since 2.0
     */
    public static Persistence setDefaultPersistence(
                                                     final Persistence newPersistence )
        {

        Objects.requireNonNull( newPersistence, "newPersistence" ) ;

        validatePersistence( newPersistence ) ;

        final Persistence savedPersistence = defaultPersistence ;

        defaultPersistence = newPersistence ;

        return savedPersistence ;

        }   // end setDefaultPersistence()


    /**
     * ensure that a persistence is supplied and applicable to a card (is either {@code TEMPORARY}
     * or {@code PERMANENT})
     *
     * @param aPersistence
     *     the {@link Persistence} to validate
     *
     * @since 2.0
     */
    private static void validatePersistence( final Persistence aPersistence )
        {

        Objects.requireNonNull( aPersistence, "aPersistence" ) ;

        if ( ( PERMANENT != aPersistence ) && ( TEMPORARY != aPersistence ) )
            {
            throw new IllegalArgumentException( String.format( "%s is not an acceptable persistence for a card",
                                                               aPersistence.getClass()
                                                                           .getSimpleName() ) ) ;
            }

        }   // end validatePersistence()


    /**
     * convenience method to set the orientation to face down
     *
     * @return this card (fluent)
     *
     * @since 2.0
     */
    public UniversalBaseCard setFaceDown()
        {

        setOrientation( FACE_DOWN ) ;

        return this ;

        }   // end setFaceDown()


    /**
     * Set the text to display when the card is face down
     *
     * @param newFaceDownText
     *     the new text to display when the card is face down
     *
     * @return this card (fluent)
     *
     * @since 1.0
     */
    public UniversalBaseCard setFaceDownText( final String newFaceDownText )
        {

        Objects.requireNonNull( newFaceDownText, "newFaceDownText" ) ;

        this.faceDownText = newFaceDownText ;

        return this ;

        }  // end setFaceDownText()


    /**
     * convenience method to set the orientation to face up
     *
     * @return this card (fluent)
     *
     * @since 2.0
     */
    public UniversalBaseCard setFaceUp()
        {

        setOrientation( FACE_UP ) ;

        return this ;

        }   // end setFaceUp()


    /**
     * Set the text to display when the card is face up
     *
     * @param newFaceUpText
     *     the new text to display when the card is face up
     *
     * @return this card (fluent)
     *
     * @since 1.0
     */
    public UniversalBaseCard setFaceUpText( final String newFaceUpText )
        {

        Objects.requireNonNull( newFaceUpText, "newFaceUpText" ) ;

        // cards are immutable wrt their face
        if ( this.faceUpTextLocked )
            {
            throw new IllegalStateException( String.format( "can't change face of card from '%s' to '%s'",
                                                            this.faceUpText,
                                                            newFaceUpText ) ) ;
            }

        this.faceUpText = newFaceUpText ;

        // prevent subsequent changes
        this.faceUpTextLocked = true ;

        return this ;

        }  // end setFaceDownUp()


    /**
     * Set a card's orientation (face up/down)
     *
     * @param newOrientation
     *     the new orientation (face up/down) state
     *
     * @return this card (fluent)
     *
     * @since 1.0
     */
    public UniversalBaseCard setOrientation( final Orientation newOrientation )
        {

        Objects.requireNonNull( newOrientation, "newOrientation" ) ;

        // only FACE_UP and FACE_DOWN are defined for cards
        if ( ( FACE_UP != newOrientation ) && ( FACE_DOWN != newOrientation ) )
            {
            throw new IllegalArgumentException( String.format( "%s is not acceptable for a card",
                                                               newOrientation.name() ) ) ;
            }

        // we have an acceptable orientation
        this.orientation = newOrientation ;

        return this ;

        }  // end setOrientation()


    /**
     * set the decoration text for permanent cards where [0] is the left decoration and [1] is the
     * right
     *
     * @param newPermanentDecoration
     *     a 2-element array containing the decoration text
     *
     * @return the prior setting
     *
     * @since 2.0
     */
    public static String[] setPermanentDecoration(
                                                   final String[] newPermanentDecoration )
        {

        // the array must be non-null, length 2, and contain non-null references
        Objects.requireNonNull( newPermanentDecoration,
                                "newPermanentDecoration" ) ;

        if ( newPermanentDecoration.length != 2 )
            {
            throw new IllegalArgumentException( String.format( "setPermanentDecoration() requires a 2-element array, argument array has %,d element(s)",
                                                               newPermanentDecoration.length ) ) ;
            }

        Objects.requireNonNull( newPermanentDecoration[ 0 ],
                                "newPermanentDecoration[ 0 ]" ) ;
        Objects.requireNonNull( newPermanentDecoration[ 1 ],
                                "newPermanentDecoration[ 1 ]" ) ;

        final String[] priorDecoration = defaultPermanentCardDecoration ;

        // make a copy of the array for safety
        defaultPermanentCardDecoration = Arrays.copyOf( newPermanentDecoration,
                                                        newPermanentDecoration.length ) ;

        return priorDecoration ;

        }   // end setPermanentDecoration()


    /**
     * set the decoration text for temporary cards where [0] is the left decoration and [1] is the
     * right
     *
     * @param newTemporaryDecoration
     *     a 2-element array containing the decoration text
     *
     * @return the prior setting
     *
     * @since 2.0
     */
    public static String[] setTemporaryDecoration(
                                                   final String[] newTemporaryDecoration )
        {

        // the array must be non-null, length 2, and contain non-null references
        Objects.requireNonNull( newTemporaryDecoration,
                                "newPermanentDecoration" ) ;

        if ( newTemporaryDecoration.length != 2 )
            {
            throw new IllegalArgumentException( String.format( "setPermanentDecoration() requires a 2-element array, argument array has %,d element(s)",
                                                               newTemporaryDecoration.length ) ) ;
            }

        Objects.requireNonNull( newTemporaryDecoration[ 0 ],
                                "newPermanentDecoration[ 0 ]" ) ;
        Objects.requireNonNull( newTemporaryDecoration[ 1 ],
                                "newPermanentDecoration[ 1 ]" ) ;

        final String[] priorDecoration = defaultPermanentCardDecoration ;

        // make a copy of the array for safety
        defaultPermanentCardDecoration = Arrays.copyOf( newTemporaryDecoration,
                                                        newTemporaryDecoration.length ) ;

        return priorDecoration ;

        }   // end setTemporaryDecoration()


    @Override
    public String toString()
        {

        return String.format( "%s%s%s",
                              this.persistence == Persistence.PERMANENT
                                  ? defaultPermanentCardDecoration[ 0 ]
                                  : defaultTemporaryCardDecoration[ 0 ],
                              this.orientation == FACE_UP
                                  ? this.faceUpText
                                  : this.faceDownText,
                              this.persistence == Persistence.PERMANENT
                                  ? defaultPermanentCardDecoration[ 1 ]
                                  : defaultTemporaryCardDecoration[ 1 ] ) ;

        }   // end toString()


    /*
     * protected and private utility methods
     */
    // none


    /*
     * for testing and debugging
     */
    // none


    /*
     * protected and private utility methods
     */
    // none


    /*
     * testing and debugging
     */
    // none


    /*
     * utility classes
     */
    // none

    }   // end class UniversalBaseCard