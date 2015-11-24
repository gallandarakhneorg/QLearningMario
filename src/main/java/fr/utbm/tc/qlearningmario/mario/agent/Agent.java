package fr.utbm.tc.qlearningmario.mario.agent;

import fr.utbm.tc.qlearningmario.mario.entity.AgentBody;

// FIXME: Use maven for managing the project: standard configuration, dependency management.

// FIXME: Package name must start with the "institution"/"group" reversed URL.

// FIXME: Resources mu be in a folder that has the same name as the packages.

public abstract class Agent<B extends AgentBody> {
	// FIXME: add final
    private final B body;

    public Agent(B body) {
    	// FIXME: check the constrains as most as possible
    	// FIXME: Put command-line option "-ea" when running the application
    	assert (body != null);
        this.body = body;
    }
    
    // FIXME: Change the name with something more clear: run(), live()
    public abstract void update();
    
    // FIXME: avoid public access
    // FIXME: avoid override
    protected final B getBody() {
        return this.body;
    }
}
