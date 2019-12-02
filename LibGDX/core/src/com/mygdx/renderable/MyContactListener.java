package com.mygdx.renderable;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class MyContactListener implements ContactListener {
	
	
	public Boolean WallHit = false;
	public Boolean WHit = false;
	public Boolean DHit = false;
	public Boolean AHit = false;
	public Boolean SHit = false;

	@Override
	public void beginContact(Contact contact) {
		WallHit = true;
		
		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();
		
		if(fixA.getUserData() == "head" || fixB.getUserData() == "head") {
			WHit = true;
		}
		
		if(fixA.getUserData() == "right" || fixB.getUserData() == "right") {
			DHit = true;
		}
		
		if(fixA.getUserData() == "back" || fixB.getUserData() == "back") {
			SHit = true;
		}
		if(fixA.getUserData() == "left" || fixB.getUserData() == "left") {
			AHit = true;
		}
		


	}

	@Override
	public void endContact(Contact contact) {
		WallHit = false;
		System.out.println("END CONTACT");
		
		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();
		
		if(fixA.getUserData() == "head" || fixB.getUserData() == "head") {
			Fixture head = fixA.getUserData() == "head" ? fixA : fixB;
			WHit = false;
			
		}
		if(fixA.getUserData() == "right" || fixB.getUserData() == "right") {
			DHit = false;
		}
		if(fixA.getUserData() == "back" || fixB.getUserData() == "back") {
			SHit = false;
		}
		if(fixA.getUserData() == "left" || fixB.getUserData() == "left") {
			AHit = false;
		}
		
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}

}
