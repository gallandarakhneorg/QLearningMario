package fr.utbm.tc.qlearningmario.mario.entity;

import static org.junit.Assert.*;

import org.junit.Test;

import fr.utbm.tc.qlearningmario.mario.common.MarioState;

@SuppressWarnings("all")
public class MarioBodyTest {

	@Test
	public void test() {
		MarioBody mbody = new MarioBody();
		assertEquals(1, mbody.getHealth());
		assertEquals(MarioState.SmallMario, mbody.getState());
		
		mbody.damage(mbody.getMaxHealth() + 1);
		assertEquals(0, mbody.getHealth());
		
		mbody.heal(mbody.getMaxHealth() + 1);
		assertEquals(mbody.getMaxHealth(), mbody.getHealth());
	}

}
