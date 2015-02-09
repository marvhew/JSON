package com.example.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PostsBase implements Serializable{
	private List<Post> posts = new ArrayList<Post>();
	public Post[] getPosts()
	{
		Post[] posts = new Post[this.posts.size()];
		for(int i=0; i<posts.length;i++)
		{
			posts[i] = this.posts.get(i);
		}
		return posts;
		
	}
	public void setPosts(List<Post> posts) {
		this.posts = posts;
		
	}
}
