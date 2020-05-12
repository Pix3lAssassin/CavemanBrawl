package com.pixelcross.cavemanbrawl.levels.tiles;

import com.pixelcross.cavemanbrawl.gfx.Assets;

import javafx.scene.image.Image;

/**
 * @author Justin Schreiber
 *
 * Defines rules for displaying the wall tiles
 */
public class WallTilePattern implements TilePattern {

	Image[] walls;
	
	public WallTilePattern() {
		this.walls = Assets.walls;
	}
	
	@Override
	public Image getTileTexture(int[] surroundingTiles) {
		Image texture = Math.random() > .5 ? walls[6] : walls[7];
		if (surroundingTiles[1] == 0 && surroundingTiles[3] == 0 && surroundingTiles[7] == 0) {
			texture = walls[21];
		} else if (surroundingTiles[1] == 0 && surroundingTiles[5] == 0 && surroundingTiles[7] == 0) {
			texture = walls[15];
		} else if (surroundingTiles[7] == 0 && surroundingTiles[3] == 0 && surroundingTiles[5] == 0) {
			texture = walls[20];
		} else if (surroundingTiles[1] == 0 && surroundingTiles[5] == 0 && surroundingTiles[3] == 0) {
			texture = walls[16];
		} else if (surroundingTiles[1] == 0 && surroundingTiles[3] == 0) {
			texture = walls[0];
		} else if (surroundingTiles[1] == 0 && surroundingTiles[5] == 0) {
			texture = walls[3];
		} else if (surroundingTiles[7] == 0 && surroundingTiles[3] == 0) {
			texture = walls[10];
		} else if (surroundingTiles[7] == 0 && surroundingTiles[5] == 0) {
			texture = walls[12];
		} else if (surroundingTiles[1] == 0 && surroundingTiles[7] == 0) {//
			texture = walls[9];
		} else if (surroundingTiles[3] == 0 && surroundingTiles[5] == 0) {
			texture = walls[4];
		} else if (surroundingTiles[1] == 0 && surroundingTiles[8] == 0) {
			texture = walls[19];
		} else if (surroundingTiles[1] == 0 && surroundingTiles[6] == 0) {
			texture = walls[18];
		} else if (surroundingTiles[7] == 0 && surroundingTiles[0] == 0) {
			texture = walls[23];
		} else if (surroundingTiles[7] == 0 && surroundingTiles[2] == 0) {
			texture = walls[24];
		} else if (surroundingTiles[1] == 0) {
			texture = Math.random() > .5 ? walls[1] : walls[2];
		} else if (surroundingTiles[3] == 0) {
			texture = walls[5];
		} else if (surroundingTiles[5] == 0) {
			texture = walls[8];
		} else if (surroundingTiles[7] == 0) {
			texture = walls[11];
		} else if (surroundingTiles[0] == 0) {
			texture = walls[13];
		} else if (surroundingTiles[2] == 0) {
			texture = walls[14];
		} else if (surroundingTiles[6] == 0) {
			texture = walls[17];
		} else if (surroundingTiles[8] == 0) {
			texture = walls[22];
		}
		return texture;
	}

}
