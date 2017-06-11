/*
 * Created by brightSPARK Labs
 * www.brightsparklabs.com
 */

package com.brightsparklabs.assam.data;

import com.brightsparklabs.assam.exception.DecodeException;
import com.brightsparklabs.assam.schema.AsnPrimitiveType;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Interface for modeling ASN.1 data which has been mapped against a schema
 *
 * @author brightSPARK Labs
 */
public interface AsnData
{
    // -------------------------------------------------------------------------
    // PUBLIC METHODS
    // -------------------------------------------------------------------------

    /**
     * Returns the {@link AsnPrimitiveType} of the specified tag
     *
     * @param tag
     *         tag to retrieve the type of
     *
     * @return the {@link AsnPrimitiveType} of the specified tag or  or {@code Optional.absent()} if
     * the tag does not exist in this data.
     */
    Optional<AsnPrimitiveType> getPrimitiveType(String tag);

    /**
     * Returns all tags found in the ASN data as a set of XPath like strings. E.g.
     * "/Document/header/published/date", "/Document/header/lastModified/date",
     * "/Document/body/prefix/text"
     *
     * @return all tags in the data
     */
    ImmutableSet<String> getTags();

    /**
     * Returns the tags from the data which could not be mapped using the schema. E.g.
     * "/Document/body/content/99", "/Document/0[99]/0[1]/0[1]"
     *
     * @return all unmapped tags in the data
     */
    ImmutableSet<String> getUnmappedTags();

    /**
     * Determines whether the specified tag is contained in the data
     *
     * @param tag
     *         tag to check
     *
     * @return {@code true} if the tag is in the data; {@code false} otherwise
     */
    boolean contains(String tag);

    /**
     * Determines whether the data contains any tags matching the supplied regular expression
     *
     * @param regex
     *         regular expression to match tags against
     *
     * @return {@code true} if the tag is in the data; {@code false} otherwise
     */
    boolean contains(Pattern regex);

    /**
     * Gets the data (bytes) associated with the specified tag.
     *
     * <p>Note that because this method is returning unprocessed bytes it will provide results for
     * partially matched tags as well as "raw" tags</p>
     *
     * @param tag
     *         tag associated with the data
     *
     * @return data associated with the specified tag or {@code Optional.absent()} if the tag does
     * not exist
     */
    Optional<byte[]> getBytes(String tag);

    /**
     * Gets the data (bytes) from all tags matching the supplied regular expression.
     *
     * @param regex
     *         regular expression to match tags against
     *
     * @return data associated with the matching tags. Map is of form: {@code tag => data}
     */
    ImmutableMap<String, byte[]> getBytesMatching(Pattern regex);

    /**
     * Gets the data (bytes) associated with the specified tag as a hex string.
     *
     * <p>Note that because this method is returning unprocessed bytes it will provide results for
     * partially matched tags as well as "raw" tags</p>
     *
     * The sting will <b>not</b> be prepended with "0x", as this function is implicitly returning a
     * hex string, using the uppercase alphabet, eg "0A12DF".
     *
     * @param tag
     *         tag associated with the data
     *
     * @return data associated with the specified tag (e.g. {@code "010203"}) or {@code
     * Optional.absent()} if the tag does not exist
     */
    Optional<String> getHexString(String tag);

    /**
     * Gets the data (bytes) from all tags matching the supplied regular expression as hex strings.
     *
     * @param regex
     *         regular expression to match tags against
     *
     * @return data associated with the matching tags. Map is of form: {@code tag => data}
     */
    ImmutableMap<String, String> getHexStringsMatching(Pattern regex);

    /**
     * Gets the data (bytes) associated with the specified tag as a printable string.
     *
     * <p>Note that because this method needs to process the bytes in a way that requires knowing
     * the tags type it will only return results for fully decoded tags</p>
     *
     * @param tag
     *         tag associated with the data
     *
     * @return data associated with the specified tag or {@code Optional.absent()} if the tag does
     * not exist
     *
     * @throws DecodeException
     *         if any errors occur decoding the data associated with the tag
     */
    Optional<String> getPrintableString(String tag) throws DecodeException;

    /**
     * Gets the data (bytes) from all tags matching the supplied regular expression as printable
     * strings.
     *
     * <p>Note that because this method needs to process the bytes in a way that requires knowing
     * the tags type it will only return results for fully decoded tags</p>
     *
     * @param regex
     *         regular expression to match tags against
     *
     * @return data associated with the matching tags. Map is of form: {@code tag => data}
     *
     * @throws DecodeException
     *         if any errors occur decoding the data associated with the tags
     */
    ImmutableMap<String, String> getPrintableStringsMatching(Pattern regex) throws DecodeException;

    /**
     * Gets the data associated with the specified tag as the decoded Java object most appropriate
     * to its type.
     *
     * <p>Note that because this method needs to process the bytes in a way that requires knowing
     * the tags' type it will only return results for fully decoded tags</p>
     *
     * @param tag
     *         tag associated with the data
     * @param <T>
     *         the type of data that will be returned in the {@link Optional}
     *
     * @return data associated with the specified tag or {@code Optional.absent()} if the tag does
     * not exist
     *
     * <p>Note: this method will be deprecated in a future release. Use {@link
     * #getDecodedObject(String, Class)} instead</p>
     *
     * <p>Note: There is no inherit type safety with this method.  The type of object returned will
     * match the tag.  If the caller mis-aligns the return type with the actual type dictated by the
     * schema then on result.get() (from the returned optional) a ClassCastException will likely be
     * thrown.</p>
     *
     * <p>The expected Java object type for each ASN.1 schema type is:</p>
     *
     * <ul>
     *
     * <li><b>BitString</b>: String</li>
     *
     * <li><b>BmpString</b>: String</li>
     *
     * <li><b>Boolean</b>: boolean</li>
     *
     * <li><b>CharacterString</b>: String</li>
     *
     * <li><b>Date</b>: Timestamp</li>
     *
     * <li><b>DateTime</b>: Timestamp</li>
     *
     * <li><b>Duration</b>: String</li>
     *
     * <li><b>EmbeddedPDV</b>: String</li>
     *
     * <li><b>Enumerated</b>: String</li>
     *
     * <li><b>External</b>: String</li>
     *
     * <li><b>GeneralizedTime</b>: Timestamp</li>
     *
     * <li><b>GeneralString</b>: String</li>
     *
     * <li><b>GraphicString</b>: String</li>
     *
     * <li><b>Ia5String</b>: String</li>
     *
     * <li><b>InstanceOf</b>: String</li>
     *
     * <li><b>Integer</b>: BigInteger</li>
     *
     * <li><b>Iri</b>: String</li>
     *
     * <li><b>Iso646String</b>: String</li>
     *
     * <li><b>Null</b>: String</li>
     *
     * <li><b>NumericString</b>: String</li>
     *
     * <li><b>ObjectClassField</b>: String</li>
     *
     * <li><b>OctetString</b>: byte[]</li>
     *
     * <li><b>Oid</b>: String</li>
     *
     * <li><b>OidIri</b>: String</li>
     *
     * <li><b>Prefixed</b>: String</li>
     *
     * <li><b>PrintableString</b>: String</li>
     *
     * <li><b>Real</b>: String</li>
     *
     * <li><b>RelativeIri</b>: String</li>
     *
     * <li><b>RelativeOid</b>: String</li>
     *
     * <li><b>RelativeOidIri</b>: String</li>
     *
     * <li><b>TeletexString</b>: String</li>
     *
     * <li><b>Time</b>: Timestamp</li>
     *
     * <li><b>TimeOfDay</b>: Timestamp</li>
     *
     * <li><b>UniversalString</b>: String</li>
     *
     * <li><b>Utf8String</b>: String</li>
     *
     * <li><b>VideotexString</b>: String</li>
     *
     * <li><b>VisibleString</b>: String</li>
     *
     * </ul>
     *
     * @throws DecodeException
     *         if any errors occur decoding the data associated with the tag
     * @deprecated please use the other overload - {@link #getDecodedObject(String, Class)} for
     * better type safety.
     */
    <T> Optional<T> getDecodedObject(String tag) throws DecodeException;

    /**
     * Gets the data associated with the specified tag.
     *
     * <p>Note that because this method needs to process the bytes in a way that requires knowing
     * the tags' type it will only return results for fully decoded tags</p>
     *
     * @param tag
     *         tag associated with the data
     * @param classOfT
     *         the class of data that is expected for this tag
     * @param <T>
     *         the type of data that will be returned in the {@link Optional}
     *
     * @return data associated with the specified tag or {@code Optional.absent()} if the tag does
     * not exist
     *
     * <p>The expected Java object type for each ASN.1 schema type is:</p>
     *
     * <ul>
     *
     * <li><b>BitString</b>: String</li>
     *
     * <li><b>BmpString</b>: String</li>
     *
     * <li><b>Boolean</b>: boolean</li>
     *
     * <li><b>CharacterString</b>: String</li>
     *
     * <li><b>Date</b>: Timestamp</li>
     *
     * <li><b>DateTime</b>: Timestamp</li>
     *
     * <li><b>Duration</b>: String</li>
     *
     * <li><b>EmbeddedPDV</b>: String</li>
     *
     * <li><b>Enumerated</b>: String</li>
     *
     * <li><b>External</b>: String</li>
     *
     * <li><b>GeneralizedTime</b>: Timestamp</li>
     *
     * <li><b>GeneralString</b>: String</li>
     *
     * <li><b>GraphicString</b>: String</li>
     *
     * <li><b>Ia5String</b>: String</li>
     *
     * <li><b>InstanceOf</b>: String</li>
     *
     * <li><b>Integer</b>: BigInteger</li>
     *
     * <li><b>Iri</b>: String</li>
     *
     * <li><b>Iso646String</b>: String</li>
     *
     * <li><b>Null</b>: String</li>
     *
     * <li><b>NumericString</b>: String</li>
     *
     * <li><b>ObjectClassField</b>: String</li>
     *
     * <li><b>OctetString</b>: byte[]</li>
     *
     * <li><b>Oid</b>: String</li>
     *
     * <li><b>OidIri</b>: String</li>
     *
     * <li><b>Prefixed</b>: String</li>
     *
     * <li><b>PrintableString</b>: String</li>
     *
     * <li><b>Real</b>: String</li>
     *
     * <li><b>RelativeIri</b>: String</li>
     *
     * <li><b>RelativeOid</b>: String</li>
     *
     * <li><b>RelativeOidIri</b>: String</li>
     *
     * <li><b>TeletexString</b>: String</li>
     *
     * <li><b>Time</b>: Timestamp</li>
     *
     * <li><b>TimeOfDay</b>: Timestamp</li>
     *
     * <li><b>UniversalString</b>: String</li>
     *
     * <li><b>Utf8String</b>: String</li>
     *
     * <li><b>VideotexString</b>: String</li>
     *
     * <li><b>VisibleString</b>: String</li>
     *
     * </ul>
     *
     * @throws DecodeException
     *         if any errors occur decoding the data associated with the tag
     * @throws ClassCastException
     *         if the type of the tag does not match T
     */
    <T> Optional<T> getDecodedObject(String tag, Class<T> classOfT)
            throws DecodeException, ClassCastException;

    /**
     * Gets the data from all tags matching the supplied regular expression as the decoded Java
     * object most appropriate to its type.
     *
     * <p>Note that because this method needs to process the bytes in a way that requires knowing
     * the tags type it will only return results for fully decoded tags</p>
     *
     * @param regex
     *         regular expression to match tags against
     *
     * @return data associated with the matching tags. Map is of form: {@code tag => data}
     *
     * @throws DecodeException
     *         if any errors occur decoding the data associated with the tags
     */
    ImmutableMap<String, Object> getDecodedObjectsMatching(Pattern regex) throws DecodeException;
}
