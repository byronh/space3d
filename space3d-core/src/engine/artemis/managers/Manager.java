package engine.artemis.managers;

import com.badlogic.gdx.utils.Disposable;

import engine.artemis.Entity;
import engine.artemis.EntityObserver;
import engine.artemis.World;


/**
 * A parent class for all managers in the World.
 * 
 * @author Arni Arent
 * 
 */
public abstract class Manager implements EntityObserver, Disposable {
    protected World world;

    /**
     * This method is called during world.initialize().
     */
    public void initialize() {
    }

    /**
     * Sets the world this manager belongs to.
     * 
     * @param world Artemis World.
     */
    public void setWorld(World world) {
        this.world = world;
    }

    /**
     * @return Returns the world the manager belongs to.
     */
    protected World getWorld() {
        return world;
    }

    @Override
    public void added(Entity e) {
    }

    @Override
    public void changed(Entity e) {
    }

    @Override
    public void deleted(Entity e) {
    }

    @Override
    public void disabled(Entity e) {
    }

    @Override
    public void enabled(Entity e) {
    }
}
