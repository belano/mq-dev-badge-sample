package com.belano.mq.events;

import javax.xml.bind.JAXBException;

public interface Advertisable {

  /**
   * Provides the ability to turn the class into <b>XML</b>. The class must provide <tt>XML annotations</tt>.
   *
   * @return Returns a string in the <b>XML</b> format.
   * @throws JAXBException Thrown when there was an error in trying to convert to XML.
   */
  String toXML() throws JAXBException;
}

