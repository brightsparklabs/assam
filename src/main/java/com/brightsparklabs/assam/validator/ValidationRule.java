/*
 * Created by brightSPARK Labs
 * www.brightsparklabs.com
 */
package com.brightsparklabs.assam.validator;

import com.brightsparklabs.assam.data.AsnData;
import com.brightsparklabs.assam.exception.DecodeException;
import com.google.common.collect.ImmutableSet;

/**
 * Represents a rule to validate {@link AsnData} against.
 *
 * @author brightSPARK Labs
 */
public interface ValidationRule
{
    // -------------------------------------------------------------------------
    // CONSTANTS
    // -------------------------------------------------------------------------

    /** null instance */
    ValidationRule.Null NULL = new ValidationRule.Null();

    // -------------------------------------------------------------------------
    // PUBLIC METHODS
    // -------------------------------------------------------------------------

    /**
     * Validates the specified tag in the data
     *
     * @param tag
     *         tag to validate in the data
     * @param asnData
     *         data to validate
     *
     * @return the results of validation
     */
    ImmutableSet<ValidationFailure> validate(String tag, AsnData asnData) throws DecodeException;

    // -------------------------------------------------------------------------
    // INTERNAL CLASS: NULL
    // -------------------------------------------------------------------------

    /**
     * NULL instance of {@link ValidationRule}
     *
     * @author brightSPARK Labs
     */
    class Null implements ValidationRule
    {
        // ---------------------------------------------------------------------
        // CONSTRUCTION
        // ---------------------------------------------------------------------

        /**
         * Default constructor. This is private, use {@link ValidationRule#NULL} to obtain an
         * instance
         */
        private Null()
        {
        }

        // ---------------------------------------------------------------------
        // IMPLEMENTATION: ValidationRule
        // ---------------------------------------------------------------------

        @Override
        public ImmutableSet<ValidationFailure> validate(String tag, AsnData asnData)
        {
            return ImmutableSet.of();
        }
    }
}
