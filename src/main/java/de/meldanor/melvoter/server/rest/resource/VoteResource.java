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

package de.meldanor.melvoter.server.rest.resource;

import static de.meldanor.melvoter.server.Core.DB;
import static de.meldanor.melvoter.server.Core.LOGGER;

import java.util.List;
import java.util.logging.Level;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import de.meldanor.melvoter.server.database.entity.Lecture;
import de.meldanor.melvoter.server.database.entity.Vote;

@Path("vote")
public class VoteResource {

    @Path("create")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createVote(VoteJson vote) {

        try {
            List<Lecture> lectures = DB.getLectureDAO().queryForEq("title", vote.getLectureTitle());
            if (lectures.isEmpty()) {
                return Response.status(Status.BAD_REQUEST).entity("No lecture found").build();
            }

            Lecture lec = lectures.get(0);

            Vote voteEntity = new Vote(vote.getVote(), vote.getMaxVote(), lec);
            DB.getVoteDAO().create(voteEntity);
            return Response.ok().build();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e, null);
            return Response.serverError().entity("SQL error").build();
        }
    }
    
    @Path("getForLecture/{title}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getVotesForLecture(@PathParam("title") String lectureTitle) {
    
        try {
//            DB.getVoteDAO().queryF

            return Response.ok().build();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e, null);
            return Response.serverError().entity("SQL error").build();
        }
    }

    private class VoteJson {

        private String lectureTitle;
        private int vote;
        private int maxVote;

        public VoteJson() {
            // TODO Auto-generated constructor stub
        }

        public VoteJson(Vote entity) {
            this.lectureTitle = entity.getLecture().getTitle();
            this.vote = entity.getVoted();
            this.maxVote = entity.getMaxVotes();
        }

        public String getLectureTitle() {
            return lectureTitle;
        }

        public int getMaxVote() {
            return maxVote;
        }

        public int getVote() {
            return vote;
        }
    }
}
