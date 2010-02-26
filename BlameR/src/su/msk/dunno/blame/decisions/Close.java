package su.msk.dunno.blame.decisions;

import java.util.LinkedList;

import su.msk.dunno.blame.prototypes.ADecision;
import su.msk.dunno.blame.prototypes.ALiving;
import su.msk.dunno.blame.prototypes.AObject;
import su.msk.dunno.blame.support.Messages;


public class Close extends ADecision 
{
	public Close(ALiving al) 
	{
		super(al);
		args.put("Close", "");
	}

	@Override public void doAction(int actionMoment) 
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
		wasExecuted = true;
	}
}