package com.gmail.sergiusz.mazan.ships.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

public class ShipTest {
	
	private static final ShipSize shipSizeForTests = ShipSize.FOUR;
	
	private Ship shipForTests;
	
	@BeforeEach
	public void initializeShip() {
		shipForTests = new Ship(shipSizeForTests, Direction.HORIZONTAL, new Point(0, 0));
	}
	
	@Test
	public void ship_settingShipSizeToNull_throwsException() {
		assertThrows(NullPointerException.class, () -> {
			new Ship(null, Direction.HORIZONTAL, new Point(0, 0));
		});
	}
	
	@Test
	public void ship_settingDirectionToNull_throwsException() {
		assertThrows(NullPointerException.class, () -> {
			new Ship(shipSizeForTests, null, new Point(0, 0));
		});
	}
	
	@ParameterizedTest
	@EnumSource(Direction.class)
	public void ship_settingPositionToNull_throwsException(Direction dir) {
		assertThrows(NullPointerException.class, () -> {
			new Ship(shipSizeForTests, dir, null);
		});
	}
	
	@ParameterizedTest
	@MethodSource("generatePoints")
	public void ship_settingPositionHorizontal_settedProperly(Point point) {

		Ship ship = new Ship(shipSizeForTests, Direction.HORIZONTAL, point);
		for(int i = 0; i < shipSizeForTests.toInt(); i++) {
			assertEquals(new Point(point.getX()+i, point.getY()), ship.getPos(i));
		}			
	}
	
	@ParameterizedTest
	@MethodSource("generatePoints")
	public void ship_settingPositionVertical_settedProperly(Point point) {

		Ship ship = new Ship(shipSizeForTests, Direction.VERTICAL, point);
		for(int i = 0; i < shipSizeForTests.toInt(); i++) {
			assertEquals(new Point(point.getX(), point.getY()+i), ship.getPos(i));
		}			
	}
	
	@ParameterizedTest
	@EnumSource(Direction.class)
	public void setPosition_settingNullPosition_throwsException(Direction dir) {
		Ship ship = new Ship(shipSizeForTests, dir, new Point(0, 0));
		assertThrows(NullPointerException.class, () -> {
			ship.setPosition(null);
		});
	}
	
	@ParameterizedTest
	@MethodSource("generatePoints")
	public void setPosition_settingPositionWhenHorizotal_settedProperly(Point point) {
		
		Ship ship = new Ship(shipSizeForTests, Direction.HORIZONTAL, new Point(0, 0));
		ship.setPosition(point);
		for(int i = 0; i < shipSizeForTests.toInt(); i++) {
			assertEquals(new Point(point.getX()+i, point.getY()), ship.getPos(i));
		}
	}
	
	@ParameterizedTest
	@MethodSource("generatePoints")
	public void setPosition_settingPositionWhenVertical_settedProperly(Point point) {
		
		Ship ship = new Ship(shipSizeForTests, Direction.VERTICAL, new Point(0, 0));
		ship.setPosition(point);
		for(int i = 0; i < shipSizeForTests.toInt(); i++) {
			assertEquals(new Point(point.getX(), point.getY()+i), ship.getPos(i));
		}
	}
	
	public static Stream<Point> generatePoints() {
		return Stream.of(new Point(0, 0), new Point(-1, -2), new Point(1, 3));
	}
	
	@Test
	public void getPosition_gettingPositionAutOfArray_throwsException() {
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
			shipForTests.getPos(shipSizeForTests.toInt());
		});
	}
	
	@Test
	public void isCollision_collidingShips_returnsTrue() {
		Ship otherShip = new Ship(shipSizeForTests, Direction.VERTICAL, new Point(3, 0));
		assertTrue(shipForTests.isCollision(otherShip));
	}
	
	@Test
	public void isCollision_collidingShips_returnsFalse() {
		Ship otherShip = new Ship(shipSizeForTests, Direction.HORIZONTAL, new Point(0, 1));
		assertFalse(shipForTests.isCollision(otherShip));
	}
	
	@Test
	public void shoot_hitsShip_returnsTrue() {
		assertTrue(shipForTests.shoot(new Point(0, 0)));
		assertTrue(shipForTests.isPartofShipDestroyed(0));
	}
	
	@Test
	public void shoot_missesShip_returnsFalse() {
		assertFalse(shipForTests.shoot(new Point(0, 1)));
		for(int i = 0; i < shipForTests.getSize().toInt(); i++)
			assertFalse(shipForTests.isPartofShipDestroyed(i));
	}
}
	