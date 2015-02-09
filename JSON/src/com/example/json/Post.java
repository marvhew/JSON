package com.example.json;

import java.io.Serializable;

public class Post implements Serializable{
	private String author;
	private String date;
	private String text;
	private String image_url;
	private String topic;
	private String[] keywords;
	
	
	public Post(String author, String date, String text, String image_url, String topic, String[] keywords)
	{
		this.author = author;
		this.date = date;
		this.text = text;
		this.topic = topic;
		this.image_url = image_url;
		this.keywords = keywords;
	}
	
	public String getAuthor()
	{
		return author;
	}
	
	public String getDate()
	{
		return date;
	}
	
	public String getText()
	{
		return text;
	}
	
	public String getImage_URL()
	{
		return image_url;
	}
	
	public String getTopic()
	{
		return topic;
	}
	
	public String[] getKeywords()
	{
		return keywords;
	}
}
