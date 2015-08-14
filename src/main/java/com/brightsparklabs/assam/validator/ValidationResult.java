/*
 * Created by brightSPARK Labs
 * www.brightsparklabs.com
 */
package com.brightsparklabs.assam.validator;

import com.brightsparklabs.assam.data.AsnData;
import com.google.common.collect.ImmutableSet;

/**
 * Contains the results from running a {@link Validator} over {@link AsnData}.
 *
 * @param <T>
 *         the type of validation failures contained in this result
 *
 * @author brightSPARK Labs
 */

public interface ValidationResult<T extends ValidationFailure>
{
    // -------------------------------------------------------------------------
    // PUBLIC METHODS
    // -------------------------------------------------------------------------

    /**
     * Determines whether failures occurred during validation
     *
     * @return {@code true} if failures occurred during validation; {@code false} otherwise
     */
    boolean hasFailures();

    /**
     * Determines whether failures occurred while validating the specified tag
     *
     * @param tag
     *         the tag of interest (e.g. "/Document/header/published/date")
     *
     * @return {@code true} if failures occurred while validating the specified tag; {@code false}
     * otherwise
     */
    boolean hasFailures(String tag);

    /**
     * Returns all failures that occurred during validation
     *
     * @return all failures that occurred during validation
     */
    ImmutableSet<T> getFailures();

    /**
     * Returns all failures that occurred validating the specified tag
     *
     * @param tag
     *         the tag of interest (e.g. "/Document/header/published/date")
     *
     * @return all failures that occurred validating the specified tag
     */
    ImmutableSet<T> getFailures(String tag);
}
