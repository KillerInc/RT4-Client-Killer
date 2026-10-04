package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!cd")
public final class ChangeLocRequest extends Node {

	@OriginalMember(owner = "client!ca", name = "X", descriptor = "Lclient!ih;")
	public static LinkedList queue = new LinkedList();

	@OriginalMember(owner = "client!cd", name = "r", descriptor = "I")
	public int y;

	@OriginalMember(owner = "client!cd", name = "t", descriptor = "I")
	public int level;

	@OriginalMember(owner = "client!cd", name = "w", descriptor = "I")
	public int originalShape;

	@OriginalMember(owner = "client!cd", name = "x", descriptor = "I")
	public int originalId;

	@OriginalMember(owner = "client!cd", name = "z", descriptor = "I")
	public int newAngle;

	@OriginalMember(owner = "client!cd", name = "A", descriptor = "I")
	public int originalAngle;

	@OriginalMember(owner = "client!cd", name = "F", descriptor = "I")
	public int newShape;

	@OriginalMember(owner = "client!cd", name = "G", descriptor = "I")
	public int layer;

	@OriginalMember(owner = "client!cd", name = "H", descriptor = "I")
	public int x;

	@OriginalMember(owner = "client!cd", name = "I", descriptor = "I")
	public int newId;

	@OriginalMember(owner = "client!cd", name = "C", descriptor = "I")
	public int resetLoops = -1;

	@OriginalMember(owner = "client!cd", name = "E", descriptor = "I")
	public int setLoops = 0;

	@OriginalMember(owner = "client!ug", name = "a", descriptor = "(B)V")
	public static void loop() {
		for (@Pc(10) ChangeLocRequest request = (ChangeLocRequest) queue.head(); request != null; request = (ChangeLocRequest) queue.next()) {
			if (request.resetLoops > 0) {
				request.resetLoops--;
			}
			if (request.resetLoops != 0) {
				if (request.setLoops > 0) {
					request.setLoops--;
				}
				if (request.setLoops == 0 && request.x >= 1 && request.y >= 1 && request.x <= 102 && request.y <= 102 && (request.newId < 0 || isLocModelReady(request.newId, request.newShape))) {
					SceneGraph.addLocModel(request.newId, request.x, request.level, request.newAngle, request.y, request.newShape, request.layer);
					request.setLoops = -1;
					if (request.originalId == request.newId && request.originalId == -1) {
						request.unlink();
					} else if (request.newId == request.originalId && request.newAngle == request.originalAngle && request.originalShape == request.newShape) {
						request.unlink();
					}
				}
			} else if (request.originalId < 0 || isLocModelReady(request.originalId, request.originalShape)) {
				SceneGraph.addLocModel(request.originalId, request.x, request.level, request.originalAngle, request.y, request.originalShape, request.layer);
				request.unlink();
			}
		}
	}

	@OriginalMember(owner = "client!ce", name = "a", descriptor = "(IIIIIIIIII)V")
	public static void push(@OriginalArg(0) int level, @OriginalArg(1) int y, @OriginalArg(3) int newAngle, @OriginalArg(4) int x, @OriginalArg(5) int resetLoops, @OriginalArg(6) int newId, @OriginalArg(7) int layer, @OriginalArg(8) int newShape, @OriginalArg(9) int setLoops) {
		@Pc(9) ChangeLocRequest loc = null;
		for (@Pc(14) ChangeLocRequest l = (ChangeLocRequest) queue.head(); l != null; l = (ChangeLocRequest) queue.next()) {
			if (l.level == level && x == l.x && l.y == y && layer == l.layer) {
				loc = l;
				break;
			}
		}
		if (loc == null) {
			loc = new ChangeLocRequest();
			loc.x = x;
			loc.y = y;
			loc.level = level;
			loc.layer = layer;
			init(loc);
			queue.addTail(loc);
		}
		loc.newShape = newShape;
		loc.setLoops = setLoops;
		loc.resetLoops = resetLoops;
		loc.newId = newId;
		loc.newAngle = newAngle;
	}

	@OriginalMember(owner = "client!sf", name = "a", descriptor = "(ILclient!cd;)V")
	public static void init(@OriginalArg(1) ChangeLocRequest loc) {
		@Pc(5) long key = 0L;
		@Pc(7) int originalId = -1;
		@Pc(14) int originalShape = 0;
		if (loc.layer == 0) {
			key = SceneGraph.getWallKey(loc.level, loc.x, loc.y);
		}
		@Pc(31) int originalAngle = 0;
		if (loc.layer == 1) {
			key = SceneGraph.getWallDecorKey(loc.level, loc.x, loc.y);
		}
		if (loc.layer == 2) {
			key = SceneGraph.getSceneryKey(loc.level, loc.x, loc.y);
		}
		if (loc.layer == 3) {
			key = SceneGraph.getGroundDecorKey(loc.level, loc.x, loc.y);
		}
		if (key != 0L) {
			originalId = Integer.MAX_VALUE & (int) (key >>> 32);
			originalAngle = (int) key >> 20 & 0x3;
			originalShape = (int) key >> 14 & 0x1F;
		}
		loc.originalId = originalId;
		loc.originalShape = originalShape;
		loc.originalAngle = originalAngle;
	}

	@OriginalMember(owner = "client!rl", name = "i", descriptor = "(I)V")
	public static void flush() {
		for (@Pc(10) ChangeLocRequest loc = (ChangeLocRequest) queue.head(); loc != null; loc = (ChangeLocRequest) queue.next()) {
			if (loc.resetLoops == -1) {
				loc.setLoops = 0;
				init(loc);
			} else {
				loc.unlink();
			}
		}
	}

	@OriginalMember(owner = "client!il", name = "a", descriptor = "(BII)Z")
	public static boolean isLocModelReady(@OriginalArg(1) int locId, @OriginalArg(2) int shape) {
		if (shape == 11) {
			shape = 10;
		}
		if (shape >= 5 && shape <= 8) {
			shape = 4;
		}
		@Pc(30) LocType type = LocTypeList.get(locId);
		return type.isReady(shape);
	}
}
