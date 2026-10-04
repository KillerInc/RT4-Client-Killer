package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

import java.util.zip.CRC32;

@OriginalClass("client!bg")
public final class Js5CachedResourceProvider extends Js5ResourceProvider {

	@OriginalMember(owner = "client!fn", name = "X", descriptor = "Ljava/util/zip/CRC32;")
	public static final CRC32 crc32 = new CRC32();

	@OriginalMember(owner = "client!bg", name = "m", descriptor = "Lclient!ii;")
	private Js5Index index;

	@OriginalMember(owner = "client!bg", name = "w", descriptor = "[B")
	private byte[] groupStatus;

	@OriginalMember(owner = "client!bg", name = "T", descriptor = "Z")
	private boolean prefetchAll;

	@OriginalMember(owner = "client!bg", name = "u", descriptor = "I")
	private int verifiedGroups = 0;

	@OriginalMember(owner = "client!bg", name = "p", descriptor = "Lclient!sc;")
	private final HashTable requests = new HashTable(16);

	@OriginalMember(owner = "client!bg", name = "S", descriptor = "I")
	private int group = 0;

	@OriginalMember(owner = "client!bg", name = "R", descriptor = "Lclient!ih;")
	private final LinkedList prefetchQueue = new LinkedList();

	@OriginalMember(owner = "client!bg", name = "V", descriptor = "J")
	private long orphanCheckTime = 0L;

	@OriginalMember(owner = "client!bg", name = "L", descriptor = "I")
	private final int archive;

	@OriginalMember(owner = "client!bg", name = "F", descriptor = "Lclient!ge;")
	private final Cache cache;

	@OriginalMember(owner = "client!bg", name = "Q", descriptor = "Z")
	private boolean verifyAll;

	@OriginalMember(owner = "client!bg", name = "U", descriptor = "Lclient!ih;")
	private LinkedList groupQueue;

	@OriginalMember(owner = "client!bg", name = "J", descriptor = "Lclient!k;")
	private final Js5CacheQueue cacheQueue;

	@OriginalMember(owner = "client!bg", name = "H", descriptor = "I")
	private final int expectedChecksum;

	@OriginalMember(owner = "client!bg", name = "W", descriptor = "Z")
	private final boolean discardOrphans;

	@OriginalMember(owner = "client!bg", name = "k", descriptor = "Lclient!ge;")
	private final Cache masterCache;

	@OriginalMember(owner = "client!bg", name = "D", descriptor = "Lclient!jb;")
	private final Js5NetQueue netQueue;

	@OriginalMember(owner = "client!bg", name = "t", descriptor = "I")
	private final int expectedVersion;

	@OriginalMember(owner = "client!bg", name = "x", descriptor = "Lclient!il;")
	private Js5Request indexRequest;

	@OriginalMember(owner = "client!bg", name = "<init>", descriptor = "(ILclient!ge;Lclient!ge;Lclient!jb;Lclient!k;IIZ)V")
	public Js5CachedResourceProvider(@OriginalArg(0) int archive, @OriginalArg(1) Cache cache, @OriginalArg(2) Cache masterCache, @OriginalArg(3) Js5NetQueue netQueue, @OriginalArg(4) Js5CacheQueue cacheQueue, @OriginalArg(5) int expectedChecksum, @OriginalArg(6) int expectedVersion, @OriginalArg(7) boolean discardOrphans) {
		this.archive = archive;
		this.cache = cache;
		if (this.cache == null) {
			this.verifyAll = false;
		} else {
			this.verifyAll = true;
			this.groupQueue = new LinkedList();
		}
		this.cacheQueue = cacheQueue;
		this.expectedChecksum = expectedChecksum;
		this.discardOrphans = discardOrphans;
		this.masterCache = masterCache;
		this.netQueue = netQueue;
		this.expectedVersion = expectedVersion;
		if (this.masterCache != null) {
			this.indexRequest = this.cacheQueue.readSynchronous(this.masterCache, this.archive);
		}
	}

	@OriginalMember(owner = "client!bg", name = "a", descriptor = "(II)V")
	@Override
	public final void prefetchGroup(@OriginalArg(0) int group) {
		if (this.cache == null) {
			return;
		}
		@Pc(15) Node node;
		for (node = this.prefetchQueue.head(); node != null; node = this.prefetchQueue.next()) {
			if (node.key == (long) group) {
				return;
			}
		}
		node = new Node();
		node.key = group;
		this.prefetchQueue.addTail(node);
	}

	@OriginalMember(owner = "client!bg", name = "b", descriptor = "(I)Lclient!ii;")
	@Override
	public final Js5Index fetchIndex() {
		if (this.index != null) {
			return this.index;
		}
		if (this.indexRequest == null) {
			if (this.netQueue.isUrgentRequestQueueFull()) {
				return null;
			}
			this.indexRequest = this.netQueue.read(255, (byte) 0, this.archive, true);
		}
		if (this.indexRequest.incomplete) {
			return null;
		}
		@Pc(52) byte[] data = this.indexRequest.getData();
		if (this.indexRequest instanceof Js5CacheRequest) {
			try {
				if (data == null) {
					throw new RuntimeException();
				}
				this.index = new Js5Index(data, this.expectedChecksum);
				if (this.expectedVersion != this.index.version) {
					throw new RuntimeException();
				}
			} catch (@Pc(88) RuntimeException ex) {
				this.index = null;
				if (this.netQueue.isUrgentRequestQueueFull()) {
					this.indexRequest = null;
				} else {
					this.indexRequest = this.netQueue.read(255, (byte) 0, this.archive, true);
				}
				return null;
			}
		} else {
			try {
				if (data == null) {
					throw new RuntimeException();
				}
				this.index = new Js5Index(data, this.expectedChecksum);
			} catch (@Pc(131) RuntimeException ignored) {
				this.netQueue.rekey();
				this.index = null;
				if (this.netQueue.isUrgentRequestQueueFull()) {
					this.indexRequest = null;
				} else {
					this.indexRequest = this.netQueue.read(255, (byte) 0, this.archive, true);
				}
				return null;
			}
			if (this.masterCache != null) {
				this.cacheQueue.write(this.masterCache, data, this.archive);
			}
		}
		if (this.cache != null) {
			this.groupStatus = new byte[this.index.capacity];
			this.verifiedGroups = 0;
		}
		this.indexRequest = null;
		return this.index;
	}

	@OriginalMember(owner = "client!bg", name = "a", descriptor = "(Z)V")
	public final void prefetchAll() {
		if (this.cache != null) {
			this.prefetchAll = true;
			if (this.groupQueue == null) {
				this.groupQueue = new LinkedList();
			}
		}
	}

	@OriginalMember(owner = "client!bg", name = "c", descriptor = "(I)I")
	public final int getVerifiedGroups() {
		return this.verifiedGroups;
	}

	@OriginalMember(owner = "client!bg", name = "d", descriptor = "(I)I")
	public final int getTotalVerifiedGroups() {
		if (this.index == null) {
			return 0;
		} else if (this.verifyAll) {
			@Pc(25) Node node = this.groupQueue.head();
			return node == null ? 0 : (int) node.key;
		} else {
			return this.index.size;
		}
	}

	@OriginalMember(owner = "client!bg", name = "c", descriptor = "(Z)V")
	public final void loop() {
		if (this.groupQueue != null) {
			if (this.fetchIndex() == null) {
				return;
			}
			@Pc(32) boolean complete;
			@Pc(37) Node node;
			@Pc(43) int groupId;
			if (this.verifyAll) {
				complete = true;
				for (node = this.groupQueue.head(); node != null; node = this.groupQueue.next()) {
					groupId = (int) node.key;
					if (this.groupStatus[groupId] == 0) {
						this.fetchGroupInner(1, groupId);
					}
					if (this.groupStatus[groupId] == 0) {
						complete = false;
					} else {
						node.unlink();
					}
				}
				while (this.index.groupSizes.length > this.group) {
					if (this.index.groupSizes[this.group] == 0) {
						this.group++;
					} else {
						if (this.cacheQueue.size >= 250) {
							complete = false;
							break;
						}
						if (this.groupStatus[this.group] == 0) {
							this.fetchGroupInner(1, this.group);
						}
						if (this.groupStatus[this.group] == 0) {
							complete = false;
							node = new Node();
							node.key = this.group;
							this.groupQueue.addTail(node);
						}
						this.group++;
					}
				}
				if (complete) {
					this.verifyAll = false;
					this.group = 0;
				}
			} else if (this.prefetchAll) {
				complete = true;
				for (node = this.groupQueue.head(); node != null; node = this.groupQueue.next()) {
					groupId = (int) node.key;
					if (this.groupStatus[groupId] != 1) {
						this.fetchGroupInner(2, groupId);
					}
					if (this.groupStatus[groupId] == 1) {
						node.unlink();
					} else {
						complete = false;
					}
				}
				while (this.index.groupSizes.length > this.group) {
					if (this.index.groupSizes[this.group] == 0) {
						this.group++;
					} else {
						if (this.netQueue.isPrefetchRequestQueueFull()) {
							complete = false;
							break;
						}
						if (this.groupStatus[this.group] != 1) {
							this.fetchGroupInner(2, this.group);
						}
						if (this.groupStatus[this.group] != 1) {
							node = new Node();
							node.key = this.group;
							this.groupQueue.addTail(node);
							complete = false;
						}
						this.group++;
					}
				}
				if (complete) {
					this.group = 0;
					this.prefetchAll = false;
				}
			} else {
				this.groupQueue = null;
			}
		}
		if (!this.discardOrphans || this.orphanCheckTime > MonotonicClock.currentTimeMillis()) {
			return;
		}
		for (@Pc(331) Js5Request request = (Js5Request) this.requests.head(); request != null; request = (Js5Request) this.requests.next()) {
			if (!request.incomplete) {
				if (request.orphan) {
					if (!request.urgent) {
						throw new RuntimeException();
					}
					request.unlink();
				} else {
					request.orphan = true;
				}
			}
		}
		this.orphanCheckTime = MonotonicClock.currentTimeMillis() + 1000L;
	}

	@OriginalMember(owner = "client!bg", name = "b", descriptor = "(II)I")
	@Override
	public final int getPercentageComplete(@OriginalArg(0) int group) {
		@Pc(15) Js5Request request = (Js5Request) this.requests.get(group);
		return request == null ? 0 : request.getPercentageComplete();
	}

	@OriginalMember(owner = "client!bg", name = "b", descriptor = "(B)I")
	public final int getIndexSize() {
		return this.index == null ? 0 : this.index.size;
	}

	@OriginalMember(owner = "client!bg", name = "a", descriptor = "(III)Lclient!il;")
	private Js5Request fetchGroupInner(@OriginalArg(0) int mode, @OriginalArg(1) int group) {
		@Pc(13) Js5Request request = (Js5Request) this.requests.get(group);
		if (request != null && mode == 0 && !request.urgent && request.incomplete) {
			request.unlink();
			request = null;
		}
		if (request == null) {
			if (mode == 0) {
				if (this.cache == null || this.groupStatus[group] == -1) {
					if (this.netQueue.isUrgentRequestQueueFull()) {
						return null;
					}
					request = this.netQueue.read(this.archive, (byte) 2, group, true);
				} else {
					request = this.cacheQueue.readSynchronous(this.cache, group);
				}
			} else if (mode == 1) {
				if (this.cache == null) {
					throw new RuntimeException();
				}
				request = this.cacheQueue.read(group, this.cache);
			} else if (mode == 2) {
				if (this.cache == null) {
					throw new RuntimeException();
				}
				if (this.groupStatus[group] != -1) {
					throw new RuntimeException();
				}
				if (this.netQueue.isPrefetchRequestQueueFull()) {
					return null;
				}
				request = this.netQueue.read(this.archive, (byte) 2, group, false);
			} else {
				throw new RuntimeException();
			}
			this.requests.put(request, group);
		}
		if (request.incomplete) {
			return null;
		}
		@Pc(161) byte[] data = request.getData();
		@Pc(199) int crcValue;
		@Pc(252) Js5NetRequest retryRequest;
		if (!(request instanceof Js5CacheRequest)) {
			try {
				if (data == null || data.length <= 2) {
					throw new RuntimeException();
				}
				crc32.reset();
				crc32.update(data, 0, data.length - 2);
				crcValue = (int) crc32.getValue();
				if (this.index.groupChecksums[group] != crcValue) {
					throw new RuntimeException();
				}
				this.netQueue.errors = 0;
				this.netQueue.response = 0;
			} catch (@Pc(225) RuntimeException ex) {
				this.netQueue.rekey();
				request.unlink();
				if (request.urgent && !this.netQueue.isUrgentRequestQueueFull()) {
					retryRequest = this.netQueue.read(this.archive, (byte) 2, group, true);
					this.requests.put(retryRequest, group);
				}
				return null;
			}
			data[data.length - 2] = (byte) (this.index.groupVersions[group] >>> 8);
			data[data.length - 1] = (byte) this.index.groupVersions[group];
			if (this.cache != null) {
				this.cacheQueue.write(this.cache, data, group);
				if (this.groupStatus[group] != 1) {
					this.verifiedGroups++;
					this.groupStatus[group] = 1;
				}
			}
			if (!request.urgent) {
				request.unlink();
			}
			return request;
		}
		try {
			if (data == null || data.length <= 2) {
				throw new RuntimeException();
			}
			crc32.reset();
			crc32.update(data, 0, data.length - 2);
			crcValue = (int) crc32.getValue();
			if (this.index.groupChecksums[group] != crcValue) {
				throw new RuntimeException();
			}
			@Pc(385) int version = ((data[data.length - 2] & 0xFF) << 8) + (data[data.length - 1] & 0xFF);
			if (version != (this.index.groupVersions[group] & 0xFFFF)) {
				throw new RuntimeException();
			}
			if (this.groupStatus[group] != 1) {
				if (this.groupStatus[group] != 0) {
				}
				this.verifiedGroups++;
				this.groupStatus[group] = 1;
			}
			if (!request.urgent) {
				request.unlink();
			}
			return request;
		} catch (@Pc(437) Exception ex) {
			this.groupStatus[group] = -1;
			request.unlink();
			if (request.urgent && !this.netQueue.isUrgentRequestQueueFull()) {
				retryRequest = this.netQueue.read(this.archive, (byte) 2, group, true);
				this.requests.put(retryRequest, group);
			}
			return null;
		}
	}

	@OriginalMember(owner = "client!bg", name = "e", descriptor = "(I)V")
	public final void processPrefetchQueue() {
		if (this.groupQueue == null || this.fetchIndex() == null) {
			return;
		}
		for (@Pc(21) Node node = this.prefetchQueue.head(); node != null; node = this.prefetchQueue.next()) {
			@Pc(28) int groupId = (int) node.key;
			if (groupId < 0 || this.index.capacity <= groupId || this.index.groupSizes[groupId] == 0) {
				node.unlink();
			} else {
				if (this.groupStatus[groupId] == 0) {
					this.fetchGroupInner(1, groupId);
				}
				if (this.groupStatus[groupId] == -1) {
					this.fetchGroupInner(2, groupId);
				}
				if (this.groupStatus[groupId] == 1) {
					node.unlink();
				}
			}
		}
	}

	@OriginalMember(owner = "client!bg", name = "f", descriptor = "(I)I")
	public final int getIndexPercentageComplete() {
		if (this.fetchIndex() == null) {
			return this.indexRequest == null ? 0 : this.indexRequest.getPercentageComplete();
		} else {
			return 100;
		}
	}

	@OriginalMember(owner = "client!bg", name = "c", descriptor = "(II)[B")
	@Override
	public final byte[] fetchGroup(@OriginalArg(0) int group) {
		@Pc(9) Js5Request request = this.fetchGroupInner(0, group);
		if (request == null) {
			return null;
		} else {
			@Pc(17) byte[] data = request.getData();
			request.unlink();
			return data;
		}
	}
}
