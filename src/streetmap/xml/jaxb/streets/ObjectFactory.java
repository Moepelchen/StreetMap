
package streetmap.xml.jaxb.streets;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the streetmap.xml.jaxb.streets package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _StreetTemplates_QNAME = new QName("", "StreetTemplates");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: streetmap.xml.jaxb.streets
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link StreetTemplates }
     * 
     */
    public StreetTemplates createStreetTemplates() {
        return new StreetTemplates();
    }

    /**
     * Create an instance of {@link LaneTemplate }
     * 
     */
    public LaneTemplate createLaneTemplate() {
        return new LaneTemplate();
    }

    /**
     * Create an instance of {@link StreetTemplate }
     * 
     */
    public StreetTemplate createStreetTemplate() {
        return new StreetTemplate();
    }

    /**
     * Create an instance of {@link LaneTemplates }
     * 
     */
    public LaneTemplates createLaneTemplates() {
        return new LaneTemplates();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StreetTemplates }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "StreetTemplates")
    public JAXBElement<StreetTemplates> createStreetTemplates(StreetTemplates value) {
        return new JAXBElement<StreetTemplates>(_StreetTemplates_QNAME, StreetTemplates.class, null, value);
    }

}
