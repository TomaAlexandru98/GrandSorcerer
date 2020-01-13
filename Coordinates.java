package pack;
/*  This class represents the coordinates  */
public class Coordinates {

	private int x; 
	private int y;
	
	public Coordinates(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
/* This method returns x */	
	public int getX()
	{
		return this.x;
	}
/* This method returns y */	
	public int getY()
	{
		return this.y;
	}
/* This methods changes x */	
	public void setX(int value)
	{
		this.x = value;
	}
/* this method changes y */	
	public void setY(int value)
	{
		this.y = value;
	}
}
