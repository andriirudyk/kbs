package com.rudyk.ordering.exceptionhandler;


public class TabNotFoundException extends RuntimeException {

    public TabNotFoundException(Long tabId) {
        super("Tab not found with id: " + tabId);
    }
}
