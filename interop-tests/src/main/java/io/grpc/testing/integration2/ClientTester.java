/*
 * Copyright (C) 2018 Lightbend Inc. <https://www.lightbend.com>
 */

package io.grpc.testing.integration2;

import io.grpc.ManagedChannel;

import java.io.InputStream;

public interface ClientTester {

    ManagedChannel createChannel();

    void setUp();

    void tearDown() throws Exception;

    void emptyUnary() throws Exception;

    void cacheableUnary();

    void largeUnary() throws Exception;

    void clientCompressedUnary() throws Exception;

    void serverCompressedUnary() throws Exception;

    void clientStreaming() throws Exception;

    void clientCompressedStreaming() throws Exception;

    void serverStreaming() throws Exception;

    void serverCompressedStreaming() throws Exception;
    
    void pingPong() throws Exception;

    void emptyStream() throws Exception;

    void computeEngineCreds(String serviceAccount, String oauthScope) throws Exception;

    void serviceAccountCreds(String jsonKey, InputStream credentialsStream, String authScope) throws Exception;

    void jwtTokenCreds(InputStream serviceAccountJson) throws Exception;

    void oauth2AuthToken(String jsonKey, InputStream credentialsStream, String authScope) throws Exception;

    void perRpcCreds(String jsonKey, InputStream credentialsStream, String oauthScope) throws Exception;

    void customMetadata() throws Exception;

    void statusCodeAndMessage() throws Exception;

    void unimplementedMethod();

    void unimplementedService();

    void cancelAfterBegin() throws Exception;

    void cancelAfterFirstResponse() throws Exception;

    void timeoutOnSleepingServer() throws Exception;


}
