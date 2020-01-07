/*
 * Copyright 2020 IBM All Rights Reserved.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package org.hyperledger.fabric.shim;

import org.hyperledger.fabric.shim.chaincode.EmptyChaincode;
import org.junit.Rule;
import org.junit.contrib.java.lang.system.EnvironmentVariables;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class ChaincodeServerImplTest {
    @Rule
    public final EnvironmentVariables environmentVariables = new EnvironmentVariables();

    @BeforeEach
    void setEnv() {
        environmentVariables.set("CORE_CHAINCODE_ID_NAME", "mycc");
        environmentVariables.set("PORT_CHAINCODE_SERVER", "9999");
        environmentVariables.set("CORE_PEER_ADDRESS", "localhost:7052");
        environmentVariables.set("CORE_PEER_TLS_ENABLED", "false");
        environmentVariables.set("CORE_PEER_TLS_ROOTCERT_FILE", "src/test/resources/ca.crt");
        environmentVariables.set("CORE_TLS_CLIENT_KEY_PATH", "src/test/resources/client.key.enc");
        environmentVariables.set("CORE_TLS_CLIENT_CERT_PATH", "src/test/resources/client.crt.enc");
    }

    @AfterEach
    void clearEnv() {
        environmentVariables.clear("CORE_CHAINCODE_ID_NAME");
        environmentVariables.clear("PORT_CHAINCODE_SERVER");
        environmentVariables.clear("CORE_PEER_ADDRESS");
        environmentVariables.clear("CORE_PEER_TLS_ENABLED");
        environmentVariables.clear("CORE_PEER_TLS_ROOTCERT_FILE");
        environmentVariables.clear("CORE_TLS_CLIENT_KEY_PATH");
        environmentVariables.clear("CORE_TLS_CLIENT_CERT_PATH");
    }

    @Test
    void init() {
        try {
            final ChaincodeBase chaincodeBase = new EmptyChaincode();
            ChaincodeServerImpl chaincodeServer = new ChaincodeServerImpl(chaincodeBase);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void initEnvNotSet() {
        clearEnv();
        try {
            final ChaincodeBase chaincodeBase = new EmptyChaincode();
            ChaincodeServerImpl chaincodeServer = new ChaincodeServerImpl(chaincodeBase);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void startAndStop() {
        try {
            final ChaincodeBase chaincodeBase = new EmptyChaincode();
            ChaincodeServer chaincodeServer = new ChaincodeServerImpl(chaincodeBase);
            new Thread(() -> {
                try {
                    chaincodeServer.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            ).start();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            chaincodeServer.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}