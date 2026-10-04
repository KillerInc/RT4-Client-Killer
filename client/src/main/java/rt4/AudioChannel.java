package rt4;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

import java.awt.Component;

@OriginalClass("client!vh")
public class AudioChannel {

	@OriginalMember(owner = "client!na", name = "w", descriptor = "Z")
	public static boolean stereo;
	@OriginalMember(owner = "client!va", name = "O", descriptor = "I")
	public static int threadPriority;
	@OriginalMember(owner = "client!em", name = "x", descriptor = "Lclient!cj;")
	public static AudioThread thread;
	@OriginalMember(owner = "client!dh", name = "h", descriptor = "I")
	public static int sampleRate;

	@OriginalMember(owner = "client!vh", name = "h", descriptor = "Lclient!qb;")
	private PcmStream stream;

	@OriginalMember(owner = "client!vh", name = "n", descriptor = "[I")
	public int[] samples;

	@OriginalMember(owner = "client!vh", name = "D", descriptor = "I")
	private int bufferAdjustment;

	@OriginalMember(owner = "client!vh", name = "H", descriptor = "I")
	public int sampleRate2;

	@OriginalMember(owner = "client!vh", name = "K", descriptor = "I")
	public int bufferCapacity;

	@OriginalMember(owner = "client!vh", name = "a", descriptor = "I")
	private final int maxActiveChannels = 32;

	@OriginalMember(owner = "client!vh", name = "f", descriptor = "J")
	private long time = MonotonicClock.currentTimeMillis();

	@OriginalMember(owner = "client!vh", name = "w", descriptor = "[Lclient!qb;")
	private final PcmStream[] priorityBucketHeads = new PcmStream[8];

	@OriginalMember(owner = "client!vh", name = "x", descriptor = "I")
	private int consumedSamples = 0;

	@OriginalMember(owner = "client!vh", name = "v", descriptor = "J")
	private long calculateConsumptionAt = 0L;

	@OriginalMember(owner = "client!vh", name = "E", descriptor = "I")
	private int priorityUpdateCountdown = 0;

	@OriginalMember(owner = "client!vh", name = "A", descriptor = "Z")
	private boolean skipConsumptionCheck = true;

	@OriginalMember(owner = "client!vh", name = "z", descriptor = "[Lclient!qb;")
	private final PcmStream[] priorityBucketTails = new PcmStream[8];

	@OriginalMember(owner = "client!vh", name = "y", descriptor = "J")
	private long closeUntil = 0L;

	@OriginalMember(owner = "client!vh", name = "G", descriptor = "I")
	private int prevConsumedSamples = 0;

	@OriginalMember(owner = "client!vh", name = "C", descriptor = "I")
	private int prevBufferSize = 0;

	@OriginalMember(owner = "client!dc", name = "a", descriptor = "(IIIZ)V")
	public static void init(@OriginalArg(3) boolean stereo) {
		threadPriority = 2;
		AudioChannel.stereo = stereo;
		sampleRate = GlobalConfig.AUDIO_SAMPLE_RATE;
	}

	@OriginalMember(owner = "client!id", name = "a", descriptor = "(ILsignlink!ll;Ljava/awt/Component;II)Lclient!vh;")
	public static AudioChannel create(@OriginalArg(0) int bufferSize, @OriginalArg(1) SignLink signLink, @OriginalArg(2) Component component, @OriginalArg(3) int channelId) {
		if (sampleRate == 0) {
			throw new IllegalStateException();
		}
		try {
			@Pc(33) AudioChannel audioChannel = new JavaAudioChannel();
			audioChannel.sampleRate2 = bufferSize;
			audioChannel.samples = new int[(stereo ? 2 : 1) * 256];
			audioChannel.init(component);
			audioChannel.bufferCapacity = (bufferSize & -1024) + 1024;
			if (audioChannel.bufferCapacity > 16384) {
				audioChannel.bufferCapacity = 16384;
			}
			audioChannel.open(audioChannel.bufferCapacity);
			if (threadPriority > 0 && thread == null) {
				thread = new AudioThread();
				thread.signLink = signLink;
				signLink.startThread(threadPriority, thread);
			}
			if (thread != null) {
				if (thread.channels[channelId] != null) {
					throw new IllegalArgumentException();
				}
				thread.channels[channelId] = audioChannel;
			}
			return audioChannel;
		} catch (@Pc(109) Throwable ex1) {
			ex1.printStackTrace();
			try {
				@Pc(120) SignLinkAudioChannel fallbackChannel = new SignLinkAudioChannel(signLink, channelId);
				fallbackChannel.samples = new int[(stereo ? 2 : 1) * 256];
				fallbackChannel.sampleRate2 = bufferSize;
				fallbackChannel.init(component);
				fallbackChannel.bufferCapacity = 16384;
				fallbackChannel.open(fallbackChannel.bufferCapacity);
				if (threadPriority > 0 && thread == null) {
					thread = new AudioThread();
					thread.signLink = signLink;
					signLink.startThread(threadPriority, thread);
				}
				if (thread != null) {
					if (thread.channels[channelId] != null) {
						throw new IllegalArgumentException();
					}
					thread.channels[channelId] = fallbackChannel;
				}
				return fallbackChannel;
			} catch (@Pc(186) Throwable ex2) {
				ex2.printStackTrace();
				return new AudioChannel();
			}
		}
	}

	@OriginalMember(owner = "client!nd", name = "a", descriptor = "(ZLclient!qb;)V")
	public static void setInactive(@OriginalArg(1) PcmStream stream) {
		if (stream.sound != null) {
			stream.sound.position = 0;
		}
		stream.active = false;
		for (@Pc(14) PcmStream subStream = stream.firstSubStream(); subStream != null; subStream = stream.nextSubStream()) {
			setInactive(subStream);
		}
	}

	@OriginalMember(owner = "client!vh", name = "a", descriptor = "()V")
	protected void write() throws Exception {
	}

	@OriginalMember(owner = "client!vh", name = "a", descriptor = "(I)V")
	public void open(@OriginalArg(0) int capacity) throws Exception {
	}

	@OriginalMember(owner = "client!vh", name = "b", descriptor = "()V")
	protected void close() throws Exception {
	}

	@OriginalMember(owner = "client!vh", name = "a", descriptor = "([II)V")
	private void read(@OriginalArg(0) int[] buf) {
		@Pc(1) short bufSize = 256;
		if (stereo) {
			bufSize = 512;
		}
		ArrayUtils.clear(buf, 0, bufSize);
		this.priorityUpdateCountdown -= 256;
		if (this.stream != null && this.priorityUpdateCountdown <= 0) {
			this.priorityUpdateCountdown += sampleRate >> 4;
			setInactive(this.stream);
			this.insertStreamByPriority(this.stream, this.stream.getEffectiveVolume());
			@Pc(45) int activeCount = 0;
			@Pc(47) int bucketMask = 255;
			@Pc(49) int priority = 7;
			fillChannels:
			while (bucketMask != 0) {
				@Pc(57) int bucketIdx;
				@Pc(62) int positionThreshold;
				if (priority < 0) {
					bucketIdx = priority & 0x3;
					positionThreshold = -(priority >> 2);
				} else {
					bucketIdx = priority;
					positionThreshold = 0;
				}
				for (@Pc(73) int mask = bucketMask >>> bucketIdx & 0x11111111; mask != 0; mask >>>= 0x4) {
					if ((mask & 0x1) != 0) {
						bucketMask &= ~(0x1 << bucketIdx);
						@Pc(91) PcmStream prev = null;
						@Pc(96) PcmStream current = this.priorityBucketHeads[bucketIdx];
						processStream:
						while (true) {
							while (true) {
								if (current == null) {
									break processStream;
								}
								@Pc(101) Sound sound = current.sound;
								if (sound == null || sound.position <= positionThreshold) {
									current.active = true;
									@Pc(125) int channelCount = current.getActiveChannelCount();
									activeCount += channelCount;
									if (sound != null) {
										sound.position += channelCount;
									}
									if (activeCount >= this.maxActiveChannels) {
										break fillChannels;
									}
									@Pc(145) PcmStream subStream = current.firstSubStream();
									if (subStream != null) {
										@Pc(150) int parentPriority = current.effectivePriority;
										while (subStream != null) {
											this.insertStreamByPriority(subStream, parentPriority * subStream.getEffectiveVolume() >> 8);
											subStream = current.nextSubStream();
										}
									}
									@Pc(169) PcmStream next = current.nextPriorityStream;
									current.nextPriorityStream = null;
									if (prev == null) {
										this.priorityBucketHeads[bucketIdx] = next;
									} else {
										prev.nextPriorityStream = next;
									}
									if (next == null) {
										this.priorityBucketTails[bucketIdx] = prev;
									}
									current = next;
								} else {
									bucketMask |= 0x1 << bucketIdx;
									prev = current;
									current = current.nextPriorityStream;
								}
							}
						}
					}
					bucketIdx += 4;
					positionThreshold++;
				}
				priority--;
			}
			for (priority = 0; priority < 8; priority++) {
				@Pc(212) PcmStream head = this.priorityBucketHeads[priority];
				this.priorityBucketHeads[priority] = this.priorityBucketTails[priority] = null;
				while (head != null) {
					@Pc(227) PcmStream next = head.nextPriorityStream;
					head.nextPriorityStream = null;
					head = next;
				}
			}
		}
		if (this.priorityUpdateCountdown < 0) {
			this.priorityUpdateCountdown = 0;
		}
		if (this.stream != null) {
			this.stream.read(buf, 0, 256);
		}
		this.time = MonotonicClock.currentTimeMillis();
	}

	@OriginalMember(owner = "client!vh", name = "a", descriptor = "(B)V")
	public final synchronized void loop() {
		if (this.samples == null) {
			return;
		}
		@Pc(14) long now = MonotonicClock.currentTimeMillis();
		try {
			if (this.closeUntil != 0L) {
				if (now < this.closeUntil) {
					return;
				}
				this.open(this.bufferCapacity);
				this.skipConsumptionCheck = true;
				this.closeUntil = 0L;
			}
			@Pc(38) int bufferSize = this.getBufferSize();
			if (this.consumedSamples < this.prevBufferSize - bufferSize) {
				this.consumedSamples = this.prevBufferSize - bufferSize;
			}
			@Pc(65) int targetBufferSize = this.sampleRate2 + this.bufferAdjustment;
			if (targetBufferSize + 256 > 16384) {
				targetBufferSize = 16128;
			}
			if (this.bufferCapacity < targetBufferSize + 256) {
				this.bufferCapacity += 1024;
				if (this.bufferCapacity > 16384) {
					this.bufferCapacity = 16384;
				}
				this.flush();
				bufferSize = 0;
				this.open(this.bufferCapacity);
				if (this.bufferCapacity < targetBufferSize + 256) {
					targetBufferSize = this.bufferCapacity - 256;
					this.bufferAdjustment = targetBufferSize - this.sampleRate2;
				}
				this.skipConsumptionCheck = true;
			}
			while (targetBufferSize > bufferSize) {
				bufferSize += 256;
				this.read(this.samples);
				this.write();
			}
			if (now > this.calculateConsumptionAt) {
				if (this.skipConsumptionCheck) {
					this.skipConsumptionCheck = false;
				} else if (this.consumedSamples == 0 && this.prevConsumedSamples == 0) {
					this.flush();
					this.closeUntil = now + 2000L;
					return;
				} else {
					this.bufferAdjustment = Math.min(this.prevConsumedSamples, this.consumedSamples);
					this.prevConsumedSamples = this.consumedSamples;
				}
				this.calculateConsumptionAt = now + 2000L;
				this.consumedSamples = 0;
			}
			this.prevBufferSize = bufferSize;
		} catch (@Pc(202) Exception ex) {
			ex.printStackTrace();
			this.flush();
			this.closeUntil = now + 2000L;
		}
		try {
			if (now > this.time + 500000L) {
				now = this.time;
			}
			while (now > this.time + 5000L) {
				this.skip();
				this.time += 256000 / sampleRate;
			}
		} catch (@Pc(247) Exception ex) {
			ex.printStackTrace();
			this.time = now;
		}
	}

	@OriginalMember(owner = "client!vh", name = "a", descriptor = "(ILclient!qb;)V")
	public final synchronized void setStream(@OriginalArg(1) PcmStream stream) {
		this.stream = stream;
	}

	@OriginalMember(owner = "client!vh", name = "a", descriptor = "(Lclient!qb;IB)V")
	private void insertStreamByPriority(@OriginalArg(0) PcmStream stream, @OriginalArg(1) int priority) {
		@Pc(16) int bucket = priority >> 5;
		@Pc(21) PcmStream tail = this.priorityBucketTails[bucket];
		if (tail == null) {
			this.priorityBucketHeads[bucket] = stream;
		} else {
			tail.nextPriorityStream = stream;
		}
		this.priorityBucketTails[bucket] = stream;
		stream.effectivePriority = priority;
	}

	@OriginalMember(owner = "client!vh", name = "c", descriptor = "()I")
	protected int getBufferSize() throws Exception {
		return this.bufferCapacity;
	}

	@OriginalMember(owner = "client!vh", name = "b", descriptor = "(B)V")
	public final synchronized void forceClose() {
		this.skipConsumptionCheck = true;
		try {
			this.close();
		} catch (@Pc(10) Exception ex) {
			ex.printStackTrace();
			this.flush();
			this.closeUntil = MonotonicClock.currentTimeMillis() + 2000L;
		}
	}

	@OriginalMember(owner = "client!vh", name = "b", descriptor = "(I)V")
	public final void pauseConsumptionCheck() {
		this.skipConsumptionCheck = true;
	}

	@OriginalMember(owner = "client!vh", name = "d", descriptor = "()V")
	protected void flush() {
	}

	@OriginalMember(owner = "client!vh", name = "a", descriptor = "(II)V")
	private void skip() {
		this.priorityUpdateCountdown -= 256;
		if (this.priorityUpdateCountdown < 0) {
			this.priorityUpdateCountdown = 0;
		}
		if (this.stream != null) {
			this.stream.skip(256);
		}
	}

	@OriginalMember(owner = "client!vh", name = "a", descriptor = "(Z)V")
	public final synchronized void quit() {
		if (thread != null) {
			@Pc(6) boolean allClosed = true;
			for (@Pc(8) int i = 0; i < 2; i++) {
				if (thread.channels[i] == this) {
					thread.channels[i] = null;
				}
				if (thread.channels[i] != null) {
					allClosed = false;
				}
			}
			if (allClosed) {
				thread.stop = true;
				while (thread.running) {
					ThreadUtils.sleep(50L);
				}
				thread = null;
			}
		}
		this.flush();
		this.samples = null;
	}

	@OriginalMember(owner = "client!vh", name = "a", descriptor = "(Ljava/awt/Component;)V")
	public void init(@OriginalArg(0) Component component) throws Exception {
	}
}
