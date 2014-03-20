/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014  Kilian Gärtner
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

package de.meldanor.melvoter.server.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import de.meldanor.melvoter.server.database.entity.Lecture;
import de.meldanor.melvoter.server.database.entity.Vote;

import java.sql.SQLException;
import java.util.logging.Level;

import static de.meldanor.melvoter.server.Core.LOGGER;

public class Database {

    private ConnectionSource connectionSource;

    public Database(String fileName) throws SQLException, ClassNotFoundException {
        this.connectionSource = createConnectionSource("jdbc:h2:" + fileName);
        this.createDatabaseStructure();
    }


    private ConnectionSource createConnectionSource(String url) throws SQLException {
        return new JdbcConnectionSource(url);
    }

    private void createDatabaseStructure() throws SQLException {
        TableUtils.createTableIfNotExists(connectionSource, Lecture.class);
        TableUtils.createTableIfNotExists(connectionSource, Vote.class);
    }

    public void shutdown() {
        try {
            this.connectionSource.close();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e, () -> "");
            e.printStackTrace();
        }
    }

    public Dao<Lecture, Integer> getLectureDAO() {
        try {
            return DaoManager.createDao(connectionSource, Lecture.class);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e, () -> "");
            return null;
        }
    }

    public Dao<Vote, Integer> getVoteDAO() {
        try {
            return DaoManager.createDao(connectionSource, Vote.class);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e, () -> "");
            return null;
        }
    }
}
