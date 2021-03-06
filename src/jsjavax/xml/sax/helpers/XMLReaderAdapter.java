// XMLReaderAdapter.java - adapt an SAX2 XMLReader to a SAX1 Parser
// http://www.saxproject.org
// Written by David Megginson
// NO WARRANTY!  This class is in the public domain.
// $Id: XMLReaderAdapter.java,v 1.9 2004/04/26 17:34:35 dmegginson Exp $

package jsjavax.xml.sax.helpers;

import java.io.IOException;
import java.util.Locale;

import jsjavax.xml.sax.Parser;	// deprecated
import jsjavax.xml.sax.Locator;
import jsjavax.xml.sax.InputSource;
import jsjavax.xml.sax.AttributeList; // deprecated
import jsjavax.xml.sax.EntityResolver;
import jsjavax.xml.sax.DTDHandler;
import jsjavax.xml.sax.DocumentHandler; // deprecated
import jsjavax.xml.sax.ErrorHandler;
import jsjavax.xml.sax.SAXException;

import jsjavax.xml.sax.XMLReader;
import jsjavax.xml.sax.Attributes;
import jsjavax.xml.sax.ContentHandler;
import jsjavax.xml.sax.SAXNotSupportedException;


/**
 * Adapt a SAX2 XMLReader as a SAX1 Parser.
 *
 * <blockquote>
 * <em>This module, both source code and documentation, is in the
 * Public Domain, and comes with <strong>NO WARRANTY</strong>.</em>
 * See <a href='http://www.saxproject.org'>http://www.saxproject.org</a>
 * for further information.
 * </blockquote>
 *
 * <p>This class wraps a SAX2 {@link jsjavax.xml.sax.XMLReader XMLReader}
 * and makes it act as a SAX1 {@link jsjavax.xml.sax.Parser Parser}.  The XMLReader 
 * must support a true value for the 
 * http://xml.org/sax/features/namespace-prefixes property or parsing will fail
 * with a {@link jsjavax.xml.sax.SAXException SAXException}; if the XMLReader 
 * supports a false value for the http://xml.org/sax/features/namespaces 
 * property, that will also be used to improve efficiency.</p>
 *
 * @since SAX 2.0
 * @author David Megginson
 * @version 2.0.1 (sax2r2)
 * @see jsjavax.xml.sax.Parser
 * @see jsjavax.xml.sax.XMLReader
 */
public class XMLReaderAdapter implements Parser, ContentHandler
{


    ////////////////////////////////////////////////////////////////////
    // Constructor.
    ////////////////////////////////////////////////////////////////////


    /**
     * Create a new adapter.
     *
     * <p>Use the "jsjavax.xml.sax.driver" property to locate the SAX2
     * driver to embed.</p>
     *
     * @exception jsjavax.xml.sax.SAXException If the embedded driver
     *            cannot be instantiated or if the
     *            jsjavax.xml.sax.driver property is not specified.
     */
    public XMLReaderAdapter ()
      throws SAXException
    {
	setup(XMLReaderFactory.createXMLReader());
    }


    /**
     * Create a new adapter.
     *
     * <p>Create a new adapter, wrapped around a SAX2 XMLReader.
     * The adapter will make the XMLReader act like a SAX1
     * Parser.</p>
     *
     * @param xmlReader The SAX2 XMLReader to wrap.
     * @exception java.lang.NullPointerException If the argument is null.
     */
    public XMLReaderAdapter (XMLReader xmlReader)
    {
	setup(xmlReader);
    }



    /**
     * Internal setup.
     *
     * @param xmlReader The embedded XMLReader.
     */
    private void setup (XMLReader xmlReader)
    {
	if (xmlReader == null) {
	    throw new NullPointerException("XMLReader must not be null");
	}
	this.xmlReader = xmlReader;
	qAtts = new AttributesAdapter();
    }



    ////////////////////////////////////////////////////////////////////
    // Implementation of jsjavax.xml.sax.Parser.
    ////////////////////////////////////////////////////////////////////


    /**
     * Set the locale for error reporting.
     *
     * <p>This is not supported in SAX2, and will always throw
     * an exception.</p>
     *
     * @param locale the locale for error reporting.
     * @see jsjavax.xml.sax.Parser#setLocale
     * @exception jsjavax.xml.sax.SAXException Thrown unless overridden.
     */
    @Override
		public void setLocale (Locale locale)
	throws SAXException
    {
	throw new SAXNotSupportedException("setLocale not supported");
    }


    /**
     * Register the entity resolver.
     *
     * @param resolver The new resolver.
     * @see jsjavax.xml.sax.Parser#setEntityResolver
     */
    @Override
		public void setEntityResolver (EntityResolver resolver)
    {
	xmlReader.setEntityResolver(resolver);
    }


    /**
     * Register the DTD event handler.
     *
     * @param handler The new DTD event handler.
     * @see jsjavax.xml.sax.Parser#setDTDHandler
     */
    @Override
		public void setDTDHandler (DTDHandler handler)
    {
	xmlReader.setDTDHandler(handler);
    }


    /**
     * Register the SAX1 document event handler.
     *
     * <p>Note that the SAX1 document handler has no Namespace
     * support.</p>
     *
     * @param handler The new SAX1 document event handler.
     * @see jsjavax.xml.sax.Parser#setDocumentHandler
     */
    @Override
		public void setDocumentHandler (DocumentHandler handler)
    {
	documentHandler = handler;
    }


    /**
     * Register the error event handler.
     *
     * @param handler The new error event handler.
     * @see jsjavax.xml.sax.Parser#setErrorHandler
     */
    @Override
		public void setErrorHandler (ErrorHandler handler)
    {
	xmlReader.setErrorHandler(handler);
    }


    /**
     * Parse the document.
     *
     * <p>This method will throw an exception if the embedded
     * XMLReader does not support the 
     * http://xml.org/sax/features/namespace-prefixes property.</p>
     *
     * @param systemId The absolute URL of the document.
     * @exception java.io.IOException If there is a problem reading
     *            the raw content of the document.
     * @exception jsjavax.xml.sax.SAXException If there is a problem
     *            processing the document.
     * @see #parse(jsjavax.xml.sax.InputSource)
     * @see jsjavax.xml.sax.Parser#parse(java.lang.String)
     */
    @Override
		public void parse (String systemId)
	throws IOException, SAXException
    {
	parse(new InputSource(systemId));
    }


    /**
     * Parse the document.
     *
     * <p>This method will throw an exception if the embedded
     * XMLReader does not support the 
     * http://xml.org/sax/features/namespace-prefixes property.</p>
     *
     * @param input An input source for the document.
     * @exception java.io.IOException If there is a problem reading
     *            the raw content of the document.
     * @exception jsjavax.xml.sax.SAXException If there is a problem
     *            processing the document.
     * @see #parse(java.lang.String)
     * @see jsjavax.xml.sax.Parser#parse(jsjavax.xml.sax.InputSource)
     */
    @Override
		public void parse (InputSource input)
	throws IOException, SAXException
    {
	setupXMLReader();
	xmlReader.parse(input);
    }


    /**
     * Set up the XML reader.
     */
    private void setupXMLReader ()
	throws SAXException
    {
	xmlReader.setFeature("http://xml.org/sax/features/namespace-prefixes", true);
	try {
	    xmlReader.setFeature("http://xml.org/sax/features/namespaces",
	                         false);
	} catch (SAXException e) {
	    // NO OP: it's just extra information, and we can ignore it
	}
	xmlReader.setContentHandler(this);
    }



    ////////////////////////////////////////////////////////////////////
    // Implementation of jsjavax.xml.sax.ContentHandler.
    ////////////////////////////////////////////////////////////////////


    /**
     * Set a document locator.
     *
     * @param locator The document locator.
     * @see jsjavax.xml.sax.ContentHandler#setDocumentLocator
     */
    @Override
		public void setDocumentLocator (Locator locator)
    {
	if (documentHandler != null)
	    documentHandler.setDocumentLocator(locator);
    }


    /**
     * Start document event.
     *
     * @exception jsjavax.xml.sax.SAXException The client may raise a
     *            processing exception.
     * @see jsjavax.xml.sax.ContentHandler#startDocument
     */
    @Override
		public void startDocument ()
	throws SAXException
    {
	if (documentHandler != null)
	    documentHandler.startDocument();
    }


    /**
     * End document event.
     *
     * @exception jsjavax.xml.sax.SAXException The client may raise a
     *            processing exception.
     * @see jsjavax.xml.sax.ContentHandler#endDocument
     */
    @Override
		public void endDocument ()
	throws SAXException
    {
	if (documentHandler != null)
	    documentHandler.endDocument();
    }


    /**
     * Adapt a SAX2 start prefix mapping event.
     *
     * @param prefix The prefix being mapped.
     * @param uri The Namespace URI being mapped to.
     * @see jsjavax.xml.sax.ContentHandler#startPrefixMapping
     */
    @Override
		public void startPrefixMapping (String prefix, String uri)
    {
    }


    /**
     * Adapt a SAX2 end prefix mapping event.
     *
     * @param prefix The prefix being mapped.
     * @see jsjavax.xml.sax.ContentHandler#endPrefixMapping
     */
    @Override
		public void endPrefixMapping (String prefix)
    {
    }


    /**
     * Adapt a SAX2 start element event.
     *
     * @param uri The Namespace URI.
     * @param localName The Namespace local name.
     * @param qName The qualified (prefixed) name.
     * @param atts The SAX2 attributes.
     * @exception jsjavax.xml.sax.SAXException The client may raise a
     *            processing exception.
     * @see jsjavax.xml.sax.ContentHandler#endDocument
     */
    @Override
		public void startElement (String uri, String localName,
			      String qName, Attributes atts)
	throws SAXException
    {
	if (documentHandler != null) {
	    qAtts.setAttributes(atts);
	    documentHandler.startElement(qName, qAtts);
	}
    }


    /**
     * Adapt a SAX2 end element event.
     *
     * @param uri The Namespace URI.
     * @param localName The Namespace local name.
     * @param qName The qualified (prefixed) name.
     * @exception jsjavax.xml.sax.SAXException The client may raise a
     *            processing exception.
     * @see jsjavax.xml.sax.ContentHandler#endElement
     */
    @Override
		public void endElement (String uri, String localName,
			    String qName)
	throws SAXException
    {
	if (documentHandler != null)
	    documentHandler.endElement(qName);
    }


    /**
     * Adapt a SAX2 characters event.
     *
     * @param ch An array of characters.
     * @param start The starting position in the array.
     * @param length The number of characters to use.
     * @exception jsjavax.xml.sax.SAXException The client may raise a
     *            processing exception.
     * @see jsjavax.xml.sax.ContentHandler#characters
     */
    @Override
		public void characters (char ch[], int start, int length)
	throws SAXException
    {
	if (documentHandler != null)
	    documentHandler.characters(ch, start, length);
    }


    /**
     * Adapt a SAX2 ignorable whitespace event.
     *
     * @param ch An array of characters.
     * @param start The starting position in the array.
     * @param length The number of characters to use.
     * @exception jsjavax.xml.sax.SAXException The client may raise a
     *            processing exception.
     * @see jsjavax.xml.sax.ContentHandler#ignorableWhitespace
     */
    @Override
		public void ignorableWhitespace (char ch[], int start, int length)
	throws SAXException
    {
	if (documentHandler != null)
	    documentHandler.ignorableWhitespace(ch, start, length);
    }


    /**
     * Adapt a SAX2 processing instruction event.
     *
     * @param target The processing instruction target.
     * @param data The remainder of the processing instruction
     * @exception jsjavax.xml.sax.SAXException The client may raise a
     *            processing exception.
     * @see jsjavax.xml.sax.ContentHandler#processingInstruction
     */
    @Override
		public void processingInstruction (String target, String data)
	throws SAXException
    {
	if (documentHandler != null)
	    documentHandler.processingInstruction(target, data);
    }


    /**
     * Adapt a SAX2 skipped entity event.
     *
     * @param name The name of the skipped entity.
     * @see jsjavax.xml.sax.ContentHandler#skippedEntity
     * @exception jsjavax.xml.sax.SAXException Throwable by subclasses.
     */
    @Override
		public void skippedEntity (String name)
	throws SAXException
    {
    }



    ////////////////////////////////////////////////////////////////////
    // Internal state.
    ////////////////////////////////////////////////////////////////////

    XMLReader xmlReader;
    DocumentHandler documentHandler;
    AttributesAdapter qAtts;



    ////////////////////////////////////////////////////////////////////
    // Internal class.
    ////////////////////////////////////////////////////////////////////


    /**
     * Internal class to wrap a SAX2 Attributes object for SAX1.
     */
    final class AttributesAdapter implements AttributeList
    {
	AttributesAdapter ()
	{
	}


	/**
	 * Set the embedded Attributes object.
	 *
	 * @param The embedded SAX2 Attributes.
	 */ 
	void setAttributes (Attributes attributes)
	{
	    this.attributes = attributes;
	}


	/**
	 * Return the number of attributes.
	 *
	 * @return The length of the attribute list.
	 * @see jsjavax.xml.sax.AttributeList#getLength
	 */
	@Override
	public int getLength ()
	{
	    return attributes.getLength();
	}


	/**
	 * Return the qualified (prefixed) name of an attribute by position.
	 *
	 * @return The qualified name.
	 * @see jsjavax.xml.sax.AttributeList#getName
	 */
	@Override
	public String getName (int i)
	{
	    return attributes.getQName(i);
	}


	/**
	 * Return the type of an attribute by position.
	 *
	 * @return The type.
	 * @see jsjavax.xml.sax.AttributeList#getType(int)
	 */
	@Override
	public String getType (int i)
	{
	    return attributes.getType(i);
	}


	/**
	 * Return the value of an attribute by position.
	 *
	 * @return The value.
	 * @see jsjavax.xml.sax.AttributeList#getValue(int)
	 */
	@Override
	public String getValue (int i)
	{
	    return attributes.getValue(i);
	}


	/**
	 * Return the type of an attribute by qualified (prefixed) name.
	 *
	 * @return The type.
	 * @see jsjavax.xml.sax.AttributeList#getType(java.lang.String)
	 */
	@Override
	public String getType (String qName)
	{
	    return attributes.getType(qName);
	}


	/**
	 * Return the value of an attribute by qualified (prefixed) name.
	 *
	 * @return The value.
	 * @see jsjavax.xml.sax.AttributeList#getValue(java.lang.String)
	 */
	@Override
	public String getValue (String qName)
	{
	    return attributes.getValue(qName);
	}

	private Attributes attributes;
    }

}

// end of XMLReaderAdapter.java
