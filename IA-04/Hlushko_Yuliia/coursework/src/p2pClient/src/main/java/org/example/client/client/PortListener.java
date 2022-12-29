package org.example.client.client;

import org.example.network.ConnectionListener;

public interface PortListener {

    void startListen(int port, ConnectionListener connectionListener);
}
