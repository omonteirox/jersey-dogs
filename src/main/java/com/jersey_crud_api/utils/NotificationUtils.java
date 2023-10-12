package com.jersey_crud_api.utils;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class NotificationUtils {
	@JsonIgnore
	 private List<String> notifications = new ArrayList<>();

	    public List<String> getNotifications() {
	        return notifications;
	    }

	    public void addNotification(String notification) {
	        notifications.add(notification);
	    }

	    
}
