package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

import java.io.IOException;

@OriginalClass("client!jb")
public final class Js5NetQueue {

	@OriginalMember(owner = "client!jb", name = "A", descriptor = "J")
	private long previousLoop;

	@OriginalMember(owner = "client!jb", name = "B", descriptor = "Lclient!ma;")
	private BufferedSocket socket;

	@OriginalMember(owner = "client!jb", name = "C", descriptor = "I")
	private int latency;

	@OriginalMember(owner = "client!jb", name = "J", descriptor = "Lclient!pm;")
	private Js5NetRequest current;

	@OriginalMember(owner = "client!jb", name = "a", descriptor = "Lclient!ce;")
	private final SecondaryLinkedList pendingUrgentRequests = new SecondaryLinkedList();

	@OriginalMember(owner = "client!jb", name = "q", descriptor = "Lclient!ce;")
	private final SecondaryLinkedList inFlightUrgentRequests = new SecondaryLinkedList();

	@OriginalMember(owner = "client!jb", name = "v", descriptor = "Lclient!ce;")
	private final SecondaryLinkedList pendingPrefetchRequests = new SecondaryLinkedList();

	@OriginalMember(owner = "client!jb", name = "z", descriptor = "Lclient!ce;")
	private final SecondaryLinkedList inFlightPrefetchRequests = new SecondaryLinkedList();

	@OriginalMember(owner = "client!jb", name = "E", descriptor = "Lclient!wa;")
	private final Buffer outBuffer = new Buffer(4);

	@OriginalMember(owner = "client!jb", name = "G", descriptor = "B")
	private byte encryptionKey = 0;

	@OriginalMember(owner = "client!jb", name = "I", descriptor = "I")
	public volatile int errors = 0;

	@OriginalMember(owner = "client!jb", name = "H", descriptor = "I")
	public volatile int response = 0;

	@OriginalMember(owner = "client!jb", name = "F", descriptor = "Lclient!wa;")
	private final Buffer inBuffer = new Buffer(8);

	@OriginalMember(owner = "client!jb", name = "a", descriptor = "(I)Z")
	public final boolean isPrefetchRequestQueueFull() {
		return this.getPrefetchRequestCount() >= 20;
	}

	@OriginalMember(owner = "client!jb", name = "b", descriptor = "(B)Z")
	public final boolean loop() {
		@Pc(19) int elapsed;
		if (this.socket != null) {
			@Pc(12) long currentTime = MonotonicClock.currentTimeMillis();
			elapsed = (int) (currentTime - this.previousLoop);
			this.previousLoop = currentTime;
			if (elapsed > 200) {
				elapsed = 200;
			}
			this.latency += elapsed;
			if (this.latency > 30000) {
				try {
					this.socket.close();
				} catch (@Pc(43) Exception ignored) {
				}
				this.socket = null;
			}
		}
		if (this.socket == null) {
			return this.getUrgentRequestCount() == 0 && this.getPrefetchRequestCount() == 0;
		}
		try {
			this.socket.checkError();
			@Pc(75) Js5NetRequest request;
			for (request = (Js5NetRequest) this.pendingUrgentRequests.head(); request != null; request = (Js5NetRequest) this.pendingUrgentRequests.next()) {
				this.outBuffer.offset = 0;
				this.outBuffer.p1(1);
				this.outBuffer.p3((int) request.secondaryKey);
				this.socket.write(this.outBuffer.data, 4);
				this.inFlightUrgentRequests.addTail(request);
			}
			for (request = (Js5NetRequest) this.pendingPrefetchRequests.head(); request != null; request = (Js5NetRequest) this.pendingPrefetchRequests.next()) {
				this.outBuffer.offset = 0;
				this.outBuffer.p1(0);
				this.outBuffer.p3((int) request.secondaryKey);
				this.socket.write(this.outBuffer.data, 4);
				this.inFlightPrefetchRequests.addTail(request);
			}
			for (@Pc(172) int iteration = 0; iteration < 100; iteration++) {
				elapsed = this.socket.available();
				if (elapsed < 0) {
					throw new IOException();
				}
				if (elapsed == 0) {
					break;
				}
				this.latency = 0;
				@Pc(196) byte headerBytesNeeded = 0;
				if (this.current == null) {
					headerBytesNeeded = 8;
				} else if (this.current.blockPosition == 0) {
					headerBytesNeeded = 1;
				}
				@Pc(228) int dataLen;
				@Pc(235) int readLen;
				@Pc(283) int j;
				if (headerBytesNeeded <= 0) {
					dataLen = this.current.buffer.data.length - this.current.trailerLen;
					readLen = 512 - this.current.blockPosition;
					if (readLen > dataLen - this.current.buffer.offset) {
						readLen = dataLen - this.current.buffer.offset;
					}
					if (readLen > elapsed) {
						readLen = elapsed;
					}
					this.socket.read(this.current.buffer.offset, readLen, this.current.buffer.data);
					if (this.encryptionKey != 0) {
						for (j = 0; j < readLen; j++) {
							this.current.buffer.data[this.current.buffer.offset + j] = (byte) (this.current.buffer.data[this.current.buffer.offset + j] ^ this.encryptionKey);
						}
					}
					this.current.blockPosition += readLen;
					this.current.buffer.offset += readLen;
					if (this.current.buffer.offset == dataLen) {
						this.current.unlinkSecondary();
						this.current.incomplete = false;
						this.current = null;
					} else if (this.current.blockPosition == 512) {
						this.current.blockPosition = 0;
					}
				} else {
					dataLen = headerBytesNeeded - this.inBuffer.offset;
					if (elapsed < dataLen) {
						dataLen = elapsed;
					}
					this.socket.read(this.inBuffer.offset, dataLen, this.inBuffer.data);
					if (this.encryptionKey != 0) {
						for (readLen = 0; readLen < dataLen; readLen++) {
							this.inBuffer.data[readLen + this.inBuffer.offset] ^= this.encryptionKey;
						}
					}
					this.inBuffer.offset += dataLen;
					if (this.inBuffer.offset >= headerBytesNeeded) {
						if (this.current == null) {
							this.inBuffer.offset = 0;
							readLen = this.inBuffer.g1();
							j = this.inBuffer.g2();
							@Pc(471) int headerByte = this.inBuffer.g1();
							@Pc(476) int compressedLen = this.inBuffer.g4();
							@Pc(480) int compressionType = headerByte & 0x7F;
							@Pc(491) boolean isPrefetch = (headerByte & 0x80) != 0;
							@Pc(501) long requestKey = (readLen << 16) + j;
							@Pc(509) Js5NetRequest matched;
							if (isPrefetch) {
								for (matched = (Js5NetRequest) this.inFlightPrefetchRequests.head(); matched != null && matched.secondaryKey != requestKey; matched = (Js5NetRequest) this.inFlightPrefetchRequests.next()) {
								}
							} else {
								for (matched = (Js5NetRequest) this.inFlightUrgentRequests.head(); matched != null && requestKey != matched.secondaryKey; matched = (Js5NetRequest) this.inFlightUrgentRequests.next()) {
								}
							}
							if (matched == null) {
								throw new IOException();
							}
							@Pc(568) int headerLen = compressionType == 0 ? 5 : 9;
							this.current = matched;
							this.current.buffer = new Buffer(compressedLen + headerLen + this.current.trailerLen);
							this.current.buffer.p1(compressionType);
							this.current.buffer.p4(compressedLen);
							this.current.blockPosition = 8;
							this.inBuffer.offset = 0;
						} else if (this.current.blockPosition != 0) {
							throw new IOException();
						} else if (this.inBuffer.data[0] == -1) {
							this.current.blockPosition = 1;
							this.inBuffer.offset = 0;
						} else {
							this.current = null;
						}
					}
				}
			}
			return true;
		} catch (@Pc(644) IOException ex) {
			try {
				this.socket.close();
			} catch (@Pc(650) Exception ignored) {
			}
			this.response = -2;
			this.errors++;
			this.socket = null;
			return this.getUrgentRequestCount() == 0 && this.getPrefetchRequestCount() == 0;
		}
	}

	@OriginalMember(owner = "client!jb", name = "a", descriptor = "(Z)V")
	public final void drop() {
		if (this.socket == null) {
			return;
		}
		try {
			this.outBuffer.offset = 0;
			this.outBuffer.p1(7);
			this.outBuffer.p3(0);
			this.socket.write(this.outBuffer.data, 4);
		} catch (@Pc(39) IOException ex) {
			try {
				this.socket.close();
			} catch (@Pc(45) Exception ignored) {
			}
			this.errors++;
			this.response = -2;
			this.socket = null;
		}
	}

	@OriginalMember(owner = "client!jb", name = "b", descriptor = "(I)I")
	private int getPrefetchRequestCount() {
		return this.pendingPrefetchRequests.size() + this.inFlightPrefetchRequests.size();
	}

	@OriginalMember(owner = "client!jb", name = "a", descriptor = "(ZZ)V")
	public final void writeLoggedIn(@OriginalArg(0) boolean loggedIn) {
		if (this.socket == null) {
			return;
		}
		try {
			this.outBuffer.offset = 0;
			this.outBuffer.p1(loggedIn ? 2 : 3);
			this.outBuffer.p3(0);
			this.socket.write(this.outBuffer.data, 4);
		} catch (@Pc(42) IOException ex) {
			try {
				this.socket.close();
			} catch (@Pc(48) Exception ignored) {
			}
			this.errors++;
			this.response = -2;
			this.socket = null;
		}
	}

	@OriginalMember(owner = "client!jb", name = "c", descriptor = "(I)V")
	public final void breakConnection() {
		if (this.socket != null) {
			this.socket.breakConnection();
		}
	}

	@OriginalMember(owner = "client!jb", name = "a", descriptor = "(ZLclient!ma;I)V")
	public final void start(@OriginalArg(0) boolean loggedIn, @OriginalArg(1) BufferedSocket socket) {
		if (this.socket != null) {
			try {
				this.socket.close();
			} catch (@Pc(14) Exception ignored) {
			}
			this.socket = null;
		}
		this.socket = socket;
		this.sendPing();
		this.writeLoggedIn(loggedIn);
		this.inBuffer.offset = 0;
		this.current = null;
		while (true) {
			@Pc(44) Js5NetRequest pending = (Js5NetRequest) this.inFlightUrgentRequests.removeHead();
			if (pending == null) {
				while (true) {
					pending = (Js5NetRequest) this.inFlightPrefetchRequests.removeHead();
					if (pending == null) {
						if (this.encryptionKey != 0) {
							try {
								this.outBuffer.offset = 0;
								this.outBuffer.p1(4);
								this.outBuffer.p1(this.encryptionKey);
								this.outBuffer.p2(0);
								this.socket.write(this.outBuffer.data, 4);
							} catch (@Pc(107) IOException ex) {
								try {
									this.socket.close();
								} catch (@Pc(113) Exception ignored) {
								}
								this.response = -2;
								this.errors++;
								this.socket = null;
							}
						}
						this.latency = 0;
						this.previousLoop = MonotonicClock.currentTimeMillis();
						return;
					}
					this.pendingPrefetchRequests.addTail(pending);
				}
			}
			this.pendingUrgentRequests.addTail(pending);
		}
	}

	@OriginalMember(owner = "client!jb", name = "c", descriptor = "(B)Z")
	public final boolean isUrgentRequestQueueFull() {
		return this.getUrgentRequestCount() >= 20;
	}

	@OriginalMember(owner = "client!jb", name = "d", descriptor = "(B)V")
	public final void rekey() {
		try {
			this.socket.close();
		} catch (@Pc(17) Exception ignored) {
		}
		this.response = -1;
		this.encryptionKey = (byte) (Math.random() * 255.0D + 1.0D);
		this.socket = null;
		this.errors++;
	}

	@OriginalMember(owner = "client!jb", name = "d", descriptor = "(I)I")
	public final int getUrgentRequestCount() {
		return this.pendingUrgentRequests.size() + this.inFlightUrgentRequests.size();
	}

	@OriginalMember(owner = "client!jb", name = "b", descriptor = "(Z)V")
	public final void quit() {
		if (this.socket != null) {
			this.socket.close();
		}
	}

	@OriginalMember(owner = "client!jb", name = "a", descriptor = "(IIBIZ)Lclient!pm;")
	public final Js5NetRequest read(@OriginalArg(1) int archive, @OriginalArg(2) byte trailerLen, @OriginalArg(3) int group, @OriginalArg(4) boolean urgent) {
		@Pc(7) Js5NetRequest request = new Js5NetRequest();
		@Pc(14) long requestKey = group + ((long) archive << 16);
		request.urgent = urgent;
		request.secondaryKey = requestKey;
		request.trailerLen = trailerLen;
		if (urgent) {
			if (this.getUrgentRequestCount() >= 20) {
				throw new RuntimeException();
			}
			this.pendingUrgentRequests.addTail(request);
		} else if (this.getPrefetchRequestCount() < 20) {
			this.pendingPrefetchRequests.addTail(request);
		} else {
			throw new RuntimeException();
		}
		return request;
	}

	@OriginalMember(owner = "client!jb", name = "e", descriptor = "(B)V")
	private void sendPing() {
		if (this.socket == null) {
			return;
		}
		try {
			this.outBuffer.offset = 0;
			this.outBuffer.p1(6);
			this.outBuffer.p3(3);
			this.socket.write(this.outBuffer.data, 4);
		} catch (@Pc(37) IOException ex) {
			try {
				this.socket.close();
			} catch (@Pc(43) Exception ignored) {
			}
			this.errors++;
			this.socket = null;
			this.response = -2;
		}
	}
}
