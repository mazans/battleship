package com.gmail.sergiusz.mazan.ships.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class FieldTest {

	private Field field;
	
	@BeforeEach
	void initializeField() {
		field = new Field(5, 5);
		field.addShip(new Ship(ShipSize.TWO, Direction.HORIZONTAL, new Point(1, 1)));
	}
	
	@ParameterizedTest
	@MethodSource("getShipsOutOfField")
	public void canBePlaced_shipOutOfFieldOrColliding_returnsFalse(Ship ship) {
		assertFalse(field.canBePlaced(ship));
	}
	
	@ParameterizedTest
	@MethodSource("getShipsOutOfField")
	public void addShip_shipOutOfFieldOrColliding_returnsFalse(Ship ship) {
		assertFalse(field.addShip(ship));
		assertFalse(field.getShips().contains(ship));
	}
	
	public static Stream<Ship> getShipsOutOfField() {
		return Stream.of(new Ship(ShipSize.FOUR, Direction.HORIZONTAL, new Point(2, 0)),
						new Ship(ShipSize.FOUR, Direction.HORIZONTAL, new Point(-1, 0)),
						new Ship(ShipSize.FOUR, Direction.VERTICAL, new Point(0, 2)),
						new Ship(ShipSize.FOUR, Direction.VERTICAL, new Point(0, -1)),
						new Ship(ShipSize.FOUR, Direction.VERTICAL, new Point(1, 1)),
						null);	
	}
	
	@Test
	public void canBePlaced_validatedShip_returnsTrue() {
		assertTrue(field.canBePlaced(new Ship(ShipSize.TWO, Direction.HORIZONTAL, new Point(1, 2))));
	}
	
	@Test
	public void addShip_validatedShip_returnsTrue() {
		Ship ship = new Ship(ShipSize.TWO, Direction.HORIZONTAL, new Point(1, 2));
		assertTrue(field.addShip(ship));
		assertTrue(field.getShips().contains(ship));
	}
	
	@Test
	public void shootShip_missesAShip_returnFalse() {
		assertFalse(field.shootShip(new Point(0, 0)));
	}
	
	@Test
	public void shootShip_hitsAShip_returnTrue() {
		assertTrue(field.shootShip(new Point(1, 1)));
	}
	
	@Test
	public void isFleetSubmerged_fleetAlive_returnsFalse() {
		assertFalse(field.isFleetSubmerged());
		field.shootShip(new Point(1, 1));
		assertFalse(field.isFleetSubmerged());
	}
	
	@Test
	public void isFleetSubmerged_fleetSubmerged_returnsTrue() {
		field.shootShip(new Point(1, 1));
		field.shootShip(new Point(2, 1));
		assertTrue(field.isFleetSubmerged());
	}
}
