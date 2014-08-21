
package streetmap.xml.jaxb.config;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the streetmap.xml.jaxb.config package. 
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

    private final static QName _JAXBConfig_QNAME = new QName("", "JAXBConfig");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: streetmap.xml.jaxb.config
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link JAXBConfig }
     * 
     */
    public JAXBConfig createJAXBConfig() {
        return new JAXBConfig();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link JAXBConfig }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "JAXBConfig")
    public JAXBElement<JAXBConfig> createJAXBConfig(JAXBConfig value) {
        return new JAXBElement<JAXBConfig>(_JAXBConfig_QNAME, JAXBConfig.class, null, value);
    }

}
