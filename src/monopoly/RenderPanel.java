package monopoly;
/*
 *---Tophat---
 * Brian O'Leary - 134775468
 * Conal O'Neill - 13315756
 * Daniel Graham - 15319536
 * 
 * This class acts as our board for monopoly. It extends JPanel which we use to paint 
 * images on the board. It is redrawn using the ActionListener in our GameScreen Class.
 *
 * */
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import monopoly.GameScreen;

import javax.swing.JPanel;


import propertyImages.PropertyImages;


@SuppressWarnings("serial")
public class RenderPanel extends JPanel {

	public boolean firstTime = true;
	private PropertyImages propertyImages = new PropertyImages();

	public Color insideGreen = new Color(165, 255, 137);

	private int dotsize = 15, logoWidth = 500, logoHeight = 200, propertyImageWidth = GameScreen.BOARD_WIDTH/3 + 100, propertyImageHeight = GameScreen.BOARD_WIDTH/2 + 100;


	@Override
	protected void paintComponent(Graphics g) {
		GameScreen screen = GameScreen.screen; //enables use of info from screen
		super.paintComponent(g);

		//Draw green background
		g.setColor(Color.WHITE);
		g.fillRect(GameScreen.BOARD_WIDTH - GameScreen.TILESIZE*11 ,GameScreen.BOARD_WIDTH - GameScreen.TILESIZE*11, GameScreen.BOARD_WIDTH, GameScreen.BOARD_WIDTH);
		g.setColor(insideGreen);
		g.fillRect(GameScreen.BOARD_WIDTH - GameScreen.TILESIZE*10 ,GameScreen.BOARD_WIDTH  - GameScreen.TILESIZE*10, GameScreen.BOARD_WIDTH - GameScreen.TILESIZE*2 , GameScreen.BOARD_WIDTH - GameScreen.TILESIZE*2);

		if(firstTime){ //get images for the tile spaces only on the first time this method is called.
			propertyImages.assignTileImages();
			firstTime = false;
		}

		//Check if mouse is on a property Tile
		if(screen.mouseIsOnATile && screen.Tiles.get(screen.currentTile).getInfoImage() != null){
			g.drawImage(screen.Tiles.get(screen.currentTile).getInfoImage(), GameScreen.BOARD_WIDTH/2 - propertyImageWidth/2, GameScreen.BOARD_WIDTH/2 - propertyImageHeight/2, propertyImageWidth, propertyImageHeight, this);
		}else{
			g.drawImage(propertyImages.monopolyLogo, GameScreen.BOARD_WIDTH/2 - logoWidth/2, GameScreen.BOARD_WIDTH/2 - logoHeight/2,logoWidth,logoHeight, this);
		}

		//Loop to go through all tiles and draw the image and the black outline.
		for(Tile o : screen.Tiles){ 
			//Draw Tile's image
			g.drawImage(o.getImage(), o.x - GameScreen.TILESIZE/2, o.y - GameScreen.TILESIZE/2, GameScreen.TILESIZE, GameScreen.TILESIZE, this);
			//Draw black rectangles around tiles
			g.setColor(Color.BLACK);
			g.drawRect(o.x - GameScreen.TILESIZE/2, o.y - GameScreen.TILESIZE/2, GameScreen.TILESIZE, GameScreen.TILESIZE);

		}

		//Draws the individual player tokens
		for(Player p : screen.Players){
			g.setColor(p.getColour());  //Sets Color to player color
			g.fillOval(p.xPosition, p.yPosition, dotsize, dotsize);  //Draws player token at current x,y coordinates
			g.setColor(Color.BLACK);
			g.drawOval(p.xPosition, p.yPosition, dotsize, dotsize);  //Draws black circle around token
			g.setFont(new Font("TimesRoman", Font.BOLD, 10));
			g.drawString("P" + p.playerNumber, p.xPosition+3, p.yPosition + 12);
		}
		/*//Mouse tracker red dot
		g.setColor(Color.red);
		g.fillOval(screen.mouseX - 10/2, screen.mouseY - 10/2, 10, 10);*/
	}
}