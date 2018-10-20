package platformer;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
/** 	
* Creates a square that represents the player
*/
public class Player
{
	//current player's position
	private double playerX;
	private double playerY;
	
	//player health variables
	private double originalHealth = 100;
	private double playerHealth = originalHealth;
	private final double damage = .7;
	private boolean isAlive = true;
	
	/** size of the player */
	private double size;
	/* starting velocity */
	private double xVelocity = 0;
	private double yVelocity = 0;
	/** is the player in the air or not */
	private boolean amJumping = false;
	/**the array of blocks, in no particular order*/
	private final ArrayList<Block> blocks;
	/**the array of all the spikes in the level*/
	private final ArrayList<Spike> spikes;
	/* not changing variables */
	private final double PLAYER_ACCELERATION = .4;
	private final double STOPPING_FORCE = 0.6;
	private final double JUMP_FORCE = 10;
	private final double GRAVITY = 0.3;
	private final double PLAYER_MAX_SPEED = 8;
	
	private LevelStructure levelStructure;
	
	public Player(double x, double y, double size,
			LevelStructure levelStructure)
	{
		this.playerX = x;
		this.playerY = y;
		this.size = size;
		this.blocks = levelStructure.blocksLevel;
		this.spikes = levelStructure.spikes;
		this.levelStructure = levelStructure;
	}
	
	/**
	 * draws the player at current position
	 * @param g2
	 */
	public void draw(Graphics2D g2)
	{
		g2.setColor(Color.PINK);
		g2.fillRect((int) (playerX + levelStructure.getCorner().x),
				(int) (playerY + levelStructure.getCorner().y),
				(int)size, (int)size);
	}
	
	/**
	 * @param moves the array for the moves: [left, right, up]
	 */
	public void update(boolean[] moves)
	{
		// left
		if (moves[MoveDirection.LEFT])
		{
			this.xVelocity -= PLAYER_ACCELERATION;
		}
		// right
		if (moves[MoveDirection.RIGHT])
		{
			this.xVelocity += PLAYER_ACCELERATION;
		}
		// Slows down if not pressing any buttons
		if (!(moves[MoveDirection.LEFT]) &&
				!(moves[MoveDirection.RIGHT]))
		{
			this.xVelocity *= STOPPING_FORCE;
		}
		// If player is pressing up and can jump
		if (moves[MoveDirection.UP] && !this.amJumping)
		{
			// pushes player up
			this.yVelocity -= JUMP_FORCE;
			// not allowed to jump until I land.
			this.amJumping = true;
		}
		// amJumping is not allowed unless player is on a block
		this.amJumping = true;
		//keeps the velocity within the min/max
		this.xVelocity = keepVelocityInside(this.xVelocity,
				-PLAYER_MAX_SPEED, PLAYER_MAX_SPEED);
		// Add velocity to x-position
		this.playerX += this.xVelocity;
		/* check for collisions on x-axis */
		this.collide(this.xVelocity, 0);
		/* add velocity to y position */
		this.playerY += this.yVelocity;
		/* check for collisions on y-axis */
		this.collide(0, this.yVelocity);
		/* check if touching a spike */
		if (this.isSpike(this))
			this.doDamage();
		/** update the corner of the map/level **/
		this.levelStructure.getCorner().x -= xVelocity;
		this.levelStructure.getCorner().y -= yVelocity;
		/* Apply gravity. */
		this.yVelocity += GRAVITY;
	}
	
	/**
	 * keeps the velocity inside the max allowed speed
	 * @param velocity the current velocity
	 * @param min the min allowed speed
	 * @param max the max allowed speed
	 * @return
	 */
	public double keepVelocityInside(double velocity, double min, double max)
	{
		if (velocity >= max)
			velocity = max;
		else if (velocity <= min)
			velocity = min;
		return velocity;
	}
	
	/**
	 * checks if the player is colliding with any blocks
	 * if so, uncollide and stop velocity
	 * @param velocityX
	 * @param velocityY
	 */
	public void collide(double velocityX, double velocityY)
	{
		//goes through all the blocks
		for (int i = 0; i < blocks.size(); i++) 
		{
			// if player is not intersecting with block, skip it
			if (!isCollision(this, blocks.get(i)))
			{
			continue;
			}
			/* player is moving left and hits a block */
			if (velocityX < 0)
			{
			/* Reposition player so not touching it */
			this.playerX = blocks.get(i).getX()
					+ blocks.get(i).getUnit();
			/* ...And reset my velocity. */
			this.xVelocity = 0;
			}
			/* player is moving right and hits a block */
			if (velocityX > 0)
			{
				/* Reposition player so not touching it */
				this.playerX = blocks.get(i).getX()
					- this.size;
				/* ...And reset my velocity. */
				this.xVelocity = 0;
			}
			/* player is jumping under it */
			if (velocityY < 0)
			{
				/* Reposition so player not touching the block above */
					this.playerY = blocks.get(i).getY()
							+ this.size;
				/* ...And reset my velocity. */
				this.yVelocity = 0;
			}
			/* falling onto it */
			if (velocityY > 0)
			{
				/* Reposition player so not touching */
				this.playerY = blocks.get(i).getY()
						- this.size;
				/* reset velocity */
				this.yVelocity = 0;
				/* player is on a block, can jump again */
				this.amJumping = false;
			}
		}
	}

	
	/**
	 * checks if player and block intersect
	 */
	public boolean isCollision (Player player, Block block) {
	 return player.getX() < block.getX() + block.getUnit() && /* checks left */
	 player.getX() + block.getUnit() > block.getX() && /* checks right */
	 player.getY() < block.getY() + block.getUnit() && /* checks up */
	 player.getY() + block.getUnit() > block.getY(); /* checks down */
	}
	
	/**
	 * checks to see if a player intersects with a portal
	 * @param player the player to check
	 * @param portal the portal to check
	 * @return returns if they intersect or not
	 */
	public boolean touchPortal(Player player, Portal portal)
	{
		boolean check =
				(player.getX() < portal.x() + portal.size() && //checks left
				player.getX() + portal.size() > portal.x() && //checks right
				player.getY() < portal.y() + portal.size() && //checks up
				player.getY() + portal.size() > portal.y()); //checks down
		return check;
	}
	/**
	 * check all spikes
	 * to see if player intersects
	 * with any of them
	 * if so, do damage.
	 */
	public boolean isSpike(Player player)
	{
		Rectangle pRect = new Rectangle();
		boolean isIntersecting = false;
		//checks all spikes
		for (int i = 0; i < spikes.size(); i++)
		{
			pRect.setBounds((int) (this.playerX + levelStructure.getCorner().x),
					(int) (this.playerY + levelStructure.getCorner().y),
					(int)this.size,
					(int)this.size);
			if (spikes.get(i).getTriangle().intersects(pRect))
			{
				isIntersecting = true;
			}
		}
		return isIntersecting;
	}
	
	/**
	 * does damage to the player
	 */
	public void doDamage()
	{
		playerHealth -= damage;
		if (playerHealth <= 0)
		{
			playerHealth = 0;
			this.isAlive = false;
		}
	}
	
	/**
	 * draws the player's health bar
	 * @param g2
	 */
	public void drawHealthBar(Graphics2D g2)
	{
		if (this.isAlive)
		{
			int x = 20;
			int y = 30;
			//drawing the outline
			Rectangle origHealthBar = new Rectangle
					(x, y, 100, 10);
			g2.setColor(Color.BLACK);
			g2.draw(origHealthBar);
			//drawing the current health
			g2.setColor(Color.RED);
			Rectangle currentHealth = new Rectangle(
					x+1,
					y+1,
					(int)((origHealthBar.width/originalHealth)*playerHealth)-1,
					9);
			g2.fill(currentHealth);
			//writing health
			Font font = new Font("Chiller", Font.BOLD, 30);
			g2.setFont(font);
			g2.drawString("Health", 20,
					30);
		}
	}
	
	/**
	 * @return the player's x-value
	 */
	public double getX()
	{
		return playerX;
	}
	/**
	 * @return the player's y-value
	 */
	public double getY()
	{
		return playerY;
	}
	
	public boolean getIsAlive()
	{
		return isAlive;
	}
	public void setIsAlive(boolean isAlive)
	{
		this.isAlive = isAlive;
	}
	
	public void resetHealth()
	{
		this.playerHealth = this.originalHealth;
	}
}

