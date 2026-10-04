package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!mj")
public final class CollisionMap {

	@OriginalMember(owner = "client!mj", name = "k", descriptor = "I")
	private final int length;

	@OriginalMember(owner = "client!mj", name = "p", descriptor = "I")
	private final int yOffset;

	@OriginalMember(owner = "client!mj", name = "v", descriptor = "I")
	private final int xOffset;

	@OriginalMember(owner = "client!mj", name = "e", descriptor = "I")
	private final int width;

	@OriginalMember(owner = "client!mj", name = "m", descriptor = "[[I")
	public final int[][] flags;

	@OriginalMember(owner = "client!mj", name = "<init>", descriptor = "(II)V")
	public CollisionMap(@OriginalArg(0) int width, @OriginalArg(1) int length) {
		this.length = length;
		this.yOffset = 0;
		this.xOffset = 0;
		this.width = width;
		this.flags = new int[this.width][this.length];
		this.clear();
	}

	@OriginalMember(owner = "client!mj", name = "a", descriptor = "(IZIIII)V")
	public final void unflagWall(@OriginalArg(0) int angle, @OriginalArg(1) boolean blockProjectiles, @OriginalArg(3) int startY, @OriginalArg(4) int shape, @OriginalArg(5) int startX) {
		@Pc(4) int x = startX - this.xOffset;
		@Pc(23) int y = startY - this.yOffset;
		if (shape == 0) {
			if (angle == 0) {
				this.unflag(y, x, 128);
				this.unflag(y, x - 1, 8);
			}
			if (angle == 1) {
				this.unflag(y, x, 2);
				this.unflag(y + 1, x, 32);
			}
			if (angle == 2) {
				this.unflag(y, x, 8);
				this.unflag(y, x + 1, 128);
			}
			if (angle == 3) {
				this.unflag(y, x, 32);
				this.unflag(y - 1, x, 2);
			}
		}
		if (shape == 1 || shape == 3) {
			if (angle == 0) {
				this.unflag(y, x, 1);
				this.unflag(y + 1, x + -1, 16);
			}
			if (angle == 1) {
				this.unflag(y, x, 4);
				this.unflag(y + 1, x + 1, 64);
			}
			if (angle == 2) {
				this.unflag(y, x, 16);
				this.unflag(y - 1, x - -1, 1);
			}
			if (angle == 3) {
				this.unflag(y, x, 64);
				this.unflag(y - 1, x + -1, 4);
			}
		}
		if (shape == 2) {
			if (angle == 0) {
				this.unflag(y, x, 130);
				this.unflag(y, x - 1, 8);
				this.unflag(y + 1, x, 32);
			}
			if (angle == 1) {
				this.unflag(y, x, 10);
				this.unflag(y + 1, x, 32);
				this.unflag(y, x + 1, 128);
			}
			if (angle == 2) {
				this.unflag(y, x, 40);
				this.unflag(y, x + 1, 128);
				this.unflag(y - 1, x, 2);
			}
			if (angle == 3) {
				this.unflag(y, x, 160);
				this.unflag(y - 1, x, 2);
				this.unflag(y, x - 1, 8);
			}
		}

		if (blockProjectiles) {
			if (shape == 0) {
				if (angle == 0) {
					this.unflag(y, x, 65536);
					this.unflag(y, x - 1, 4096);
				}
				if (angle == 1) {
					this.unflag(y, x, 1024);
					this.unflag(y + 1, x, 16384);
				}
				if (angle == 2) {
					this.unflag(y, x, 4096);
					this.unflag(y, x + 1, 65536);
				}
				if (angle == 3) {
					this.unflag(y, x, 16384);
					this.unflag(y - 1, x, 1024);
				}
			}
			if (shape == 1 || shape == 3) {
				if (angle == 0) {
					this.unflag(y, x, 512);
					this.unflag(y + 1, x + -1, 8192);
				}
				if (angle == 1) {
					this.unflag(y, x, 2048);
					this.unflag(y + 1, x - -1, 32768);
				}
				if (angle == 2) {
					this.unflag(y, x, 8192);
					this.unflag(y - 1, x + 1, 512);
				}
				if (angle == 3) {
					this.unflag(y, x, 32768);
					this.unflag(y - 1, x + -1, 2048);
				}
			}
			if (shape == 2) {
				if (angle == 0) {
					this.unflag(y, x, 66560);
					this.unflag(y, x - 1, 4096);
					this.unflag(y + 1, x, 16384);
				}
				if (angle == 1) {
					this.unflag(y, x, 5120);
					this.unflag(y + 1, x, 16384);
					this.unflag(y, x + 1, 65536);
				}
				if (angle == 2) {
					this.unflag(y, x, 20480);
					this.unflag(y, x + 1, 65536);
					this.unflag(y - 1, x, 1024);
				}
				if (angle == 3) {
					this.unflag(y, x, 81920);
					this.unflag(y - 1, x, 1024);
					this.unflag(y, x - 1, 4096);
				}
			}
		}
	}

	@OriginalMember(owner = "client!mj", name = "a", descriptor = "(IIIZII)V")
	public final void flagWall(@OriginalArg(0) int angle, @OriginalArg(2) int shape, @OriginalArg(3) boolean blocksProjectiles, @OriginalArg(4) int startY, @OriginalArg(5) int startX) {
		@Pc(4) int x = startX - this.xOffset;
		@Pc(13) int y = startY - this.yOffset;
		if (shape == 0) {
			if (angle == 0) {
				this.flag(128, x, y);
				this.flag(8, x - 1, y);
			}
			if (angle == 1) {
				this.flag(2, x, y);
				this.flag(32, x, y + 1);
			}
			if (angle == 2) {
				this.flag(8, x, y);
				this.flag(128, x + 1, y);
			}
			if (angle == 3) {
				this.flag(32, x, y);
				this.flag(2, x, y - 1);
			}
		}
		if (shape == 1 || shape == 3) {
			if (angle == 0) {
				this.flag(1, x, y);
				this.flag(16, x - 1, y + 1);
			}
			if (angle == 1) {
				this.flag(4, x, y);
				this.flag(64, x + 1, y + 1);
			}
			if (angle == 2) {
				this.flag(16, x, y);
				this.flag(1, x + 1, y + -1);
			}
			if (angle == 3) {
				this.flag(64, x, y);
				this.flag(4, x - 1, y + -1);
			}
		}
		if (shape == 2) {
			if (angle == 0) {
				this.flag(130, x, y);
				this.flag(8, x - 1, y);
				this.flag(32, x, y + 1);
			}
			if (angle == 1) {
				this.flag(10, x, y);
				this.flag(32, x, y + 1);
				this.flag(128, x + 1, y);
			}
			if (angle == 2) {
				this.flag(40, x, y);
				this.flag(128, x + 1, y);
				this.flag(2, x, y - 1);
			}
			if (angle == 3) {
				this.flag(160, x, y);
				this.flag(2, x, y - 1);
				this.flag(8, x - 1, y);
			}
		}
		if (blocksProjectiles) {
			if (shape == 0) {
				if (angle == 0) {
					this.flag(65536, x, y);
					this.flag(4096, x - 1, y);
				}
				if (angle == 1) {
					this.flag(1024, x, y);
					this.flag(16384, x, y + 1);
				}
				if (angle == 2) {
					this.flag(4096, x, y);
					this.flag(65536, x + 1, y);
				}
				if (angle == 3) {
					this.flag(16384, x, y);
					this.flag(1024, x, y - 1);
				}
			}
			if (shape == 1 || shape == 3) {
				if (angle == 0) {
					this.flag(512, x, y);
					this.flag(8192, x - 1, y + 1);
				}
				if (angle == 1) {
					this.flag(2048, x, y);
					this.flag(32768, x + 1, y + 1);
				}
				if (angle == 2) {
					this.flag(8192, x, y);
					this.flag(512, x + 1, y + -1);
				}
				if (angle == 3) {
					this.flag(32768, x, y);
					this.flag(2048, x - 1, y - 1);
				}
			}
			if (shape == 2) {
				if (angle == 0) {
					this.flag(66560, x, y);
					this.flag(4096, x - 1, y);
					this.flag(16384, x, y + 1);
				}
				if (angle == 1) {
					this.flag(5120, x, y);
					this.flag(16384, x, y + 1);
					this.flag(65536, x + 1, y);
				}
				if (angle == 2) {
					this.flag(20480, x, y);
					this.flag(65536, x + 1, y);
					this.flag(1024, x, y - 1);
				}
				if (angle == 3) {
					this.flag(81920, x, y);
					this.flag(1024, x, y - 1);
					this.flag(4096, x - 1, y);
				}
			}
		}
	}

	@OriginalMember(owner = "client!mj", name = "a", descriptor = "(IIIIIIIIB)Z")
	private boolean isInsideRect(@OriginalArg(0) int srcWidth, @OriginalArg(1) int destWidth, @OriginalArg(2) int destX, @OriginalArg(3) int destHeight, @OriginalArg(4) int srcY, @OriginalArg(5) int destY, @OriginalArg(6) int srcHeight, @OriginalArg(7) int srcX) {
		if (destWidth + destX > srcX && destX < srcWidth + srcX) {
			return destHeight + destY > srcY && srcHeight + srcY > destY;
		} else {
			return false;
		}
	}

	@OriginalMember(owner = "client!mj", name = "a", descriptor = "(IIZIIIII)Z")
	public final boolean isAtWall(@OriginalArg(0) int srcY, @OriginalArg(1) int destX, @OriginalArg(3) int destY, @OriginalArg(4) int srcX, @OriginalArg(5) int destType, @OriginalArg(6) int size, @OriginalArg(7) int destAngle) {
		if (size == 1) {
			if (destX == srcX && destY == srcY) {
				return true;
			}
		} else if (destX <= srcX && destX + size - 1 >= srcX && srcY <= size + srcY - 1) {
			return true;
		}
		@Pc(49) int endY = destY - this.yOffset;
		@Pc(54) int startX = srcX - this.xOffset;
		@Pc(59) int startY = srcY - this.yOffset;
		@Pc(64) int endX = destX - this.xOffset;
		if (size == 1) {
			if (destType == 0) {
				if (destAngle == 0) {
					if (endX == startX - 1 && endY == startY) {
						return true;
					}
					if (endX == startX && startY + 1 == endY && (this.flags[endX][endY] & 0x12C0120) == 0) {
						return true;
					}
					if (startX == endX && startY - 1 == endY && (this.flags[endX][endY] & 0x12C0102) == 0) {
						return true;
					}
				} else if (destAngle == 1) {
					if (startX == endX && endY == startY + 1) {
						return true;
					}
					if (startX - 1 == endX && startY == endY && (this.flags[endX][endY] & 0x12C0108) == 0) {
						return true;
					}
					if (endX == startX + 1 && endY == startY && (this.flags[endX][endY] & 0x12C0180) == 0) {
						return true;
					}
				} else if (destAngle == 2) {
					if (startX + 1 == endX && endY == startY) {
						return true;
					}
					if (startX == endX && endY == startY + 1 && (this.flags[endX][endY] & 0x12C0120) == 0) {
						return true;
					}
					if (startX == endX && startY - 1 == endY && (this.flags[endX][endY] & 0x12C0102) == 0) {
						return true;
					}
				} else if (destAngle == 3) {
					if (endX == startX && startY - 1 == endY) {
						return true;
					}
					if (startX - 1 == endX && startY == endY && (this.flags[endX][endY] & 0x12C0108) == 0) {
						return true;
					}
					if (startX + 1 == endX && endY == startY && (this.flags[endX][endY] & 0x12C0180) == 0) {
						return true;
					}
				}
			}
			if (destType == 2) {
				if (destAngle == 0) {
					if (endX == startX - 1 && startY == endY) {
						return true;
					}
					if (endX == startX && endY == startY + 1) {
						return true;
					}
					if (endX == startX + 1 && endY == startY && (this.flags[endX][endY] & 0x12C0180) == 0) {
						return true;
					}
					if (startX == endX && endY == startY - 1 && (this.flags[endX][endY] & 0x12C0102) == 0) {
						return true;
					}
				} else if (destAngle == 1) {
					if (endX == startX - 1 && startY == endY && (this.flags[endX][endY] & 0x12C0108) == 0) {
						return true;
					}
					if (startX == endX && startY + 1 == endY) {
						return true;
					}
					if (endX == startX + 1 && startY == endY) {
						return true;
					}
					if (startX == endX && endY == startY - 1 && (this.flags[endX][endY] & 0x12C0102) == 0) {
						return true;
					}
				} else if (destAngle == 2) {
					if (endX == startX - 1 && startY == endY && (this.flags[endX][endY] & 0x12C0108) == 0) {
						return true;
					}
					if (startX == endX && endY == startY + 1 && (this.flags[endX][endY] & 0x12C0120) == 0) {
						return true;
					}
					if (startX + 1 == endX && startY == endY) {
						return true;
					}
					if (endX == startX && startY - 1 == endY) {
						return true;
					}
				} else if (destAngle == 3) {
					if (endX == startX - 1 && startY == endY) {
						return true;
					}
					if (endX == startX && startY + 1 == endY && (this.flags[endX][endY] & 0x12C0120) == 0) {
						return true;
					}
					if (endX == startX + 1 && startY == endY && (this.flags[endX][endY] & 0x12C0180) == 0) {
						return true;
					}
					if (endX == startX && endY == startY - 1) {
						return true;
					}
				}
			}
			if (destType == 9) {
				if (endX == startX && startY + 1 == endY && (this.flags[endX][endY] & 0x20) == 0) {
					return true;
				}
				if (endX == startX && endY == startY - 1 && (this.flags[endX][endY] & 0x2) == 0) {
					return true;
				}
				if (endX == startX - 1 && endY == startY && (this.flags[endX][endY] & 0x8) == 0) {
					return true;
				}
				return startX + 1 == endX && startY == endY && (this.flags[endX][endY] & 0x80) == 0;
			}
		} else {
			@Pc(785) int x1 = size + endX - 1;
			@Pc(792) int y1 = endY + size - 1;
			if (destType == 0) {
				if (destAngle == 0) {
					if (endX == startX - size && endY <= startY && startY <= y1) {
						return true;
					}
					if (endX <= startX && x1 >= startX && startY + 1 == endY && (this.flags[startX][endY] & 0x12C0120) == 0) {
						return true;
					}
					if (endX <= startX && x1 >= startX && startY - size == endY && (this.flags[startX][y1] & 0x12C0102) == 0) {
						return true;
					}
				} else if (destAngle == 1) {
					if (startX >= endX && startX <= x1 && startY + 1 == endY) {
						return true;
					}
					if (endX == startX - size && endY <= startY && y1 >= startY && (this.flags[x1][startY] & 0x12C0108) == 0) {
						return true;
					}
					if (endX == startX + 1 && endY <= startY && y1 >= startY && (this.flags[endX][startY] & 0x12C0180) == 0) {
						return true;
					}
				} else if (destAngle == 2) {
					if (startX + 1 == endX && endY <= startY && startY <= y1) {
						return true;
					}
					if (startX >= endX && x1 >= startX && endY == startY + 1 && (this.flags[startX][endY] & 0x12C0120) == 0) {
						return true;
					}
					if (startX >= endX && x1 >= startX && startY - size == endY && (this.flags[startX][y1] & 0x12C0102) == 0) {
						return true;
					}
				} else if (destAngle == 3) {
					if (endX <= startX && x1 >= startX && startY - size == endY) {
						return true;
					}
					if (endX == startX - size && startY >= endY && y1 >= startY && (this.flags[x1][startY] & 0x12C0108) == 0) {
						return true;
					}
					if (startX + 1 == endX && endY <= startY && startY <= y1 && (this.flags[endX][startY] & 0x12C0180) == 0) {
						return true;
					}
				}
			}
			if (destType == 2) {
				if (destAngle == 0) {
					if (endX == startX - size && startY >= endY && startY <= y1) {
						return true;
					}
					if (endX <= startX && x1 >= startX && endY == startY + 1) {
						return true;
					}
					if (startX + 1 == endX && startY >= endY && startY <= y1 && (this.flags[endX][startY] & 0x12C0180) == 0) {
						return true;
					}
					if (startX >= endX && x1 >= startX && startY - size == endY && (this.flags[startX][y1] & 0x12C0102) == 0) {
						return true;
					}
				} else if (destAngle == 1) {
					if (endX == startX - size && startY >= endY && startY <= y1 && (this.flags[x1][startY] & 0x12C0108) == 0) {
						return true;
					}
					if (endX <= startX && x1 >= startX && endY == startY + 1) {
						return true;
					}
					if (startX + 1 == endX && startY >= endY && startY <= y1) {
						return true;
					}
					if (startX >= endX && startX <= x1 && endY == startY - size && (this.flags[startX][y1] & 0x12C0102) == 0) {
						return true;
					}
				} else if (destAngle == 2) {
					if (startX - size == endX && endY <= startY && y1 >= startY && (this.flags[x1][startY] & 0x12C0108) == 0) {
						return true;
					}
					if (endX <= startX && startX <= x1 && endY == startY + 1 && (this.flags[startX][endY] & 0x12C0120) == 0) {
						return true;
					}
					if (endX == startX + 1 && endY <= startY && startY <= y1) {
						return true;
					}
					if (endX <= startX && x1 >= startX && startY - size == endY) {
						return true;
					}
				} else if (destAngle == 3) {
					if (startX - size == endX && startY >= endY && startY <= y1) {
						return true;
					}
					if (endX <= startX && startX <= x1 && startY + 1 == endY && (this.flags[startX][endY] & 0x12C0120) == 0) {
						return true;
					}
					if (endX == startX + 1 && startY >= endY && startY <= y1 && (this.flags[endX][startY] & 0x12C0180) == 0) {
						return true;
					}
					if (endX <= startX && x1 >= startX && startY - size == endY) {
						return true;
					}
				}
			}
			if (destType == 9) {
				if (endX <= startX && startX <= x1 && endY == startY + 1 && (this.flags[startX][endY] & 0x12C0120) == 0) {
					return true;
				}
				if (startX >= endX && startX <= x1 && endY == startY - size && (this.flags[startX][y1] & 0x12C0102) == 0) {
					return true;
				}
				if (startX - size == endX && startY >= endY && startY <= y1 && (this.flags[x1][startY] & 0x12C0108) == 0) {
					return true;
				}
				return endX == startX + 1 && startY >= endY && y1 >= startY && (this.flags[endX][startY] & 0x12C0180) == 0;
			}
		}
		return false;
	}

	@OriginalMember(owner = "client!mj", name = "a", descriptor = "(IZBIII)V")
	public final void flagScenery(@OriginalArg(0) int startX, @OriginalArg(1) boolean blocksProjectiles, @OriginalArg(3) int startY, @OriginalArg(4) int sizeX, @OriginalArg(5) int sizeY) {
		@Pc(6) int y = startY - this.yOffset;
		@Pc(11) int x = startX - this.xOffset;
		@Pc(17) int flags = 256;
		if (blocksProjectiles) {
			flags = 131328;
		}
		for (@Pc(25) int x0 = x; x0 < x + sizeX; x0++) {
			if (x0 >= 0 && x0 < this.width) {
				for (@Pc(47) int y0 = y; y0 < sizeY + y; y0++) {
					if (y0 >= 0 && this.length > y0) {
						this.flag(flags, x0, y0);
					}
				}
			}
		}
	}

	@OriginalMember(owner = "client!mj", name = "a", descriptor = "(IBII)V")
	private void flag(@OriginalArg(0) int flags, @OriginalArg(2) int x, @OriginalArg(3) int y) {
		this.flags[x][y] |= flags;
	}

	@OriginalMember(owner = "client!mj", name = "a", descriptor = "(IIIIIIII)Z")
	public final boolean isAtWallDecor(@OriginalArg(0) int srcY, @OriginalArg(1) int destType, @OriginalArg(2) int srcX, @OriginalArg(3) int destY, @OriginalArg(4) int size, @OriginalArg(5) int destAngle, @OriginalArg(6) int destX) {
		if (size == 1) {
			if (srcX == destX && srcY == destY) {
				return true;
			}
		} else if (destX <= srcX && size + destX - 1 >= srcX && srcY + size - 1 >= srcY) {
			return true;
		}
		@Pc(62) int endX = destX - this.xOffset;
		@Pc(67) int startY = srcY - this.yOffset;
		@Pc(72) int startX = srcX - this.xOffset;
		@Pc(77) int endY = destY - this.yOffset;
		if (size == 1) {
			if (destType == 6 || destType == 7) {
				if (destType == 7) {
					destAngle = destAngle + 2 & 0x3;
				}
				if (destAngle == 0) {
					if (endX == startX + 1 && startY == endY && (this.flags[endX][endY] & 0x80) == 0) {
						return true;
					}
					if (startX == endX && endY == startY - 1 && (this.flags[endX][endY] & 0x2) == 0) {
						return true;
					}
				} else if (destAngle == 1) {
					if (endX == startX - 1 && startY == endY && (this.flags[endX][endY] & 0x8) == 0) {
						return true;
					}
					if (startX == endX && endY == startY - 1 && (this.flags[endX][endY] & 0x2) == 0) {
						return true;
					}
				} else if (destAngle == 2) {
					if (endX == startX - 1 && endY == startY && (this.flags[endX][endY] & 0x8) == 0) {
						return true;
					}
					if (startX == endX && startY + 1 == endY && (this.flags[endX][endY] & 0x20) == 0) {
						return true;
					}
				} else if (destAngle == 3) {
					if (endX == startX + 1 && endY == startY && (this.flags[endX][endY] & 0x80) == 0) {
						return true;
					}
					if (startX == endX && endY == startY + 1 && (this.flags[endX][endY] & 0x20) == 0) {
						return true;
					}
				}
			}
			if (destType == 8) {
				if (endX == startX && startY + 1 == endY && (this.flags[endX][endY] & 0x20) == 0) {
					return true;
				}
				if (endX == startX && startY - 1 == endY && (this.flags[endX][endY] & 0x2) == 0) {
					return true;
				}
				if (startX - 1 == endX && endY == startY && (this.flags[endX][endY] & 0x8) == 0) {
					return true;
				}
				return endX == startX + 1 && startY == endY && (this.flags[endX][endY] & 0x80) == 0;
			}
		} else {
			@Pc(414) int x1 = endX + size - 1;
			@Pc(420) int y1 = endY + size - 1;
			if (destType == 6 || destType == 7) {
				if (destType == 7) {
					destAngle = destAngle + 2 & 0x3;
				}
				if (destAngle == 0) {
					if (startX + 1 == endX && endY <= startY && startY <= y1 && (this.flags[endX][startY] & 0x80) == 0) {
						return true;
					}
					if (startX >= endX && x1 >= startX && startY - size == endY && (this.flags[startX][y1] & 0x2) == 0) {
						return true;
					}
				} else if (destAngle == 1) {
					if (startX - size == endX && startY >= endY && y1 >= startY && (this.flags[x1][startY] & 0x8) == 0) {
						return true;
					}
					if (startX >= endX && x1 >= startX && startY - size == endY && (this.flags[startX][y1] & 0x2) == 0) {
						return true;
					}
				} else if (destAngle == 2) {
					if (endX == startX - size && startY >= endY && startY <= y1 && (this.flags[x1][startY] & 0x8) == 0) {
						return true;
					}
					if (endX <= startX && startX <= x1 && endY == startY + 1 && (this.flags[startX][endY] & 0x20) == 0) {
						return true;
					}
				} else if (destAngle == 3) {
					if (startX + 1 == endX && endY <= startY && startY <= y1 && (this.flags[endX][startY] & 0x80) == 0) {
						return true;
					}
					if (startX >= endX && startX <= x1 && endY == startY + 1 && (this.flags[startX][endY] & 0x20) == 0) {
						return true;
					}
				}
			}
			if (destType == 8) {
				if (endX <= startX && x1 >= startX && endY == startY + 1 && (this.flags[startX][endY] & 0x20) == 0) {
					return true;
				}
				if (startX >= endX && x1 >= startX && endY == startY - size && (this.flags[startX][y1] & 0x2) == 0) {
					return true;
				}
				if (endX == startX - size && endY <= startY && y1 >= startY && (this.flags[x1][startY] & 0x8) == 0) {
					return true;
				}
				return endX == startX + 1 && startY >= endY && startY <= y1 && (this.flags[endX][startY] & 0x80) == 0;
			}
		}
		return false;
	}

	@OriginalMember(owner = "client!mj", name = "a", descriptor = "(IIIIZIIIII)Z")
	private boolean isOutsideRect(@OriginalArg(0) int destX, @OriginalArg(1) int destY, @OriginalArg(2) int destWidth, @OriginalArg(3) int destBlockedSides, @OriginalArg(5) int destHeight, @OriginalArg(6) int srcX, @OriginalArg(7) int srcY, @OriginalArg(8) int srcWidth, @OriginalArg(9) int srcHeight) {
		@Pc(9) int srcRight = srcX + srcWidth;
		@Pc(13) int srcTop = srcY + srcHeight;
		@Pc(22) int destRight = destWidth + destX;
		@Pc(27) int destTop = destY + destHeight;
		@Pc(45) int i;
		@Pc(52) int limit;
		if (srcX >= destX && srcX < destRight) {
			if (srcTop == destY && (destBlockedSides & 0x4) == 0) {
				i = srcX;
				limit = destRight >= srcRight ? srcRight : destRight;
				while (limit > i) {
					if ((this.flags[i - this.xOffset][srcTop - this.yOffset - 1] & 0x2) == 0) {
						return true;
					}
					i++;
				}
			} else if (destTop == srcY && (destBlockedSides & 0x1) == 0) {
				i = srcX;
				limit = srcRight > destRight ? destRight : srcRight;
				while (limit > i) {
					if ((this.flags[i - this.xOffset][srcY - this.yOffset] & 0x20) == 0) {
						return true;
					}
					i++;
				}
			}
		} else if (srcRight > destX && srcRight <= destRight) {
			if (destY == srcTop && (destBlockedSides & 0x4) == 0) {
				for (i = destX; i < srcRight; i++) {
					if ((this.flags[i - this.xOffset][srcTop - this.yOffset - 1] & 0x2) == 0) {
						return true;
					}
				}
			} else if (srcY == destTop && (destBlockedSides & 0x1) == 0) {
				for (i = destX; i < srcRight; i++) {
					if ((this.flags[i - this.xOffset][srcY - this.yOffset] & 0x20) == 0) {
						return true;
					}
				}
			}
		} else if (srcY >= destY && destTop > srcY) {
			if (srcRight == destX && (destBlockedSides & 0x8) == 0) {
				i = srcY;
				limit = destTop >= srcTop ? srcTop : destTop;
				while (i < limit) {
					if ((this.flags[srcRight - this.xOffset - 1][i - this.yOffset] & 0x8) == 0) {
						return true;
					}
					i++;
				}
			} else if (srcX == destRight && (destBlockedSides & 0x2) == 0) {
				i = srcY;
				limit = destTop < srcTop ? destTop : srcTop;
				while (i < limit) {
					if ((this.flags[srcX - this.xOffset][i - this.yOffset] & 0x80) == 0) {
						return true;
					}
					i++;
				}
			}
		} else if (destY < srcTop && destTop >= srcTop) {
			if (srcRight == destX && (destBlockedSides & 0x8) == 0) {
				for (i = destY; i < srcTop; i++) {
					if ((this.flags[srcRight - this.xOffset - 1][i - this.yOffset] & 0x8) == 0) {
						return true;
					}
				}
			} else if (destRight == srcX && (destBlockedSides & 0x2) == 0) {
				for (i = destY; i < srcTop; i++) {
					if ((this.flags[srcX - this.xOffset][i - this.yOffset] & 0x80) == 0) {
						return true;
					}
				}
			}
		}
		return false;
	}

	@OriginalMember(owner = "client!mj", name = "a", descriptor = "(I)V")
	public final void clear() {
		for (@Pc(3) int x = 0; x < this.width; x++) {
			for (@Pc(13) int y = 0; y < this.length; y++) {
				if (x == 0 || y == 0 || x >= this.width - 5 || this.length - 5 <= y) {
					this.flags[x][y] = 16777215;
				} else {
					this.flags[x][y] = 16777216;
				}
			}
		}
	}

	@OriginalMember(owner = "client!mj", name = "a", descriptor = "(III)V")
	public final void flagTile(@OriginalArg(0) int startY, @OriginalArg(2) int startX) {
		@Pc(12) int y = startY - this.yOffset;
		@Pc(17) int x = startX - this.xOffset;
		this.flags[x][y] |= 0x200000;
	}

	@OriginalMember(owner = "client!mj", name = "a", descriptor = "(ZIIIIIIII)Z")
	public final boolean isInsideOrOutsideRect(@OriginalArg(1) int destX, @OriginalArg(2) int y, @OriginalArg(3) int x, @OriginalArg(4) int size, @OriginalArg(5) int destWidth, @OriginalArg(6) int destBlockedSides, @OriginalArg(7) int destY, @OriginalArg(8) int destLength) {
		if (size > 1) {
			return this.isInsideRect(size, destWidth, destX, destLength, y, destY, size, x) || this.isOutsideRect(destX, destY, destWidth, destBlockedSides, destLength, x, y, size, size);
		}

		@Pc(41) int destX1 = destWidth + destX - 1;
		@Pc(47) int destY1 = destY + destLength - 1;
		if (destX <= x && destX1 >= x && destY <= y && y <= destY1) {
			return true;
		} else if (destX - 1 == x && y >= destY && y <= destY1 && (this.flags[x - this.xOffset][y - this.yOffset] & 0x8) == 0 && (destBlockedSides & 0x8) == 0) {
			return true;
		} else if (x == destX1 + 1 && destY <= y && destY1 >= y && (this.flags[x - this.xOffset][y - this.yOffset] & 0x80) == 0 && (destBlockedSides & 0x2) == 0) {
			return true;
		} else if (y == destY - 1 && destX <= x && destX1 >= x && (this.flags[x - this.xOffset][y - this.yOffset] & 0x2) == 0 && (destBlockedSides & 0x4) == 0) {
			return true;
		} else {
			return y == destY1 + 1 && x >= destX && destX1 >= x && (this.flags[x - this.xOffset][y - this.yOffset] & 0x20) == 0 && (destBlockedSides & 0x1) == 0;
		}
	}

	@OriginalMember(owner = "client!mj", name = "a", descriptor = "(IBI)V")
	public final void unflagGroundDecor(@OriginalArg(0) int startY, @OriginalArg(2) int startX) {
		@Pc(4) int x = startX - this.xOffset;
		@Pc(9) int y = startY - this.yOffset;
		this.flags[x][y] &= 0xFFFBFFFF;
	}

	@OriginalMember(owner = "client!mj", name = "b", descriptor = "(IIIII)Z")
	public final boolean isPathClear(@OriginalArg(1) int srcY, @OriginalArg(2) int destX, @OriginalArg(3) int destY, @OriginalArg(4) int srcX) {
		if (srcX == destX && srcY == destY) {
			return true;
		}
		@Pc(22) int dy = destY - this.yOffset;
		@Pc(33) int dx = destX - this.xOffset;
		if (dx < 0 || this.width <= dx || dy < 0 || dy >= this.length) {
			return false;
		}
		@Pc(61) int sy = srcY - this.yOffset;
		@Pc(66) int sx = srcX - this.xOffset;
		@Pc(77) int absDx;
		if (dx > sx) {
			absDx = dx - sx;
		} else {
			absDx = sx - dx;
		}
		@Pc(96) int absDy;
		if (dy <= sy) {
			absDy = sy - dy;
		} else {
			absDy = dy - sy;
		}
		@Pc(117) int step;
		@Pc(111) int accumulator;
		if (absDx <= absDy) {
			accumulator = 32768;
			step = absDx * 65536 / absDy;
			while (sy != dy) {
				if (dy > sy) {
					if ((this.flags[sx][sy] & 0x12C0102) != 0) {
						return false;
					}
					sy++;
				} else if (dy < sy) {
					if ((this.flags[sx][sy] & 0x12C0120) != 0) {
						return false;
					}
					sy--;
				}
				accumulator += step;
				if (accumulator >= 65536) {
					accumulator -= 65536;
					if (sx < dx) {
						if ((this.flags[sx][sy] & 0x12C0108) != 0) {
							return false;
						}
						sx++;
					} else if (sx > dx) {
						if ((this.flags[sx][sy] & 0x12C0180) != 0) {
							return false;
						}
						sx--;
					}
				}
			}
		} else {
			step = absDy * 65536 / absDx;
			accumulator = 32768;
			while (dx != sx) {
				if (sx < dx) {
					if ((this.flags[sx][sy] & 0x12C0108) != 0) {
						return false;
					}
					sx++;
				} else if (dx < sx) {
					if ((this.flags[sx][sy] & 0x12C0180) != 0) {
						return false;
					}
					sx--;
				}
				accumulator += step;
				if (accumulator >= 65536) {
					accumulator -= 65536;
					if (sy < dy) {
						if ((this.flags[sx][sy] & 0x12C0102) != 0) {
							return false;
						}
						sy++;
					} else if (dy < sy) {
						if ((this.flags[sx][sy] & 0x12C0120) != 0) {
							return false;
						}
						sy--;
					}
				}
			}
		}
		return (this.flags[dx][dy] & 0x1240100) == 0;
	}

	@OriginalMember(owner = "client!mj", name = "a", descriptor = "(BIII)V")
	private void unflag(@OriginalArg(1) int y, @OriginalArg(2) int x, @OriginalArg(3) int flags) {
		this.flags[x][y] &= ~flags;
	}

	@OriginalMember(owner = "client!mj", name = "a", descriptor = "(IIIZIII)V")
	public final void unflagScenery(@OriginalArg(1) int startX, @OriginalArg(2) int width, @OriginalArg(3) boolean blockProjectiles, @OriginalArg(4) int angle, @OriginalArg(5) int length, @OriginalArg(6) int startY) {
		@Pc(6) int x = startX - this.xOffset;
		@Pc(11) int y = startY - this.yOffset;
		@Pc(13) int flags = 256;
		if (blockProjectiles) {
			flags = 131328;
		}
		@Pc(40) int temp;
		if (angle == 1 || angle == 3) {
			temp = width;
			width = length;
			length = temp;
		}
		for (int x0 = x; x0 < x + width; x0++) {
			if (x0 >= 0 && x0 < this.width) {
				for (@Pc(61) int y0 = y; y0 < length + y; y0++) {
					if (y0 >= 0 && this.length > y0) {
						this.unflag(y0, x0, flags);
					}
				}
			}
		}
	}

	@OriginalMember(owner = "client!mj", name = "b", descriptor = "(III)V")
	public final void flagGroundDecor(@OriginalArg(0) int startX, @OriginalArg(1) int startY) {
		@Pc(4) int y = startY - this.yOffset;
		@Pc(17) int x = startX - this.xOffset;
		this.flags[x][y] |= 0x40000;
	}
}
