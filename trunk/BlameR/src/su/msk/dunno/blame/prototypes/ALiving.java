package su.msk.dunno.blame.prototypes;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;

import su.msk.dunno.blame.decisions.Move;
import su.msk.dunno.blame.main.Blame;
import su.msk.dunno.blame.map.Field;
import su.msk.dunno.blame.objects.Livings;
import su.msk.dunno.blame.objects.items.ImpBio;
import su.msk.dunno.blame.objects.items.ImpLaser;
import su.msk.dunno.blame.objects.items.ImpEnergy;
import su.msk.dunno.blame.objects.items.ImpAcid;
import su.msk.dunno.blame.objects.items.ImpSocketExtender;
import su.msk.dunno.blame.screens.InventoryScreen;
import su.msk.dunno.blame.screens.WeaponScreen;
import su.msk.dunno.blame.support.Color;
import su.msk.dunno.blame.support.Messages;
import su.msk.dunno.blame.support.Point;


public abstract class ALiving extends AObject 
{	
	// effects
	public boolean isDead;
	
	protected InventoryScreen inventory;
	protected WeaponScreen weapon;
	
	// previous position: set private to prevent some possibilities "to hack" the system :)
	protected Point old_pos = cur_pos;	// not anymore :(	
	
	private int lastActionTime;
	private int actionPeriod;
	private ADecision decision;
	
	protected Field field;	

	public ALiving(int i, int j, Field field) 
	{
		super(i, j);
		initStats();
		inventory = new InventoryScreen(this, field);
		weapon = new WeaponScreen(this);
		this.field = field;
		field.addObject(this);
	}
	
	public ALiving(Point p, Field field) 
	{
		this(p.x, p.y, field);
	}

	public abstract ADecision livingAI();
	
	public void updateOldPos()
	{//	trying to predict some possible bugs... :3
		if(!field.getObjectsAtPoint(cur_pos).contains(this))
		{
			field.findObject(this);	// fail. what a dumbass :)
		}
		old_pos = cur_pos;
	}
	
	public void nextStep()
	{
		int cur_time = Livings.instance().getTime();
		weapon.energyRefill();
		if(cur_time - lastActionTime >= actionPeriod)
		{
			if(decision == null || decision.wasExecuted)
			{
				/*if(decision == null)
				{*/
					decision = livingAI();
				/*}*/
				if(decision != null && !decision.wasExecuted)
				{
					actionPeriod = decision.getActionPeriod();	// doAction AFTER getActionPeriod is necessary...
					decision.doAction(cur_time);		// At least until I'd need getActionPeriod to obtain its result depends on doAction results
				}
				//decision = null;
			}
			if(!this.getState().containsKey("CancelMove"))lastActionTime = cur_time;
		}
	}
	
	public boolean isEnemyAtDir(int dir)
	{
		for(AObject ao: getObjectsAtDir(dir))
		{
			if(isEnemy(ao) || ao.isEnemy(this))return true;
		}
		return false;
	}
	
	public LinkedList<AObject> getObjectsAtDir(int dir)
	{
		switch(dir)
		{
		case Move.UP:
			return field.getObjectsAtPoint(new Point(cur_pos.x, cur_pos.y+1));
		case Move.LEFT: 
			return field.getObjectsAtPoint(new Point(cur_pos.x-1, cur_pos.y));
		case Move.DOWN: 
			return field.getObjectsAtPoint(new Point(cur_pos.x, cur_pos.y-1));
		case Move.RIGHT: 
			return field.getObjectsAtPoint(new Point(cur_pos.x+1, cur_pos.y));
		case Move.UPRIGHT: 
			return field.getObjectsAtPoint(new Point(cur_pos.x+1, cur_pos.y+1));
		case Move.UPLEFT: 
			return field.getObjectsAtPoint(new Point(cur_pos.x-1, cur_pos.y+1));
		case Move.DOWNLEFT: 
			return field.getObjectsAtPoint(new Point(cur_pos.x-1, cur_pos.y-1));
		case Move.DOWNRIGHT: 
			return field.getObjectsAtPoint(new Point(cur_pos.x+1, cur_pos.y-1));
		case Move.STAY:	//will not return this object, only other objects on the point
			LinkedList<AObject> atPoint = new LinkedList<AObject>();
			for(AObject ao: field.getObjectsAtPoint(new Point(cur_pos.x, cur_pos.y)))
			{
				if(!ao.equals(this))atPoint.add(ao);
			}
			return atPoint;
		}
		return null;
	}
	
	public boolean getPassabilityAtDir(int dir)
	{
		for(AObject ao: this.getObjectsAtDir(dir))
		{
			if(!ao.getPassability())return false;
		}
		return true;
	}
	
	public LinkedList<AObject> getMyNearestNeighbours() // dov = depth of vision
	{
		return field.getNeighbours(this, 1);
	}
	
	public LinkedList<AObject> getMyNeighbours() // dov = depth of vision
	{
		return field.getNeighbours(this, getDov()+1);	// +1 because of the rlforj fov algorithm
	}
	
	public LinkedList<AObject> getMyEnemies()
	{
		LinkedList<AObject> enemies = new LinkedList<AObject>();
		for(AObject o: getMyNeighbours())
		{
			if(isEnemy(o) || o.isEnemy(this))enemies.add(o);
		}
		return enemies;
	}

	public boolean checkStatus(ListIterator<ALiving> li) 
	{
		if(getStat("Health") <= 0)isDead = true;
		if(isDead)
		{
			if(isNearPlayer())Messages.instance().addPropMessage("living.dead", getName());
			li.remove();
			field.removeObject(this);
			int rand = (int)(Math.random()*5);
			switch(rand)
			{
			case 0: field.addObject(new ImpBio(cur_pos)); return isDead;
			case 1: field.addObject(new ImpLaser(cur_pos)); return isDead;
			case 2: field.addObject(new ImpEnergy(cur_pos)); return isDead;
			case 3: field.addObject(new ImpAcid(cur_pos)); return isDead;
			case 4: field.addObject(new ImpSocketExtender(cur_pos)); return isDead;			
			}
		}
		return isDead;
	}
	
	@Override public abstract boolean isEnemy(AObject ao);
	
	public abstract boolean isPlayer();
	
	@Override public boolean getPassability() // all livings are impossible to pass through
	{
		return false;
	}
	
	@Override public boolean getTransparency() // and to look through
	{
		return true;
	}

	public Point getOldPos() 
	{
		return old_pos;
	}

	public boolean isNearPlayer()
	{
		if(isPlayer())return true;
		for(AObject ao: Blame.getCurrentPlayer().getMyNeighbours())
		{
			if(this.equals(ao))return true;
		}
		return false;
	}
	
	@Override public abstract  Color getColor();

	public int getLastActionTime() {
		return lastActionTime;
	}

	public int getActionPeriod() {
		return actionPeriod;
	}
	
	public void setDecision(ADecision d)
	{
		int cur_time = Livings.instance().getTime();
		actionPeriod = d.getActionPeriod();
		d.doAction(cur_time);
		if(!this.getState().containsKey("CancelMove"))lastActionTime = cur_time;
	}
	
	protected abstract void initStats();
	@Override public float getStat(String key)
	{
		return super.getStat(key) + weapon.getModifier(key);
	}
	
	public InventoryScreen getInventory()
	{
		return inventory;
	}
	
	public WeaponScreen getWeapon()
	{
		return weapon;
	}
}
