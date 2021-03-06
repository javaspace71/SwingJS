/*
 * Some portions of this file have been modified by Robert Hanson hansonr.at.stolaf.edu 2012-2017
 * for use in SwingJS via transpilation into JavaScript using Java2Script.
 *
 * Copyright (c) 2005, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package jsjava.util.spi;

import java.util.Locale;

/**
 * An abstract class for service providers that
 * provide localized time zone names for the
 * {@link java.util.TimeZone TimeZone} class.
 * The localized time zone names available from the implementations of
 * this class are also the source for the
 * {@link java.text.DateFormatSymbols#getZoneStrings()
 * DateFormatSymbols.getZoneStrings()} method.
 *
 * @since        1.6
 */
public abstract class TimeZoneNameProvider extends LocaleServiceProvider {

    /**
     * Sole constructor.  (For invocation by subclass constructors, typically
     * implicit.)
     */
    protected TimeZoneNameProvider() {
    }

    /**
     * Returns a name for the given time zone ID that's suitable for
     * presentation to the user in the specified locale. The given time
     * zone ID is "GMT" or one of the names defined using "Zone" entries
     * in the "tz database", a public domain time zone database at
     * <a href="ftp://elsie.nci.nih.gov/pub/">ftp://elsie.nci.nih.gov/pub/</a>.
     * The data of this database is contained in a file whose name starts with
     * "tzdata", and the specification of the data format is part of the zic.8
     * man page, which is contained in a file whose name starts with "tzcode".
     * <p>
     * If <code>daylight</code> is true, the method should return a name
     * appropriate for daylight saving time even if the specified time zone
     * has not observed daylight saving time in the past.
     *
     * @param ID a time zone ID string
     * @param daylight if true, return the daylight saving name.
     * @param style either {@link java.util.TimeZone#LONG TimeZone.LONG} or
     *    {@link java.util.TimeZone#SHORT TimeZone.SHORT}
     * @param locale the desired locale
     * @return the human-readable name of the given time zone in the
     *     given locale, or null if it's not available.
     * @exception IllegalArgumentException if <code>style</code> is invalid,
     *     or <code>locale</code> isn't one of the locales returned from
     *     {@link java.util.spi.LocaleServiceProvider#getAvailableLocales()
     *     getAvailableLocales()}.
     * @exception NullPointerException if <code>ID</code> or <code>locale</code>
     *     is null
     * @see java.util.TimeZone#getDisplayName(boolean, int, java.util.Locale)
     */
    public abstract String getDisplayName(String ID, boolean daylight, int style, Locale locale);
}
