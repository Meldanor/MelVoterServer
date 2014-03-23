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

package de.meldanor.melvoter.server.rest.resource;

import com.j256.ormlite.dao.Dao;
import de.meldanor.melvoter.server.database.entity.Lecture;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

import static de.meldanor.melvoter.server.Core.DB;
import static de.meldanor.melvoter.server.Core.LOGGER;

@Path("lecture")
public class LectureResource {

    @Path("create")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createLecture(String name) {
        try {
            if (lectureExists(name))
                return Response.status(Response.Status.BAD_REQUEST).entity("Duplicate lecture").build();

            DB.getLectureDAO().create(new Lecture(name));
            return Response.ok().build();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Create Lecture", e);
            return Response.serverError().entity("SQL Exception").build();
        }
    }

    private boolean lectureExists(String name) throws SQLException {
        Dao<Lecture, Integer> lectureDAO = DB.getLectureDAO();
        return !lectureDAO.queryForEq("title", name).isEmpty();
    }

    @Path("exists")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response exists(String lectureTitle) {
        try {
            if (lectureExists(lectureTitle))
                return Response.ok().build();
            else
                return Response.noContent().build();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Create Lecture", e);
            return Response.serverError().entity("SQL Exception").build();
        }
    }

    @Path("getAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        try {
            // Search for all lectures and map them in a sorted list
            List<String> collect = DB.getLectureDAO().queryForAll().stream().map(Lecture::getTitle).sorted().collect(Collectors.toList());

            return Response.ok(collect).build();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Get All lectures", e);
            return Response.serverError().entity("SQL Exception").build();
        }
    }

}
