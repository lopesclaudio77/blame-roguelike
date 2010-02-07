package su.msk.dunno.blame.symbols;

import su.msk.dunno.blame.prototypes.AObject;

public class SocketSymbol extends AObject
{

	public SocketSymbol(int i, int j) 
	{
		super(i, j);
	}

	@Override public String getName() 
	{
		return "SocketPlace";
	}

	@Override public boolean getPassability() 
	{
		return true;
	}

	@Override public char getSymbol() 
	{
		return 'o';
	}

	@Override public boolean getTransparency()	
	{
		return true;
	}
}