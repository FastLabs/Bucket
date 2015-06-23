package stream.xml;


import org.junit.Test;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class XmlStreamTest {

    @Test
    public void testIt() throws FileNotFoundException, XMLStreamException {
        XMLInputFactory xmlInputFactory =  XMLInputFactory.newInstance();
       XMLStreamReader xmlReader =  xmlInputFactory.createXMLStreamReader(new InputStreamReader(XmlStreamTest.class.getClassLoader().getResourceAsStream("./stream/xml/index.xml")));

         while(xmlReader.hasNext()) {
             int x = xmlReader.next();
            String e = getEventTypeString( x );
             System.out.println(e + " -> " + ((x != XMLEvent.CHARACTERS && x != XMLEvent.END_DOCUMENT)?xmlReader.getName().getLocalPart():""));
         }
    }

    public final static String getEventTypeString(int eventType) {
        switch (eventType){
            case XMLEvent.START_ELEMENT:
                return "START_ELEMENT";
            case XMLEvent.END_ELEMENT:
                return "END_ELEMENT";
            case XMLEvent.PROCESSING_INSTRUCTION:
                return "PROCESSING_INSTRUCTION";
            case XMLEvent.CHARACTERS:
                return "CHARACTERS";
            case XMLEvent.COMMENT:
                return "COMMENT";
            case XMLEvent.START_DOCUMENT:
                return "START_DOCUMENT";
            case XMLEvent.END_DOCUMENT:
                return "END_DOCUMENT";
            case XMLEvent.ENTITY_REFERENCE:
                return "ENTITY_REFERENCE";
            case XMLEvent.ATTRIBUTE:
                return "ATTRIBUTE";
            case XMLEvent.DTD:
                return "DTD";
            case XMLEvent.CDATA:
                return "CDATA";
            case XMLEvent.SPACE:
                return "SPACE";
        }
        return "UNKNOWN_EVENT_TYPE , " + eventType;
    }
}
