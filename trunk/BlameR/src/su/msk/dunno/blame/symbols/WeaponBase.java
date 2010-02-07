package su.msk.dunno.blame.symbols;

import su.msk.dunno.blame.main.support.Point;
import su.msk.dunno.blame.prototypes.AObject;

public class WeaponBase extends AObject 
{

	public WeaponBase(Point p) 
	{
		super(p);
	}

	public WeaponBase(int i, int j) 
	{
		super(i, j);
	}

	@Override public String getName() 
	{
		return "Weapon Sceleton";
	}

	@Override public boolean getPassability() 
	{
		return true;
	}

	@Override public char getSymbol() 
	{
		return 'w';
	}

	@Override public boolean getTransparency() 
	{
		return true;
	}

}
