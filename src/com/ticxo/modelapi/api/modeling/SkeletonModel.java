package com.ticxo.modelapi.api.modeling;

public class SkeletonModel {

	private Bone head, body;
	
	public SkeletonModel(Bone head, Bone body) {
		this.head = head;
		this.body = body;
	}

	public Bone getHead() {
		return head;
	}

	public Bone getBody() {
		return body;
	}

	public void setHead(Bone head) {
		this.head = head;
	}

	public void setBody(Bone body) {
		this.body = body;
	}
	
	
	
}
