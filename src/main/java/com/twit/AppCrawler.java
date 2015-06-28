/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twit;

import com.twit.controller.CidadeController;
import com.twit.entity.Cidade;
import java.util.List;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

/**
 *
 * @author root
 */
public class AppCrawler {

    public static void main(String[] args) throws TwitterException {

        TwitterFactory tf = new TwitterFactory();
        Twitter twitter = tf.getInstance();
        
        CidadeController cidadeController = new CidadeController(new Cidade());
        List<Cidade> cidades = cidadeController.findEntities();
        
        String searchStr = "\"#viagem\" ";
        
        for (Cidade cidade : cidades) {
            
            //searchStr += "\"" + cidade.getNome() + "\" "; 
            
        }
        
        
        // Create a Query object.
        Query query = new Query(searchStr);

        // Send API request to execute a search with the given query.
        QueryResult result = twitter.search(query);

        // Display search results.
        for (Status status : result.getTweets()) {
            System.out.println("\n@" + status.getUser().getName() + ": "
                    + status.getText());
        }

    }

}
