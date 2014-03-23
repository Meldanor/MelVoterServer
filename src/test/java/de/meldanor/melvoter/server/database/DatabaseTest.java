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
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import de.meldanor.melvoter.server.database.entity.Lecture;
import de.meldanor.melvoter.server.database.entity.Vote;

import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class DatabaseTest {

    private static Database db;

    @BeforeClass
    public static void beforeClass() throws Exception {
        db = new Database("target/database");

        clearDatabase(db.getLectureDAO().getConnectionSource());
    }

    public static void clearDatabase(ConnectionSource source) throws Exception {
        TableUtils.dropTable(source, Lecture.class, true);
        TableUtils.dropTable(source, Vote.class, true);
        TableUtils.createTable(source, Lecture.class);
        TableUtils.createTable(source, Vote.class);
    }

    @Test
    public void createLecture() throws SQLException, ClassNotFoundException {
        Dao<Lecture, Integer> lectureDAO = db.getLectureDAO();
        lectureDAO.create(new Lecture("TestLecture"));

        assertEquals(1, lectureDAO.queryForEq("title", "TestLecture").size());
        assertNotEquals(1, lectureDAO.queryForEq("title", "NotExistingTitle").size());
    }

    @Test
    public void createVote() throws SQLException {
        Dao<Lecture, Integer> lectureDAO = db.getLectureDAO();
        Dao<Vote, Integer> voteDAO = db.getVoteDAO();

        Lecture lecture = lectureDAO.queryForEq("title", "TestLecture").get(0);
        Vote vote = new Vote(1, 2, lecture);

        voteDAO.create(vote);
        
        List<Vote> queryForEq = voteDAO.queryForEq("lecture_id", lectureDAO.queryForEq("title", "TestLecture").get(0));
        System.out.println(queryForEq);
    }
}