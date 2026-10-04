package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!hh")
public final class PlayerAppearance {

	@OriginalMember(owner = "client!md", name = "V", descriptor = "[[S")
	public static final short[][] GAME0_DESTINATION_BODY_COLORS = new short[][]{{6798, 107, 10283, 16, 4797, 7744, 5799, 4634, -31839, 22433, 2983, -11343, 8, 5281, 10438, 3650, -27322, -21845, 200, 571, 908, 21830, 28946, -15701, -14010}, {8741, 12, -1506, -22374, 7735, 8404, 1701, -27106, 24094, 10153, -8915, 4783, 1341, 16578, -30533, 25239, 8, 5281, 10438, 3650, -27322, -21845, 200, 571, 908, 21830, 28946, -15701, -14010}, {25238, 8742, 12, -1506, -22374, 7735, 8404, 1701, -27106, 24094, 10153, -8915, 4783, 1341, 16578, -30533, 8, 5281, 10438, 3650, -27322, -21845, 200, 571, 908, 21830, 28946, -15701, -14010}, {4626, 11146, 6439, 12, 4758, 10270}, {4550, 4537, 5681, 5673, 5790, 6806, 8076, 4574}};

	@OriginalMember(owner = "client!dg", name = "c", descriptor = "[[S")
	public static final short[][] GAME0_DESTINATION_SKIN_COLORS = new short[][]{{6554, 115, 10304, 28, 5702, 7756, 5681, 4510, -31835, 22437, 2859, -11339, 16, 5157, 10446, 3658, -27314, -21965, 472, 580, 784, 21966, 28950, -15697, -14002}, {9104, 10275, 7595, 3610, 7975, 8526, 918, -26734, 24466, 10145, -6882, 5027, 1457, 16565, -30545, 25486, 24, 5392, 10429, 3673, -27335, -21957, 192, 687, 412, 21821, 28835, -15460, -14019}, new short[0], new short[0], new short[0]};

	@OriginalMember(owner = "client!pk", name = "V", descriptor = "[S")
	public static final short[] GAME0_SOURCE_SKIN_COLORS = new short[]{-10304, 9104, -1, -1, -1};

	@OriginalMember(owner = "client!vd", name = "B", descriptor = "[S")
	public static final short[] GAME0_SOURCE_BODY_COLORS = new short[]{6798, 8741, 25238, 4626, 4550};

	@OriginalMember(owner = "client!bj", name = "t", descriptor = "[S")
	public static final short[] GAME1_SOURCE_SKIN_COLORS = new short[]{-4160, -4163, -8256, -8259, 22461};

	@OriginalMember(owner = "client!aa", name = "h", descriptor = "[S")
	public static final short[] GAME1_SOURCE_BODY_COLORS = new short[]{960, 957, -21568, -21571, 22464};

	@OriginalMember(owner = "client!ci", name = "u", descriptor = "[[S")
	public static final short[][] GAME1_DESTINATION_SKIN_COLORS = new short[][]{{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 24, 44, 64, 84, 104, 304, 678, 698, 550, 934, 954, 6448, 6946, 6966, 2352, 2726, 2746, 10544, 10918, 10938, 10304, 10550, 10570, 14640, 15014, 15034, 19760, 20134, 20154, -29392, -29018, -28998, 31024, 31270, 31290, -24272, -23898, -23878, -19152, -18778, -18758, -14032, -13658, -13638, -6864, -6490, -6470, 516, 536, 6788, 6808, 11012, 11032, 14980, 15000, 21124, 21144, -28924, -28904, -22012, -21992, -12924, -12904}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10339, 10574, 10425, 10398, 10345, 7512, 8507, 7378, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 24, 44, 64, 84, 104, 304, 678, 698, 550, 934, 954, 6448, 6946, 6966, 2352, 2726, 2746, 10544, 10918, 10938, 10304, 10550, 10570, 14640, 15014, 15034, 19760, 20134, 20154, -29392, -29018, -28998, 31024, 31270, 31290, -24272, -23898, -23878, -19152, -18778, -18758, -14032, -13658, -13638, -6864, -6490, -6470, 516, 536, 6788, 6808, 11012, 11032, 14980, 15000, 21124, 21144, -28924, -28904, -22012, -21992, -12924, -12904}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 13753, 13737, 13719, 13883, 13863, 13974, 19643, 18601, 16532, 23993, 25121, 24980, 26944, 26921, 24854, 27191, 27171, 26130, 26941, 28696, 30100, 12477, 10407, 10388, 10685, 10665, 10646, 6711, 6693, 6674, 6965, 7073, 7056, 2361, 4387, 3346, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 24, 44, 64, 84, 104, 304, 678, 698, 550, 934, 954, 6448, 6946, 6966, 2352, 2726, 2746, 10544, 10918, 10938, 10304, 10550, 10570, 14640, 15014, 15034, 19760, 20134, 20154, -29392, -29018, -28998, 31024, 31270, 31290, -24272, -23898, -23878, -19152, -18778, -18758, -14032, -13658, -13638, -6864, -6490, -6470, 516, 536, 6788, 6808, 11012, 11032, 14980, 15000, 21124, 21144, -28924, -28904, -22012, -21992, -12924, -12904}};

	@OriginalMember(owner = "client!q", name = "d", descriptor = "[[S")
	public static final short[][] GAME1_DESTINATION_BODY_COLORS = new short[][]{{10, 30, 50, 70, 90, 110, 310, 684, 704, 556, 940, 960, 6454, 6952, 6972, 2358, 2732, 2752, 10550, 10924, 10944, 10310, 10556, 10576, 14646, 15020, 15040, 19766, 20140, 20160, -29386, -29012, -28992, 31030, 31276, 31296, -24266, -23892, -23872, -19146, -18772, -18752, -14026, -13652, -13632, -6858, -6484, -6464, 522, 542, 6794, 6814, 11018, 11038, 14986, 15006, 21130, 21150, -28918, -28898, -22006, -21986, -12918, -12898, 10, 30, 50, 70, 90, 110, 310, 684, 704, 556, 940, 960, 6454, 6952, 6972, 2358, 2732, 2752, 10550, 10924, 10944, 10310, 10556, 10576, 14646, 15020, 15040, 19766, 20140, 20160, -29386, -29012, -28992, 31030, 31276, 31296, -24266, -23892, -23872, -19146, -18772, -18752, -14026, -13652, -13632, -6858, -6484, -6464, 522, 542, 6794, 6814, 11018, 11038, 14986, 15006, 21130, 21150, -28918, -28898, -22006, -21986, -12918, -12898, 10, 30, 50, 70, 90, 110, 310, 684, 704, 556, 940, 960, 6454, 6952, 6972, 2358, 2732, 2752, 10550, 10924, 10944, 10310, 10556, 10576, 14646, 15020, 15040, 19766, 20140, 20160, -29386, -29012, -28992, 31030, 31276, 31296, -24266, -23892, -23872, -19146, -18772, -18752, -14026, -13652, -13632, -6858, -6484, -6464, 522, 542, 6794, 6814, 11018, 11038, 14986, 15006, 21130, 21150, -28918, -28898, -22006, -21986, -12918, -12898, 10, 30, 50, 70, 90, 110, 310, 684, 704, 556, 940, 960, 6454, 6952, 6972, 2358, 2732, 2752, 10550, 10924, 10944, 10310, 10556, 10576, 14646, 15020, 15040, 19766, 20140, 20160, -29386, -29012, -28992, 31030, 31276, 31296, -24266, -23892, -23872, -19146, -18772, -18752, -14026, -13652, -13632, -6858, -6484, -6464, 522, 542, 6794, 6814, 11018, 11038, 14986, 15006, 21130, 21150, -28918, -28898, -22006, -21986, -12918, -12898}, {10, 30, 50, 70, 90, 110, 310, 684, 704, 556, 940, 960, 6454, 6952, 6972, 2358, 2732, 2752, 10550, 10924, 10944, 10310, 10556, 10576, 14646, 15020, 15040, 19766, 20140, 20160, -29386, -29012, -28992, 31030, 31276, 31296, -24266, -23892, -23872, -19146, -18772, -18752, -14026, -13652, -13632, -6858, -6484, -6464, 522, 542, 6794, 6814, 11018, 11038, 14986, 15006, 21130, 21150, -28918, -28898, -22006, -21986, -12918, -12898, 10, 30, 50, 70, 90, 110, 310, 684, 704, 556, 940, 960, 6454, 6952, 6972, 2358, 2732, 2752, 10550, 10924, 10944, 10310, 10556, 10576, 14646, 15020, 15040, 19766, 20140, 20160, -29386, -29012, -28992, 31030, 31276, 31296, -24266, -23892, -23872, -19146, -18772, -18752, -14026, -13652, -13632, -6858, -6484, -6464, 522, 542, 6794, 6814, 11018, 11038, 14986, 15006, 21130, 21150, -28918, -28898, -22006, -21986, -12918, -12898, 10, 30, 50, 70, 90, 110, 310, 684, 704, 556, 940, 960, 6454, 6952, 6972, 2358, 2732, 2752, 10550, 10924, 10944, 10310, 10556, 10576, 14646, 15020, 15040, 19766, 20140, 20160, -29386, -29012, -28992, 31030, 31276, 31296, -24266, -23892, -23872, -19146, -18772, -18752, -14026, -13652, -13632, -6858, -6484, -6464, 522, 542, 6794, 6814, 11018, 11038, 14986, 15006, 21130, 21150, -28918, -28898, -22006, -21986, -12918, -12898, 10347, 10582, 10429, 10407, 10359, 8414, 9540, 10456, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {10, 30, 50, 70, 90, 110, 310, 684, 704, 556, 940, 960, 6454, 6952, 6972, 2358, 2732, 2752, 10550, 10924, 10944, 10310, 10556, 10576, 14646, 15020, 15040, 19766, 20140, 20160, -29386, -29012, -28992, 31030, 31276, 31296, -24266, -23892, -23872, -19146, -18772, -18752, -14026, -13652, -13632, -6858, -6484, -6464, 522, 542, 6794, 6814, 11018, 11038, 14986, 15006, 21130, 21150, -28918, -28898, -22006, -21986, -12918, -12898, 10, 30, 50, 70, 90, 110, 310, 684, 704, 556, 940, 960, 6454, 6952, 6972, 2358, 2732, 2752, 10550, 10924, 10944, 10310, 10556, 10576, 14646, 15020, 15040, 19766, 20140, 20160, -29386, -29012, -28992, 31030, 31276, 31296, -24266, -23892, -23872, -19146, -18772, -18752, -14026, -13652, -13632, -6858, -6484, -6464, 522, 542, 6794, 6814, 11018, 11038, 14986, 15006, 21130, 21150, -28918, -28898, -22006, -21986, -12918, -12898, 10, 30, 50, 70, 90, 110, 310, 684, 704, 556, 940, 960, 6454, 6952, 6972, 2358, 2732, 2752, 10550, 10924, 10944, 10310, 10556, 10576, 14646, 15020, 15040, 19766, 20140, 20160, -29386, -29012, -28992, 31030, 31276, 31296, -24266, -23892, -23872, -19146, -18772, -18752, -14026, -13652, -13632, -6858, -6484, -6464, 522, 542, 6794, 6814, 11018, 11038, 14986, 15006, 21130, 21150, -28918, -28898, -22006, -21986, -12918, -12898, 10, 30, 50, 70, 90, 110, 310, 684, 704, 556, 940, 960, 6454, 6952, 6972, 2358, 2732, 2752, 10550, 10924, 10944, 10310, 10556, 10576, 14646, 15020, 15040, 19766, 20140, 20160, -29386, -29012, -28992, 31030, 31276, 31296, -24266, -23892, -23872, -19146, -18772, -18752, -14026, -13652, -13632, -6858, -6484, -6464, 522, 542, 6794, 6814, 11018, 11038, 14986, 15006, 21130, 21150, -28918, -28898, -22006, -21986, -12918, -12898}, {4300, 3294, 3303, 3264, 4506, 4382, 4387, 5293, 7622, 7384, 8412, 7496, 86, 123, 111, 99, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 30, 50, 70, 90, 110, 310, 684, 704, 556, 940, 960, 6454, 6952, 6972, 2358, 2732, 2752, 10550, 10924, 10944, 10310, 10556, 10576, 14646, 15020, 15040, 19766, 20140, 20160, -29386, -29012, -28992, 31030, 31276, 31296, -24266, -23892, -23872, -19146, -18772, -18752, -14026, -13652, -13632, -6858, -6484, -6464, 522, 542, 6794, 6814, 11018, 11038, 14986, 15006, 21130, 21150, -28918, -28898, -22006, -21986, -12918, -12898, 10, 30, 50, 70, 90, 110, 310, 684, 704, 556, 940, 960, 6454, 6952, 6972, 2358, 2732, 2752, 10550, 10924, 10944, 10310, 10556, 10576, 14646, 15020, 15040, 19766, 20140, 20160, -29386, -29012, -28992, 31030, 31276, 31296, -24266, -23892, -23872, -19146, -18772, -18752, -14026, -13652, -13632, -6858, -6484, -6464, 522, 542, 6794, 6814, 11018, 11038, 14986, 15006, 21130, 21150, -28918, -28898, -22006, -21986, -12918, -12898, 13766, 13745, 13726, 13890, 13743, 13852, 17602, 18605, 21660, 24000, 24997, 24088, 27972, 25903, 26904, 27193, 27175, 27156, 30020, 28975, 29976, 12482, 13485, 10392, 10692, 10669, 10776, 6717, 6695, 7830, 6971, 6951, 5910, 3389, 3369, 3356, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {10, 30, 50, 70, 90, 110, 310, 684, 704, 556, 940, 960, 6454, 6952, 6972, 2358, 2732, 2752, 10550, 10924, 10944, 10310, 10556, 10576, 14646, 15020, 15040, 19766, 20140, 20160, -29386, -29012, -28992, 31030, 31276, 31296, -24266, -23892, -23872, -19146, -18772, -18752, -14026, -13652, -13632, -6858, -6484, -6464, 522, 542, 6794, 6814, 11018, 11038, 14986, 15006, 21130, 21150, -28918, -28898, -22006, -21986, -12918, -12898, 10, 30, 50, 70, 90, 110, 310, 684, 704, 556, 940, 960, 6454, 6952, 6972, 2358, 2732, 2752, 10550, 10924, 10944, 10310, 10556, 10576, 14646, 15020, 15040, 19766, 20140, 20160, -29386, -29012, -28992, 31030, 31276, 31296, -24266, -23892, -23872, -19146, -18772, -18752, -14026, -13652, -13632, -6858, -6484, -6464, 522, 542, 6794, 6814, 11018, 11038, 14986, 15006, 21130, 21150, -28918, -28898, -22006, -21986, -12918, -12898, 10, 30, 50, 70, 90, 110, 310, 684, 704, 556, 940, 960, 6454, 6952, 6972, 2358, 2732, 2752, 10550, 10924, 10944, 10310, 10556, 10576, 14646, 15020, 15040, 19766, 20140, 20160, -29386, -29012, -28992, 31030, 31276, 31296, -24266, -23892, -23872, -19146, -18772, -18752, -14026, -13652, -13632, -6858, -6484, -6464, 522, 542, 6794, 6814, 11018, 11038, 14986, 15006, 21130, 21150, -28918, -28898, -22006, -21986, -12918, -12898, 10, 30, 50, 70, 90, 110, 310, 684, 704, 556, 940, 960, 6454, 6952, 6972, 2358, 2732, 2752, 10550, 10924, 10944, 10310, 10556, 10576, 14646, 15020, 15040, 19766, 20140, 20160, -29386, -29012, -28992, 31030, 31276, 31296, -24266, -23892, -23872, -19146, -18772, -18752, -14026, -13652, -13632, -6858, -6484, -6464, 522, 542, 6794, 6814, 11018, 11038, 14986, 15006, 21130, 21150, -28918, -28898, -22006, -21986, -12918, -12898}};

	@OriginalMember(owner = "client!wd", name = "d", descriptor = "[I")
	public static final int[] BASE_PART_MAP = new int[]{8, 11, 4, 6, 9, 7, 10, 0};

	@OriginalMember(owner = "client!r", name = "b", descriptor = "[Lclient!tk;")
	public static final SeqType[] slotSeqTypes = new SeqType[14];

	@OriginalMember(owner = "client!ri", name = "b", descriptor = "[I")
	public static final int[] slotTweenFrames = new int[14];

	@OriginalMember(owner = "client!uf", name = "j", descriptor = "Lclient!n;")
	public static final SoftLruHashTable bodyModels = new SoftLruHashTable(260);

	@OriginalMember(owner = "client!l", name = "b", descriptor = "Lclient!n;")
	public static final SoftLruHashTable headModels = new SoftLruHashTable(5);

	@OriginalMember(owner = "client!qi", name = "x", descriptor = "[I")
	public static final int[] MALE_FEATURES = new int[]{0, 1, 2, 3, 4, 5, 6, 14};

	@OriginalMember(owner = "client!kh", name = "g", descriptor = "Lclient!hh;")
	public static final PlayerAppearance DEFAULT = new PlayerAppearance();

	@OriginalMember(owner = "client!mc", name = "ab", descriptor = "[I")
	public static final int[] FEMALE_FEATURES = new int[]{7, 8, 9, 10, 11, 12, 13, 15};

	@OriginalMember(owner = "client!wh", name = "j", descriptor = "[Lclient!cl;")
	public static final AnimFrameset[] slotCurrentFramesets = new AnimFrameset[14];

	@OriginalMember(owner = "client!vf", name = "g", descriptor = "[I")
	public static final int[] slotFrameDelays = new int[14];

	@OriginalMember(owner = "client!kf", name = "d", descriptor = "[Lclient!cl;")
	public static final AnimFrameset[] slotTweenFramesets = new AnimFrameset[14];

	@OriginalMember(owner = "client!fm", name = "fb", descriptor = "[I")
	public static final int[] slotDelayClocks = new int[14];
	@OriginalMember(owner = "client!th", name = "i", descriptor = "[I")
	public static final int[] slotCurrentFrames = new int[14];

	@OriginalMember(owner = "client!cj", name = "e", descriptor = "[[S")
	public static short[][] destinationBodyColors;

	@OriginalMember(owner = "client!nj", name = "m", descriptor = "[[S")
	public static short[][] destinationSkinColors;

	@OriginalMember(owner = "client!qe", name = "r", descriptor = "[S")
	public static short[] sourceBodyColors;

	@OriginalMember(owner = "client!mj", name = "C", descriptor = "[S")
	public static short[] sourceSkinColors;

	@OriginalMember(owner = "client!hh", name = "e", descriptor = "I")
	public int npcId;

	@OriginalMember(owner = "client!hh", name = "i", descriptor = "J")
	private long prevChecksum;

	@OriginalMember(owner = "client!hh", name = "l", descriptor = "[I")
	private int[] identikit;

	@OriginalMember(owner = "client!hh", name = "m", descriptor = "I")
	private int basId;

	@OriginalMember(owner = "client!hh", name = "q", descriptor = "J")
	private long checksum;

	@OriginalMember(owner = "client!hh", name = "t", descriptor = "[I")
	private int[] colors;

	@OriginalMember(owner = "client!hh", name = "w", descriptor = "[[I")
	private int[][] modelTransformCache;

	@OriginalMember(owner = "client!hh", name = "x", descriptor = "Z")
	public boolean gender;

	@OriginalMember(owner = "client!wk", name = "b", descriptor = "(II)V")
	public static void clean() {
		bodyModels.clean(5);
		headModels.clean(5);
	}

	@OriginalMember(owner = "client!ph", name = "b", descriptor = "(B)V")
	public static void removeSoft() {
		bodyModels.removeSoft();
		headModels.removeSoft();
	}

	@OriginalMember(owner = "client!sj", name = "c", descriptor = "(I)V")
	public static void clear() {
		bodyModels.clear();
		headModels.clear();
	}

	@OriginalMember(owner = "client!q", name = "a", descriptor = "(B)I")
	public static int getModelCacheSize() {
		return bodyModels.size();
	}

	@OriginalMember(owner = "client!hh", name = "a", descriptor = "(IIIILclient!tk;III)Lclient!ak;")
	public final Model getPartialHeadModel(@OriginalArg(0) int animFrame, @OriginalArg(1) int hairId, @OriginalArg(2) int jawId, @OriginalArg(3) int nextFrame, @OriginalArg(4) SeqType seqType, @OriginalArg(5) int delayDelta, @OriginalArg(6) int beardId) {
		@Pc(24) long cacheKey = (long) jawId | ((long) beardId << 16) | (long) hairId << 32;
		@Pc(30) Model model = (Model) headModels.get(cacheKey);
		if (model == null) {
			@Pc(36) RawModel[] models = new RawModel[3];
			@Pc(38) int modelCount = 0;
			if (!IdkTypeList.get(jawId).isHeadModelReady() || !IdkTypeList.get(beardId).isHeadModelReady() || !IdkTypeList.get(hairId).isHeadModelReady()) {
				return null;
			}
			@Pc(66) RawModel rawModel = IdkTypeList.get(jawId).getHeadModel();
			if (rawModel != null) {
				modelCount++;
				models[0] = rawModel;
			}
			rawModel = IdkTypeList.get(beardId).getHeadModel();
			if (rawModel != null) {
				models[modelCount++] = rawModel;
			}
			rawModel = IdkTypeList.get(hairId).getHeadModel();
			if (rawModel != null) {
				models[modelCount++] = rawModel;
			}
			rawModel = new RawModel(models, modelCount);
			for (@Pc(110) int i = 0; i < 5; i++) {
				if (this.colors[i] < destinationBodyColors[i].length) {
					rawModel.recolor(sourceBodyColors[i], destinationBodyColors[i][this.colors[i]]);
				}
				if (destinationSkinColors[i].length > this.colors[i]) {
					rawModel.recolor(sourceSkinColors[i], destinationSkinColors[i][this.colors[i]]);
				}
			}
			model = rawModel.createModel(64, 768, -50, -10, -50);
			headModels.put(model, cacheKey);
		}
		if (seqType != null) {
			model = seqType.animateEntity(model, animFrame, nextFrame, delayDelta);
		}
		return model;
	}

	@OriginalMember(owner = "client!hh", name = "a", descriptor = "(I)V")
	private void updateChecksum() {
		@Pc(8) long previousChecksum = this.checksum;
		this.checksum = -1L;
		@Pc(13) long[] CRC64_TABLE = Buffer.CRC64_TABLE;
		this.checksum = CRC64_TABLE[(int) (((long) (this.basId >> 8) ^ this.checksum) & 0xFFL)] ^ this.checksum >>> 8;
		this.checksum = CRC64_TABLE[(int) ((this.checksum ^ (long) this.basId) & 0xFFL)] ^ this.checksum >>> 8;
		for (int body = 0; body < 12; body++) {
			this.checksum = this.checksum >>> 8 ^ CRC64_TABLE[(int) ((this.checksum ^ (long) (this.identikit[body] >> 24)) & 0xFFL)];
			this.checksum = this.checksum >>> 8 ^ CRC64_TABLE[(int) ((this.checksum ^ (long) (this.identikit[body] >> 16)) & 0xFFL)];
			this.checksum = CRC64_TABLE[(int) (((long) (this.identikit[body] >> 8) ^ this.checksum) & 0xFFL)] ^ this.checksum >>> 8;
			this.checksum = this.checksum >>> 8 ^ CRC64_TABLE[(int) ((this.checksum ^ (long) this.identikit[body]) & 0xFFL)];
		}
		for (int color = 0; color < 5; color++) {
			this.checksum = CRC64_TABLE[(int) (((long) this.colors[color] ^ this.checksum) & 0xFFL)] ^ this.checksum >>> 8;
		}
		this.checksum = CRC64_TABLE[(int) (((long) (this.gender ? 1 : 0) ^ this.checksum) & 0xFFL)] ^ this.checksum >>> 8;
		if (previousChecksum != 0L && this.checksum != previousChecksum) {
			bodyModels.remove(previousChecksum);
		}
	}

	@OriginalMember(owner = "client!hh", name = "a", descriptor = "(ZZ)V")
	public final void setGender(@OriginalArg(0) boolean female) {
		this.gender = female;
		this.updateChecksum();
	}

	@OriginalMember(owner = "client!hh", name = "a", descriptor = "([IIZI[II)V")
	public final void set(@OriginalArg(0) int[] colors, @OriginalArg(1) int npcId, @OriginalArg(2) boolean female, @OriginalArg(4) int[] identikit, @OriginalArg(5) int basId) {
		if (basId != this.basId) {
			this.basId = basId;
			this.modelTransformCache = null;
		}
		if (identikit == null) {
			identikit = new int[12];
			for (@Pc(24) int i = 0; i < 8; i++) {
				for (@Pc(31) int id = 0; id < IdkTypeList.count; id++) {
					@Pc(38) IdkType type = IdkTypeList.get(id);
					if (type != null && !type.disable && type.feature == (female ? FEMALE_FEATURES[i] : MALE_FEATURES[i])) {
						identikit[BASE_PART_MAP[i]] = Integer.MIN_VALUE | id;
						break;
					}
				}
			}
		}
		this.npcId = npcId;
		this.gender = female;
		this.colors = colors;
		this.identikit = identikit;
		this.updateChecksum();
	}

	@OriginalMember(owner = "client!hh", name = "a", descriptor = "(IZI)V")
	public final void setColor(@OriginalArg(0) int i, @OriginalArg(2) int color) {
		this.colors[i] = color;
		this.updateChecksum();
	}

	@OriginalMember(owner = "client!hh", name = "b", descriptor = "(I)I")
	public final int getAppearanceHash() {
		return this.npcId == -1 ? (this.identikit[8] << 10) + ((this.colors[0] << 25) + (this.colors[4] << 20)) + (this.identikit[0] << 15) + (this.identikit[11] << 5) + this.identikit[1] : NpcTypeList.get(this.npcId).id + 305419896;
	}

	@OriginalMember(owner = "client!hh", name = "a", descriptor = "(III)V")
	public final void setIdentikit(@OriginalArg(0) int featureIndex, @OriginalArg(1) int identikit) {
		@Pc(7) int part = BASE_PART_MAP[featureIndex];
		if (0 != this.identikit[part] && IdkTypeList.get(identikit) != null) {
			this.identikit[part] = identikit | Integer.MIN_VALUE;
			this.updateChecksum();
		}
	}

	@OriginalMember(owner = "client!hh", name = "a", descriptor = "([Lclient!ub;ILclient!tk;Lclient!tk;IIIIZII)Lclient!ak;")
	public final Model getBodyModel(@OriginalArg(0) SlotAnimation[] slotAnims, @OriginalArg(1) int seqNextFrame, @OriginalArg(2) SeqType movementSeq, @OriginalArg(3) SeqType seq, @OriginalArg(4) int movementDelayClock, @OriginalArg(5) int seqTweenFrame, @OriginalArg(7) int seqDelayClock, @OriginalArg(9) int seqFrame, @OriginalArg(10) int movementFrame) {
		if (this.npcId != -1) {
			return NpcTypeList.get(this.npcId).getBodyModel(slotAnims, seqTweenFrame, movementFrame, seqNextFrame, seqDelayClock, seqFrame, movementSeq, movementDelayClock, seq);
		}
		@Pc(38) int[] activeIdentikit = this.identikit;
		@Pc(41) long cacheKey = this.checksum;
		if (seq != null && (seq.mainhand >= 0 || seq.offhand >= 0)) {
			activeIdentikit = new int[12];
			for (@Pc(61) int i = 0; i < 12; i++) {
				activeIdentikit[i] = this.identikit[i];
			}
			if (seq.mainhand >= 0) {
				if (seq.mainhand == 65535) {
					cacheKey ^= 0xFFFFFFFF00000000L;
					activeIdentikit[5] = 0;
				} else {
					activeIdentikit[5] = seq.mainhand | 0x40000000;
					cacheKey ^= (long) activeIdentikit[5] << 32;
				}
			}
			if (seq.offhand >= 0) {
				if (seq.offhand == 65535) {
					activeIdentikit[3] = 0;
					cacheKey ^= 0xFFFFFFFFL;
				} else {
					activeIdentikit[3] = seq.offhand | 0x40000000;
					cacheKey ^= activeIdentikit[3];
				}
			}
		}
		@Pc(154) Model model = (Model) bodyModels.get(cacheKey);
		@Pc(158) boolean notReady;
		@Pc(353) int translateY;
		@Pc(360) int translateZ;
		@Pc(374) int rotateY;
		@Pc(367) int rotateX;
		@Pc(381) int rotateZ;
		@Pc(451) int cosRotY;
		@Pc(457) int sinRotY;
		@Pc(475) int cosRotZ;
		@Pc(481) int sinRotZ;
		@Pc(598) int intermediate;
		@Pc(346) int translateX;
		if (model == null) {
			notReady = false;
			@Pc(169) int slot;
			for (@Pc(160) int i = 0; i < 12; i++) {
				slot = activeIdentikit[i];
				if ((slot & 0x40000000) == 0) {
					if ((slot & Integer.MIN_VALUE) != 0 && !IdkTypeList.get(slot & 0x3FFFFFFF).isBodyModelReady()) {
						notReady = true;
					}
				} else if (!ObjTypeList.get(slot & 0x3FFFFFFF).isWearModelReady(this.gender)) {
					notReady = true;
				}
			}
			if (notReady) {
				if (this.prevChecksum != -1L) {
					model = (Model) bodyModels.get(this.prevChecksum);
				}
				if (model == null) {
					return null;
				}
			}
			if (model == null) {
				@Pc(239) RawModel[] models = new RawModel[12];
				@Pc(250) int slotIndex;
				for (slot = 0; slot < 12; slot++) {
					slotIndex = activeIdentikit[slot];
					@Pc(272) RawModel rawModel;
					if ((slotIndex & 0x40000000) != 0) {
						rawModel = ObjTypeList.get(slotIndex & 0x3FFFFFFF).getBodyModel(this.gender);
						if (rawModel != null) {
							models[slot] = rawModel;
						}
					} else if ((Integer.MIN_VALUE & slotIndex) != 0) {
						rawModel = IdkTypeList.get(slotIndex & 0x3FFFFFFF).getBodyModel();
						if (rawModel != null) {
							models[slot] = rawModel;
						}
					}
				}
				@Pc(303) BasType basType = null;
				if (this.basId != -1) {
					basType = BasTypeList.get(this.basId);
				}
				if (basType != null && basType.modelRotateTranslate != null) {
					for (slotIndex = 0; slotIndex < basType.modelRotateTranslate.length; slotIndex++) {
						if (basType.modelRotateTranslate[slotIndex] != null && models[slotIndex] != null) {
							translateX = basType.modelRotateTranslate[slotIndex][0];
							translateY = basType.modelRotateTranslate[slotIndex][1];
							translateZ = basType.modelRotateTranslate[slotIndex][2];
							rotateX = basType.modelRotateTranslate[slotIndex][4];
							rotateY = basType.modelRotateTranslate[slotIndex][3];
							rotateZ = basType.modelRotateTranslate[slotIndex][5];
							if (this.modelTransformCache == null) {
								this.modelTransformCache = new int[basType.modelRotateTranslate.length][];
							}
							if (this.modelTransformCache[slotIndex] == null) {
								@Pc(404) int[] matrix = this.modelTransformCache[slotIndex] = new int[15];
								if (rotateY == 0 && rotateX == 0 && rotateZ == 0) {
									matrix[12] = -translateX;
									matrix[13] = -translateY;
									matrix[0] = matrix[4] = matrix[8] = 32768;
									matrix[14] = -translateZ;
								} else {
									cosRotY = MathUtils.cos[rotateY] >> 1;
									sinRotY = MathUtils.sin[rotateY] >> 1;
									@Pc(463) int cosRotX = MathUtils.cos[rotateX] >> 1;
									@Pc(469) int sinRotX = MathUtils.sin[rotateX] >> 1;
									cosRotZ = MathUtils.cos[rotateZ] >> 1;
									sinRotZ = MathUtils.sin[rotateZ] >> 1;
									matrix[4] = cosRotY * cosRotZ + 16384 >> 15;
									matrix[5] = -sinRotY;
									matrix[3] = sinRotZ * cosRotY + 16384 >> 15;
									matrix[2] = cosRotY * sinRotX + 16384 >> 15;
									matrix[8] = cosRotX * cosRotY + 16384 >> 15;
									@Pc(534) int sinZsinY = sinRotZ * sinRotY + 16384 >> 15;
									matrix[0] = sinRotX * sinZsinY + cosRotZ * cosRotX + 16384 >> 15;
									matrix[14] = matrix[8] * -translateZ + -translateY * matrix[5] + matrix[2] * -translateX + 16384 >> 15;
									matrix[6] = cosRotX * sinZsinY + cosRotZ * -sinRotX + 16384 >> 15;
									intermediate = cosRotZ * sinRotY + 16384 >> 15;
									matrix[7] = -sinRotZ * -sinRotX + intermediate * cosRotX + 16384 >> 15;
									matrix[1] = sinRotX * intermediate + cosRotX * -sinRotZ + 16384 >> 15;
									matrix[12] = -translateY * matrix[3] + matrix[0] * -translateX + -translateZ * matrix[6] + 16384 >> 15;
									matrix[13] = -translateY * matrix[4] + matrix[1] * -translateX + -translateZ * matrix[7] + 16384 >> 15;
								}
								matrix[9] = translateX;
								matrix[11] = translateZ;
								matrix[10] = translateY;
							}
							if (rotateY != 0 || rotateX != 0 || rotateZ != 0) {
								models[slotIndex].rotateXYZ(rotateY, rotateX, rotateZ);
							}
							if (translateX != 0 || translateY != 0 || translateZ != 0) {
								models[slotIndex].translate(translateX, translateY, translateZ);
							}
						}
					}
				}
				@Pc(740) RawModel combined = new RawModel(models, models.length);
				for (translateX = 0; translateX < 5; translateX++) {
					if (destinationBodyColors[translateX].length > this.colors[translateX]) {
						combined.recolor(sourceBodyColors[translateX], destinationBodyColors[translateX][this.colors[translateX]]);
					}
					if (destinationSkinColors[translateX].length > this.colors[translateX]) {
						combined.recolor(sourceSkinColors[translateX], destinationSkinColors[translateX][this.colors[translateX]]);
					}
				}
				model = combined.createModel(64, 850, -30, -50, -30);
				if (GlRenderer.enabled) {
					((GlModel) model).uploadBuffers(false, false, true, false, false, true);
				}
				bodyModels.put(model, cacheKey);
				this.prevChecksum = cacheKey;
			}
		}
		notReady = false;
		@Pc(827) boolean hasAlphaTransform = false;
		translateX = slotAnims == null ? 0 : slotAnims.length;
		@Pc(836) boolean hasColorTransform = false;
		@Pc(838) boolean hasModelTransform = false;
		@Pc(979) int packed;
		for (translateY = 0; translateY < translateX; translateY++) {
			if (slotAnims[translateY] != null) {
				@Pc(858) SeqType slotSeqType = SeqTypeList.get(slotAnims[translateY].seqId);
				if (slotSeqType.frames != null) {
					notReady = true;
					slotSeqTypes[translateY] = slotSeqType;
					rotateY = slotAnims[translateY].currentFrame;
					rotateX = slotAnims[translateY].nextFrame;
					rotateZ = slotSeqType.frames[rotateY];
					slotCurrentFramesets[translateY] = SeqTypeList.getAnimFrameset(rotateZ >>> 16);
					rotateZ &= 0xFFFF;
					slotCurrentFrames[translateY] = rotateZ;
					if (slotCurrentFramesets[translateY] != null) {
						hasColorTransform |= slotCurrentFramesets[translateY].isColorTransformed(rotateZ);
						hasAlphaTransform |= slotCurrentFramesets[translateY].isAlphaTransformed(rotateZ);
						hasModelTransform |= slotSeqType.hasModelTransforms;
					}
					if ((slotSeqType.tween || SeqType.applyTweening) && rotateX != -1 && rotateX < slotSeqType.frames.length) {
						slotFrameDelays[translateY] = slotSeqType.frameDelay[rotateY];
						slotDelayClocks[translateY] = slotAnims[translateY].delayClock;
						packed = slotSeqType.frames[rotateX];
						slotTweenFramesets[translateY] = SeqTypeList.getAnimFrameset(packed >>> 16);
						@Pc(991) int tweenFrame = packed & 0xFFFF;
						slotTweenFrames[translateY] = tweenFrame;
						if (slotTweenFramesets[translateY] != null) {
							hasColorTransform |= slotTweenFramesets[translateY].isColorTransformed(tweenFrame);
							hasAlphaTransform |= slotTweenFramesets[translateY].isAlphaTransformed(tweenFrame);
						}
					} else {
						slotFrameDelays[translateY] = 0;
						slotDelayClocks[translateY] = 0;
						slotTweenFramesets[translateY] = null;
						slotTweenFrames[translateY] = -1;
					}
				}
			}
		}
		if (!notReady && seq == null && movementSeq == null) {
			return model;
		}
		translateY = -1;
		translateZ = -1;
		rotateY = 0;
		@Pc(1040) AnimFrameset seqTweenFrameset = null;
		@Pc(1042) AnimFrameset seqFrameset = null;
		if (seq != null) {
			translateY = seq.frames[seqFrame];
			packed = translateY >>> 16;
			seqFrameset = SeqTypeList.getAnimFrameset(packed);
			translateY &= 0xFFFF;
			if (seqFrameset != null) {
				hasColorTransform |= seqFrameset.isColorTransformed(translateY);
				hasAlphaTransform |= seqFrameset.isAlphaTransformed(translateY);
				hasModelTransform |= seq.hasModelTransforms;
			}
			if ((seq.tween || SeqType.applyTweening) && seqNextFrame != -1 && seq.frames.length > seqNextFrame) {
				translateZ = seq.frames[seqNextFrame];
				cosRotY = translateZ >>> 16;
				translateZ &= 0xFFFF;
				rotateY = seq.frameDelay[seqFrame];
				if (packed == cosRotY) {
					seqTweenFrameset = seqFrameset;
				} else {
					seqTweenFrameset = SeqTypeList.getAnimFrameset(translateZ >>> 16);
				}
				if (seqTweenFrameset != null) {
					hasColorTransform |= seqTweenFrameset.isColorTransformed(translateZ);
					hasAlphaTransform |= seqTweenFrameset.isAlphaTransformed(translateZ);
				}
			}
		}
		packed = -1;
		cosRotY = -1;
		@Pc(1154) AnimFrameset movementFrameset = null;
		@Pc(1156) AnimFrameset movementTweenFrameset = null;
		sinRotY = 0;
		if (movementSeq != null) {
			packed = movementSeq.frames[movementFrame];
			cosRotZ = packed >>> 16;
			packed &= 0xFFFF;
			movementFrameset = SeqTypeList.getAnimFrameset(cosRotZ);
			if (movementFrameset != null) {
				hasColorTransform |= movementFrameset.isColorTransformed(packed);
				hasAlphaTransform |= movementFrameset.isAlphaTransformed(packed);
				hasModelTransform |= movementSeq.hasModelTransforms;
			}
			if ((movementSeq.tween || SeqType.applyTweening) && seqTweenFrame != -1 && movementSeq.frames.length > seqTweenFrame) {
				sinRotY = movementSeq.frameDelay[movementFrame];
				cosRotY = movementSeq.frames[seqTweenFrame];
				sinRotZ = cosRotY >>> 16;
				cosRotY &= 0xFFFF;
				if (cosRotZ == sinRotZ) {
					movementTweenFrameset = movementFrameset;
				} else {
					movementTweenFrameset = SeqTypeList.getAnimFrameset(cosRotY >>> 16);
				}
				if (movementTweenFrameset != null) {
					hasColorTransform |= movementTweenFrameset.isColorTransformed(cosRotY);
					hasAlphaTransform |= movementTweenFrameset.isAlphaTransformed(cosRotY);
				}
			}
		}
		@Pc(1284) Model animatedModel = model.copyForEntity(!hasAlphaTransform, !hasColorTransform, !hasModelTransform);
		sinRotZ = 0;
		intermediate = 1;
		while (sinRotZ < translateX) {
			if (slotCurrentFramesets[sinRotZ] != null) {
				animatedModel.applyMaskedAnimation(slotCurrentFramesets[sinRotZ], slotCurrentFrames[sinRotZ], slotTweenFramesets[sinRotZ], slotTweenFrames[sinRotZ], slotDelayClocks[sinRotZ] - 1, slotFrameDelays[sinRotZ], intermediate, slotSeqTypes[sinRotZ].hasModelTransforms, this.modelTransformCache[sinRotZ]);
			}
			sinRotZ++;
			intermediate <<= 0x1;
		}
		if (seqFrameset != null && movementFrameset != null) {
			animatedModel.applyDualAnimation(seqFrameset, translateY, seqTweenFrameset, translateZ, seqDelayClock - 1, rotateY, movementFrameset, packed, movementTweenFrameset, cosRotY, movementDelayClock - 1, sinRotY, seq.framegroup, seq.hasModelTransforms | movementSeq.hasModelTransforms);
		} else if (seqFrameset != null) {
			animatedModel.applyAnimation(seqFrameset, translateY, seqTweenFrameset, translateZ, seqDelayClock - 1, rotateY, seq.hasModelTransforms);
		} else if (movementFrameset != null) {
			animatedModel.applyAnimation(movementFrameset, packed, movementTweenFrameset, cosRotY, movementDelayClock - 1, sinRotY, movementSeq.hasModelTransforms);
		}
		for (sinRotZ = 0; sinRotZ < translateX; sinRotZ++) {
			slotCurrentFramesets[sinRotZ] = null;
			slotTweenFramesets[sinRotZ] = null;
			slotSeqTypes[sinRotZ] = null;
		}
		return animatedModel;
	}

	@OriginalMember(owner = "client!hh", name = "a", descriptor = "(IBLclient!tk;II)Lclient!ak;")
	public final Model getHeadModel(@OriginalArg(0) int frame, @OriginalArg(2) SeqType seqType, @OriginalArg(3) int nextFrame, @OriginalArg(4) int delayDelta) {
		if (this.npcId != -1) {
			return NpcTypeList.get(this.npcId).getHeadModel(seqType, frame, delayDelta, nextFrame);
		}
		@Pc(30) Model model = (Model) headModels.get(this.checksum);
		if (model == null) {
			@Pc(42) boolean notReady = false;
			@Pc(52) int slotId;
			for (@Pc(44) int i = 0; i < 12; i++) {
				slotId = this.identikit[i];
				if ((slotId & 0x40000000) == 0) {
					if ((slotId & Integer.MIN_VALUE) != 0 && !IdkTypeList.get(slotId & 0x3FFFFFFF).isHeadModelReady()) {
						notReady = true;
					}
				} else if (!ObjTypeList.get(slotId & 0x3FFFFFFF).isHeadModelReady(this.gender)) {
					notReady = true;
				}
			}
			if (notReady) {
				return null;
			}
			@Pc(100) RawModel[] models = new RawModel[12];
			slotId = 0;
			@Pc(114) int slot;
			for (@Pc(104) int i = 0; i < 12; i++) {
				slot = this.identikit[i];
				@Pc(134) RawModel rawModel;
				if ((slot & 0x40000000) != 0) {
					rawModel = ObjTypeList.get(slot & 0x3FFFFFFF).getHeadModel(this.gender);
					if (rawModel != null) {
						models[slotId++] = rawModel;
					}
				} else if ((Integer.MIN_VALUE & slot) != 0) {
					rawModel = IdkTypeList.get(slot & 0x3FFFFFFF).getHeadModel();
					if (rawModel != null) {
						models[slotId++] = rawModel;
					}
				}
			}
			@Pc(171) RawModel combined = new RawModel(models, slotId);
			for (slot = 0; slot < 5; slot++) {
				if (destinationBodyColors[slot].length > this.colors[slot]) {
					combined.recolor(sourceBodyColors[slot], destinationBodyColors[slot][this.colors[slot]]);
				}
				if (destinationSkinColors[slot].length > this.colors[slot]) {
					combined.recolor(sourceSkinColors[slot], destinationSkinColors[slot][this.colors[slot]]);
				}
			}
			model = combined.createModel(64, 768, -50, -10, -50);
			headModels.put(model, this.checksum);
		}
		if (seqType != null) {
			model = seqType.animateEntity(model, delayDelta, frame, nextFrame);
		}
		return model;
	}
}
