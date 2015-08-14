/*
 * Created by brightSPARK Labs
 * www.brightsparklabs.com
 */

package com.brightsparklabs.assam.validator;

import com.brightsparklabs.assam.data.AsnData;

/**
 * Used to validate {@link AsnData} against its associated schema or a custom validation rule.
 *
 * @author brightSPARK Labs
 */
public interface Validator
{
    // -------------------------------------------------------------------------
    // PUBLIC METHODS
    // -------------------------------------------------------------------------

    /**
     * Validates the supplied data using the rules in this validator
     *
     * @param asnData
     *         data to validate
     *
     * @return the results from validating the data
     */
    ValidationResult validate(AsnData asnData);
}
