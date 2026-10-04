package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!nd")
public final class MonochromeImageCache {

	@OriginalMember(owner = "client!nd", name = "c", descriptor = "[B")
	public static final byte[] normalBrightnessLookup = new byte[32896];

	@OriginalMember(owner = "client!a", name = "b", descriptor = "[I")
	public static final int[] smoothstepLookup = new int[4096];

	@OriginalMember(owner = "client!bc", name = "N", descriptor = "Lclient!lb;")
	public static final MonochromeImageCacheEntry entry = new MonochromeImageCacheEntry(0, 0);

	@OriginalMember(owner = "client!nd", name = "f", descriptor = "I")
	private int singleRow = -1;

	@OriginalMember(owner = "client!nd", name = "k", descriptor = "I")
	private int size = 0;

	@OriginalMember(owner = "client!nd", name = "g", descriptor = "Lclient!ih;")
	private LinkedList recentlyUsed = new LinkedList();

	@OriginalMember(owner = "client!nd", name = "w", descriptor = "Z")
	public boolean invalid = false;

	@OriginalMember(owner = "client!nd", name = "l", descriptor = "I")
	private final int height;

	@OriginalMember(owner = "client!nd", name = "i", descriptor = "I")
	private final int capacity;

	@OriginalMember(owner = "client!nd", name = "o", descriptor = "[Lclient!lb;")
	private MonochromeImageCacheEntry[] entries;

	@OriginalMember(owner = "client!nd", name = "a", descriptor = "[[[I")
	private int[][][] pixels;

	static {
		@Pc(8) int offset = 0;
		for (@Pc(10) int i = 0; i < 256; i++) {
			for (@Pc(15) int j = 0; j <= i; j++) {
				normalBrightnessLookup[offset++] = (byte) (255.0D / Math.sqrt((float) (j * j + i * i + 65535) / 65535.0F));
			}
		}

		for (@Pc(4) int i = 0; i < 4096; i++) {
			smoothstepLookup[i] = smoothstep(i);
		}
	}

	@OriginalMember(owner = "client!nd", name = "<init>", descriptor = "(III)V")
	public MonochromeImageCache(@OriginalArg(0) int capacity, @OriginalArg(1) int height, @OriginalArg(2) int width) {
		this.height = height;
		this.capacity = capacity;
		this.entries = new MonochromeImageCacheEntry[this.height];
		this.pixels = new int[this.capacity][3][width];
	}

	@OriginalMember(owner = "client!we", name = "a", descriptor = "(BI)I")
	public static int smoothstep(@OriginalArg(1) int x) {
		@Pc(13) int cubed = x * (x * x >> 12) >> 12;
		@Pc(26) int linear = x * 6 - 61440;
		@Pc(34) int factor = (x * linear >> 12) + 40960;
		return cubed * factor >> 12;
	}

	@OriginalMember(owner = "client!nd", name = "a", descriptor = "(B)[[[I")
	public final int[][][] get() {
		if (this.height != this.capacity) {
			throw new RuntimeException("Can only retrieve a full image cache");
		}
		for (@Pc(27) int i = 0; i < this.capacity; i++) {
			this.entries[i] = entry;
		}
		return this.pixels;
	}

	@OriginalMember(owner = "client!nd", name = "b", descriptor = "(B)V")
	public final void clear() {
		for (@Pc(7) int i = 0; i < this.capacity; i++) {
			this.pixels[i][0] = null;
			this.pixels[i][1] = null;
			this.pixels[i][2] = null;
			this.pixels[i] = null;
		}
		this.entries = null;
		this.pixels = null;
		this.recentlyUsed.clear();
		this.recentlyUsed = null;
	}

	@OriginalMember(owner = "client!nd", name = "a", descriptor = "(BI)[[I")
	public final int[][] get(@OriginalArg(1) int row) {
		if (this.capacity == this.height) {
			this.invalid = this.entries[row] == null;
			this.entries[row] = entry;
			return this.pixels[row];
		} else if (this.capacity == 1) {
			this.invalid = this.singleRow != row;
			this.singleRow = row;
			return this.pixels[0];
		} else {
			@Pc(44) MonochromeImageCacheEntry cacheEntry = this.entries[row];
			if (cacheEntry == null) {
				this.invalid = true;
				if (this.size < this.capacity) {
					cacheEntry = new MonochromeImageCacheEntry(row, this.size);
					this.size++;
				} else {
					@Pc(80) MonochromeImageCacheEntry evicted = (MonochromeImageCacheEntry) this.recentlyUsed.tail();
					cacheEntry = new MonochromeImageCacheEntry(row, evicted.row);
					this.entries[evicted.index] = null;
					evicted.unlink();
				}
				this.entries[row] = cacheEntry;
			} else {
				this.invalid = false;
			}
			this.recentlyUsed.addHead(cacheEntry);
			return this.pixels[cacheEntry.row];
		}
	}
}
