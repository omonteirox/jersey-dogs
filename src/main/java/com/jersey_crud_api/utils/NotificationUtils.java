package com.jersey_crud_api.utils;

import java.util.ArrayList;
import java.util.List;

public abstract class NotificationUtils {
	 private List<String> notifications = new ArrayList<>();

	    public List<String> getNotifications() {
	        return notifications;
	    }

	    public void addNotification(String notification) {
	        notifications.add(notification);
	    }

	    
}
