package su.msk.dunno.blame.objects.items;

import su.msk.dunno.blame.main.support.Color;
import su.msk.dunno.blame.main.support.Point;
import su.msk.dunno.blame.prototypes.AItem;

public class SocketExtender extends AItem 
{

	public SocketExtender(Point p) 
	{
		super(p);
		state.put("Part", "");
		state.put("Info", "Adds new socket places to weapon");
		state.put("Extender", "");
	}

	@Override public String getName() 
	{
		return "Socket Extender";
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