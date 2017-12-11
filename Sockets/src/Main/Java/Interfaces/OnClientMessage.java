package Interfaces;

import Enums.MessageType;

/**
 * Created by Gebruiker on 11-12-2017.
 */
public interface OnClientMessage {

    void OnclientMessage(MessageType messageType, String message);
}
