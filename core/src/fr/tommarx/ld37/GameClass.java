package fr.tommarx.ld37;


import fr.tommarx.gameengine.Game.Game;

public class GameClass extends Game {

	public void create() {
		init();
		setScreen(new GameScreen(this));
	}
}
