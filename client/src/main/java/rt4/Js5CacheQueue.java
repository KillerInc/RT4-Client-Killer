package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!k")
public final class Js5CacheQueue implements Runnable {

	@OriginalMember(owner = "client!k", name = "q", descriptor = "Lclient!ce;")
	private final SecondaryLinkedList queue = new SecondaryLinkedList();

	@OriginalMember(owner = "client!k", name = "s", descriptor = "I")
	public int size = 0;

	@OriginalMember(owner = "client!k", name = "w", descriptor = "Z")
	private boolean stop = false;

	@OriginalMember(owner = "client!k", name = "v", descriptor = "Ljava/lang/Thread;")
	private Thread thread;

	@OriginalMember(owner = "client!k", name = "<init>", descriptor = "()V")
	public Js5CacheQueue() {
		@Pc(20) PrivilegedRequest request = GameShell.signLink.startThread(5, this);
		while (request.status == 0) {
			ThreadUtils.sleep(10L);
		}
		if (request.status == 2) {
			throw new RuntimeException();
		}
		this.thread = (Thread) request.result;
	}

	@OriginalMember(owner = "client!k", name = "a", descriptor = "(Lclient!c;I)V")
	private void enqueue(@OriginalArg(0) Js5CacheRequest request) {
		synchronized (this.queue) {
			this.queue.addTail(request);
			this.size++;
			this.queue.notifyAll();
		}
	}

	@OriginalMember(owner = "client!k", name = "a", descriptor = "(I)V")
	public final void quit() {
		this.stop = true;
		synchronized (this.queue) {
			this.queue.notifyAll();
		}
		try {
			this.thread.join();
		} catch (@Pc(23) InterruptedException ignored) {
		}
		this.thread = null;
	}

	@OriginalMember(owner = "client!k", name = "a", descriptor = "(Lclient!ge;I[BI)Lclient!c;")
	public final Js5CacheRequest write(@OriginalArg(0) Cache cache, @OriginalArg(2) byte[] data, @OriginalArg(3) int group) {
		@Pc(7) Js5CacheRequest request = new Js5CacheRequest();
		request.data = data;
		request.urgent = false;
		request.secondaryKey = group;
		request.cache = cache;
		request.type = 2;
		this.enqueue(request);
		return request;
	}

	@OriginalMember(owner = "client!k", name = "a", descriptor = "(IILclient!ge;)Lclient!c;")
	public final Js5CacheRequest read(@OriginalArg(0) int group, @OriginalArg(2) Cache cache) {
		@Pc(7) Js5CacheRequest request = new Js5CacheRequest();
		request.cache = cache;
		request.type = 3;
		request.urgent = false;
		request.secondaryKey = group;
		this.enqueue(request);
		return request;
	}

	@OriginalMember(owner = "client!k", name = "a", descriptor = "(Lclient!ge;BI)Lclient!c;")
	public final Js5CacheRequest readSynchronous(@OriginalArg(0) Cache cache, @OriginalArg(2) int group) {
		@Pc(9) Js5CacheRequest request = new Js5CacheRequest();
		request.type = 1;
		synchronized (this.queue) {
			@Pc(31) Js5CacheRequest queued = (Js5CacheRequest) this.queue.head();
			while (true) {
				if (queued == null) {
					break;
				}
				if (queued.secondaryKey == (long) group && queued.cache == cache && queued.type == 2) {
					request.data = queued.data;
					request.incomplete = false;
					return request;
				}
				queued = (Js5CacheRequest) this.queue.next();
			}
		}
		request.data = cache.read(group);
		request.incomplete = false;
		request.urgent = true;
		return request;
	}

	@OriginalMember(owner = "client!k", name = "run", descriptor = "()V")
	@Override
	public final void run() {
		while (!this.stop) {
			@Pc(19) Js5CacheRequest request;
			synchronized (this.queue) {
				request = (Js5CacheRequest) this.queue.removeHead();
				if (request == null) {
					try {
						this.queue.wait();
					} catch (@Pc(35) InterruptedException ignored) {
					}
					continue;
				}
				this.size--;
			}
			try {
				if (request.type == 2) {
					request.cache.write((int) request.secondaryKey, request.data.length, request.data);
				} else if (request.type == 3) {
					request.data = request.cache.read((int) request.secondaryKey);
				}
			} catch (@Pc(83) Exception ex) {
				TracingException.report(null, ex);
			}
			request.incomplete = false;
		}
	}
}
