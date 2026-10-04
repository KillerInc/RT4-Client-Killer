package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!al")
public final class Js5MasterIndex {

	@OriginalMember(owner = "client!al", name = "a", descriptor = "Lclient!wa;")
	private Buffer buffer;

	@OriginalMember(owner = "client!al", name = "s", descriptor = "[Lclient!bg;")
	private Js5CachedResourceProvider[] resourceProviders;

	@OriginalMember(owner = "client!al", name = "j", descriptor = "Lclient!k;")
	private final Js5CacheQueue cacheQueue;

	@OriginalMember(owner = "client!al", name = "f", descriptor = "Lclient!jb;")
	private final Js5NetQueue netQueue;

	@OriginalMember(owner = "client!al", name = "c", descriptor = "Lclient!pm;")
	private Js5NetRequest request;

	@OriginalMember(owner = "client!al", name = "<init>", descriptor = "(Lclient!jb;Lclient!k;)V")
	public Js5MasterIndex(@OriginalArg(0) Js5NetQueue netQueue, @OriginalArg(1) Js5CacheQueue cacheQueue) {
		this.cacheQueue = cacheQueue;
		this.netQueue = netQueue;
		if (!this.netQueue.isUrgentRequestQueueFull()) {
			this.request = this.netQueue.read(255, (byte) 0, 255, true);
		}
	}

	@OriginalMember(owner = "client!al", name = "b", descriptor = "(I)Z")
	public final boolean isReady() {
		if (this.buffer != null) {
			return true;
		}
		if (this.request == null) {
			if (this.netQueue.isUrgentRequestQueueFull()) {
				return false;
			}
			this.request = this.netQueue.read(255, (byte) 0, 255, true);
		}
		if (this.request.incomplete) {
			return false;
		} else {
			this.buffer = new Buffer(this.request.getData());
			this.resourceProviders = new Js5CachedResourceProvider[(this.buffer.data.length - 5) / 8];
			return true;
		}
	}

	@OriginalMember(owner = "client!al", name = "a", descriptor = "(B)V")
	public final void processResourceProviders() {
		if (this.resourceProviders == null) {
			return;
		}
		@Pc(13) int i;
		for (i = 0; i < this.resourceProviders.length; i++) {
			if (this.resourceProviders[i] != null) {
				this.resourceProviders[i].processPrefetchQueue();
			}
		}
		for (i = 0; i < this.resourceProviders.length; i++) {
			if (this.resourceProviders[i] != null) {
				this.resourceProviders[i].loop();
			}
		}
	}

	@OriginalMember(owner = "client!al", name = "a", descriptor = "(IILclient!ge;Lclient!ge;)Lclient!bg;")
	public final Js5CachedResourceProvider getResourceProvider(@OriginalArg(1) int archive, @OriginalArg(2) Cache cache, @OriginalArg(3) Cache masterCache) {
		return this.createResourceProvider(masterCache, archive, cache);
	}

	@OriginalMember(owner = "client!al", name = "a", descriptor = "(Lclient!ge;IIZLclient!ge;)Lclient!bg;")
	private Js5CachedResourceProvider createResourceProvider(@OriginalArg(0) Cache masterCache, @OriginalArg(2) int archive, @OriginalArg(4) Cache cache) {
		if (this.buffer == null) {
			throw new RuntimeException();
		}
		this.buffer.offset = archive * 8 + 5;
		if (this.buffer.data.length <= this.buffer.offset) {
			throw new RuntimeException();
		} else if (this.resourceProviders[archive] == null) {
			@Pc(56) int expectedChecksum = this.buffer.g4();
			@Pc(61) int expectedVersion = this.buffer.g4();
			@Pc(75) Js5CachedResourceProvider provider = new Js5CachedResourceProvider(archive, masterCache, cache, this.netQueue, this.cacheQueue, expectedChecksum, expectedVersion, true);
			this.resourceProviders[archive] = provider;
			return provider;
		} else {
			return this.resourceProviders[archive];
		}
	}
}
