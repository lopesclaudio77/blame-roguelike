package su.msk.dunno.blame.animations;

import su.msk.dunno.blame.main.Blame;
import su.msk.dunno.blame.map.Field;
import su.msk.dunno.blame.prototypes.AAnimation;
import su.msk.dunno.blame.prototypes.AObject;
import su.msk.dunno.blame.support.MyFont;
import su.msk.dunno.blame.support.Point;
import su.msk.dunno.blame.support.Vector2D;

public class Moving extends AAnimation 
{
	Point pFrom, pTo;
	Point dir;
	AObject ao;
	
	public Moving(int cur_time, Field field, AObject ao, Point p_from, Point p_to) 
	{
		super(cur_time, field, false);
		
		this.ao = ao;
		ao.preventDraw();
		pFrom = p_from;
		pTo = p_to;

		dir = pTo.minus(pFrom);
				
		duration = Blame.fps/4;
		frames = duration;
		
		if((Blame.playCibo?"Cibo":"Killy").equals(ao.getName()))
		{
			field.playerMoves++;
			if(field.playerMovingCoord == null)
				field.playerMovingCoord = new Vector2D(pFrom.x*Blame.scale*3/4, 
													   pFrom.y*Blame.scale);
		}
	}

	@Override public void nextFrame()
	{
		MyFont.instance().drawDisplayList(ao.getCode(), 
								   		  pFrom.x*100*3/4+dir.x*cur_frame*100*3/4/frames, 
								   		  pFrom.y*100+dir.y*cur_frame*100/frames, 
								   		  ao.getColor());
		if((Blame.playCibo?"Cibo":"Killy").equals(ao.getName()))
		{
			field.playerMovingCoord = new Vector2D(pFrom.x*Blame.scale*3/4+dir.x*cur_frame*Blame.scale*3/4/frames, 
					   							   pFrom.y*Blame.scale+dir.y*cur_frame*Blame.scale/frames);
		}
		
	}
	
	@Override public void stop()
	{
		ao.allowDraw();
		if("Cibo".equals(ao.getName()) || "Killy".equals(ao.getName()))field.playerMoves--;
		if(field.playerMoves < 0)field.playerMoves = 0;
	}
}