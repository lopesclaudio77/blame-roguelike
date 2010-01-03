package net.dunno.blame.decisions;

import java.util.LinkedList;

import net.dunno.blame.main.support.Messages;
import net.dunno.blame.prototypes.ADecision;
import net.dunno.blame.prototypes.ALiving;
import net.dunno.blame.prototypes.AObject;

public class Close extends ADecision 
{
	public Close(ALiving al) 
	{
		super(al);
		args.put("Close", 1);
	}

	@Override public void doAction() 
	{
		LinkedList<AObject> neighbours = al.getMyNearestNeighbours();
		for(AObject ao: neighbours)
		{
			if("Open door".equals(ao.getName()))
			{
				ao.changeState(args);
				if(al.isNearPlayer())Messages.instance().addMessage(al.getName()+" closes a door");
			}
		}		
	}
}
