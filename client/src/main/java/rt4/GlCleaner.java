package rt4;

import com.jogamp.opengl.GL2;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

public final class GlCleaner {

	@OriginalMember(owner = "client!fa", name = "a", descriptor = "I")
	public static int onCardTexture = 0;

	@OriginalMember(owner = "client!fa", name = "b", descriptor = "Lclient!ih;")
	private static final LinkedList pendingBufferDeletes = new LinkedList();

	@OriginalMember(owner = "client!fa", name = "c", descriptor = "I")
	public static int contextId = 0;

	@OriginalMember(owner = "client!fa", name = "d", descriptor = "J")
	private static long lastGcTime = 0L;

	@OriginalMember(owner = "client!fa", name = "e", descriptor = "I")
	public static int onCard2d = 0;

	@OriginalMember(owner = "client!fa", name = "f", descriptor = "I")
	public static int onCardGeometry = 0;

	@OriginalMember(owner = "client!fa", name = "g", descriptor = "Lclient!ih;")
	private static final LinkedList pendingTexture2dDeletes = new LinkedList();

	@OriginalMember(owner = "client!fa", name = "h", descriptor = "Lclient!ih;")
	private static final LinkedList pendingTextureDeletes = new LinkedList();

	@OriginalMember(owner = "client!fa", name = "i", descriptor = "Lclient!ih;")
	private static final LinkedList pendingListDeletes = new LinkedList();

	@OriginalMember(owner = "client!fa", name = "j", descriptor = "[I")
	private static final int[] deleteIdBatch = new int[1000];

	@OriginalMember(owner = "client!fa", name = "a", descriptor = "(III)V")
	public static synchronized void deleteTexture(@OriginalArg(0) int id, @OriginalArg(1) int size, @OriginalArg(2) int context) {
		if (context == contextId) {
			@Pc(8) IntNode node = new IntNode(size);
			node.key = id;
			pendingTextureDeletes.addTail(node);
		}
	}

	@OriginalMember(owner = "client!fa", name = "a", descriptor = "(II)V")
	public static synchronized void deleteList(@OriginalArg(0) int id, @OriginalArg(1) int context) {
		if (context == contextId) {
			@Pc(7) IntNode node = new IntNode();
			node.key = id;
			pendingListDeletes.addTail(node);
		}
	}

	@OriginalMember(owner = "client!fa", name = "b", descriptor = "()V")
	public static synchronized void clear() {
		contextId++;
		pendingBufferDeletes.clear();
		pendingTexture2dDeletes.clear();
		pendingTextureDeletes.clear();
		pendingListDeletes.clear();
		onCardGeometry = 0;
		onCard2d = 0;
		onCardTexture = 0;
	}

	@OriginalMember(owner = "client!fa", name = "b", descriptor = "(III)V")
	public static synchronized void deleteBuffer(@OriginalArg(0) int id, @OriginalArg(1) int size, @OriginalArg(2) int context) {
		if (context == contextId) {
			@Pc(8) IntNode node = new IntNode(size);
			node.key = id;
			pendingBufferDeletes.addTail(node);
		}
	}

	@OriginalMember(owner = "client!fa", name = "c", descriptor = "()V")
	public static synchronized void process() {
		@Pc(1) GL2 gl = GlRenderer.gl;
		@Pc(3) int count = 0;
		while (true) {
			@Pc(8) IntNode node = (IntNode) pendingBufferDeletes.removeHead();
			if (node == null) {
				if (count > 0) {
					gl.glDeleteBuffers(count, deleteIdBatch, 0);
					count = 0;
				}
				while (true) {
					node = (IntNode) pendingTexture2dDeletes.removeHead();
					if (node == null) {
						while (true) {
							node = (IntNode) pendingTextureDeletes.removeHead();
							if (node == null) {
								if (count > 0) {
									gl.glDeleteTextures(count, deleteIdBatch, 0);
								}
								while (true) {
									node = (IntNode) pendingListDeletes.removeHead();
									if (node == null) {
										if (onCardGeometry + onCard2d + onCardTexture > 100663296 && MonotonicClock.currentTimeMillis() > lastGcTime + 60000L) {
											System.gc();
											lastGcTime = MonotonicClock.currentTimeMillis();
										}
										return;
									}
									@Pc(126) int listId = (int) node.key;
									gl.glDeleteLists(listId, 1);
								}
							}
							deleteIdBatch[count++] = (int) node.key;
							onCardTexture -= node.value;
							if (count == 1000) {
								gl.glDeleteTextures(count, deleteIdBatch, 0);
								count = 0;
							}
						}
					}
					deleteIdBatch[count++] = (int) node.key;
					onCard2d -= node.value;
					if (count == 1000) {
						gl.glDeleteTextures(count, deleteIdBatch, 0);
						count = 0;
					}
				}
			}
			deleteIdBatch[count++] = (int) node.key;
			onCardGeometry -= node.value;
			if (count == 1000) {
				gl.glDeleteBuffers(count, deleteIdBatch, 0);
				count = 0;
			}
		}
	}

	@OriginalMember(owner = "client!fa", name = "c", descriptor = "(III)V")
	public static synchronized void deleteTexture2d(@OriginalArg(0) int id, @OriginalArg(1) int size, @OriginalArg(2) int context) {
		if (context == contextId) {
			@Pc(8) IntNode node = new IntNode(size);
			node.key = id;
			pendingTexture2dDeletes.addTail(node);
		}
	}
}
