package Interfaces;

import Enums.MessageType;
import com.sun.xml.internal.ws.wsdl.writer.document.Message;

/**
 * Created by Gebruiker on 11-12-2017.
 */
public interface OnServerMessage {

    void OnserverMessage(MessageType messageType, String message);
}
