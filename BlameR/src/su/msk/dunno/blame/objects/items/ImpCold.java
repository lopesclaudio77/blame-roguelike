package su.msk.dunno.blame.objects.items;

import su.msk.dunno.blame.prototypes.AItem;
import su.msk.dunno.blame.support.Color;
import su.msk.dunno.blame.support.MyFont;
import su.msk.dunno.blame.support.Point;

public class ImpCold extends AItem 
{
	public ImpCold(Point p) 
	{
		super(p);
		item_properties.put("Imp");
		item_properties.putString("Info", "Adds cold damage to weapon (damage +1)");
		item_properties.putInt("EffectsCapacity", 1);
		item_properties.putString("Effect1", "Damage");
		item_properties.putFloat("Damage", 1);
	}

	@Override public String getName() 
	{
		return "Cold";
	}

	@Override public boolean getPassability() 
	{
		return true;
	}
	
	@Override public int getSymbol()
	{
		return MyFont.IMP;
	}

	@Override public boolean getTransparency() 
	{
		return true;
	}
	
	@Override public Color getColor()
	{
		return Color.CYAN;
	}
}