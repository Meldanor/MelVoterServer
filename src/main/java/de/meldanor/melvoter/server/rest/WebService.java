/* The MIT License (MIT)
 * 
 * Copyright (c) 2014 Kilian Gärtner
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package de.meldanor.melvoter.server.rest;

import java.io.IOException;
import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class WebService {

    private HttpServer httpServer;

    public WebService(String webFolder, String baseURI, int port) {

        URI restURI = UriBuilder.fromUri(baseURI).port(port).path("api").build();
        ResourceConfig config = new MelVoterApplication();
        this.httpServer = GrizzlyHttpServerFactory.createHttpServer(restURI, config);

        StaticHttpHandler fileHandler = new StaticHttpHandler(webFolder);
        fileHandler.setFileCacheEnabled(false); // TODO: Remove this at runtime
        this.httpServer.getServerConfiguration().addHttpHandler(fileHandler);

    }

    public void start() throws IOException {
        this.httpServer.start();
    }

    public void stop() {
        this.httpServer.shutdown();
    }
}
