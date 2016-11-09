package com.mycompany.app;

import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@SuppressWarnings("serial")
public class RSSFeedServlet extends HttpServlet {
  private static final Logger LOG = Logger.getLogger(RSSFeedServlet.class.getName());

  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    //Get a Handle to the Memcache Service
    MemcacheService cache = MemcacheServiceFactory.getMemcacheService();
    //Read the request Parameter
    String rssFeedURL = req.getParameter("rssFeedURL");
    String result;


    //Check if Key is present in the Cache.
    //If not present, add it to the Cache with Expiration of 60 seconds
    //else if available, simply return the result from the Cache
    if (!cache.contains(rssFeedURL)) {
      result = "Loaded into cache at " + (new Date());
      cache.put(rssFeedURL, result, Expiration.byDeltaSeconds(60));
      LOG.info(result);
    } else {
      result = "From Cache : " + cache.get(rssFeedURL);
      LOG.info(result);
    }
    resp.getWriter().println(result);
  }
}
