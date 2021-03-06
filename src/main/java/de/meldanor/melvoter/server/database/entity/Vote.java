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

package de.meldanor.melvoter.server.database.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "vote")
public class Vote {

    @DatabaseField(generatedId =  true)
    private int id;

    @DatabaseField
    private int voted;
    @DatabaseField
    private int maxVotes;
    @DatabaseField(foreign =  true)
    private Lecture lecture;

    public Vote() {
        // Empty constructor for ORMLite
    }

    public Vote(int voted, int maxVoted, Lecture lecture) {
        this.lecture = lecture;
        this.maxVotes = maxVoted;
        this.voted = voted;
    }

    public int getMaxVotes() {
        return maxVotes;
    }

    public int getVoted() {
        return voted;
    }

    public Lecture getLecture() {
        return lecture;
    }
}
