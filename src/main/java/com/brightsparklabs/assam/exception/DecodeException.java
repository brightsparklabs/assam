/*
 * Created by brightSPARK Labs
 * www.brightsparklabs.com
 */

package com.brightsparklabs.assam.exception;

/**
 * Signals that invalid data was supplied to a decoder
 *
 * @author brightSPARK Labs
 */
public class DecodeException extends Exception
{
    // -------------------------------------------------------------------------
    // CONSTRUCTION
    // -------------------------------------------------------------------------

    /**
     * Default constructor
     *
     * @param message
     *         reason for the failure
     */
    public DecodeException(String message)
    {
        super(message);
    }
}
