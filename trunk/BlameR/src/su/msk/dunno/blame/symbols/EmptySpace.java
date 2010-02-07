package su.msk.dunno.blame.symbols;

import su.msk.dunno.blame.prototypes.AObject;

public class EmptySpace extends AObject
{

	public EmptySpace(int i, int j) 
	{
		super(i, j);
	}

	@Override public String getName() 
	{
		return "Weapon's Empty";
	}

	@Override public boolean getPassability() 
	{
		return true;
	}

	@Override public char getSymbol() 
	{
		return ' ';
	}

	@Override public boolean getTransparency()	
	{
		return true;
	}
}