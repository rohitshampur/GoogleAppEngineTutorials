package com.rohit.uttara.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.cmd.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * WARNING: This generated code is intended as a sample or starting point for using a
 * Google Cloud Endpoints RESTful API with an Objectify entity. It provides no data access
 * restrictions and no data validation.
 * <p/>
 * DO NOT deploy this code unchanged as part of a real application to real users.
 */
@Api(
        name = "loginBeanApi",
        version = "v1",
        resource = "loginBean",
        namespace = @ApiNamespace(
                ownerDomain = "backend.uttara.rohit.com",
                ownerName = "backend.uttara.rohit.com",
                packagePath = ""))
public class LoginBeanEndpoint {

    private static final Logger logger = Logger.getLogger(LoginBeanEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;


    /**
     * Returns the {@link LoginBean} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code LoginBean} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "loginBean/{id}",
            httpMethod = ApiMethod.HttpMethod.POST)
    public LoginBean get(@Named("id") long id) throws NotFoundException {
        logger.info("Getting LoginBean with ID: " + id);
        LoginBean loginBean = ofy().load().type(LoginBean.class).id(id).now();
        if (loginBean == null) {
            throw new NotFoundException("Could not find LoginBean with ID: " + id);
        }
        return loginBean;
    }

    /**
     * Inserts a new {@code LoginBean}.
     */
    @ApiMethod(
            name = "register",
            path = "loginBean",
            httpMethod = ApiMethod.HttpMethod.POST)
    public LoginBean register(@Named("username") String username,@Named("password") String password ) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that loginBean.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        LoginBean bean = new LoginBean();
        bean.setUsername(username);
        bean.setPassword(password);
        ofy().save().entity(bean).now();
        logger.info("Created LoginBean.");

        return ofy().load().entity(bean).now();
    }

    /**
     * Updates an existing {@code LoginBean}.
     *
     * @param id        the ID of the entity to be updated
     * @param loginBean the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code LoginBean}
     */
    @ApiMethod(
            name = "update",
            path = "loginBean/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public LoginBean update(@Named("id") long id, LoginBean loginBean) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(loginBean).now();
        logger.info("Updated LoginBean: " + loginBean);
        return ofy().load().entity(loginBean).now();
    }

    /**
     * Deletes the specified {@code LoginBean}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code LoginBean}
     */
    @ApiMethod(
            name = "remove",
            path = "loginBean/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(LoginBean.class).id(id).now();
        logger.info("Deleted LoginBean with ID: " + id);
    }

    /**
     * List all entities.
     *
     * @param cursor used for pagination to determine which page to return
     * @param limit  the maximum number of entries to return
     * @return a response that encapsulates the result list and the next page token/cursor
     */
    @ApiMethod(
            name = "list",
            path = "loginBean",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<LoginBean> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<LoginBean> query = ofy().load().type(LoginBean.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<LoginBean> queryIterator = query.iterator();
        List<LoginBean> loginBeanList = new ArrayList<LoginBean>(limit);
        while (queryIterator.hasNext()) {
            loginBeanList.add(queryIterator.next());
        }
        return CollectionResponse.<LoginBean>builder().setItems(loginBeanList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(long id) throws NotFoundException {
        try {
            ofy().load().type(LoginBean.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find LoginBean with ID: " + id);
        }
    }
}