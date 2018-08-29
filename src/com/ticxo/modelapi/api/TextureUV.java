package com.ticxo.modelapi.api;

public class TextureUV {

	private Double x;
	private Double y;
	private Double oX;
	private Double oY;

	public TextureUV(Double x, Double y, Double oX, Double oY, Double scale, Boolean mirror) {

		this.setX((mirror ? (x + oX) : x) / scale);
		this.setY(y / scale);
		this.setoX((mirror ? x : (x + oX)) / scale);
		this.setoY((y + oY) / scale);

	}

	public Double getX() {
		return x;
	}

	public void setX(Double x) {
		this.x = x;
	}

	public Double getY() {
		return y;
	}

	public void setY(Double y) {
		this.y = y;
	}

	public Double getoX() {
		return oX;
	}

	public void setoX(Double oX) {
		this.oX = oX;
	}

	public Double getoY() {
		return oY;
	}

	public void setoY(Double oY) {
		this.oY = oY;
	}

}
