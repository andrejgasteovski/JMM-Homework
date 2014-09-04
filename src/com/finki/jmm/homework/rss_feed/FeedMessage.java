package com.finki.jmm.homework.rss_feed;

import java.io.Serializable;

public class FeedMessage implements Serializable{
	String title;
	String description;
	String link;
	
	private static final long serialVersionUID = 1L;
	
	public FeedMessage(){}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	
	public FeedMessage(String title, String description, String link) {
		super();
		this.title = title;
		this.description = description;
		this.link = link;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return title;
	}
}
