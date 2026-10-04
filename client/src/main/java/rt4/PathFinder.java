package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

public class PathFinder {
	@OriginalMember(owner = "client!li", name = "h", descriptor = "[Lclient!mj;")
	public static final CollisionMap[] collisionMaps = new CollisionMap[4];
	@OriginalMember(owner = "client!lf", name = "a", descriptor = "[[I")
	public static final int[][] parents = new int[104][104];
	@OriginalMember(owner = "client!nd", name = "q", descriptor = "[[I")
	public static final int[][] costs = new int[104][104];
	@OriginalMember(owner = "client!vc", name = "eb", descriptor = "[I")
	public static final int[] queueX = new int[4096];
	@OriginalMember(owner = "client!gk", name = "c", descriptor = "[I")
	public static final int[] queueY = new int[4096];
	@OriginalMember(owner = "client!s", name = "d", descriptor = "I")
	public static int approximateDestination = 0;

	@OriginalMember(owner = "client!hn", name = "a", descriptor = "(IIIZIIIIIIII)Z")
	public static boolean findPath(@OriginalArg(0) int srcY, @OriginalArg(1) int locAngle, @OriginalArg(2) int locSizeY, @OriginalArg(3) boolean approx, @OriginalArg(4) int blockAccessFlags, @OriginalArg(6) int destX, @OriginalArg(7) int locSizeX, @OriginalArg(8) int locShape, @OriginalArg(9) int moveType, @OriginalArg(10) int destY, @OriginalArg(11) int srcX) {
		if (PlayerList.self.getSize() == 2) {
			return findPath2(locSizeX, locShape, blockAccessFlags, srcY, destY, approx, locSizeY, locAngle, destX, moveType, srcX);
		} else if (PlayerList.self.getSize() <= 2) {
			return findPathN(destX, blockAccessFlags, srcX, destY, moveType, locSizeY, locAngle, approx, locShape, srcY, locSizeX);
		} else {
			return findPath1(destY, locSizeX, moveType, locAngle, PlayerList.self.getSize(), destX, locShape, blockAccessFlags, srcX, locSizeY, approx, srcY);
		}
	}

	@OriginalMember(owner = "client!aa", name = "a", descriptor = "(IIIIIZIIIIII)Z")
	public static boolean findPath2(@OriginalArg(0) int locSizeX, @OriginalArg(1) int locShape, @OriginalArg(2) int blockAccessFlags, @OriginalArg(3) int srcY, @OriginalArg(4) int destY, @OriginalArg(5) boolean approx, @OriginalArg(6) int locSizeY, @OriginalArg(7) int locAngle, @OriginalArg(8) int destX, @OriginalArg(9) int moveType, @OriginalArg(11) int srcX) {
		@Pc(3) int x;
		@Pc(8) int y;
		for (x = 0; x < 104; x++) {
			for (y = 0; y < 104; y++) {
				parents[x][y] = 0;
				costs[x][y] = 99999999;
			}
		}
		parents[srcX][srcY] = 99;
		costs[srcX][srcY] = 0;
		y = srcY;
		x = srcX;
		@Pc(53) byte initialIndex = 0;
		queueX[0] = srcX;
		@Pc(59) boolean reachedDest = false;
		@Pc(61) int queueReaderIndex = 0;
		@Pc(64) int queueWriterIndex = initialIndex + 1;
		queueY[0] = srcY;
		@Pc(71) int[][] flags = collisionMaps[Player.plane].flags;
		@Pc(193) int cost;
		while (queueReaderIndex != queueWriterIndex) {
			x = queueX[queueReaderIndex];
			y = queueY[queueReaderIndex];
			queueReaderIndex = queueReaderIndex + 1 & 0xFFF;
			if (destX == x && destY == y) {
				reachedDest = true;
				break;
			}
			if (locShape != 0) {
				if ((locShape < 5 || locShape == 10) && collisionMaps[Player.plane].isAtWall(destY, x, y, destX, locShape - 1, 2, locAngle)) {
					reachedDest = true;
					break;
				}
				if (locShape < 10 && collisionMaps[Player.plane].isAtWallDecor(destY, locShape - 1, destX, y, 2, locAngle, x)) {
					reachedDest = true;
					break;
				}
			}
			if (locSizeX != 0 && locSizeY != 0 && collisionMaps[Player.plane].isInsideOrOutsideRect(destX, y, x, 2, locSizeX, blockAccessFlags, destY, locSizeY)) {
				reachedDest = true;
				break;
			}
			cost = costs[x][y] + 1;
			if (x > 0 && parents[x - 1][y] == 0 && (flags[x - 1][y] & 0x12C010E) == 0 && (flags[x - 1][y + 1] & 0x12C0138) == 0) {
				queueX[queueWriterIndex] = x - 1;
				queueY[queueWriterIndex] = y;
				queueWriterIndex = queueWriterIndex + 1 & 0xFFF;
				parents[x - 1][y] = 2;
				costs[x - 1][y] = cost;
			}
			if (x < 102 && parents[x + 1][y] == 0 && (flags[x + 2][y] & 0x12C0183) == 0 && (flags[x + 2][y + 1] & 0x12C01E0) == 0) {
				queueX[queueWriterIndex] = x + 1;
				queueY[queueWriterIndex] = y;
				queueWriterIndex = queueWriterIndex + 1 & 0xFFF;
				parents[x + 1][y] = 8;
				costs[x + 1][y] = cost;
			}
			if (y > 0 && parents[x][y - 1] == 0 && (flags[x][y - 1] & 0x12C010E) == 0 && (flags[x + 1][y - 1] & 0x12C0183) == 0) {
				queueX[queueWriterIndex] = x;
				queueY[queueWriterIndex] = y - 1;
				parents[x][y - 1] = 1;
				costs[x][y - 1] = cost;
				queueWriterIndex = queueWriterIndex + 1 & 0xFFF;
			}
			if (y < 102 && parents[x][y + 1] == 0 && (flags[x][y + 2] & 0x12C0138) == 0 && (flags[x + 1][y + 2] & 0x12C01E0) == 0) {
				queueX[queueWriterIndex] = x;
				queueY[queueWriterIndex] = y + 1;
				parents[x][y + 1] = 4;
				queueWriterIndex = queueWriterIndex + 1 & 0xFFF;
				costs[x][y + 1] = cost;
			}
			if (x > 0 && y > 0 && parents[x - 1][y - 1] == 0 && (flags[x - 1][y] & 0x12C0138) == 0 && (flags[x - 1][y - 1] & 0x12C010E) == 0 && (flags[x][y - 1] & 0x12C0183) == 0) {
				queueX[queueWriterIndex] = x - 1;
				queueY[queueWriterIndex] = y - 1;
				parents[x - 1][y - 1] = 3;
				costs[x - 1][y - 1] = cost;
				queueWriterIndex = queueWriterIndex + 1 & 0xFFF;
			}
			if (x < 102 && y > 0 && parents[x + 1][y - 1] == 0 && (flags[x + 1][y - 1] & 0x12C010E) == 0 && (flags[x + 2][y - 1] & 0x12C0183) == 0 && (flags[x + 2][y] & 0x12C01E0) == 0) {
				queueX[queueWriterIndex] = x + 1;
				queueY[queueWriterIndex] = y - 1;
				queueWriterIndex = queueWriterIndex + 1 & 0xFFF;
				parents[x + 1][y - 1] = 9;
				costs[x + 1][y - 1] = cost;
			}
			if (x > 0 && y < 102 && parents[x - 1][y + 1] == 0 && (flags[x - 1][y + 1] & 0x12C010E) == 0 && (flags[x - 1][y + 2] & 0x12C0138) == 0 && (flags[x][y + 2] & 0x12C01E0) == 0) {
				queueX[queueWriterIndex] = x - 1;
				queueY[queueWriterIndex] = y + 1;
				parents[x - 1][y + 1] = 6;
				costs[x - 1][y + 1] = cost;
				queueWriterIndex = queueWriterIndex + 1 & 0xFFF;
			}
			if (x < 102 && y < 102 && parents[x + 1][y + 1] == 0 && (flags[x + 1][y + 2] & 0x12C0138) == 0 && (flags[x + 2][y + 2] & 0x12C01E0) == 0 && (flags[x + 2][y + 1] & 0x12C0183) == 0) {
				queueX[queueWriterIndex] = x + 1;
				queueY[queueWriterIndex] = y + 1;
				queueWriterIndex = queueWriterIndex + 1 & 0xFFF;
				parents[x + 1][y + 1] = 12;
				costs[x + 1][y + 1] = cost;
			}
		}
		approximateDestination = 0;
		@Pc(921) int bestCost;
		if (!reachedDest) {
			if (!approx) {
				return false;
			}
			cost = 1000;
			bestCost = 100;
			for (@Pc(928) int scanX = destX - 10; scanX <= destX + 10; scanX++) {
				for (@Pc(942) int scanY = destY - 10; scanY <= destY + 10; scanY++) {
					if (scanX >= 0 && scanY >= 0 && scanX < 104 && scanY < 104 && costs[scanX][scanY] < 100) {
						@Pc(978) int dx = 0;
						@Pc(980) int dy = 0;
						if (scanX < destX) {
							dx = destX - scanX;
						} else if (scanX > locSizeX + destX - 1) {
							dx = scanX + 1 - locSizeX - destX;
						}
						if (destY > scanY) {
							dy = destY - scanY;
						} else if (scanY > destY + locSizeY - 1) {
							dy = scanY + 1 - destY - locSizeY;
						}
						@Pc(1057) int distSq = dx * dx + dy * dy;
						if (distSq < cost || cost == distSq && costs[scanX][scanY] < bestCost) {
							y = scanY;
							bestCost = costs[scanX][scanY];
							cost = distSq;
							x = scanX;
						}
					}
				}
			}
			if (cost == 1000) {
				return false;
			}
			if (srcX == x && y == srcY) {
				return false;
			}
			approximateDestination = 1;
		}
		@Pc(1121) byte initialBacktrackIndex = 0;
		queueX[0] = x;
		queueReaderIndex = initialBacktrackIndex + 1;
		queueY[0] = y;
		cost = bestCost = parents[x][y];
		while (srcX != x || srcY != y) {
			if (bestCost != cost) {
				queueX[queueReaderIndex] = x;
				queueY[queueReaderIndex++] = y;
				bestCost = cost;
			}
			if ((cost & 0x2) != 0) {
				x++;
			} else if ((cost & 0x8) != 0) {
				x--;
			}
			if ((cost & 0x1) != 0) {
				y++;
			} else if ((cost & 0x4) != 0) {
				y--;
			}
			cost = parents[x][y];
		}
		if (queueReaderIndex > 0) {
			ClientProt.sendMovePacket(queueReaderIndex, moveType);
			return true;
		} else return moveType != 1;
	}

	@OriginalMember(owner = "client!di", name = "a", descriptor = "(IIIIIIIIZIII)Z")
	public static boolean findPathN(@OriginalArg(0) int destX, @OriginalArg(1) int blockAccessFlags, @OriginalArg(2) int srcX, @OriginalArg(4) int destY, @OriginalArg(5) int moveType, @OriginalArg(6) int locSizeY, @OriginalArg(7) int locAngle, @OriginalArg(8) boolean approx, @OriginalArg(9) int locShape, @OriginalArg(10) int srcY, @OriginalArg(11) int locSizeX) {
		@Pc(3) int x;
		@Pc(10) int y;
		for (x = 0; x < 104; x++) {
			for (y = 0; y < 104; y++) {
				parents[x][y] = 0;
				costs[x][y] = 99999999;
			}
		}
		x = srcX;
		parents[srcX][srcY] = 99;
		y = srcY;
		costs[srcX][srcY] = 0;
		@Pc(51) byte initialIndex = 0;
		@Pc(53) boolean reachedDest = false;
		@Pc(64) int queueReaderIndex = 0;
		queueX[0] = srcX;
		@Pc(71) int queueWriterIndex = initialIndex + 1;
		queueY[0] = srcY;
		@Pc(78) int[][] flags = collisionMaps[Player.plane].flags;
		@Pc(198) int cost;
		while (queueWriterIndex != queueReaderIndex) {
			y = queueY[queueReaderIndex];
			x = queueX[queueReaderIndex];
			queueReaderIndex = queueReaderIndex + 1 & 0xFFF;
			if (x == destX && y == destY) {
				reachedDest = true;
				break;
			}
			if (locShape != 0) {
				if ((locShape < 5 || locShape == 10) && collisionMaps[Player.plane].isAtWall(destY, x, y, destX, locShape - 1, 1, locAngle)) {
					reachedDest = true;
					break;
				}
				if (locShape < 10 && collisionMaps[Player.plane].isAtWallDecor(destY, locShape - 1, destX, y, 1, locAngle, x)) {
					reachedDest = true;
					break;
				}
			}
			if (locSizeX != 0 && locSizeY != 0 && collisionMaps[Player.plane].isInsideOrOutsideRect(destX, y, x, 1, locSizeX, blockAccessFlags, destY, locSizeY)) {
				reachedDest = true;
				break;
			}
			cost = costs[x][y] + 1;
			if (x > 0 && parents[x - 1][y] == 0 && (flags[x - 1][y] & 0x12C0108) == 0) {
				queueX[queueWriterIndex] = x - 1;
				queueY[queueWriterIndex] = y;
				queueWriterIndex = queueWriterIndex + 1 & 0xFFF;
				parents[x - 1][y] = 2;
				costs[x - 1][y] = cost;
			}
			if (x < 103 && parents[x + 1][y] == 0 && (flags[x + 1][y] & 0x12C0180) == 0) {
				queueX[queueWriterIndex] = x + 1;
				queueY[queueWriterIndex] = y;
				queueWriterIndex = queueWriterIndex + 1 & 0xFFF;
				parents[x + 1][y] = 8;
				costs[x + 1][y] = cost;
			}
			if (y > 0 && parents[x][y - 1] == 0 && (flags[x][y - 1] & 0x12C0102) == 0) {
				queueX[queueWriterIndex] = x;
				queueY[queueWriterIndex] = y - 1;
				parents[x][y - 1] = 1;
				queueWriterIndex = queueWriterIndex + 1 & 0xFFF;
				costs[x][y - 1] = cost;
			}
			if (y < 103 && parents[x][y + 1] == 0 && (flags[x][y + 1] & 0x12C0120) == 0) {
				queueX[queueWriterIndex] = x;
				queueY[queueWriterIndex] = y + 1;
				queueWriterIndex = queueWriterIndex + 1 & 0xFFF;
				parents[x][y + 1] = 4;
				costs[x][y + 1] = cost;
			}
			if (x > 0 && y > 0 && parents[x - 1][y - 1] == 0 && (flags[x - 1][y - 1] & 0x12C010E) == 0 && (flags[x - 1][y] & 0x12C0108) == 0 && (flags[x][y - 1] & 0x12C0102) == 0) {
				queueX[queueWriterIndex] = x - 1;
				queueY[queueWriterIndex] = y - 1;
				queueWriterIndex = queueWriterIndex + 1 & 0xFFF;
				parents[x - 1][y - 1] = 3;
				costs[x - 1][y - 1] = cost;
			}
			if (x < 103 && y > 0 && parents[x + 1][y - 1] == 0 && (flags[x + 1][y - 1] & 0x12C0183) == 0 && (flags[x + 1][y] & 0x12C0180) == 0 && (flags[x][y - 1] & 0x12C0102) == 0) {
				queueX[queueWriterIndex] = x + 1;
				queueY[queueWriterIndex] = y - 1;
				queueWriterIndex = queueWriterIndex + 1 & 0xFFF;
				parents[x + 1][y - 1] = 9;
				costs[x + 1][y - 1] = cost;
			}
			if (x > 0 && y < 103 && parents[x - 1][y + 1] == 0 && (flags[x - 1][y + 1] & 0x12C0138) == 0 && (flags[x - 1][y] & 0x12C0108) == 0 && (flags[x][y + 1] & 0x12C0120) == 0) {
				queueX[queueWriterIndex] = x - 1;
				queueY[queueWriterIndex] = y + 1;
				parents[x - 1][y + 1] = 6;
				queueWriterIndex = queueWriterIndex + 1 & 0xFFF;
				costs[x - 1][y + 1] = cost;
			}
			if (x < 103 && y < 103 && parents[x + 1][y + 1] == 0 && (flags[x + 1][y + 1] & 0x12C01E0) == 0 && (flags[x + 1][y] & 0x12C0180) == 0 && (flags[x][y + 1] & 0x12C0120) == 0) {
				queueX[queueWriterIndex] = x + 1;
				queueY[queueWriterIndex] = y + 1;
				parents[x + 1][y + 1] = 12;
				queueWriterIndex = queueWriterIndex + 1 & 0xFFF;
				costs[x + 1][y + 1] = cost;
			}
		}
		approximateDestination = 0;
		@Pc(839) int bestCost;
		if (!reachedDest) {
			if (!approx) {
				return false;
			}
			cost = 1000;
			bestCost = 100;
			for (@Pc(846) int scanX = destX - 10; scanX <= destX + 10; scanX++) {
				for (@Pc(856) int scanY = destY - 10; scanY <= destY + 10; scanY++) {
					if (scanX >= 0 && scanY >= 0 && scanX < 104 && scanY < 104 && costs[scanX][scanY] < 100) {
						@Pc(894) int dy = 0;
						if (scanY < destY) {
							dy = destY - scanY;
						} else if (locSizeY + destY - 1 < scanY) {
							dy = scanY + 1 - destY - locSizeY;
						}
						@Pc(927) int dx = 0;
						if (scanX < destX) {
							dx = destX - scanX;
						} else if (scanX > locSizeX + destX - 1) {
							dx = scanX + 1 - locSizeX - destX;
						}
						@Pc(968) int distSq = dy * dy + dx * dx;
						if (distSq < cost || distSq == cost && costs[scanX][scanY] < bestCost) {
							y = scanY;
							cost = distSq;
							x = scanX;
							bestCost = costs[scanX][scanY];
						}
					}
				}
			}
			if (cost == 1000) {
				return false;
			}
			if (srcX == x && y == srcY) {
				return false;
			}
			approximateDestination = 1;
		}
		@Pc(1032) byte initialBacktrackIndex = 0;
		queueX[0] = x;
		queueReaderIndex = initialBacktrackIndex + 1;
		queueY[0] = y;
		cost = bestCost = parents[x][y];
		while (srcX != x || y != srcY) {
			if (bestCost != cost) {
				bestCost = cost;
				queueX[queueReaderIndex] = x;
				queueY[queueReaderIndex++] = y;
			}
			if ((cost & 0x2) != 0) {
				x++;
			} else if ((cost & 0x8) != 0) {
				x--;
			}
			if ((cost & 0x1) != 0) {
				y++;
			} else if ((cost & 0x4) != 0) {
				y--;
			}
			cost = parents[x][y];
		}
		if (queueReaderIndex > 0) {
			ClientProt.sendMovePacket(queueReaderIndex, moveType);
			return true;
		} else return moveType != 1;
	}

	@OriginalMember(owner = "client!hh", name = "a", descriptor = "(IBIIIIIIIIIZI)Z")
	public static boolean findPath1(@OriginalArg(0) int destY, @OriginalArg(2) int locSizeX, @OriginalArg(3) int moveType, @OriginalArg(4) int locAngle, @OriginalArg(5) int entitySize, @OriginalArg(6) int destX, @OriginalArg(7) int locShape, @OriginalArg(8) int blockAccessFlags, @OriginalArg(9) int srcX, @OriginalArg(10) int locSizeY, @OriginalArg(11) boolean approx, @OriginalArg(12) int srcY) {
		@Pc(3) int x;
		@Pc(10) int y;
		for (x = 0; x < 104; x++) {
			for (y = 0; y < 104; y++) {
				parents[x][y] = 0;
				costs[x][y] = 99999999;
			}
		}
		x = srcX;
		y = srcY;
		parents[srcX][srcY] = 99;
		costs[srcX][srcY] = 0;
		@Pc(53) byte initialIndex = 0;
		queueX[0] = srcX;
		@Pc(65) int queueWriterIndex = initialIndex + 1;
		queueY[0] = srcY;
		@Pc(69) int queueReaderIndex = 0;
		@Pc(71) boolean reachedDest = false;
		@Pc(76) int[][] flags = collisionMaps[Player.plane].flags;
		@Pc(201) int cost;
		@Pc(242) int i;
		nextTile:
		while (queueReaderIndex != queueWriterIndex) {
			x = queueX[queueReaderIndex];
			y = queueY[queueReaderIndex];
			queueReaderIndex = queueReaderIndex + 1 & 0xFFF;
			if (destX == x && y == destY) {
				reachedDest = true;
				break;
			}
			if (locShape != 0) {
				if ((locShape < 5 || locShape == 10) && collisionMaps[Player.plane].isAtWall(destY, x, y, destX, locShape - 1, entitySize, locAngle)) {
					reachedDest = true;
					break;
				}
				if (locShape < 10 && collisionMaps[Player.plane].isAtWallDecor(destY, locShape - 1, destX, y, entitySize, locAngle, x)) {
					reachedDest = true;
					break;
				}
			}
			if (locSizeX != 0 && locSizeY != 0 && collisionMaps[Player.plane].isInsideOrOutsideRect(destX, y, x, entitySize, locSizeX, blockAccessFlags, destY, locSizeY)) {
				reachedDest = true;
				break;
			}
			cost = costs[x][y] + 1;
			if (x > 0 && parents[x - 1][y] == 0 && (flags[x - 1][y] & 0x12C010E) == 0 && (flags[x - 1][entitySize + y - 1] & 0x12C0138) == 0) {
				i = 1;
				while (true) {
					if (entitySize - 1 <= i) {
						queueX[queueWriterIndex] = x - 1;
						queueY[queueWriterIndex] = y;
						parents[x - 1][y] = 2;
						queueWriterIndex = queueWriterIndex + 1 & 0xFFF;
						costs[x - 1][y] = cost;
						break;
					}
					if ((flags[x - 1][y + i] & 0x12C013E) != 0) {
						break;
					}
					i++;
				}
			}
			if (x < 102 && parents[x + 1][y] == 0 && (flags[x + entitySize][y] & 0x12C0183) == 0 && (flags[entitySize + x][y + entitySize - 1] & 0x12C01E0) == 0) {
				i = 1;
				while (true) {
					if (i >= entitySize - 1) {
						queueX[queueWriterIndex] = x + 1;
						queueY[queueWriterIndex] = y;
						parents[x + 1][y] = 8;
						costs[x + 1][y] = cost;
						queueWriterIndex = queueWriterIndex + 1 & 0xFFF;
						break;
					}
					if ((flags[entitySize + x][y + i] & 0x12C01E3) != 0) {
						break;
					}
					i++;
				}
			}
			if (y > 0 && parents[x][y - 1] == 0 && (flags[x][y - 1] & 0x12C010E) == 0 && (flags[entitySize + x - 1][y - 1] & 0x12C0183) == 0) {
				i = 1;
				while (true) {
					if (entitySize - 1 <= i) {
						queueX[queueWriterIndex] = x;
						queueY[queueWriterIndex] = y - 1;
						parents[x][y - 1] = 1;
						queueWriterIndex = queueWriterIndex + 1 & 0xFFF;
						costs[x][y - 1] = cost;
						break;
					}
					if ((flags[x + i][y - 1] & 0x12C018F) != 0) {
						break;
					}
					i++;
				}
			}
			if (y < 102 && parents[x][y + 1] == 0 && (flags[x][y + entitySize] & 0x12C0138) == 0 && (flags[x + entitySize - 1][entitySize + y] & 0x12C01E0) == 0) {
				i = 1;
				while (true) {
					if (i >= entitySize - 1) {
						queueX[queueWriterIndex] = x;
						queueY[queueWriterIndex] = y + 1;
						parents[x][y + 1] = 4;
						costs[x][y + 1] = cost;
						queueWriterIndex = queueWriterIndex + 1 & 0xFFF;
						break;
					}
					if ((flags[x + i][entitySize + y] & 0x12C01F8) != 0) {
						break;
					}
					i++;
				}
			}
			if (x > 0 && y > 0 && parents[x - 1][y - 1] == 0 && (flags[x - 1][entitySize + y - 1 - 1] & 0x12C0138) == 0 && (flags[x - 1][y - 1] & 0x12C010E) == 0 && (flags[entitySize + x - 1 - 1][y - 1] & 0x12C0183) == 0) {
				i = 1;
				while (true) {
					if (entitySize - 1 <= i) {
						queueX[queueWriterIndex] = x - 1;
						queueY[queueWriterIndex] = y - 1;
						queueWriterIndex = queueWriterIndex + 1 & 0xFFF;
						parents[x - 1][y - 1] = 3;
						costs[x - 1][y - 1] = cost;
						break;
					}
					if ((flags[x - 1][y + i - 1] & 0x12C013E) != 0 || (flags[i + x - 1][y - 1] & 0x12C018F) != 0) {
						break;
					}
					i++;
				}
			}
			if (x < 102 && y > 0 && parents[x + 1][y - 1] == 0 && (flags[x + 1][y - 1] & 0x12C010E) == 0 && (flags[entitySize + x][y - 1] & 0x12C0183) == 0 && (flags[x + entitySize][y + entitySize - 1 - 1] & 0x12C01E0) == 0) {
				i = 1;
				while (true) {
					if (i >= entitySize - 1) {
						queueX[queueWriterIndex] = x + 1;
						queueY[queueWriterIndex] = y - 1;
						queueWriterIndex = queueWriterIndex + 1 & 0xFFF;
						parents[x + 1][y - 1] = 9;
						costs[x + 1][y - 1] = cost;
						break;
					}
					if ((flags[x + entitySize][y + i - 1] & 0x12C01E3) != 0 || (flags[i + x + 1][y - 1] & 0x12C018F) != 0) {
						break;
					}
					i++;
				}
			}
			if (x > 0 && y < 102 && parents[x - 1][y + 1] == 0 && (flags[x - 1][y + 1] & 0x12C010E) == 0 && (flags[x - 1][y + entitySize] & 0x12C0138) == 0 && (flags[x][y + entitySize] & 0x12C01E0) == 0) {
				i = 1;
				while (true) {
					if (entitySize - 1 <= i) {
						queueX[queueWriterIndex] = x - 1;
						queueY[queueWriterIndex] = y + 1;
						queueWriterIndex = queueWriterIndex + 1 & 0xFFF;
						parents[x - 1][y + 1] = 6;
						costs[x - 1][y + 1] = cost;
						break;
					}
					if ((flags[x - 1][y + i + 1] & 0x12C013E) != 0 || (flags[i + x - 1][entitySize + y] & 0x12C01F8) != 0) {
						break;
					}
					i++;
				}
			}
			if (x < 102 && y < 102 && parents[x + 1][y + 1] == 0 && (flags[x + 1][y + entitySize] & 0x12C0138) == 0 && (flags[x + entitySize][y + entitySize] & 0x12C01E0) == 0 && (flags[entitySize + x][y + 1] & 0x12C0183) == 0) {
				for (i = 1; i < entitySize - 1; i++) {
					if ((flags[i + x + 1][y + entitySize] & 0x12C01F8) != 0 || (flags[entitySize + x][i + y + 1] & 0x12C01E3) != 0) {
						continue nextTile;
					}
				}
				queueX[queueWriterIndex] = x + 1;
				queueY[queueWriterIndex] = y + 1;
				parents[x + 1][y + 1] = 12;
				costs[x + 1][y + 1] = cost;
				queueWriterIndex = queueWriterIndex + 1 & 0xFFF;
			}
		}
		approximateDestination = 0;
		if (!reachedDest) {
			if (!approx) {
				return false;
			}
			cost = 1000;
			i = 100;
			for (@Pc(1247) int scanX = destX - 10; scanX <= destX + 10; scanX++) {
				for (@Pc(1257) int scanY = destY - 10; scanY <= destY + 10; scanY++) {
					if (scanX >= 0 && scanY >= 0 && scanX < 104 && scanY < 104 && costs[scanX][scanY] < 100) {
						@Pc(1295) int dx = 0;
						if (destX > scanX) {
							dx = destX - scanX;
						} else if (destX + locSizeX - 1 < scanX) {
							dx = scanX + 1 - locSizeX - destX;
						}
						@Pc(1334) int dy = 0;
						if (scanY < destY) {
							dy = destY - scanY;
						} else if (destY + locSizeY - 1 < scanY) {
							dy = scanY + 1 - destY - locSizeY;
						}
						@Pc(1377) int distSq = dx * dx + dy * dy;
						if (distSq < cost || distSq == cost && i > costs[scanX][scanY]) {
							i = costs[scanX][scanY];
							x = scanX;
							cost = distSq;
							y = scanY;
						}
					}
				}
			}
			if (cost == 1000) {
				return false;
			}
			if (x == srcX && srcY == y) {
				return false;
			}
			approximateDestination = 1;
		}
		@Pc(1438) byte initialBacktrackIndex = 0;
		queueX[0] = x;
		queueReaderIndex = initialBacktrackIndex + 1;
		queueY[0] = y;
		cost = i = parents[x][y];
		while (x != srcX || srcY != y) {
			if (i != cost) {
				queueX[queueReaderIndex] = x;
				i = cost;
				queueY[queueReaderIndex++] = y;
			}
			if ((cost & 0x2) != 0) {
				x++;
			} else if ((cost & 0x8) != 0) {
				x--;
			}
			if ((cost & 0x1) != 0) {
				y++;
			} else if ((cost & 0x4) != 0) {
				y--;
			}
			cost = parents[x][y];
		}
		if (queueReaderIndex > 0) {
			ClientProt.sendMovePacket(queueReaderIndex, moveType);
			return true;
		} else return moveType != 1;
	}

	@OriginalMember(owner = "client!t", name = "a", descriptor = "(BJII)Z")
	public static boolean findPathToLoc(@OriginalArg(1) long key, @OriginalArg(2) int destY, @OriginalArg(3) int destX) {
		@Pc(12) int shape = (int) key >> 14 & 0x1F;
		@Pc(24) int angle = (int) key >> 20 & 0x3;
		@Pc(31) int id = (int) (key >>> 32) & Integer.MAX_VALUE;
		if (shape == 10 || shape == 11 || shape == 22) {
			@Pc(46) LocType locType = LocTypeList.get(id);
			@Pc(62) int sizeX;
			@Pc(59) int sizeY;
			if (angle == 0 || angle == 2) {
				sizeY = locType.length;
				sizeX = locType.width;
			} else {
				sizeY = locType.width;
				sizeX = locType.length;
			}
			@Pc(73) int blockFlags = locType.blocksides;
			if (angle != 0) {
				blockFlags = (blockFlags << angle & 0xF) + (blockFlags >> 4 - angle);
			}
			findPath(PlayerList.self.movementQueueY[0], 0, sizeY, true, blockFlags, destX, sizeX, 0, 2, destY, PlayerList.self.movementQueueX[0]);
		} else {
			findPath(PlayerList.self.movementQueueY[0], angle, 0, true, 0, destX, 0, shape + 1, 2, destY, PlayerList.self.movementQueueX[0]);
		}
		Cross.y = Mouse.clickY;
		Cross.milliseconds = 0;
		Cross.type = 2;
		Cross.x = Mouse.clickX;
		return true;
	}
}
