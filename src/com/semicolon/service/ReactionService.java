/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.semicolon.service;

import com.semicolon.entity.Enumerations.ReactionType;
import com.semicolon.entity.Post;

/**
 *
 * @author Elyes
 */
public class ReactionService {
    private static ReactionService instance;
    
    public static ReactionService getInstance(){
        if (instance == null)
            instance = new ReactionService();
        return instance;
    }
    private ReactionService(){
        
    }
    
    public void react(Post p, ReactionType type){
	
    }
}
