package su.msk.dunno.blame.animations;

import java.util.LinkedList;

import su.msk.dunno.blame.main.Blame;
import su.msk.dunno.blame.main.support.Point;
import su.msk.dunno.blame.map.Field;
import su.msk.dunno.blame.objects.symbols.Bullet;
import su.msk.dunno.blame.prototypes.AAnimation;

public class BulletFlight extends AAnimation 
{
	Point from, to;
	LinkedList<Point> line;
	Bullet b;
	
	public BulletFlight(int cur_time, Point from, Point to, Field field) 
	{
		super(cur_time, field, false);
		this.from = from;
		this.to = to;
		line = field.getLine(from, to);
		b = new Bullet(line.getFirst());
		frames = line.size();
		duration = frames*(Blame.fps/4)/4;
	}

	@Override public void nextFrame() 
	{
		field.removeObject(b);
		b.cur_pos = line.getFirst();
		field.addObject(b);
		line.removeFirst();
	}
	
	@Override public void stop()
	{
		field.removeObject(b);
	}
}
