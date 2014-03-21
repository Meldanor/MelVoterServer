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

package de.meldanor.melvoter.server;

import de.meldanor.melvoter.server.database.Database;
import de.meldanor.melvoter.server.rest.WebService;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Core {

    public static final Logger LOGGER;
    public static Database DB;

    static {
        LOGGER = Logger.getLogger(Core.class.getName());
    }

    public static void main(String[] args) {
        LOGGER.info("Starting the webservice at localhost:8123");

        try {
            DB = new Database("db.h2");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Can't create database connection", e);
        }
        WebService service = new WebService("http://localhost", 8123);
        try {
            service.start();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Can't start the HTTP server! This server is shutting down!", e);
            return;
        }

        LOGGER.info("WebService ist started!");
        LOGGER.info("Enter 'shutdown' to shutdown the server safely");

        Scanner scanner = new Scanner(System.in);
        String line;
        boolean isRunning = true;
        while (isRunning) {
            line = scanner.nextLine().toLowerCase();
            if (line.equals("shutdown")) {
                isRunning = false;
            }

        }

        LOGGER.info("Server is shutting down...");
        scanner.close();
        service.stop();
    }
}
