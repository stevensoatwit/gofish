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


package edu.wit.scds.ds.lists.app.card_game.universal_base.support;

/**
 * Represents the persistence of a {@code Card} (permanent or temporary).  It is used to distinguish between cards created by a deck (permanent) and those
 * used for lookup/matching (temporary).  This also provides support for filtering cards when they are added to a pile.
 * <p>
 * NOTE: You probably won't modify this code
 * 
 * @author David M Rosenberg
 * 
 * @version 1.0 2025-07-12 Initial implementation - extracted from {@code UniversalBaseCard}.
 *
 * @author Your Name
 * 
 * @version 2.0 2025-07-13 only modify this tag if you modified the code
 */
public enum Persistence
    {

    // @formatter:off
    // these are applicable to cards and piles

    /** indicate the card is real (instantiated by a deck for game play */
    PERMANENT ( "permanent" )

    , /** indicate the card's purpose is for lookup or matching, not game play */
    TEMPORARY ( "temporary" )


    // these are only applicable to piles for filtering when cards are being added

    , /** for piles, to indicate either permanent or temporary cards are acceptable */
    UNRESTRICTED ( "unrestricted" )

    ;
    // @formatter:on

    /*
     * instance variables
     */
    
    /** 'pretty' (capitalized) form of the color's name */
    private final String displayName ;


    /**
     * configure the instance state
     * 
     * @param theDisplayName
     *     the 'pretty' (capitalized) form of the persistence type
     */
    private Persistence( final String theDisplayName )
        {
        this.displayName =  theDisplayName ;

        } // end constructor


    /**
     * retrieve the 'pretty' (capitalized) form of the persistence type
     *
     * @return the 'pretty' name of the persistence
     */
    public String getDisplayName()
        {
        return this.displayName ;

        } // end getDisplayName()


    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
        {
        return getDisplayName() ;

        }   // end toString()


    /**
     * Test driver
     *
     * @param args
     *     -unused-
     */
    public static void main( final String[] args )
        {
        // display column headers
        System.out.printf( "  %-5s %-17s %-17s %-10s%n",
                           "#",
                           "Name",
                           "Persistence",
                           "Display Name" ) ;

        // display each element of the enumeration
        for ( final Persistence aPersistence : Persistence.values() )
            {
            System.out.printf( "  %-5d %-17s %-17s %-10s%n",
                               aPersistence.ordinal(),
                               aPersistence.name(),
                               aPersistence,
                               aPersistence.displayName ) ;
            }   // end for

        }   // end main()

    }   // end enum Persistence   // end class Persistence